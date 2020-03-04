package ar.com.igrib.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    
    private static final int N = 15;
    public static void main(String[] args) throws IOException {

        DatagramSocket socket = new DatagramSocket(9090, InetAddress.getByName("localhost"));
        DatagramPacket packet;
        byte[] buff = new byte[256];
        byte[] sendBuff = "Modulo 15".getBytes();
        int i = 0;
        while(true) {
            packet = new DatagramPacket(buff, buff.length);
            socket.receive(packet);
            System.out.println(new String(packet.getData()));
            i = (i+1)%N;
            if (i == 0) {
                socket.send(new DatagramPacket(sendBuff, sendBuff.length, InetAddress.getByName("localhost"), 9091));
            }
        }
    }

}