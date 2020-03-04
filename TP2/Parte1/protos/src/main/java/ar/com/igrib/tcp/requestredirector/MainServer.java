package ar.com.igrib.tcp.requestredirector;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainServer {

    public static void main(String[] args){ 

        Runnable setupInputServer = () -> {
            try{
                System.out.println("Opening server socket @localhost:" + args[0]);
                ServerSocket socket = new ServerSocket(Integer.parseInt(args[0]));

                System.out.println("Opening socket @localhost:" + args[1]);
                Socket forwardSocket = new Socket("localhost", Integer.parseInt(args[1]));
                OutputStream out = forwardSocket.getOutputStream();

                String read;
                while (true) {
                    Socket connection = socket.accept();
                    System.out.println("Connected with client @localhost:" + args[0]);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while(true) {
                        read = reader.readLine();
                        System.out.println(read);
                        out.write((read+"\n").getBytes());
                    } // out.write(read);
                } 
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        };

        Executor ex = Executors.newFixedThreadPool(2);
        ex.execute(setupInputServer);
    }
}