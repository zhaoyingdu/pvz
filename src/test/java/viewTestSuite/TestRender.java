package viewTestSuite;

import java.util.ArrayList;

public class TestRender {
     
    private ArrayList<String> testcase1;// = new ArrayList<>();
    /*private ArrayList<char[]> testcase2 = new ArrayList<>();
    private ArrayList<char[]> testcase3 = new ArrayList<>();
    private ArrayList<char[]> testcase4 = new ArrayList<>();
    private ArrayList<char[]> testcase5 = new ArrayList<>();
    private ArrayList<char[]> testcase6 = new ArrayList<>();
    private ArrayList<char[]> testcase7 = new ArrayList<>();
    private ArrayList<char[]> testcase8 = new ArrayList<>();
    private ArrayList<char[]> testcase9 = new ArrayList<>();
    private ArrayList<char[]> testcase10 = new ArrayList<>();*/

    public  TestRender(){
        StringBuilder garden = new StringBuilder(
						"  0 1 2 3 4 5 6 7\n"+
                        "0 _ _ _ _ _ _ _ _\n"+
						"1 _ _ _ _ _ _ _ _\n"+
						"2 _ _ _ _ _ _ _ _\n"+
						"3 _ _ _ _ _ _ _ _\n"+
                        "4 _ _ _ _ _ _ _ _\n");
                        
        testcase1 = new ArrayList<>();
        testcase1.add("s 0 0");
        testcase1.add("s 1 0");
        testcase1.add("s 2 0");
        testcase1.add("s 3 0");
        testcase1.add("s 4 0");
        testcase1.add("p 0 1");
        testcase1.add("p 1 1");
        testcase1.add("p 2 1");
        testcase1.add("p 3 1");
        testcase1.add("p 4 1");
        testcase1.add("o 0 2");
        testcase1.add("o 1 3");
        testcase1.add("o 2 4");
        testcase1.add("o 3 3");
        testcase1.add("o 4 2");
        testcase1.add("z 0 7");
        testcase1.add("z 1 7");
        testcase1.add("z 2 7");
        testcase1.add("z 3 7");
        testcase1.add("z 4 7");

    }




}
