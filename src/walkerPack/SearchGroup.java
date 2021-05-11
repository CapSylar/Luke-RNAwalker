package walkerPack;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class SearchGroup
{
    ArrayList<Block> collection;

    // DEBUG INFO

    private Sequence lastQuery = null ;
    /**
     * SearchGroup is essentially a component holding a collection of sorted RNA Sequences ( Sequences class )
     * Operations such as KNN or range can be applied to the SearchGroup removing some elements
     * Other operations such as sort by similarity using Cosine... will reorder the sequences inside the SearchGroup
     */

    public SearchGroup ( ArrayList<Sequence> sequences )
    {
        this.collection = new ArrayList<>();

        for ( int i = 0 ; i < sequences.size() ; ++i )
        {
            collection.add( new Block( sequences.get(i) ));
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
        for ( int i = 0 ; i < collection.size() ; ++i )
        {
            TimeNSimilarity returned = measure.calculateSimilarity( new Block(query) , collection.get(i) ); //TOFIX: why new Block(query) every time ?
            collection.get(i).lastSimilarityValue = returned.similarity;
            cumulativeTime += returned.time;
        }

        sortCollection();

        return cumulativeTime;
    }

    public void filterSelection(SelectionOperator operator )
    {
        operator.ApplyOperator(this); // apply range on ourselves
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

    public long getSetPreProcessingTime ()
    {
        long sum = 0;
        for ( int i = 0 ; i < this.collection.size() ; ++i )
        {
            sum += this.collection.get(i).getSetPreProcessingTime();
        }

        return sum;
    }

    public long getVectorPreProcessingTime()
    {
        long sum = 0;
        for ( int i = 0 ; i < this.collection.size() ; ++i )
        {
            sum += this.collection.get(i).getVectorPreProcessingTime();
        }

        return sum;
    }


    public static SearchGroup fromXML ( String filepath )
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

            return new SearchGroup(toReturn);
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
            SequenceAsSet = null;
            SequenceAsVector = null;
            lastSimilarityValue = -1; // not yet computed
        }

        public SetSequence getSequenceAsSet()
        {
            if ( SequenceAsSet == null )
            {
                SequenceAsSet = new SetSequence(NativeSequence.getSequence());
            }

            return SequenceAsSet;
        }

        public VectorSequence getSequenceAsVector()
        {
            if ( SequenceAsVector == null )
            {
                SequenceAsVector = new VectorSequence(NativeSequence.getSequence()); // TODO: for now we only use TF, change it !!!
            }

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
