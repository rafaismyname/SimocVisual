package com.biosmart.simoc.stimulators;

import java.util.ArrayList;
import java.util.List;

public final class StimulatorUtils {
    public static byte calculate_stimulators_checksum(List<Stimulator> stimulators){
        byte checksum = 0;
        for (Stimulator stimulator : stimulators)
            checksum += stimulator.checksum;
        return checksum;
    }
    
    public static byte[] generate_stimulation_command(List<Stimulator> stimulators){
        int size_of_array = 4 + (stimulators.size() * 4);
        int index_aux = 1;
        byte[] commands = new byte[size_of_array];

        commands[0] = 36;
        commands[1] = 67;

        for (Stimulator stimulator : stimulators) {
            commands[++index_aux] = stimulator.get_pulse_length();
            commands[++index_aux] = stimulator.get_time_between_puses();
            commands[++index_aux] = stimulator.get_amplitude();
            commands[++index_aux] = stimulator.get_modulation_frequency();
        }

        commands[size_of_array - 2] = (byte) (commands[0] + commands[1] + calculate_stimulators_checksum(stimulators));
        commands[size_of_array - 1] = 59;

        return commands;
    }
    
    public static byte[] generate_stop_stimulation(){
        byte[] commands = {36, 90, 59};

        return commands;
    }
    
    public static List<Stimulator> generate_blank_list(int channels){
        List<Stimulator> stimulators = new ArrayList<>();
        for(int x = 0; x < channels; x++){
            stimulators.add(x, new Stimulator((byte) 0, (byte) 0, (byte) 0, (byte) 0));
        }
        return stimulators;
    }
}
