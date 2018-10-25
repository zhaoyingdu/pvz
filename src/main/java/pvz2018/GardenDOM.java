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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


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
            newPlant.setAttributeNode(id);
            newPlant.setIdAttributeNode(id, true);
                Element row = doc.createElement("row");
                row.appendChild(doc.createTextNode(Integer.toString(plant.getRow())));
                newPlant.appendChild(row);
                Element col = doc.createElement("col");
                col.appendChild(doc.createTextNode(Integer.toString(plant.getCol())));
                newPlant.appendChild(col);
            if(nodeStatic.getNodeType()==Node.ELEMENT_NODE){
                Element staticElement = (Element)nodeStatic;
                staticElement.appendChild(newPlant);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("./garden.xml"));
            transformer.transform(source, result);
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
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



    public void ModifyStatic(){
        File inputFile = new File("./garden.xml");

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


    public void deleteStatic(Plant plant){
        String id = Integer.toString(plant.hashCode());
        
        File inputFile = new File("./garden.xml");

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);

            Node staticNode = doc.getElementsByTagName("static").item(0);
            NodeList childNodes = staticNode.getChildNodes();
            for(int i = 0;i<childNodes.getLength();i++){
                Node node = childNodes.item(i);
                if(node.getNodeType()==Node.ELEMENT_NODE){
                    Element nodeElem = (Element)node;
                    if(nodeElem.getAttribute("id").equals(id)) staticNode.removeChild(node);
                }
            }
            
            /*Element plantToRemove = doc.getElementById(id);
            if(plantToRemove == null) {
                System.out.println("remove failed. id:"+id);
                return;
            } 
            Node parentNode = plantToRemove.getParentNode();
            if(parentNode.getNodeType()==Node.ELEMENT_NODE && parentNode.getNodeName()=="static"){
                parentNode.removeChild(plantToRemove);
            }else{
                System.out.println("not a child of node static");
            }*/



            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(inputFile);
            transformer.transform(source, result);
            //NodeList statics = staticNode.getChildNodes();
            
            /*for(int i = 0;i<statics.getLength();i++){
                Node node = statics.item(i);
                
                
                String nodeID = 
            }*/

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
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



    private ArrayList<String> parseStatus(Node node){
        ArrayList<String> ret = new ArrayList<>();
        
        NodeList nodeList = node.getChildNodes();
        for(int i = 0;i<nodeList.getLength();i++){
            if(nodeList.item(i).getNodeName().equals("coolDown")){
                NodeList coolDownList = nodeList.item(i).getChildNodes();
                for(int j = 0;j<coolDownList.getLength();j++){
                    ret.add(coolDownList.item(j).getNodeName()+" "+
                            coolDownList.item(j).getTextContent());
                }
            }else{
                ret.add(nodeList.item(i).getNodeName()+" "+
                    nodeList.item(i).getTextContent());
            }
        }
        
        return ret;
    }

    private ArrayList<String> parseStatic(Node node){
        ArrayList<String> ret = new ArrayList<>();
        
        NodeList staticList = node.getChildNodes();


        //NodeList staticList = childs.item(i).getChildNodes();
        char type;
        char row='-';
        char col='-';
        for(int i = 0;i<staticList.getLength();i++){
            type = staticList.item(i).getNodeName().charAt(0);
            //+staticList.item(i));
            NodeList rowAndCol = staticList.item(i).getChildNodes();
            for(int j=0;j<rowAndCol.getLength();j++){
                if(rowAndCol.item(j).getNodeName()=="row"){
                    row = rowAndCol.item(j).getTextContent().charAt(0);
                }
                if(rowAndCol.item(j).getNodeName()=="col"){
                    col = rowAndCol.item(j).getTextContent().charAt(0);
                }
            }

            ret.add(type+" "+row+" "+col);
        }
        return ret;
    }

    public ArrayList<String> parseGardenXML(){
        ArrayList<String> ret = new ArrayList<>();
        File inputFile = new File("./garden.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder;
        try {
            dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            Node root = doc.getDocumentElement();

            NodeList childs = root.getChildNodes();

            for(int i = 0;i<childs.getLength();i++){
                if(childs.item(i).getNodeName()=="status"){
                    ret.addAll(parseStatus(childs.item(i)));
                }
                if(childs.item(i).getNodeName()=="static"){
                    ret.addAll(parseStatic(childs.item(i)));
                }
                if(childs.item(i).getNodeName()=="movable"){
                    //need implementation
                }

            }
            

            
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
        
        
        
        return ret;
    }


    public static void main (String[] args){
        GardenDOM garden = GardenDOM.getInstance();
        garden.createNewXML();
        Sunflower sf = new Sunflower(2,3);
        Peashooter ps = new Peashooter(5,3);
        garden.addStatic(sf);
        garden.addStatic(ps);

        //garden.deleteStatic(sf);

        ArrayList<String> str = garden.parseGardenXML();
        for(String i: str){
            System.out.println(" ");
            System.out.println("_"+i+"_");

        }
    }


}