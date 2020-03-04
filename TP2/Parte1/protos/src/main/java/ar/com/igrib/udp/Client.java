package ar.com.igrib.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[] args) throws IOException {

        Runnable sendMessageDatagram = () -> {
            try{
                DatagramSocket socket = new DatagramSocket();
                InetAddress address = InetAddress.getByName("localhost");
                DatagramPacket packet;
                byte [] msg;

                while (true) {
                    msg = "MESSAGE".getBytes();
                    Thread.sleep(500);
                    packet = new DatagramPacket(msg, msg.length, address, 9090);
                    socket.send(packet);
                } 
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }      
        };

        Runnable receiveMessageDatagram = () -> {
            try {
                byte[] receivedBuff = new byte[256];
                DatagramPacket receivedPacket = new DatagramPacket(receivedBuff, receivedBuff.length);
                DatagramSocket socket = new DatagramSocket(9091, InetAddress.getByName("localhost"));
                while(true) {
                    socket.receive(receivedPacket);
                    System.out.println(new String(receivedPacket.getData()));
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        };

        Executor executor = Executors.newFixedThreadPool(2);
        executor.execute(sendMessageDatagram);
        executor.execute(receiveMessageDatagram);
    }

}