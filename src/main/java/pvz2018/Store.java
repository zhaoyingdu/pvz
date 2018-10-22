package pvz2018;

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


}
