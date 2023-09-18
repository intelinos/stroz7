package connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class TCPNIOConnection {
    InetAddress host;
    int port = 43498;
    SocketAddress addr;
    SocketChannel sock;
    public SocketChannel connect(){
        try (ServerSocketChannel serv = ServerSocketChannel.open()) {
            Selector selector = Selector.open();
            serv.configureBlocking(false);
            host = InetAddress.getLocalHost();
            addr = new InetSocketAddress(host, port);
            serv.bind(addr);
            serv.register(selector, SelectionKey.OP_ACCEPT);
            while(true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                for (var iter = keys.iterator(); iter.hasNext();){
                    SelectionKey key = iter.next();
                    iter.remove();
                    if (key.isValid()) {
                        /*if(key.isAcceptable()){doAccept();}
                        if(key.isReadable()){doRead();}
                        if (key.isWritable()){doWrite();}*/
                    }
                }
                sock = serv.accept();
                while (true) {
                    if (sock == null) {
                        sock = serv.accept();
                    } else return sock;
                }
            }
        } catch(IOException e) {
            System.out.println(e);
        } return null;
    }
}

