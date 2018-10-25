package pvz2018;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;


public class GardenDOM{

    private static GardenDOM gardenDOM;
    private GardenDOM(){};
    public static GardenDOM getInstance(){
        if(gardenDOM == null){
            return new GardenDOM();
        }
        return gardenDOM;
    }

    
   
    //public static void main(String[] args){
    public void createNewXML() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder;
        Document doc;
       
        try {
            dbBuilder = dbFactory.newDocumentBuilder();
            doc = dbBuilder.newDocument();
            
            //add root
            Element rootElement = doc.createElement("garden");
            doc.appendChild(rootElement);

                //add root-status
                Element gameStatus = doc.createElement("status");
                rootElement.appendChild(gameStatus);

                    //add root-status-money
                    Element money = doc.createElement("money");
                    money.appendChild(doc.createTextNode("0"));
                    gameStatus.appendChild(money);
                    //add root-status-suns
                    Element suns = doc.createElement("suns");
                    suns.appendChild(doc.createTextNode("0"));
                    gameStatus.appendChild(suns);
                    //add root-status-CD
                    Element cd = doc.createElement("coolDown");
                    gameStatus.appendChild(cd);
                        //add root-status-cd-sunflower
                        Element sunflower = doc.createElement("sunflower");
                        sunflower.appendChild(doc.createTextNode("5"));
                        cd.appendChild(sunflower);
                        //add root-status-cd-peashooter
                        Element peashooter = doc.createElement("peashooter");
                        peashooter.appendChild(doc.createTextNode("5"));
                        cd.appendChild(peashooter);

            //add root-static
            Element staticElement = doc.createElement("static");
            rootElement.appendChild(staticElement);

            //add root-static
            Element movableElement = doc.createElement("movable");
            rootElement.appendChild(movableElement);


            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(".\\garden.xml"));
            transformer.transform(source,result);

            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);



        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
		}
    }




    private void addStatic(Plant plant){
        File inputFile = new File("garden.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder;
        try {
            dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(inputFile);
            Node nodeStatic = doc.getElementsByTagName("static").item(0);

            Element newPlant = doc.createElement(plant.getName());
            Attr id = doc.createAttribute("id");
            id.setValue(Integer.toString(plant.hashCode()));
            newPlant.setIdAttributeNode(id, true);
                Element row = doc.createElement("row");
                row.appendChild(doc.createTextNode(Integer.toString(plant.getRow())));
                newPlant.appendChild(row);
                Element col = doc.createElement("col");
                col.appendChild(doc.createTextNode(Integer.toString(plant.getCol())));
                newPlant.appendChild(col);
            
            

            



        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    public void ModifyStatic(){
        File inputFile = new File("garden.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder;
        try {
            dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(inputFile);
            Node nodeStatic = doc.getElementsByTagName("static").item(0);



        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
        

    }


}