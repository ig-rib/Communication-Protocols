package ar.com.igrib.tcp.requestredirector;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FwdServer {

    private static final int BUFF_SIZE = 10240;

    public static void main(String[] args){ 

        Runnable setupOutputServer = () -> {
            try { 
                ServerSocket socket = new ServerSocket(Integer.parseInt(args[0]));
                System.out.println("Listening on port " + args[0]);
                Socket connection = socket.accept();       
                System.out.println("Connection accepted");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                byte[] forwarded = new byte[BUFF_SIZE];
                while (true) {
                    System.out.println(reader.readLine());
                }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
        };

        Executor ex = Executors.newFixedThreadPool(2);
        ex.execute(setupOutputServer);
    }
}