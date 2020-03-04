package ar.com.igrib;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class App {
    private App() {
    }

    private static final int BUFF_SIZE = 10240;

    public static void getResource(String urlString) throws IOException {
        String[] urlParts = urlString.split("/");
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        connection.setDoOutput(true);
        OutputStream out = connection.getOutputStream();
        
        String msg = "GET HTTP/1.1\nhost: " + urlParts[1] + "\nconnection: keep alive\n\n";
        out.write(msg.getBytes());
        
        byte[] receivedData = new byte[BUFF_SIZE];
        
        InputStream in = connection.getInputStream();
        
        int rcv = 0;
        int off = 0;
        while((rcv = in.read(receivedData, off, BUFF_SIZE-off)) != 0){
            System.out.println(new String(receivedData));
            off += rcv;
        }

    }

    public static void main(String[] args) throws Exception{
        getResource("https://wiki.debian.org/Bind9");
    }
}
