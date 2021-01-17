/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserverudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author info1
 */
public class ThreadWrite {

    private final DatagramSocket datagramSocket;

    public ThreadWrite(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public void send(String message, String address, int port) {
        try {
//            System.out.println(message);
            byte[] buf = new byte[1024];
            DatagramPacket dataGramPacket = new DatagramPacket(buf, 1024);
            
            dataGramPacket.setData(message.getBytes());
            //client ip
           
            dataGramPacket.setAddress(InetAddress.getByName(address));
           
            dataGramPacket.setPort(port);
            datagramSocket.send(dataGramPacket);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(ThreadWrite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ThreadWrite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
