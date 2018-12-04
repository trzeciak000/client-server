import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException{
        String name = args[0];
        Socket socket = new Socket("localhost", 4444);
        Reader r = new Reader(socket);
    	r.start();
        BufferedReader bufferedReaderFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(name);
        BufferedReader bufferedReaderFromCmdPrompt = new java.io.BufferedReader(new InputStreamReader(System.in));
        while(true){
        	Thread.sleep(100);
        	displayMenu();
            String readerInput = bufferedReaderFromCmdPrompt.readLine();
            String forCheck = readerInput;
            printWriter.println(readerInput);
            try {
            	int var = Integer.parseInt(forCheck);
                if(var == 1) {
                	System.out.println("");
                } else if (var == 2) {
                	System.out.println("");
                } else if (var == 3) {
                	clientBooking(bufferedReaderFromCmdPrompt, bufferedReaderFromClient, printWriter);
                } else if (var == 4) {
                	System.out.println("Type number of your termin to cancel:\n");
                	String newReaderInput = bufferedReaderFromCmdPrompt.readLine();
                    String newForCheck = newReaderInput;
                    printWriter.println(newReaderInput);
                    try {
                    	Integer.parseInt(newForCheck);
                    } catch (NumberFormatException e) {
                    	System.out.println("\n\nError! Type a number!!\n\n");
                    }
                } else if (var == 5) {
                	System.exit(0);
                } else {
                	System.out.println("\n\nError! Wrong number!\n\n");
                }
            } catch (NumberFormatException e) {
            	System.out.println("\n\nError! Write number!!\n\n");
            }
            
        }
    }
    
    public synchronized static void clientBooking(BufferedReader bufferedReaderFromCmdPrompt, BufferedReader bufferedReaderFromClient, PrintWriter printWriter) throws IOException {
    	System.out.println("Type number of termin to book:\n");
    	String newReaderInput = bufferedReaderFromCmdPrompt.readLine();
        String newForCheck = newReaderInput;
        printWriter.println(newReaderInput);
        try {
        	Integer.parseInt(newForCheck);
        } catch (NumberFormatException e) {
        	System.out.println("\n\nError! Type a number!!\n\n");
        }
    }
    
    public static void displayMenu() {
    	System.out.println("Wybierz opcje:\n1. Free termins\n2. Your termins\n3. Book termin\n4. Cancel termin\n5. Exit");
    }
    
    public static class Reader extends Thread {
    	Socket socket = new Socket();
    	Reader(Socket socket){
    		this.socket = socket;
    	}
    	public void run() {
    		while(true) {
    		try {
    			BufferedReader bufferedReaderFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    			if(bufferedReaderFromClient != null) {   				
    				String readed = bufferedReaderFromClient.readLine();
                	String reg =";";
                	String[] res = readed.split(reg);
                	for (String out : res) {
                        if (!"".equals(out)) {
                            System.out.print(out + "\n");
                        }
                	}
    			}
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    }
}
