package pvz2018;

import java.io.File;

public class App {

  public static void main(String... args){
    new File(Utility.getResourceFilePath("temp/")).mkdirs();
    new File(Utility.getResourceFilePath("save/")).mkdirs();

    
    View v = new View();
   

  }
}