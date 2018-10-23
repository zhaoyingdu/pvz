package pvz2018;

public enum plantEnum{
    sunflower("sun flower"),
    peashooter("pea shooter");

    private final String name;

    plantEnum(String plantName){
        name = plantName;
    };

    public String getName(){
        return name;
    }
}