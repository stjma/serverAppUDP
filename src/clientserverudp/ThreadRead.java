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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author info1
 */
public class ThreadRead implements Runnable {

    private final DatagramSocket datagramSocket;
    private JTextArea jTextArea;

    private ArrayList<User> ArrayListUser = new ArrayList<>();

    public ThreadRead(DatagramSocket datagramSocket, JTextArea jTextArea) {
        this.datagramSocket = datagramSocket;
        this.jTextArea = jTextArea;
    }

    @Override
    public void run() {

        while (true) {
            byte[] buf = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buf, 1024);
            try {
                datagramSocket.receive(datagramPacket);
                String message = new String(datagramPacket.getData());

                jTextArea.append(String.format("message %s\n", message));
                jTextArea.append(String.format("emeteur %s\n", datagramPacket.getAddress().toString()));
                jTextArea.append(String.format("port %d\n", datagramPacket.getPort()));

                String[] parts = message.split("!/!");
//
//              

                String messageReceive = "error";
                int check = 0;
                if (parts[0].equals("addUser")) {

                    for (User user : ArrayListUser) {
                        if (user.getNickName().contains(parts[1])) {
                            System.err.println(user.getNickName() + " " + parts[1]);
                            messageReceive = "NickName Existe deja Veillez change de nickname";
                            check++;
                        }

//                        System.out.println(user.getNickName());
                    }
                    if (check == 0) {
                        User user = new User(parts[1], parts[2]);
                        System.out.println(user.toSting());
                        ArrayListUser.add(user);
                        messageReceive = "User ajouter";
                    }

                } else if (parts[0].equals("delUser")) {

                    for (int cpt = 0; cpt < ArrayListUser.size(); cpt++) {
                        if (ArrayListUser.get(cpt).getNickName().contains(parts[1])) {
                            ArrayListUser.remove(cpt);
                            messageReceive = "NickName supprimer";
                            check++;
                        }
//                        if (user.getNickName().contains(parts[1])) {
//                            ArrayListUser.remove(user);
//                            messageReceive = "NickName supprimer";
//                            check++;
//                        }
                    }
                    if (check == 0) {
                        messageReceive = "nickName existe pas suppresion annuler";
                    }
                } else {
                    messageReceive = "annuler";
                }
                String ip = datagramPacket.getAddress().toString();
                ThreadWrite tw = new ThreadWrite(datagramSocket);

                tw.send(messageReceive, ip.substring(1), datagramPacket.getPort());

//               
            } catch (IOException ex) {
                Logger.getLogger(ThreadRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
