import java.io.*;
import java.net.*;

/**
 * A TCP Server class that listens on a given TCP port
 * (specified as the command-line argument to the program)
 * That reads a string from the client, and then sends that 
 * same data back to the client.
 * 
 * Usage:  java TcpServerThreaded portNum
 * 		e.g. java TcpServerThreaded 9000
 * 
 * @author Adam Steinberger
 */
public class TcpServer implements Runnable {
	
	private Socket connection;
	private static int numConnect = 0;
	private static long code = System.currentTimeMillis();
	
	/**
	 * Public TcpServerThreaded Constructor
	 * @param connection a specific Socket object
	 * @param c number of connections to server
	 */
	public TcpServer(Socket connection, int c) {
		this.connection = connection;
		numConnect = c;
	} // end TcpServerThreaded constructor
	
	/**
	 * TCP Server Main Method
	 * @param args portNum
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		// check for port number argument
		if (args.length >= 1 && isParsableToInt(args[0])) {
			
			// start timer
			final double mega = 1000000.0;
			long start = System.nanoTime();
			
			// start server socket listener
			ServerSocket listener = new ServerSocket(Integer.parseInt(args[0]));
			
			// stop timer
			double time = (System.nanoTime() - start) / mega;
			System.out.println("! Listening on TCP port: " + listener.getLocalPort() + 
					"  (" + time + " ms)");
			out("listen","",time);
			
			// start infinite loop
			while (true) {
				
				// start timer
				start = System.nanoTime();
				
				// listen for a client connection
				Socket connection = listener.accept();
				String socket = connection.getRemoteSocketAddress().toString();
				
				// stop timer
				time = (System.nanoTime() - start) / mega;
				System.out.println("! Accepted connection from: " +
						connection.getRemoteSocketAddress() + "  (" + time + " ms)");
				out("connect", socket, time);
				
				// start timer
				start = System.nanoTime();
				
				// spawn new connection thread
				numConnect++;
				TcpServer server = new TcpServer(connection, numConnect);
				Thread thread = new Thread(server, "t"+numConnect);
				thread.start();
				
				// stop timer
				time = (System.nanoTime() - start) / mega;
				System.out.println("! Started connection thread: " +
						thread.getName() + "  (" + time + " ms)");
				out("new thread", socket, time);
				
			} // end while
			
		} else {
			System.out.println("Usage: java TcpServerThreaded portNum\n" +
					"e.g. java TcpServerThreaded 9000");
		} // end if
		
	} // end main()
	
	/**
	 * Get #-receipt to send back to client
	 * @param clientDataIn
	 * @return string of #s
	 */
	private static String receipt(int clientDataIn) {
		String result = "";
		for (int i = 0; i < clientDataIn; i++)
			result += "#";
		return result;
	} // end receipt()
	
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
	
	/**
	 * Append data to serverData CVS file
	 * @param event
	 * @param socket
	 * @param duration
	 * @throws IOException
	 */
	private static void out(String event, String socket, double duration) throws IOException {
		
		// Create FileWriter object for clientData CSV
	    FileWriter dataStream = new FileWriter("tcpServerData" + code + ".csv", true);
	    
	    // Create BufferedWriter object to append to clientData CSV
	    BufferedWriter dataFile = new BufferedWriter(dataStream);
	    
	    // Write data to file
	    dataFile.write(event + "," + socket + "," + Double.toString(duration) + "\n");
	    
	    // Close BufferedWriter object
		dataFile.close();
		
		// Close FileWriter object
		dataStream.close();
	    
	} // end out()
	
	/**
	 * Each connection thread will run this code when connected
	 */
	public void run() {
		
		try {
			
			// set up our readers/writers
			BufferedReader clientIn = new BufferedReader(
					new InputStreamReader(this.connection.getInputStream()));
			DataOutputStream clientOut = new DataOutputStream(
					this.connection.getOutputStream());
			String socket = this.connection.getRemoteSocketAddress().toString();
			
			// start timer
			long start = System.nanoTime();
			final double mega = 1000000.0;
			
			// read a line from the client
			String clientDataIn = clientIn.readLine();
			
			// stop timer
			double time = (System.nanoTime() - start) / mega;
			System.out.println("< Received: " + clientDataIn + "  (" + time + " ms)");
			out("receive",socket,time);
			String clientDataOut;
			
			// check if client data is a positive integer
			if (isParsableToInt(clientDataIn)) {
				
				Integer length = Integer.parseInt(clientDataIn);
				
				// write that data back to the client - NOTE:
				// we append a newline to the end of the data to indicate
				// the end of our transmission to the client
				if (Integer.parseInt(clientDataIn) > 0) {
					
					for (int i = 0; i < length; i++) {
						
						// start timer
						start = System.nanoTime();
						
						// make receipt and send back to client
						clientDataOut = receipt(length-i);
						clientOut.writeBytes(clientDataOut + "\n");
						
						// stop timer
						time = (System.nanoTime() - start) / mega;
						System.out.println("> Sent: " + clientDataOut + "  (" + time + " ms)");
						out("send",socket,time);
						
					} // end for
					
				} else {
					
					// start timer
					start = System.nanoTime();
					
					// send error message back to client
					clientDataOut = "Invalid input \'" + clientDataIn + "\' received. " +
							"Please specify a positive integer value.";
					clientOut.writeBytes(clientDataOut + "\n");
					
					// stop timer
					time = (System.nanoTime() - start) / mega;
					System.out.println("> Sent: " + clientDataOut + "  (" + time + " ms)");
					out("send",socket,time);
					
				} // end if
				
			} else {
				
				// start timer
				start = System.nanoTime();
				
				// send error message back to client
				clientDataOut = "Invalid input \'" + clientDataIn + "\' received. " +
						"Please specify a positive integer value.";
				clientOut.writeBytes(clientDataOut + "\n");
				
				// stop timer
				time = (System.nanoTime() - start) / mega;
				System.out.println("> Sent: " + clientDataOut + "  (" + time + " ms)");
				out("send",socket,time);
				
			} // end if
			
			// start timer
			start = System.nanoTime();
			
			// close the connection
			this.connection.close();
			
			// stop timer
			time = (System.nanoTime() - start) / mega;
			System.out.println("! Remote Client Disconnected: " + 
					this.connection.getInetAddress() + "  (" + time + " ms)");
			out("disconnect",socket,time);
			
			numConnect--;
			
		} catch (IOException e) {
			e.printStackTrace();
		} // end try/catch
		
	} // end run()
	
} // end TcpServerThreaded class
