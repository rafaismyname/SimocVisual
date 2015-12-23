package com.biosmart.simoc;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

public class Server extends SwingWorker<Void, Void> {

    private ServerSocket server_socket;
    private Socket server;
    private byte[] socket_buffer = new byte[24];

    public Server(int port) throws IOException {
        server_socket = new ServerSocket(port);
    }
    
    public void close_socket() {
        try {
            server_socket.close();
            server.close();
            firePropertyChange("server_status", null, 0);
            firePropertyChange("server_debug_status", null, "Servidor Desconectado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read_buffer() {
        if (socket_buffer[0] == 36) {
            if (socket_buffer[1] == 65) {
                read_buffer_as_battety();
            } else {
                read_buffer_as_sensor();
            }
        }
    }

    public void read_buffer_as_battety() {
        firePropertyChange("battery_level", null, ((int) socket_buffer[2]));
    }

    public void read_buffer_as_sensor() {
        firePropertyChange("sensor_updated", null, socket_buffer);
    }

    public void send_message(final byte[] send_buffer) {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                DataOutputStream out;
                try {
                    out = new DataOutputStream(server.getOutputStream());
                    out.write(send_buffer);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        }.execute();
    }

    @Override
    protected Void doInBackground() throws Exception {
        firePropertyChange("server_status", null, 1);
        try {
            firePropertyChange("server_debug_status", null, "Servidor aguardando conexão...");
            server = server_socket.accept();
            firePropertyChange("server_debug_status", null, "Servidor conectado: " + server.getRemoteSocketAddress());
            firePropertyChange("server_status", null, 2);
            InputStream stream = server.getInputStream();
            while (true) {
                if (stream.read(socket_buffer) != -1) {
                    read_buffer();
                } else {
                    close_socket();
                    break;
                }
            }
        } catch (Exception e) {
            close_socket();
        }
        return null;
    }
}
