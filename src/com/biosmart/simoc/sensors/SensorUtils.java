package com.biosmart.simoc.sensors;

import com.biosmart.simoc.sensors.Sensor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SensorUtils {    
    public static boolean is_checksum_valid(byte[] socket_buffer){
        byte checksum = 0;
        byte sensor_checksum = socket_buffer[socket_buffer.length - 2];

        for (int i = 0; i < socket_buffer.length; i++) {
            //System.out.print(socket_buffer[i] + " ");
            if (i < (socket_buffer.length - 2)) {
                checksum += socket_buffer[i];
            }
        }
        //System.out.print("\n");
        if (sensor_checksum != checksum) {
            System.out.println("CHECKSUM INVÁLIDO: " + sensor_checksum + " - " + checksum);
            return false;
        }
        
        return true;
    }
    
    public static List<Sensor> get_sensors_from_socket(byte[] sensor_buffer){
        List<Sensor> sensors = new ArrayList<>();
        int index_aux = 1;
        int sensor_quantity =  (sensor_buffer.length - 4) / 4;
        
        for (int y = 0; y < sensor_quantity; y++) {
            sensors.add(y, new Sensor(
                    y+1,
                    sensor_buffer[++index_aux], 
                    sensor_buffer[++index_aux], 
                    sensor_buffer[++index_aux], 
                    sensor_buffer[++index_aux]
                )
            );
        }
        
        return sensors;
    }
    
    public static double get_right_knee_angle(List<Sensor> sensors){
        return 180 - (sensors.get(1).degrees_x - sensors.get(0).degrees_x);
    }
    
    public static double get_left_knee_angle(List<Sensor> sensors){
        return 180 - (sensors.get(3).degrees_x - sensors.get(2).degrees_x);
    }
    
    public static double get_right_hip_angle(List<Sensor> sensors){
        return 180 - (sensors.get(4).degrees_y - sensors.get(1).degrees_x);
    }
    
    public static double get_left_hip_angle(List<Sensor> sensors){
        return 180 - (sensors.get(4).degrees_y - sensors.get(3).degrees_x);
    }
}
