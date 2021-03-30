package walkerPack;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

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
        this.sequence = sequence;
        this.accession = accession;
        this.description = description;
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

}
