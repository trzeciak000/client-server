import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int PORT = 4444;
    public static volatile List<ServerThread> clients = new ArrayList<>();
    
    public static void main(String[] args) throws IOException{
    	new Server();
    }
    
    public Server() {
    	try {
    		ServerSocket serverSocket = new ServerSocket(PORT);
    		System.out.println("Server up and ready for connections...");
    		while(true) {
    			Socket socket = serverSocket.accept();
    			ServerThread client = new ServerThread(socket);
    			client.start();
    			clients.add(client);
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    public static void sendToAll() {
    	for (ServerThread client : clients) {
    		client.outputWriter.println("Termin updated!!! Check list!!!;");
    		System.out.println(client);
    	}
    }
}
