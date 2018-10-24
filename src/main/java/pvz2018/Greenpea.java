package pvz2018;

public class Greenpea extends Movable{

    public final double SPEED = 0.5;
    //private  unit;
    public Greenpea(int row,int col){
        this.speed = SPEED;
        this.row = row;
        initCol = col;
        //this.displace = col;
        displacement = 0;
        name="greenpea";
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public double getDisplacement() {
        return displacement;
    }
    public void propagate(){
        displacement+=speed;
        //Math.floor(variable)
        if ((displacement == Math.floor(displacement)) && !Double.isInfinite(displacement)) {
            firePropertyChange("propagate pea", null, new int[]{row,(int)Math.floor(displacement)});
        }     
    }

    @Override
    public int getInitCol() {
        return initCol;
    }

    @Override
    public double getPosition() {
        return initCol*2+displacement;//this is a bit dirty.. it shouldn't know the actual screen configuration
    }

    @Override
    public String getName() {
        return name;
    }

}