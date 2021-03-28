package walkerPack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


interface EditOperation
{
   int apply( StringBuilder string , int offset ) ; // apply the operation on the string passed, offset to accoutn for prev operations
   EditOperation getReverse () ; // get the reverse operation

   Element toXML ( Document doc ) ; // convert to xml , returns an element that we can then attach

   static EditOperation fromXML ( Node operationInXML ) // parse the operation from XML
   {
      String editType = operationInXML.getNodeName();
      EditOperation toReturn ;

      switch (editType)
      {
         case "Insert":
         {
            char gotNucl = operationInXML.getChildNodes().item(0).getTextContent().charAt(0);
            int insertIndex = Integer.parseInt(operationInXML.getChildNodes().item(1).getTextContent());

            toReturn = new InsertOperation( insertIndex , gotNucl );
         }
            break;

         case "Delete":
         {
            int deleteIndex = Integer.parseInt(operationInXML.getChildNodes().item(0).getTextContent());

            toReturn = new DeleteOperation(deleteIndex);
         }
            break;

         case "Update":
         {
            char gotNucl = operationInXML.getChildNodes().item(0).getTextContent().charAt(0);
            int updateIndex = Integer.parseInt(operationInXML.getChildNodes().item(1).getTextContent());

            toReturn = new UpdateOperation( updateIndex , gotNucl );
         }
            break;

         default:
            return null; // bad idea probably
      }

      return toReturn;
   }
}
