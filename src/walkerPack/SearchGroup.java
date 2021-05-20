package walkerPack;
import SearchGroupOperations.SelectionOperator;
import SearchGroupOperations.TimeNSimilarity;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchGroup
{
    public ArrayList<SequenceBlock> collection;
    int termCollectionCount[] = new int[9];

    private boolean useTF ;
    private boolean useIDF ;

    // DEBUG INFO

    private Sequence lastQuery = null ;

    /**
     * SearchGroup is essentially a component holding a collection of sorted RNA Sequences ( Sequences class )
     * Operations such as KNN or range can be applied to the SearchGroup removing some elements
     * Other operations such as sort by similarity using Cosine... will reorder the sequences inside the SearchGroup
     */

    public SearchGroup ( ArrayList<Sequence> sequences , boolean useTF , boolean useIDF )
    {
        this.collection = new ArrayList<>();
        this.useIDF = useIDF;
        this.useTF = useTF;


        for ( int i = 0 ; i < sequences.size() ; ++i )
        {
            collection.add( new SequenceBlock( sequences.get(i) ));
        }

        recalculateAll();
    }

    private void recalculateAll()
    {
        calculateCollectionStatistics();

        if ( useIDF )
            for ( int i = 0 ; i < this.collection.size() ; ++i )
            {
                collection.get(i).process( collection.size() , termCollectionCount , useTF ); // gets every Block ready by calculating set and vector for the sequence
            }
        else
            for ( int i = 0 ; i < this.collection.size() ; ++i )
            {
                collection.get(i).process(); // gets every Block ready by calculating set and vector for the sequence
            }
    }

    private void calculateCollectionStatistics ()
    {
        // this is used solely to calculate IDF
        // we calculate an array with similar order as set and vector but with number of sequences containing this nucleotide

        Arrays.fill( this.termCollectionCount , 0 );

        for ( int i = 0 ; i < this.collection.size() ; ++i )
        {
            double[] fromUpThere = this.collection.get(i).getSequenceAsSet().nucleotides ;

            for ( int j = 0; j < termCollectionCount.length ; ++j )
                this.termCollectionCount[j] += (fromUpThere[j] > 0 ? 1 : 0) ;
        }
    }

    /*
        rankUsingSimilarity return the time it took to compute all computation namely for every first:
        preprocessing time for all entities and then compute time for similarity measure
    */

    public long rankUsingSimilarity ( Sequence query , SimilarityMeasure measure )
    {
        long cumulativeTime = 0 ;
        this.lastQuery = query;

        SequenceBlock queryAsSequenceBlock = new SequenceBlock(query);
        queryAsSequenceBlock.process(); // always use just TF for query

        for ( int i = 0 ; i < collection.size() ; ++i )
        {
            TimeNSimilarity returned = measure.calculateSimilarity(queryAsSequenceBlock, collection.get(i) );
            collection.get(i).lastSimilarityValue = returned.similarity;
            cumulativeTime += returned.time;
        }

        sortCollection();
        return cumulativeTime;
    }

    public void filterSelection( SelectionOperator operator )
    {
        operator.ApplyOperator(this); // apply range on ourselves

        recalculateAll();
    }

    private void sortCollection()
    {
        // sort collection with respect to lastSimilarityValue

        this.collection.sort((o1, o2) ->
        {
            if ( o1.lastSimilarityValue > o2.lastSimilarityValue )
                return -1;
            else if ( o1.lastSimilarityValue == o2.lastSimilarityValue )
                    return 0;
            else
                return 1;
        });
    }


    @Override
    public String toString()
    {
        // list the ranked collection with last similarity info
        String builder = "";
        DecimalFormat currentFormat = new DecimalFormat("##.####") ;

        for ( int i = 0 ; i < collection.size() ; ++i )
        {
            String padding = "";
            // bye bye performance

            if ( this.lastQuery != null && collection.get(i).getSequence().toString().contains(this.lastQuery.toString()) )
                padding = "===> " ;

            builder += padding + "rank "+ i + "   Sim: " + currentFormat.format(collection.get(i).lastSimilarityValue ) +
                    "   " + collection.get(i).getSequence().getSequence() + " " + '\n' ;
        }

        return builder;
    }

    public String getPrintableStatistics()
    {
        String builder = "Search Group number of Sequences that have nucleotide { \n" ;
        char chars[] = { 'G' ,'A' ,'U' ,'C' ,'R' ,'M' ,'S' ,'V' ,'N' };

        for ( int i = 0;  i < this.termCollectionCount.length ; ++i )
        {
            builder += " " + chars[i] + " count: " + this.termCollectionCount[i] + " \n";
        }
        builder += " }\n";

        return builder;
    }

    public long getAllSetPreProcessingTime()
    {
        long sum = 0;

        for ( int i = 0 ; i < this.collection.size() ; ++i )
        {
            sum += this.collection.get(i).getSetPreProcessingTime();
        }

        return sum;
    }

    public long getAllVectorPreProcessingTime()
    {
        long sum = 0;
        for ( int i = 0 ; i < this.collection.size() ; ++i )
        {
            sum += this.collection.get(i).getVectorPreProcessingTime();
        }

        return sum;
    }

    public static SearchGroup fromXML ( String filepath , boolean useTF , boolean useIDF )
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);

            Document document = factory.newDocumentBuilder().parse(new File(filepath));
            Node root = document.getElementsByTagName("RNADataBank").item(0);
            NodeList sequences = root.getChildNodes();

            ArrayList<Sequence> toReturn = new ArrayList<>() ;
            for ( int i = 0 ; i < sequences.getLength() ; ++i )
            {
                toReturn.add( Sequence.fromXML(sequences.item(i)));
            }

            return new SearchGroup(toReturn , useTF , useIDF );
        }
        catch ( Exception exc )
        {
            System.out.println(exc);
        }

        return null;
    }
}
