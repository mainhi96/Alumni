/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author SONY
 */
public class TCPChat {
    private ServerSocket serverSocket;
    private  InputStream in;
    private OutputStream out;
    
    
    public TCPChat(int port) throws UnknownHostException, IOException{
           serverSocket= new ServerSocket(port);
    }
    
    private void waitConnection()
    {
        Scanner scan = new Scanner(System.in);
        String mess;
        String kq;
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Socket socket1 = serverSocket.accept();
                
            
                SeverThread severThread= new SeverThread(socket,socket1);
                severThread.start();
                System.out.println("Server socket successful");
                severThread.setPlayerNames();
                String namePlayer=severThread.getPlayer().getName();
                severThread.send1(namePlayer+"");
                String namePlayer1= severThread.getPlayer1().getName();
                severThread.send(namePlayer1);
                String a;
               //while true để có được 2 hàm nhận song song
                while (true) {
                 a=severThread.nhan1();
                severThread.send(namePlayer1+": "+a);
                }
                
               // 
         
            } catch (Exception e) {
                
            }
            
            
        }
    }    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       
        try {
            TCPChat tCPChat= new TCPChat(1234);
            tCPChat.waitConnection();
            
           
      
          //  Scanner scan = new Scanner(System.in);
          
         
           
        } catch (Exception e) {
            System.out.println("Can't create server socket");
        }
    }
    
}
