package ru.nsu.ccfit.terekhov.net.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main
{

	

	public static void main(String[] args) throws IOException, InterruptedException
	{
		Server server = null;
		Thread serverThread = null;

		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String readStr = null;
		while( (readStr = reader.readLine()) != null ) {
			if( "start".equals(readStr)) {
				server = new Server();
				serverThread = new Thread(server);
				serverThread.start();
				System.out.println("Server started");
			} else if( "stop".equals(readStr)) {
				serverThread.interrupt();
				serverThread.join();
				System.out.println("Server shutdown");
				return;
			} else {
				System.out.println("Incurrect command");
			}
		}

		
	}

	
}
