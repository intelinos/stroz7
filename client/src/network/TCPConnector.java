package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class TCPConnector {
    Socket sock;
    InetAddress host;
    int port = 43498;
    public void setLocalHost() {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e){
            System.out.println(e);
        }
    }
    public Socket connect() {
        setLocalHost();
        System.out.println("LocalHost has been got.");
        int counter=0;
        while (counter <= 5) {
            try {
                sock = new Socket(host, port);
                System.out.println("Connected.");
                return sock;
            } catch (ConnectException e) {
                counter++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        System.out.println("Сервер недоступен, попробуйте позже.");
        System.exit(1);
        return null;
    }
    public OutputStream getOutputStream(){
        try {
            return sock.getOutputStream();
        } catch(IOException e) {
            System.out.println(e);
        } return null;
    }
    public InputStream getInputStream(){
        try {
            return sock.getInputStream();
        } catch(IOException e) {
            System.out.println(e);
        } return null;
    }
    public Socket getSock(){
        return sock;
    }
}
