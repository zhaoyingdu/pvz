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
        damage = 10;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public double getDisplacement() {
        return displacement;
    }
    @Override
    public int getCol(){
        return (int)Math.floor(getPosition());
    }
    public boolean propagate(){
        double oldDisplacement= displacement;
        displacement+=speed;
        return Math.floor(oldDisplacement)==Math.floor(displacement);
        //Math.floor(variable)
        /*if ((displacement == Math.floor(displacement)) && !Double.isInfinite(displacement)) {
            firePropertyChange("propagate pea", null, new int[]{row,(int)Math.floor(displacement)});
        }*/     
    }

    public int hit(){
        return damage;
    }

    @Override
    public int getInitCol() {
        return initCol;
    }

    @Override
    public double getPosition() {
        return initCol+displacement;//this is a bit dirty.. it shouldn't know the actual screen configuration
    }

    @Override
    public String getName() {
        return name;
    }

}