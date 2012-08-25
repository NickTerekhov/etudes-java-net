package ru.nsu.ccfit.terekhov.http;

import javax.sound.midi.SysexMessage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		Socket clientSocket = new Socket("google.com", 80);
		final OutputStream outputStream = clientSocket.getOutputStream();
		final InputStream inputStream = clientSocket.getInputStream();

		PrintWriter writer = new PrintWriter(outputStream);
		writer.print("GET / HTTP/1.1\r\n");
		writer.print("Host: google.com\r\n");
		writer.print("\r\n");
		writer.flush();
		
		BufferedReader socketReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String readLine = null;
		while( (readLine = socketReader.readLine()) != null ) {
			System.out.println("'" + readLine + "'");
			if( readLine.equals("")) {
				System.out.println("Empty string");
				inputStream.close();
				outputStream.close();
				clientSocket.close();
				return;
			}
		}
	}
}
