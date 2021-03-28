package walkerPack;

import org.w3c.dom.*;

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

    public static Sequence fromXML ( Document doc ) //TODO: change this to accept node not doc
    {
        // node refers to "RNA" Element

        String Accession = doc.getElementsByTagName("accession").item(0).getTextContent().strip() ;
        String Description = doc.getElementsByTagName("description").item(0).getTextContent().strip();

        String Sequence = doc.getElementsByTagName("sequence").item(0).getTextContent().strip();
        // why parse sequence length , reconsider ?

        return new Sequence( Sequence , Accession , Description ) ;
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

}
