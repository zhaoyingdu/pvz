package pvz2018;


public class PlantFactory{
    
    public PlantFactory(){}

    public Plant createPlant(String plantName){
        switch(plantName){
            case "sunflower":
                return new SunFlower();
                //break;
            case "peashooter":
                break;
        }
    }
}