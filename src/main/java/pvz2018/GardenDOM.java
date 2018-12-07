package pvz2018;

import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URLClassLoader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;


public class GardenDOM{

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dbBuilder;
    Document doc;

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer;
    
    private static GardenDOM gardenDOM;
    private GardenDOM(){
        try {
            this.dbBuilder = dbFactory.newDocumentBuilder();
            this.transformer = transformerFactory.newTransformer();
            readXML("template.xml");            
        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace();
        }
    };

    public static GardenDOM getInstance(){
        if(gardenDOM == null){
            gardenDOM =  new GardenDOM();
            return gardenDOM;
        }
        return gardenDOM;
    }

   

    /*
        populate plant factory status portion
        accepted args: "sunflower,5,20","peashooter,4,30"..
        for each string elem, first part indicate factry name,
        second part indicate factory cooldown,
        third part indicate factory charges for each product
    */
    public void addFactory(String... factories){
        String facName, coolDown, cost;
        XPath xPath =  XPathFactory.newInstance().newXPath();
        String expression = "//factories";
        Node nodeFactories = null;
        try {
            nodeFactories = (Node) xPath.compile(expression).evaluate(this.doc, XPathConstants.NODE);
            if(nodeFactories == null){
                throw new NoSuchElementException("'factories node not found'");
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }      
        for (String s : factories){
            facName = s.split(",")[0];
            coolDown = s.split(",")[1];
            cost = s.split(",")[2];
            Element factory = this.doc.createElement(facName);
            factory.setAttribute("coolDown", coolDown);
            factory.setAttribute("cost", cost);
            nodeFactories.appendChild(factory);               
        }

    }

    public void readXML(String fileName) {  
        try {
            this.doc = dbBuilder.parse(new File(fileName));
           // DOMSource source = new DOMSource(tampDoc);
           // StreamResult result = new StreamResult(new File("garden.xml"));
            //transformer.transform(source,result);
            //this.doc = dbBuilder.parse(new File("garden.xml"));
        } catch ( SAXException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
    
    public boolean scanZombie(int baseX){
        //fnd all zombie node whose coord x is same as base
        XPath xPath =  XPathFactory.newInstance().newXPath();
        String expression = "//zombie[@coordinateX ="+baseX+"]";
        NodeList zombies = (NodeList)find(null,expression,NodeList.class);
        if(zombies.getLength() == 0) return false;
        //System.out.println("following zombies detected");
        //System.out.println(nodeToString(zombies));
        
        return true;

    }
    // <ammunition id = "23121" name="green pea" type="ordinary" speed="5" damage="6" coordinateX = "0" coordinateY="2">
    public void addMovables(String tagName, String amoInfo){
        //String plantXML = "<plant id=\""+plantInfo[0]+"\" name=\""+plantInfo[1]+"\" type=\""+plantInfo[2]+"\" life = \""+plantInfo[3]+"\" health = \""+plantInfo[4]+"\" range = \""+plantInfo[5]+"\" coordinateX = \""+plantInfo[6]+"\" coordinateY=\""+plantInfo[7]+"\"></plant>";
        try {
            Node parent = doc.getElementsByTagName(tagName).item(0);
            Document plantDoc = dbBuilder.parse(new ByteArrayInputStream(amoInfo.getBytes("UTF-8")));
            Node plant = doc.importNode(plantDoc.getDocumentElement(),true);
            parent.appendChild(plant);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public Node removePlantById(String id){
        NodeList plantAtLot = (NodeList)find(null,"//plant[@id="+id+"]",NodeList.class);
        if(plantAtLot.getLength()==0){
            System.out.println("no plant at spot, remove has no effect");
            return null;
        }
        if(plantAtLot.getLength()>1){
           throw new Error("operation error, found two plants at one spot, debug your app!");
        }
        Node plants = doc.getElementsByTagName("plants").item(0);
        plants.removeChild(plantAtLot.item(0));
        return plantAtLot.item(0);
    }
    /*
        <hashcode> <type> <name> <life> <health> <range> <coordinateX> <coordinateY>
    */
    public Node addPlant(String plantInfo, int x, int y){
        Node plantAtLot = (Node)getObjectAt("plant", x, y);//find(null,"//plant[@coordinateX="+x+"][@coordinateY= "+y+"]",NodeList.class);
        if(plantAtLot!=null){
            return null;
            //throw new Error("already a plant occupied the space");
        }
        try {
            Node plants = doc.getElementsByTagName("plants").item(0);
            Document plantDoc = dbBuilder.parse(new ByteArrayInputStream(plantInfo.getBytes("UTF-8")));
            //Element plant =  plantDoc.getDocumentElement();
            Node plant = doc.importNode(plantDoc.getDocumentElement(),true);
            plants.appendChild(plant);
            return plant;
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getObjectX(Node obj){
        return (Integer)gardenDOM.find(obj,"number(.//@coordinateX)",Integer.class);
    }

    public Integer getObjectId(Node obj){
        return (Integer)gardenDOM.find(obj,"number(.//@id)",Integer.class);
    }

    public String getObjectName(Node obj){
        
        return (String)gardenDOM.find(obj,".//@name",String.class);

    }
    public int getObjectY(Node obj){
        return (Integer)gardenDOM.find(obj,"number(.//@coordinateY)",Integer.class);

    }
    public int getObjectSpeed(Node obj){
        return (Integer)gardenDOM.find(obj,"number(.//@ speed)",Integer.class);
    }
    
    public int getObjectDamage(Node obj){
        return (Integer)gardenDOM.find(obj,"number(.//@damage)",Integer.class);
    }

    public int getObjectLife(Node obj){
        return (Integer)gardenDOM.find(obj,"number(.//@life)",Integer.class);
    }

    public int getObjectHealth(Node obj){
        return (Integer)gardenDOM.find(obj,"number(.//@health)",Integer.class);
    }

    public Node getObjectAt(String tagName, int x,int y){
        Node ret =  (Node)gardenDOM.find(null,String.format("//%s[@coordinateX = \"%d\"][@coordinateY = \"%d\"]",tagName,x,y),Node.class);
        return ret;
    }

    /*<coordinateX> <coordinateY>*/
    public Node removePlant(String... coordInfo){
        NodeList plantAtLot = (NodeList)find(null,"//plant[@coordinateX="+coordInfo[0]+"][@coordinateY= "+coordInfo[1]+"]",NodeList.class);
        if(plantAtLot.getLength()==0){
            System.out.println("no plant at spot, remove has no effect");
            return null;
        }
        if(plantAtLot.getLength()>1){
           throw new Error("operation error, found two plants at one spot, debug your app!");
        }
        Node plants = doc.getElementsByTagName("plants").item(0);
        plants.removeChild(plantAtLot.item(0));
        return plantAtLot.item(0);
    }

    
    public void dumpToXML(String fileName){
        try {
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


    public static String nodeToString(Node nodes) {
        StringWriter sw = new StringWriter();
        StringBuffer retStr = new StringBuffer();
        try {
          Transformer t = TransformerFactory.newInstance().newTransformer();
          t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
 //for(int i = 0;i<nodes.getLength();i++){
            t.transform(new DOMSource(nodes), new StreamResult(sw));
            retStr.append(sw.toString());
   //       }    
        } catch (TransformerException te) {
          System.out.println("nodeToString Transformer Exception");
        }
        return retStr.toString();
      }

    

    public Object find( Node context, String expr, Class retType){
        Object retVal = null;
        if(context == null){
            //System.out.println("running findByTYoe against doc element");
            context = doc;
        }
        
        XPath xPath =  XPathFactory.newInstance().newXPath();
        try {
            XPathExpression XExpr = xPath.compile(expr);
            switch(retType.getSimpleName()){
                case "Node":
                    retVal = XExpr.evaluate(context, XPathConstants.NODE);   
                    break;
                case "NodeList":
                    retVal = XExpr.evaluate(context, XPathConstants.NODESET);   
                    break;
                case "String":
                    retVal = (String)XExpr.evaluate(context, XPathConstants.STRING);  
                    break;
                case "Integer":
                    retVal = ((Double)XExpr.evaluate(context, XPathConstants.NUMBER)).intValue();  
                    break;
                case "Boolean":
                    retVal = ((Boolean)XExpr.evaluate(context, XPathConstants.BOOLEAN));  
                    break;

            }
            
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return retVal;
        
    }

    public boolean move(Node node){
        Element elem = (Element)node;
        Integer coordY =(Integer)getObjectY(node);
        Integer coordX =(Integer)getObjectX(node);
        int speed = (Integer)getObjectSpeed(node);
        Integer side = (Integer)find(node, "number(.//@side)", Integer.class);
        String findTarget = String.format(".//*[@side = %d][@coordinateX = %d][@coordinateY = %d]",
        0,coordX,coordY);
        if(side == 0){
            findTarget = String.format(".//*[@side = %d][@coordinateX = %d][@coordinateY <= %d]",
            1,coordX,coordY);
        }
        Node enemy = (Node)find(null,findTarget, Node.class);
        if(enemy!=null){
            Integer atkDamage = getObjectDamage(node);
            Integer takeDamage = getObjectDamage(enemy);
            if(decreaseHealth(enemy, atkDamage)){
                 elem.setAttribute("coordinateY", coordY+speed+"");
            }
            return decreaseHealth(node, takeDamage);
            
        }
        elem.setAttribute("coordinateY", coordY+speed+"");
        return false;
    }

    public boolean decreaseHealth(Node node,int damage){
        Element elem = (Element) node;
        int health = getObjectHealth(node);
        if(health<damage){
            node.getParentNode().removeChild(node);
            System.out.println(node.getNodeName()+" "+getObjectId(node)+" died");
            return true;
        }else{
            elem.setAttribute("health",health-damage+"");
            return false;
        }

    }
    private void log(Object exp){
        if( exp.getClass().equals(Node.class)){
            System.out.println(nodeToString(Node.class.cast(exp)));
        }
        System.out.println(exp);
        
    }
    private static void print(Object exp){
        if( exp.getClass().equals(NodeList.class)){
            System.out.println(nodeToString(Node.class.cast(exp)));
        }
        System.out.println(exp);
        
    }

    public boolean saveAs(String filename){
        File file = new File(Utility.getResourceFilePath("save/"+filename));
        if(file.exists()){
            log("file already exists, try another name");
            return false;
        }
        dumpToXML(file.getPath());
        return true;
    } 

    public boolean load(String filename){
        File file = new File(Utility.getResourceFilePath("save/"+filename));
        if(!file.exists()){
            log("file doesn't exists, try another name");
            return false;
        }
        try {
            doc = dbBuilder.parse(file.toURI().toString());
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    

    public static void main(String ... args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, URISyntaxException {
        
        GardenDOM dom = GardenDOM.getInstance();
        
        Node zombie  = (Node)dom.find(null, "//zombie[1]",Node.class);
        print(nodeToString(zombie));
        dom.move(zombie);
        print(nodeToString(zombie));
        
    }

}

