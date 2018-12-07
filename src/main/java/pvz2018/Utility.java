package pvz2018;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Utility {
  public static void printMap(Map<String, List<Object[]>> classes){
    for(String k:classes.keySet()){
        System.out.println("");
        System.out.print("key: "+k+ " ");
        System.out.print("values: ");
        for(Object[] obj: classes.get(k)){
            System.out.print("[ "+obj[0]+", "+obj[1]+", "+obj[2]+" ]\n");
        }  
    }
  }
  public static String getResourceFilePath(String fileName){
    String ret = "";
    try {
        //note: change "pvz2018" to your package name
        URI uri = Utility.class.getClassLoader().getSystemClassLoader().getResource("pvz2018/App.class").toURI();
        Path resDirPath = Paths.get(uri).getParent();
        ret = resDirPath.resolve(fileName).toString();
    } catch (URISyntaxException e) {
        e.printStackTrace();
    }
    return ret;
}

  public static void printStack(Stack<Map<String, List<Object[]>>> stack){
    Iterator<Map<String, List<Object[]>>> iter = stack.iterator();
    int i = 0;
    while (iter.hasNext()){
        Map<String, List<Object[]>> mapi = iter.next();
        System.out.println(i+": "+mapi.hashCode());
        printMap(mapi);
        i++;
    }
  }
  public static Map<String, List<Object[]>> deepCopyMap(Map<String, List<Object[]>> classes){
    Map<String, List<Object[]>> cls = new HashMap<>();
    
    for(String k: classes.keySet()){
    //classes.forEach((k,v)->{
        List<Object[]> values = new ArrayList<>();
        for(Object[] obj: classes.get(k)){
            Object[] copy = new Object[3];
            copy[0]=obj[0];
            copy[1]=obj[1];
            copy[2]=obj[2];
            values.add(copy);
        }
        cls.put(k,values);

    };
    return cls;
  }

 

  

}

