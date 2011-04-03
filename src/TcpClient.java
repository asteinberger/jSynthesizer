import java.io.*;
import java.net.*;
	
/**
 * A simple TCP client that connects to a remote server
 * on the specified hostName (first command-line parameter)
 * and port (second command-line parameter).
 * 
 * Usage: java TcpClientThreaded hostName portNum numToSendToServer loopNum
 * 		e.g.:  java TcpClientThreaded localhost 9000 20 10
 * 
 * @author Adam Steinberger
 */
public class TcpClient {
	
	/**
	 * TCP Client Main Method
	 * @param args hostName portNum numToSendToServer loopNum
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		// Check for correct arguments
		if (args.length >= 4 && isParsableToInt(args[1]) && isParsableToInt(args[2])
				&& isParsableToInt(args[3])) {
			
			// Create FileWriter object for clientData CSV
		    FileWriter dataStream = new FileWriter("tcpClientData" + 
		    		Long.toString(System.currentTimeMillis()) + ".csv", true);
			
		    // Create BufferedWriter object to append to clientData CSV
		    BufferedWriter dataFile = new BufferedWriter(dataStream);
		    dataFile.write("event,client,duration\n");
		    
		    // Initialize connection data
			String serverDataOut = args[2];
			int loop = Integer.parseInt(args[3]);
			final double mega = 1000000.0;
			
			for (int index = 0; index < loop; index++) {
				
				// start timer
				long start = System.nanoTime();
				
				// connect to the remote host (args[0]) on the specified port (args[1])
				Socket clientSocket = new Socket(args[0], Integer.parseInt(args[1]));
				String socket = clientSocket.getLocalSocketAddress().toString();
				
				// stop timer
				double time = (System.nanoTime() - start) / mega;
				System.out.println("! Connected to: " +
						clientSocket.getInetAddress() + " on port: " +
						clientSocket.getLocalPort() + "  (" + time + " ms)");
				dataFile.write("connect,"+socket+","+time+"\n");
				
				// set up the readers/writers
				DataOutputStream serverOut = 
					new DataOutputStream(clientSocket.getOutputStream());
				BufferedReader serverIn = 
					new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));
				
				// start timer
				start = System.nanoTime();
				
				// write the string to the remote host.  NOTE:
				// we add a newline character to the end of the string to indicate
				// to the remote host that the transmission is complete
				serverOut.writeBytes(serverDataOut + "\n");
				
				// stop timer
				time = (System.nanoTime() - start) / mega;
				System.out.println("> Sent: " + serverDataOut + "  (" + time + " ms)");
				dataFile.write("send,"+socket+","+time+"\n");
				
				// now we read the data returned from the remote host
				// check if server data is a positive integer
				if (isParsableToInt(serverDataOut)) {
					
					if (Integer.parseInt(serverDataOut) > 0) {
						
						for (int i = 0; i < Integer.parseInt(serverDataOut); i++) {
							
							// start timer
							start = System.nanoTime();
							
							// read line of data from server
							String serverDataIn = serverIn.readLine();
							
							// stop timer
							time = (System.nanoTime() - start) / mega;
							System.out.println("< Received: " + serverDataIn + "  (" + time + " ms)");
							dataFile.write("receive,"+socket+","+time+"\n");
							
						} // end for
						
					} else {
						
						// start timer
						start = System.nanoTime();
						
						// read line of data from server
						String serverDataIn = serverIn.readLine();
						
						// stop timer
						time = (System.nanoTime() - start) / mega;
						System.out.println("< Received: " + serverDataIn + "  (" + time + " ms)");
						dataFile.write("receive,"+socket+","+time+"\n");
						
					} // end if
					
				} else {
					
					// start timer
					start = System.nanoTime();
					
					// read line of data from server
					String serverDataIn = serverIn.readLine();
					
					// stop timer
					time = (System.nanoTime() - start) / mega;
					System.out.println("< Received: " + serverDataIn + "  (" + time + " ms)");
					dataFile.write("receive,"+socket+","+time+"\n");
					
				} // end if
				
				// start timer
				start = System.nanoTime();
				
				// and... we're done - close the socket
				clientSocket.close();
				
				// stop timer
				time = (System.nanoTime() - start) / mega;
				System.out.println("! Disconnected from remote host: " +
						clientSocket.getInetAddress() + "  (" + time + " ms)");
				dataFile.write("disconnect,"+socket+","+time+"\n");
				
			} // end for
			
			// Close BufferedWriter object
			dataFile.close();
			
			// Close FileWriter object
			dataStream.close();
			
		} else {
			System.out.println("Usage: java TcpClientThreaded hostName portNum numToSendToServer loopNum\n" +
					"e.g. java TcpClientThreaded localhost 9000 20 10");
		} // end if
		
	} // end main()
	
	/**
	 * Check whether string s contains an integer
	 * @param s String to check
	 * @return true if int, else false
	 */
	public static boolean isParsableToInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		} // end try/catch
	} // end isParsableToInt()
	
} // end TcpClientThreaded class
