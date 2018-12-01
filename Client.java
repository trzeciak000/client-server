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
    public static void main(String[] args) throws UnknownHostException, IOException{
        String name = args[0];
        Socket socket = new Socket("localhost", 4444);
//        DataInputStream dis = new DataInputStream(socket.getInputStream());
//        String msg = dis.readUTF();
//        System.out.println(msg);
        BufferedReader bufferedReaderFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(name);
        BufferedReader bufferedReaderFromCmdPrompt = new java.io.BufferedReader(new InputStreamReader(System.in));
        while(true){
        	displayMenu();
            String readerInput = bufferedReaderFromCmdPrompt.readLine();
            String forCheck = readerInput;
            printWriter.println(readerInput);
            try {
            	int var = Integer.parseInt(forCheck);
                if(var == 1) {
                	String readed = bufferedReaderFromClient.readLine();
                	String reg =";";
                	String[] res = readed.split(reg);
                	for (String out : res) {
                        if (!"".equals(out)) {
                            System.out.print(out + "\n");
                        }
                	}
                } else if (var == 2) {
                	String readed = bufferedReaderFromClient.readLine();
                	String reg =";";
                	String[] res = readed.split(reg);
                	for (String out : res) {
                        if (!"".equals(out)) {
                            System.out.print(out + "\n");
                        }
                	}
                } else if (var == 3) {
                	clientBooking(bufferedReaderFromCmdPrompt, bufferedReaderFromClient, printWriter);
                } else if (var == 4) {
                	System.out.println("Type number of your termin to cancel:\n");
                	String newReaderInput = bufferedReaderFromCmdPrompt.readLine();
                    String newForCheck = newReaderInput;
                    printWriter.println(newReaderInput);
                    try {
                    	Integer.parseInt(newForCheck);
                		String readed = bufferedReaderFromClient.readLine();
                    	System.out.println("\n\n" + readed + "\n\n");
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
    		String readed = bufferedReaderFromClient.readLine();
        	System.out.println("\n\n" + readed + "\n\n");
        } catch (NumberFormatException e) {
        	System.out.println("\n\nError! Type a number!!\n\n");
        }
    }
    
    public static void displayMenu() {
    	System.out.println("Wybierz opcje:\n1. Free termins\n2. Your termins\n3. Book termin\n4. Cancel termin\n5. Exit");
    }
}
