package ar.com.igrib.kvstore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server {

    private static KVStore store = new KVStore();
    private static final String INC = "inc";
    private static final String DEC = "dec";
    private static final String GET = "get";
    private static final String PUT = "put";

    public static void main(String[] args) {

        Executor executor = Executors.newCachedThreadPool();
        try {
            ServerSocket server = new ServerSocket(9090);
            BufferedReader reader;
            OutputStream out;
            Socket connSocket;
            String readMessage;
            while (true) {
                connSocket = server.accept();
                reader = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
                out = connSocket.getOutputStream();
                while(true) {
                    readMessage = reader.readLine();
                    System.out.println(readMessage);
                    String[] parts = readMessage.split(" ");
                    switch(parts[0]){
                        case INC:
                            executor.execute(() -> {
                                System.out.println("executing inc");
                                if(store.incrementKeyValue(parts[1])==1){
                                    try {
                                        out.write("OK\n".getBytes());          
                                    } catch(Exception e){
                                        System.out.println("Error sending OK");
                                    }
                                }                      
                            });
                        break;
                        case GET:
                            executor.execute(() -> {
                                System.out.println("executing get");
                                // just a proof of concept, should include error handling
                                try{
                                    out.write((store.getKeyValue(parts[1]) + "\n").getBytes());
                                } catch (Exception e) {
                                    System.out.println("Couldn't return value");
                                }
                            });
                        break;
                        case DEC:
                            executor.execute(() -> {
                                System.out.println("executing dec");

                                if(store.decrementKeyValue(parts[1])==1){
                                    try {
                                        out.write("OK\n".getBytes());          
                                    } catch(Exception e){
                                        System.out.println("Error sending OK");
                                    }
                                }                      
                            });
                        break;
                        case PUT:
                            executor.execute(() -> {
                                System.out.println("executing put");

                                try{
                                    if(store.setKeyValue(parts[1], parts[2]) == 1){
                                        System.out.println("OK" + parts[1] + parts[2]);
                                        out.write("OK\n".getBytes());
                                    }
                                } catch (Exception e) {
                                    System.out.println("Couldn't return value");
                                }
                            });
                        break;
                        default:
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}