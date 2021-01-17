/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserverudp;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author info1
 */
public class Client_Server_UDP extends Thread {

    private final String ipLocal;
    private final int localPort;
    private JTextArea jTextArea;
    private ThreadWrite threadWrite;

    public Client_Server_UDP(String ipLocal, int localPort, JTextArea jTextArea) {
        this.ipLocal = ipLocal;
        this.localPort = localPort;
        this.jTextArea = jTextArea;

    }



    

    @Override
    public void run() {
        try {
            SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(ipLocal), localPort);

            DatagramSocket datagramSocket = new DatagramSocket(socketAddress);

//            datagramSocket.bind(socketAddress);
            // create and start thread (read and write)
            new Thread(new ThreadRead(datagramSocket, jTextArea)).start();
            threadWrite = new ThreadWrite(datagramSocket);

        } catch (SocketException ex) {
            Logger.getLogger(Client_Server_UDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client_Server_UDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendMessage(String message, String ip, int port) {
        threadWrite.send(message, ip, port);

    }

}
