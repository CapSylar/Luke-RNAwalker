package walkerPack;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Iterator;

public class Sequence
{
    private String sequence;
    private String accession ;
    private String description ;

    public String getAccession()
    {
        return accession;
    }

    public void setAccession(String accession)
    {
        this.accession = accession;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getSequence()
    {
        return sequence;
    }

    public void setSequence(String sequence)
    {
        this.sequence = sequence;
    }

    public Sequence(String sequence, String accession, String description)
    {
        this.sequence = sequence.toUpperCase();
        this.accession = accession;
        this.description = description;
    }

    public Sequence( String sequence )
    {
        this.sequence = sequence.toUpperCase();
        this.accession = "none";
        this.description = "none";
    }

    public static Sequence fromXML ( String sequencePath ) throws InternalApplicationException
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().parse(new File(sequencePath));

            // node refers to "RNA" Element

            String Accession = doc.getElementsByTagName("accession").item(0).getTextContent().strip() ;
            String Description = doc.getElementsByTagName("description").item(0).getTextContent().strip();

            String Sequence = doc.getElementsByTagName("sequence").item(0).getTextContent().strip();
            // why parse sequence length , reconsider ?

            return new Sequence( Sequence , Accession , Description ) ;
        }
        catch ( SAXException | ParserConfigurationException | IOException | NullPointerException excp )
        {
            throw new InternalApplicationException("Invalid Path Specified!");
        }
    }

    public static double[] countTermFreq ( String sequence )
    {
        // not only counts term frequency, but also takes into account the ambiguity symbols

        double tf[] = new double [9];

        for ( int i = 0 ; i < sequence.length() ; ++i )
        {
            ++tf[EquivalenceManager.NuclMapper(sequence.charAt(i))] ;
        }

        // do another pass
        String all = "GAUCRMSVN" ;
        for ( int i = 0 ; i < all.length(); ++i )
        {
            char current = all.charAt(i);
            HashSet<Character> currentSet =  EquivalenceManager.NuclSets.get(current);
            int size = currentSet.size();

            if ( size == 1 || tf[EquivalenceManager.NuclMapper(current)] == 0 )
                continue;

            Iterator itr = currentSet.iterator();

            while ( itr.hasNext() )
            {
                char c = (( Character )itr.next());
                tf[EquivalenceManager.NuclMapper(c)] += ((1.0/size) * tf[EquivalenceManager.NuclMapper(current)]);
            }
        }

        return tf;
    }

    public static Sequence fromXML ( Node RNAroot ) throws InternalApplicationException
    {
        // TODO: warning this assumes that children are in the order specified by the document and that not TEXT is located between them

        NodeList children = RNAroot.getChildNodes();
        return new Sequence( children.item(3).getTextContent() , children.item(0).getTextContent() ,
                children.item(2).getTextContent() );
    }

    public Element toXML ( Document doc )
    {
        Element localRoot = doc.createElement("RNA");

        Element accession = doc.createElement("accession") ;
        accession.setTextContent(this.accession);

        Element description = doc.createElement("description");
        description.setTextContent(this.description);

        Element length = doc.createElement("length");
        length.setTextContent("" + this.sequence.length());

        Element sequence = doc.createElement("sequence");
        sequence.setTextContent(this.sequence);

        // attach all elements above to RNA

        localRoot.appendChild(accession);
        localRoot.appendChild(description);
        localRoot.appendChild(length);
        localRoot.appendChild(sequence);

        return localRoot;
    }

    public String getMD5()
    {
        String HashText = null ;

        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5") ;
            byte[] messageDigest = md.digest(this.sequence.getBytes());

            BigInteger no = new BigInteger(1,messageDigest);
            HashText = no.toString(16) ; // to hex
        }
        catch ( Exception excp )
        {
            excp.printStackTrace();
        }

        return HashText;
    }

    public boolean EqualToHash( String MD5Hash )
    {
        // compare the current string to the hash
        return getMD5().equals(MD5Hash) ;
    }

    @Override
    public String toString()
    {
        return this.sequence;
    }
}
