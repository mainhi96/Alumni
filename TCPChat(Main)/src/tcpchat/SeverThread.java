/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import javax.xml.ws.Service;

/**
 *
 * @author SONY
 */
public class SeverThread extends Thread{
  final  private PlayerSocket player;
  private PlayerSocket player1;
   
    
    public SeverThread(Socket socket, Socket socket1) throws IOException{
       player= new PlayerSocket(socket);
      player1= new PlayerSocket(socket1);
      
       
    }
    public void setPlayerNames(){
        String a=nhan();
        player.setName(a);
        String b=nhan1();
        player1.setName(b);
    }

    public PlayerSocket getPlayer1() {
        return player1;
    }

    public void setPlayer1(PlayerSocket player1) {
        this.player1 = player1;
    }

    public PlayerSocket getPlayer() {
        return player;
    }
    
    //gui tin nhan
      void send(String mess){
       try {
          // for (PlayerSocket playerSocket: player)
           player.getOut().write(mess.getBytes());
           
  
       } catch (IOException e) {
           System.out.println("Can't send");
       }
       }
      
       void send1(String mess){
       try {
          // for (PlayerSocket playerSocket: player)
           player1.getOut().write(mess.getBytes());
           
  
       } catch (IOException e) {
           System.out.println("Can't send");
       }
       }
       
       public String nhan(){
           String mess;
           byte[] buff = new byte[2048];
        try {
            while (true) {
            int receivedBytes = player.getIn().read(buff);
            if(receivedBytes<1) break;
                 mess = new String(buff,0,receivedBytes);
              return mess;
            }
        } catch (Exception e) {
            System.out.println("Can't read");
        }
       return null;
       }
       public String nhan1(){
           String mess;
           byte[] buff = new byte[2048];
        try {
            while (true) {
            int receivedBytes = player1.getIn().read(buff);
            if(receivedBytes<1) break;
                 mess = new String(buff,0,receivedBytes);
              return mess;
            }
        } catch (Exception e) {
            System.out.println("Can't read");
        }
       return null;
}
    //nhan
   @Override
    public void run(){
     String a;
      while (true){
          a= nhan();
          if (a!=null) {
              send1(player.getName()+": "+a);
              
               
             
          }
         
      }
      
        }
    }
    
    

