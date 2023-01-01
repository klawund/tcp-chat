package com.klawund.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable
{
	private static final int DEFAULT_PORT = 9999;

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	private boolean done = false;

	@Override
	public void run()
	{
		setupSocket();
		setupIn();
		setupOut();

		new Thread(new InputHandler(this)).start();

		String inMessage;
		try
		{
			while ((inMessage = in.readLine()) != null)
			{
				System.out.println(inMessage);
			}
		}
		catch (Exception e)
		{
			// TODO a
		}

	}

	public PrintWriter getOut()
	{
		return out;
	}

	public boolean isDone()
	{
		return done;
	}

	private void setupSocket()
	{
		try
		{
			socket = new Socket(requestChannelAddress(), DEFAULT_PORT);
		}
		catch (Exception e)
		{
			// TODO log
		}
	}

	private String requestChannelAddress()
	{
		// TODO scanner
		return "127.0.0.1";
	}

	private void setupIn()
	{
		try
		{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch (Exception e)
		{
			// TODO close
		}
	}

	private void setupOut()
	{
		try
		{
			out = new PrintWriter(socket.getOutputStream(), true);
		}
		catch (Exception e)
		{
			// TODO close
		}
	}

	public void close()
	{
		done = true;
		if (socket == null)
		{
			return;
		}

		try
		{
			if (!socket.isClosed())
			{
				socket.close();
			}

			in.close();
			out.close();
		}
		catch (Exception e)
		{
			// TODO log
		}
	}
}
