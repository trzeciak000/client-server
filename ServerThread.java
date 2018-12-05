import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class ServerThread extends Thread {
	public static volatile Termin[] termins = new Termin[] {
			new Termin(8), new Termin(9), new Termin(10), new Termin(11), new Termin(12), new Termin(13), new Termin(14), new Termin(15), new Termin(16), new Termin(17),
    };
    Socket socket;
    PrintWriter outputWriter;
    ServerThread(Socket socket){
        this.socket = socket;
        try {
        	outputWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        }
    }
    public void run(){
        try {
            String message = null;
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String name = bufferedReader.readLine();
            System.out.println("user '" + name + "' is now connected to the server");
            while (true) {
            	message = bufferedReader.readLine();
                System.out.println("incoming client '" + name + "' message: " + message);
                try {
                	int sth = Integer.parseInt(message);
                	if (sth == 1) {
                		printWriter.println(freeTermins());
                		printWriter.flush();
                	} else if (sth == 2) {
                		printWriter.println(userTermins(name));
                	} else if (sth == 3) {
                		BufferedReader temporaryReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                		String tmp = temporaryReader.readLine();
                		try {
                			int number = Integer.parseInt(tmp);
                			if (number >=0 && number <=9) {
	                    		synchronized(termins[number]) {
		                			if(termins[number].getReserved() == false) {
		                				termins[number].setReserved(true);
		                    			termins[number].setUser(name);
		                    			if(termins[number].getUser() == name) {
		                    				printWriter.println("Termin booked successfuly!");
		                    				Server.sendToAll();
		                    			} else {
		                    				printWriter.println("Error, it's already booked!");
		                    			}
		                			} else {
		                				printWriter.println("Error! Termin is already booked!");
		                			}
	                        	}
                			} else {
                				printWriter.println("Reservation doesn't exists!");
                			}
                		} catch (NumberFormatException e) {
                			
                		}
                	} else if (sth == 4) {
                		BufferedReader temporaryReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                		String tmp = temporaryReader.readLine();
                		try {
                			int number = Integer.parseInt(tmp);
                			if (number >=0 && number <=9) {
                			if(termins[number].getReserved() == true) {
                				if (termins[number].getUser() == name) {
                					termins[number].setReserved(false);
                					termins[number].resetUser();
                					printWriter.println("Termin canceled successfuly!");
                				} else {
                					printWriter.println("Error! This isn't your reservation!");
                				}
                			} else {
                				printWriter.println("Error! Termin isn't already booked!");
                			}
                			} else {
                				printWriter.println("Reservation doesn't exists!");
                			}
                		} catch (NumberFormatException e) {
                			
                		}
                	} else if (sth == 5) {
                		System.out.println("socket closed: '" + name + "'");
                    	socket.close();
                    } else {
                    	
                    }
                } catch (NumberFormatException e) {
                	
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String userTermins(String user) {
    	String yourTermins ="";
    	for (int i = 0; i < 10; i++) {
			if(termins[i].reserved == true && termins[i].user == user) {
				yourTermins += i + ". " + termins[i].hour + ".00;";
			}
		}
    	return yourTermins;
    }
    
    private String freeTermins() {
    	String freeTermins = "";
    	for (int i = 0; i < 10; i++) {
			if(termins[i].reserved == false) {
				freeTermins += i + ". " + termins[i].hour + ".00;";
			} else if (termins[i].reserved == true) {
				freeTermins += i + ". reserved;";
			}
		}
    	return freeTermins;
    }

}
