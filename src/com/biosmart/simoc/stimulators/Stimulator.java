
package com.biosmart.simoc.stimulators;

public class Stimulator {
    private byte pulse_length = 0;
    private byte time_between_puses = 0;
    private byte amplitude = 0;
    private byte modulation_frequency = 0;
    public byte checksum = 0;
    
    public Stimulator(byte pulse_length, byte time_between_puses, byte amplitude, byte modulation_frequency){
        this.pulse_length = pulse_length;
        this.time_between_puses = time_between_puses;
        this.amplitude = amplitude;
        this.modulation_frequency = modulation_frequency;
        
        calculate_stimulator_checksum();
    }
    
    public void update_stimulator(byte pulse_length, byte time_between_puses, byte amplitude, byte modulation_frequency){
        this.pulse_length = pulse_length;
        this.time_between_puses = time_between_puses;
        this.amplitude = amplitude;
        this.modulation_frequency = modulation_frequency;
        
        calculate_stimulator_checksum();
    }
    
    public byte get_pulse_length(){
        return this.pulse_length;
    }
    
    public byte get_time_between_puses(){
        return this.time_between_puses;
    }
    
    public byte get_amplitude(){
        return this.amplitude;
    }
    
    public byte get_modulation_frequency(){
        return this.modulation_frequency;
    }
    
    private void calculate_stimulator_checksum(){
        this.checksum = 0;
        this.checksum += this.pulse_length;
        this.checksum += this.time_between_puses;
        this.checksum += this.amplitude;
        this.checksum += this.modulation_frequency;
    }
}
