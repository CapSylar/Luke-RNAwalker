package walkerPack;

public class testing {
    public static void main(String[] args){
        SetSequence set1 = new SetSequence("SASASASASAS");
        SetSequence set2 = new SetSequence("GUGUGUGUGUG");
        JaccardCoefficient t = new JaccardCoefficient();
        System.out.println(t.getSimilarity(set1,set2));
    }
}
