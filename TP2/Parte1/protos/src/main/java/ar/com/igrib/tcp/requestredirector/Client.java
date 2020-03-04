package ar.com.igrib.tcp.requestredirector;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        
        System.out.println("Client connecting to localhost:9090");
        
        Socket sock = new Socket("localhost", 9090);

        while(true) {
            System.out.println("Sending message");
            sock.getOutputStream().write("message\n".getBytes());
            Thread.sleep(500);
        }
    }
}