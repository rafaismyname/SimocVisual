package com.biosmart.simoc.sensors;

import static java.lang.Math.asin;

public class Sensor {
    private int channel;
    private byte x_msb;
    private byte x_lsb;
    private byte y_msb;
    private byte y_lsb;
    public int inclination_x;
    public int inclination_y;
    public double degrees_x;
    public double degrees_y;
    
    public Sensor(int channel, byte x_msb, byte x_lsb, byte y_msb, byte y_lsb){
        this.channel = channel;
        this.x_msb = x_msb;
        this.x_lsb = x_lsb;
        this.y_lsb = y_lsb;
        this.y_msb = y_msb;
        
        calculate_inclination();
    }
    
    public int get_channel(){
        return this.channel;
    }
    
    private void calculate_inclination(){
        this.inclination_x = ((this.x_msb << 8) + this.x_lsb);
        this.inclination_y = ((this.y_msb << 8) + this.y_lsb);

        this.degrees_x = asin((this.inclination_x - 1024)/819);
        this.degrees_y = asin((this.inclination_y - 1024)/819);
        /*System.out.println("Sensor "+this.channel+": ");
        System.out.println("Inclinação X (angulo): "+this.inclination_x+"("+this.degrees_x+")");
        System.out.println("Inclinação Y (angulo): "+this.inclination_y+"("+this.degrees_y+")");*/
    }
}
