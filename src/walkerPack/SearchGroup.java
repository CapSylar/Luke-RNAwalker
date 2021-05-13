package walkerPack;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchGroup
{
    ArrayList<Block> collection;
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
            collection.add( new Block( sequences.get(i) ));
        }

        recalculateAll();
    }

    private void recalculateAll()
    {
        calculateCollectionStatistics();

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
            int[] fromUpThere = this.collection.get(i).getSequenceAsSet().nucleotides ;

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
        Block QueryAsBlock = new Block(query);
        QueryAsBlock.process();

        for ( int i = 0 ; i < collection.size() ; ++i )
        {
            TimeNSimilarity returned = measure.calculateSimilarity( QueryAsBlock , collection.get(i) );
            collection.get(i).lastSimilarityValue = returned.similarity;
            cumulativeTime += returned.time;
        }

        sortCollection();
        return cumulativeTime;
    }

    public void filterSelection(SelectionOperator operator )
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
        String builder = "Search Group{ last query used: " + (this.lastQuery != null ? this.lastQuery.getSequence() : "none") + "\n";

        for ( int i = 0 ; i < collection.size() ; ++i )
        {
            builder += "rank "+ i + " : " + collection.get(i).NativeSequence.getSequence() + " " + collection.get(i).lastSimilarityValue + '\n' ;
        }

        return builder + "} \n";
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

    // TODO: ugly ass thing, reconsider this decision, whole things feels like a hack!
    public class Block
    {
        // Block houses the sequence, the set and the vector corresponding to the sequence
        // internal representation used

        private Sequence NativeSequence;
        private SetSequence SequenceAsSet ;
        private VectorSequence SequenceAsVector;
        public double lastSimilarityValue; // used for sorting or trimming the current collection after applying some operation

        public Block(Sequence nativeSequence )
        {
            NativeSequence = nativeSequence;
            SequenceAsSet = new SetSequence(NativeSequence.getSequence());
            lastSimilarityValue = -1; // not yet computed
        }

        public void process()
        {
            if ( useIDF )
                SequenceAsVector = new VectorSequence(NativeSequence.getSequence() , collection.size() , termCollectionCount , useTF );
            else
                SequenceAsVector = new VectorSequence(NativeSequence.getSequence());
        }

        public SetSequence getSequenceAsSet()
        {
            return SequenceAsSet;
        }

        public VectorSequence getSequenceAsVector()
        {
            return SequenceAsVector;
        }

        public Sequence getSequence()
        {
            return NativeSequence;
        }

        public long getSetPreProcessingTime ()
        {
            return getSequenceAsSet().getPreprocessTime();
        }

        public long getVectorPreProcessingTime ()
        {
            return getSequenceAsVector().getPreprocessTime();
        }
    }
}
