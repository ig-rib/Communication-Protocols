package ar.com.igrib.kvstore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Client {

    public static void main(String[] args) throws IOException {

        Socket clientSocket = new Socket("localhost", 9090);

        Executor executor = Executors.newCachedThreadPool();

        OutputStream out = clientSocket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        executor.execute(() -> {
            try {
                while (true) {
                    System.out.println(responseReader.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        while (true) {
            out.write((reader.readLine() + "\n").getBytes());
        }
    }
}