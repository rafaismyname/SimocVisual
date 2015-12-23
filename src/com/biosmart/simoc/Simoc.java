package com.biosmart.simoc;

import com.biosmart.simoc.sensors.Sensor;
import com.biosmart.simoc.sensors.SensorUtils;
import com.biosmart.simoc.stimulators.Stimulator;
import com.biosmart.simoc.stimulators.StimulatorUtils;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.SwingWorker;

public class Simoc extends javax.swing.JFrame implements PropertyChangeListener {

    private Server server;
    private final List<Stimulator> stimulators = StimulatorUtils.generate_blank_list(8);
    private List<Sensor> sensors = new ArrayList<>();
    private boolean selects_populated = false;

    public Simoc() throws IOException {
        initComponents();
        populate_selects();
    }

    //<editor-fold defaultstate="collapsed" desc="Main Declaration">
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Simoc().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Simoc.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    //</editor-fold>

    private void sensor_updated(byte[] socket_buffer) {
        if (SensorUtils.is_checksum_valid(socket_buffer)) {
            sensors = SensorUtils.get_sensors_from_socket(socket_buffer);
            left_knee_value.setText(String.valueOf(SensorUtils.get_left_knee_angle(sensors)) + "°");
            right_knee_value.setText(String.valueOf(SensorUtils.get_right_knee_angle(sensors)) + "°");
            left_hip_value.setText(String.valueOf(SensorUtils.get_left_hip_angle(sensors)) + "°");
            right_hip_value.setText(String.valueOf(SensorUtils.get_right_hip_angle(sensors)) + "°");
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Static Work Zone">
    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("battery_level")) {
            change_battery_level((Integer) evt.getNewValue());
        } else if (evt.getPropertyName().equals("server_debug_status")) {
            server_status_label.setText((String) evt.getNewValue());
        } else if (evt.getPropertyName().equals("server_status")) {
            toggle_server_button((Integer) evt.getNewValue());
        } else if (evt.getPropertyName().equals("sensor_updated")) {
            sensor_updated((byte[]) evt.getNewValue());
        }
    }
    
    private void update_stimulators(){
        if(selects_populated){
            int stimulator_index = ((int) channel_select.getSelectedItem()) - 1;
            byte pulse_lenght = (byte) (((int) pulse_lenght_select.getSelectedItem()) / 50);
            byte pulse_time = (byte) (((int) pulse_time_select.getSelectedItem()) / 50);
            byte frequency = (byte) (((int) frequency_select.getSelectedItem()) / 5);
            byte amplitude = (byte) ((int) amplitude_select.getSelectedItem());

            stimulators.get(stimulator_index).update_stimulator(pulse_lenght, pulse_time, amplitude, frequency);

            channels_textarea.setText("");
            stimulator_index = 0;
            for(Stimulator stimulator : stimulators){
                channels_textarea.append("Canal " + (++stimulator_index) + ": "+stimulator.get_pulse_length()+";"+
                stimulator.get_time_between_puses()+";"+
                stimulator.get_amplitude()+";"+
                stimulator.get_modulation_frequency()+"\n");
            }
        }
    }
    
    private void populate_selects() {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                for (int x = 50; x <= 10000; x += 50) {
                    pulse_time_select.addItem(x);
                    if (x <= 600) {
                        pulse_lenght_select.addItem(x);
                    }
                }
                for (int x = 5; x <= 150; x += 5) {
                    frequency_select.addItem(x);
                }
                for (int x = 0; x <= 100; x++) {
                    if(x < stimulators.size()){
                        channel_select.addItem(x + 1);
                    }
                    amplitude_select.addItem(x);
                }
                selects_populated = true;
                return null;
            }
        }.execute();
    }

    private void change_battery_level(int level) {
        battery_progressbar.setValue(level);
    }

    private void toggle_server_button(int on) {
        if (on > 0) {
            server_toggle_button.setSelected(true);
            server_toggle_button.setText("Desabilitar Servidor");
            stimulation_toggle_button.setEnabled(true);
        } else {
            server_toggle_button.setSelected(false);
            server_toggle_button.setText("Habilitar Servidor");
            stimulation_toggle_button.setEnabled(false);
            stimulation_toggle_button.setSelected(false);
            emergency_button.setEnabled(false);
        }
    }

    private void toggle_server() {
        if (server_toggle_button.isSelected()) {
            try {
                server = new Server(5049);
                server.addPropertyChangeListener(this);
                server.execute();
            } catch (Exception ex) {
            }
        } else {
            server.close_socket();
            server.cancel(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        angles_panel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        right_knee_value = new javax.swing.JLabel();
        left_knee_value = new javax.swing.JLabel();
        right_hip_value = new javax.swing.JLabel();
        left_hip_value = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        status_panel = new javax.swing.JPanel();
        server_status_label = new javax.swing.JLabel();
        top_panel = new javax.swing.JPanel();
        server_toggle_button = new javax.swing.JToggleButton();
        battery_label = new javax.swing.JLabel();
        battery_progressbar = new javax.swing.JProgressBar();
        main_tabs = new javax.swing.JTabbedPane();
        stimulation_panel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        channels_textarea = new javax.swing.JTextArea();
        pulse_lenght_select = new javax.swing.JComboBox();
        channel_select = new javax.swing.JComboBox();
        channel_label = new javax.swing.JLabel();
        pulse_time_select = new javax.swing.JComboBox();
        pulse_lenght_label = new javax.swing.JLabel();
        emergency_button = new javax.swing.JButton();
        stimulation_toggle_button = new javax.swing.JToggleButton();
        frequency_select = new javax.swing.JComboBox();
        amplitude_select = new javax.swing.JComboBox();
        pulse_time_label = new javax.swing.JLabel();
        amplitude_label = new javax.swing.JLabel();
        frequency_label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        angles_panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Joelho direito:");

        jLabel3.setText("Joelho esquerdo");

        jLabel4.setText("Quadril direito:");

        jLabel5.setText("Quadril esquerdo:");

        right_knee_value.setText("180.0°");

        left_knee_value.setText("180.0°");

        right_hip_value.setText("180.0°");

        left_hip_value.setText("180.0°");

        jLabel1.setText("Angulos");

        javax.swing.GroupLayout angles_panelLayout = new javax.swing.GroupLayout(angles_panel);
        angles_panel.setLayout(angles_panelLayout);
        angles_panelLayout.setHorizontalGroup(
            angles_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(angles_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(angles_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(angles_panelLayout.createSequentialGroup()
                        .addGroup(angles_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(angles_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(right_knee_value, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(left_knee_value, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(right_hip_value, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(left_hip_value, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(angles_panelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        angles_panelLayout.setVerticalGroup(
            angles_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(angles_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(angles_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(right_knee_value))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(angles_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(left_knee_value))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(angles_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(right_hip_value))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(angles_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(left_hip_value))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        status_panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        server_status_label.setText("Servidor Desconectado");

        javax.swing.GroupLayout status_panelLayout = new javax.swing.GroupLayout(status_panel);
        status_panel.setLayout(status_panelLayout);
        status_panelLayout.setHorizontalGroup(
            status_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(status_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(server_status_label)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        status_panelLayout.setVerticalGroup(
            status_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, status_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(server_status_label)
                .addContainerGap())
        );

        top_panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        server_toggle_button.setText("Habilitar Servidor");
        server_toggle_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                server_toggle_buttonActionPerformed(evt);
            }
        });

        battery_label.setText("Bateria:");

        battery_progressbar.setMaximum(4);
        battery_progressbar.setToolTipText("Nível da bateria");

        javax.swing.GroupLayout top_panelLayout = new javax.swing.GroupLayout(top_panel);
        top_panel.setLayout(top_panelLayout);
        top_panelLayout.setHorizontalGroup(
            top_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(top_panelLayout.createSequentialGroup()
                .addComponent(server_toggle_button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(battery_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(battery_progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        top_panelLayout.setVerticalGroup(
            top_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(top_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(server_toggle_button)
                .addComponent(battery_label))
            .addComponent(battery_progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        channels_textarea.setEditable(false);
        channels_textarea.setColumns(1);
        channels_textarea.setRows(5);
        channels_textarea.setText("Canal 1: 0;0;0;0\nCanal 2: 0;0;0;0\nCanal 3: 0;0;0;0\nCanal 4: 0;0;0;0\nCanal 5: 0;0;0;0\nCanal 6: 0;0;0;0\nCanal 7: 0;0;0;0\nCanal 8: 0;0;0;0");
        jScrollPane3.setViewportView(channels_textarea);

        pulse_lenght_select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pulse_lenght_selectActionPerformed(evt);
            }
        });

        channel_label.setText("Canal");

        pulse_time_select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pulse_time_selectActionPerformed(evt);
            }
        });

        pulse_lenght_label.setText("Largura do pulso (us)");

        emergency_button.setText("Emergencia");
        emergency_button.setEnabled(false);
        emergency_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emergency_buttonActionPerformed(evt);
            }
        });

        stimulation_toggle_button.setText("Inicar");
        stimulation_toggle_button.setEnabled(false);
        stimulation_toggle_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stimulation_toggle_buttonActionPerformed(evt);
            }
        });

        frequency_select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frequency_selectActionPerformed(evt);
            }
        });

        amplitude_select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amplitude_selectActionPerformed(evt);
            }
        });

        pulse_time_label.setText("Tempo entre pulsos (us)");

        amplitude_label.setText("Amplitude (V)");

        frequency_label.setText("Frequencia (Hz)");

        javax.swing.GroupLayout stimulation_panelLayout = new javax.swing.GroupLayout(stimulation_panel);
        stimulation_panel.setLayout(stimulation_panelLayout);
        stimulation_panelLayout.setHorizontalGroup(
            stimulation_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stimulation_panelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(stimulation_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(stimulation_panelLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(emergency_button, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(stimulation_toggle_button, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))
                    .addGroup(stimulation_panelLayout.createSequentialGroup()
                        .addGroup(stimulation_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(stimulation_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(pulse_time_label)
                                .addComponent(amplitude_label, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(frequency_label, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(pulse_lenght_label, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(channel_label, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(stimulation_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(channel_select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pulse_lenght_select, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pulse_time_select, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(frequency_select, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(amplitude_select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        stimulation_panelLayout.setVerticalGroup(
            stimulation_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stimulation_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(stimulation_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stimulation_panelLayout.createSequentialGroup()
                        .addGroup(stimulation_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(channel_label)
                            .addComponent(channel_select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(stimulation_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pulse_lenght_label)
                            .addComponent(pulse_lenght_select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(stimulation_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pulse_time_label)
                            .addComponent(pulse_time_select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(stimulation_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(frequency_label)
                            .addComponent(frequency_select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(stimulation_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(amplitude_label)
                    .addComponent(amplitude_select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(stimulation_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emergency_button)
                    .addComponent(stimulation_toggle_button))
                .addContainerGap())
        );

        main_tabs.addTab("Estimulação", stimulation_panel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(status_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(top_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(angles_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(main_tabs)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(top_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(main_tabs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(angles_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(status_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void server_toggle_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_server_toggle_buttonActionPerformed
        toggle_server();
    }//GEN-LAST:event_server_toggle_buttonActionPerformed

    private void frequency_selectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_frequency_selectActionPerformed
        update_stimulators();
    }//GEN-LAST:event_frequency_selectActionPerformed

    private void amplitude_selectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amplitude_selectActionPerformed
        update_stimulators();
    }//GEN-LAST:event_amplitude_selectActionPerformed

    private void stimulation_toggle_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stimulation_toggle_buttonActionPerformed
        if(stimulation_toggle_button.isSelected()){
            emergency_button.setEnabled(true);
            server.send_message(StimulatorUtils.generate_stimulation_command(stimulators));
        }else{
            emergency_button.setEnabled(false);
        }
    }//GEN-LAST:event_stimulation_toggle_buttonActionPerformed

    private void emergency_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emergency_buttonActionPerformed
        if(emergency_button.isEnabled()){
            server.send_message(StimulatorUtils.generate_stop_stimulation());
        }
    }//GEN-LAST:event_emergency_buttonActionPerformed

    private void pulse_time_selectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pulse_time_selectActionPerformed
        update_stimulators();
    }//GEN-LAST:event_pulse_time_selectActionPerformed

    private void pulse_lenght_selectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pulse_lenght_selectActionPerformed
        update_stimulators();
    }//GEN-LAST:event_pulse_lenght_selectActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel amplitude_label;
    private javax.swing.JComboBox amplitude_select;
    private javax.swing.JPanel angles_panel;
    private javax.swing.JLabel battery_label;
    private javax.swing.JProgressBar battery_progressbar;
    private javax.swing.JLabel channel_label;
    private javax.swing.JComboBox channel_select;
    private javax.swing.JTextArea channels_textarea;
    private javax.swing.JButton emergency_button;
    private javax.swing.JLabel frequency_label;
    private javax.swing.JComboBox frequency_select;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel left_hip_value;
    private javax.swing.JLabel left_knee_value;
    private javax.swing.JTabbedPane main_tabs;
    private javax.swing.JLabel pulse_lenght_label;
    private javax.swing.JComboBox pulse_lenght_select;
    private javax.swing.JLabel pulse_time_label;
    private javax.swing.JComboBox pulse_time_select;
    private javax.swing.JLabel right_hip_value;
    private javax.swing.JLabel right_knee_value;
    private javax.swing.JLabel server_status_label;
    private javax.swing.JToggleButton server_toggle_button;
    private javax.swing.JPanel status_panel;
    private javax.swing.JPanel stimulation_panel;
    private javax.swing.JToggleButton stimulation_toggle_button;
    private javax.swing.JPanel top_panel;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>  
}
