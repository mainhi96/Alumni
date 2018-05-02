package com.example.sony.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by SONY on 5/2/2018.
 */

public class TCPClient extends Thread {
    private InputStream in;
    private OutputStream out;
    private Socket socket;

    public TCPClient(String severAddress, int severPort) throws IOException {
        socket = new Socket(severAddress, severPort);
        in = socket.getInputStream();
        out = socket.getOutputStream();

    }

    //gui tin nhan
    public void send(String mess) {
        try {
            out.write(mess.getBytes());
        } catch (IOException e) {
            System.out.println("Can't send");
        }
    }

    //nhan tin nhan


    @Override
    public void run() {


        byte[] buff = new byte[2048];
        try {
            while (true) {
                int receivedBytes = in.read(buff);
                if (receivedBytes < 1) break;
                String mess = new String(buff, 0, receivedBytes);
                System.out.println(mess);
            }
        } catch (Exception e) {
            System.out.println("Can't read");
        }

    }
}
