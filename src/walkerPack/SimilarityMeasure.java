package walkerPack;

import SearchGroupOperations.SED;
import SearchGroupOperations.SetSimilarityMethod;
import SearchGroupOperations.TimeNSimilarity;
import SearchGroupOperations.VectorSimilarityMethod;

public abstract class SimilarityMeasure extends SearchGroupOperation
{
    public enum SimilarityType
    {
        Vector,
        Set,
        SED
    };

    public TimeNSimilarity calculateSimilarity(SequenceBlock query , SequenceBlock seq)
    {
        if ( this instanceof VectorSimilarityMethod)
        {
            return ((VectorSimilarityMethod) this).getSimilarity( query.getSequenceAsVector() , seq.getSequenceAsVector() );
        }
        else if ( this instanceof SetSimilarityMethod)
        {
            return ((SetSimilarityMethod) this).getSimilarity( query.getSequenceAsSet() , seq.getSequenceAsSet() );
        }
        else if ( this instanceof SED)
        {
            return ((SED) this).getSimilarity( query.getSequence() , seq.getSequence() );
        }
        else // could not happen
            return null; // TODO: handle this case in a better way. Murphy's law: "Anything that can go wrong will go wrong"
    }

    public abstract SimilarityType getType();
}
