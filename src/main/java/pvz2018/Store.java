package pvz2018;

import java.util.HashMap;
import java.util.Map;

public class Store{
    enum Plants{
        SUNFLOWER("sun flower",5),
        PEASHOOTER("pea shooter",4);
        private String nm;
        private int cd;
        private Plants(String name,int cooldown){
            nm = name;
            cd = cooldown;
        };

        public String getName(){return nm;}
        public int getCd(){return cd;}
    }

    private Map<String,Integer> plantCD;

    public Store(){
        plantCD = new HashMap<>();
    }


    

}
