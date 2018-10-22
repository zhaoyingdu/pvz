package pvz2018;

import java.util.Random;

public class Plant {
    Random rand = new Random();
    Plant(){
        System.out.println("new plant, #: "+rand.nextInt());
    }
}