package pvz2018;

import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GraphicView {
  /**
   * Create the top level fram of our application and show it, which is just a
   * windoe with border. Also setup its icon, a custom one made by notepad.
   * 
   */
  private static void createAppFrame() {
    JFrame app = new JFrame("plant vs. zombie");
    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    JFrame.setDefaultLookAndFeelDecorated(true);//you must invoke the setDefaultLookAndFeelDecorated method before creating the frame whose decorations you wish to affect. 
        /*The image should be in the same package (folder in OS terms) 
     * as the compiled class. Check whether you have both .class and 
     * .png in the same folder. If not, you can use classpath-relative 
     * paths in getResource(..), by starting with / */
     //note: there is some error when javac and java this file in internal terminal
     //using vscode, but it wont happened with cmd,
     //note2: now I am using relative path to access the icon.PNG,
     //but there is some way to access it location independantly..
    app.setIconImage(new ImageIcon(GraphicView.class.getResource("./../../resources/drawables/icon.PNG")).getImage());

    
    app.pack();
    app.setVisible(true);
  }

  public static void main(String[] args) {
    //Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAppFrame();
      }
    });
    ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
        	System.out.println(url.getFile());
        }
  }

}