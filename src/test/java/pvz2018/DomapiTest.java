package pvz2018;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import pvz2018.GardenDOM;

public class DomapiTest {
  GardenDOM testDom = GardenDOM.getInstance();
  // Class<?> GardenDOMClass = testDom.getClass();
  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
  DocumentBuilder dbBuilder;
  String testOutputDirectry = "D:\\f18\\pvz\\pvz\\testOutput\\";
  String oracleDirectry = "D:\\f18\\pvz\\pvz\\oracle\\";
  String testInputDirectry = "D:\\f18\\pvz\\pvz\\testInput\\";




  @Test
  public void testAddFactory(){
    String[] addFactoryParam = {"sunflower,5,20", "peashooter,4,30"};
    String inputTemplate = testInputDirectry+"inputTemplate.xml";
    //assertTrue(testAddDriver(inputTemplate, "addFactory", addFactoryParam));
  }

  @Test
  public void testaddPlant(){
    String[] addPlantParam = {"00080","peashooter","aggresive","30","50","2","2"};//, "00250,walnut,harmless,,50,2,3"};
    String inputTemplate = testInputDirectry+"inputTemplate.xml";
    //assertTrue(testAddDriver(inputTemplate, "addPlant", addPlantParam));
  }
  
  /*
    param: Oracle, Template, Parameter
  */

  private boolean testAddDriver(String template, String methodName, String[] parameter) {
    DocumentBuilder dbBuilder;
    boolean ret = false;
    try {
      dbBuilder = dbFactory.newDocumentBuilder();
      Document doc = dbBuilder.parse(new File(template));

      Field fieldDoc = testDom.getClass().getDeclaredField("doc");
      fieldDoc.set(testDom, doc);
      Method addFactory = GardenDOM.class.getDeclaredMethod(methodName, String[].class);
      addFactory.invoke(testDom, (Object[])parameter);

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);

      File outputFile = new File(testOutputDirectry+methodName+"TestOutput.xml");
      if(!outputFile.exists()) outputFile.createNewFile();
      System.out.println("==============");
      System.out.println(outputFile.getAbsolutePath());
      StreamResult result = new StreamResult(outputFile);
      transformer.transform(source, result);

      ret = fileComparater(outputFile.getPath(),oracleDirectry+methodName+"Oracle.xml");
      
    } catch (SAXException | IOException | NoSuchMethodException | SecurityException | NoSuchFieldException
        | IllegalArgumentException | IllegalAccessException | InvocationTargetException | TransformerException
        | ParserConfigurationException e) {
        e.printStackTrace();;
    }
    return ret;
  }

  
  private boolean fileComparater(String testFile, String oracle) {
    long start = System.nanoTime();
    BufferedInputStream fis1;
    BufferedInputStream fis2;
    boolean retVal = true;
    try {
      fis1 = new BufferedInputStream(new FileInputStream(testFile));
      fis2 = new BufferedInputStream(new FileInputStream(oracle));
      int b1 = 0, b2 = 0, pos = 1;
      while (b1 != -1 && b2 != -1) {
        if (b1 != b2) {
          System.out.println("Files differ at position " + pos);
          retVal = false;
          break;
        }
        pos++;
        b2 = fis2.read();
        b1 = fis1.read();
        if (b1 != b2) {
          System.out.println("Files have different length");
          retVal = false;
          break;
        }  
      }
      if(retVal == true) {
        System.out.println("Files are identical, you can delete one of them.");
      }   
      fis1.close();
      fis2.close();
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    long end = System.nanoTime();
    System.out.print("Execution time: " + (end - start) / 1000000 + "ms");
    return retVal;
  }
    public static void main(String[] args) {
      String a = "dsawdfa";
      String b = "testOutput\\"+a+"TestOutput.xml";
      //System.out.print("testOutput\\"+a+"TestOutput.xml");
      try {
        File outputFile = new File(b);
        if(!outputFile.exists()) outputFile.createNewFile();
        System.out.print(outputFile.getCanonicalPath());

      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      //File outputFile = new File("testOutput\\"+"methodName"+"TestOutput.xml");
      //if(!outputFile.exists()) outputFile.createNewFile();
      //System.out.print(outputFile.getPath());
      
    }
}
