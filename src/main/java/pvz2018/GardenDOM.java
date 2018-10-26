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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class GardenDOM{

    private static GardenDOM gardenDOM;
    private GardenDOM(){};

    private ArrayList<String> localStatics = new ArrayList<>();
    private ArrayList<String> localMovables = new ArrayList<>();
    
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
                    //add root-status-game progress
                    Element gameProgress = doc.createElement("gameProgress");
                    gameProgress.appendChild(doc.createTextNode("0"));
                    gameStatus.appendChild(gameProgress);
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

    private void addElement(Map<String, String> objInfo){
        String parentTagName = objInfo.get("parentTagName");
        String tagName = objInfo.get("tagName");
        String hashCode = objInfo.get("id");
        String rowValue = objInfo.get("row");
        String colValue = objInfo.get("col");
        //String parentTagName = objInfo.get("parentTagName");

        File inputFile = new File("garden.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder;
        try {
            dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(inputFile);
            Node parentNode = doc.getElementsByTagName(parentTagName).item(0);
            Element newChild = doc.createElement(tagName);
            Attr id = doc.createAttribute("id");
            id.setValue(hashCode);
            newChild.setAttributeNode(id);
            newChild.setIdAttributeNode(id, true);
                Element row = doc.createElement("row");
                row.appendChild(doc.createTextNode(rowValue));
                newChild.appendChild(row);
                Element col = doc.createElement("col");
                col.appendChild(doc.createTextNode(colValue));
                newChild.appendChild(col);
            if(parentNode.getNodeType()==Node.ELEMENT_NODE){
                Element staticElement = (Element)parentNode;
                staticElement.appendChild(newChild);
            }
            switch(parentTagName){
                case "static":
                    localStatics.add(hashCode);
                    break;
                case "movable":
                    localMovables.add(hashCode);
                    break;

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


    /*private void addStatic(Plant plant){
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

            //update localStatics
            localStatics.add(Integer.toString(plant.hashCode()));

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
        
        
    }*/


    public void updateDOM(List<Movable> movables, List<Plant> plants){
        Map<String,Plant> gardenStatics = new HashMap<>();
        Map<String,Movable> gardenMovables = new HashMap<>();
        for(Plant p:plants){
            gardenStatics.put(Integer.toString(p.hashCode()),p);    
        }

        //remove digged
        for(String id:localStatics){
            if(!gardenStatics.containsKey(id)){
                deleteElement("static",id);
            }
        }
        for(String id:gardenStatics.keySet()){
            if(!localStatics.contains(id)){
                Map<String,String> newElement = new HashMap<>();
                newElement.put("parentTagName","static");
                newElement.put("tagName",gardenStatics.get(id).getName());
                newElement.put("id",id);
                newElement.put("row",Integer.toString(gardenStatics.get(id).getRow()));
                newElement.put("col",Integer.toString(gardenStatics.get(id).getCol()));
                addElement(newElement);
            }
        }

        //Map<String,Movable> gardenMovables = new HashMap<>();
        for(Movable p:movables){
            gardenMovables.put(Integer.toString(p.hashCode()),p);    
        }

        
        for(String id:localMovables){
            if(!gardenMovables.containsKey(id)){
                deleteElement("movable",id);
            }
        }
        for(String id:gardenMovables.keySet()){
            if(!localMovables.contains(id)){
                Map<String,String> newElement = new HashMap<>();
                newElement.put("parentTagName","movable");
                newElement.put("tagName",gardenMovables.get(id).getName());
                newElement.put("id",id);
                newElement.put("row",Integer.toString(gardenMovables.get(id).getRow()));
                newElement.put("col",Integer.toString(gardenMovables.get(id).getCol()));
                addElement(newElement);
            }
        }
    }

    /*public void updateStatic(List<Plant> plants){
        Map<String,Plant> gardenStatics = new HashMap<>();
        for(Plant p:plants){
            gardenStatics.put(Integer.toString(p.hashCode()),p);    
            //ids.add(Integer.toString(p.hashCode()));
        }

        //remove digged
        for(String id:localStatics){
            if(!gardenStatics.containsKey(id)){
                deleteStatic(id);
            }
        }
        for(String id:gardenStatics.keySet()){
            if(!localStatics.contains(id)){
                addStatic(gardenStatics.get(id));
            }
        }

    }*/



    
    /*public void testID(Plant p){
        File inputFile = new File("./garden.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder;
        //try {
            try {
            dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(inputFile);
            if(doc.getElementById(Integer.toString(p.hashCode())) == null){
                System.out.println("get by id failed");
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


    }*/

    

    public void updateStatus(int money,int gameProgress, int suns){
        File inputFile = new File("./garden.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder;
        try {
            dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(inputFile);
            Node nodeMoney = doc.getElementsByTagName("money").item(0);
            nodeMoney.setTextContent(Integer.toString(money));
            Node nodeSuns = doc.getElementsByTagName("suns").item(0);
            nodeSuns.setTextContent(Integer.toString(suns));
            Node nodeGameProgress = doc.getElementsByTagName("gameProgress").item(0);
            nodeGameProgress.setTextContent(Integer.toString(gameProgress));
           
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

    /*private void deleteStatic(String id){
        //String id = Integer.toString(plant.hashCode());
        
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

            Iterator<String> localStaticsItr = localStatics.iterator();
            while(localStaticsItr.hasNext()){
                String s = localStaticsItr.next();
                if(s==id){
                    localStaticsItr.remove();
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(inputFile);
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

    }*/

    private void deleteElement(String parentTagName, String id){
        //String id = Integer.toString(plant.hashCode());
        
        File inputFile = new File("./garden.xml");

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);

            Node parentNode = doc.getElementsByTagName(parentTagName).item(0);
            NodeList childNodes = parentNode.getChildNodes();
            for(int i = 0;i<childNodes.getLength();i++){
                Node node = childNodes.item(i);
                if(node.getNodeType()==Node.ELEMENT_NODE){
                    Element nodeElem = (Element)node;
                    if(nodeElem.getAttribute("id").equals(id)) parentNode.removeChild(node);
                }
            }


            switch(parentTagName){
                case "static":
                    Iterator<String> localStaticsItr = localStatics.iterator();
                    while(localStaticsItr.hasNext()){
                        String s = localStaticsItr.next();
                        if(s==id){
                            localStaticsItr.remove();
                        }
                    }
                    break;
                case "movable":
                    Iterator<String> localMovableItr = localMovables.iterator();
                    while(localMovableItr.hasNext()){
                        String s = localMovableItr.next();
                        if(s==id){
                            localMovableItr.remove();
                        }
                    }
                    break;
            }
            

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(inputFile);
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

    /*private ArrayList<String> parseStatic(Node node){
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
    }*/


    private ArrayList<String> parseStaticMovable(Node node){
        ArrayList<String> ret = new ArrayList<>();
        
        NodeList childList = node.getChildNodes();


        //NodeList staticList = childs.item(i).getChildNodes();
        char type;
        char row='-';
        char col='-';
        Node n;
        for(int i = 0;i<childList.getLength();i++){
            n= childList.item(i);
            if(n.getNodeName()=="greenpea"){
                type = 'o';
            }else{
                type = n.getNodeName().charAt(0);
            }
            //+staticList.item(i));
            NodeList rowAndCol = n.getChildNodes();
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

            NodeList childs = root.getChildNodes();//status, static and movable


            for(int i = 0;i<childs.getLength();i++){
                if(childs.item(i).getNodeName()=="status"){
                    ret.addAll(parseStatus(childs.item(i)));
                }
                if(childs.item(i).getNodeName()=="static"||childs.item(i).getNodeName()=="movable"){
                    ret.addAll(parseStaticMovable(childs.item(i)));//static or movable
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


   /* public static void main (String[] args){
        GardenDOM garden = GardenDOM.getInstance();
        garden.createNewXML();
        Sunflower sf = new Sunflower(2,3);
        Peashooter ps = new Peashooter(5,3);
        garden.addStatic(sf);
        garden.addStatic(ps);
        garden.testID(ps);
        garden.deleteStatic(sf);

        ArrayList<String> str = garden.parseGardenXML();
        for(String i: str){
            System.out.println(" ");
            System.out.println("_"+i+"_");

        }
    }*/


}