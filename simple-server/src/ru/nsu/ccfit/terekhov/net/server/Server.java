package ru.nsu.ccfit.terekhov.net.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable
{
	public static final int LISTEN_PORT = 9999;
	private final ExecutorService threadPool =
			Executors.newCachedThreadPool();

	@Override
	public void run()
	{
		try {
			ServerSocket serverSocket = new ServerSocket(LISTEN_PORT);
			System.out.println(String.format("Start listen on port %d", LISTEN_PORT));
			for (; ; ) {


				final Socket clientSocket = serverSocket.accept();
				if( Thread.currentThread().isInterrupted() ) {
					System.out.println("Shutdowing server");
					serverSocket.close();
					return;
				}
				final Runnable socketprocessor = new Runnable()
				{
					@Override
					public void run()
					{
						System.out.println("Acceped socket from " + clientSocket.getInetAddress());
						try {
							processClient(clientSocket);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				threadPool.submit(socketprocessor);


			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processClient(Socket clientSocket) throws IOException
	{
		final InputStream inputStream = clientSocket.getInputStream();
		final OutputStream outputStream = clientSocket.getOutputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter writer = new PrintWriter(outputStream);

		String readedLine = null;
		while ((readedLine = reader.readLine()) != null) {
			System.out.println(String.format("Received string %s", readedLine));
			if ("date".equals(readedLine)) {
				String currentDate = new Date().toString();
				writer.print("Current date is " + currentDate);
				writer.flush();
			} else if ("exit".equals(readedLine)) {
				writer.print("Goodbye");
				writer.flush();
				inputStream.close();
				outputStream.close();
				clientSocket.close();
				return;
			}
		}
	}
}

