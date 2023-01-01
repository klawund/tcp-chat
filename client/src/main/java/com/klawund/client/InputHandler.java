package com.klawund.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InputHandler implements Runnable
{
	private static final String QUIT_COMMAND = "/quit";

	private final Client client;
	private BufferedReader in;

	public InputHandler(Client client)
	{
		this.client = client;
	}

	@Override
	public void run()
	{
		try
		{
			in = new BufferedReader(new InputStreamReader(System.in));
			while (!client.isDone())
			{
				String message = in.readLine();
				handleMessage(message);
			}
		}
		catch (Exception e)
		{
			// TODO handle
		}
	}

	private void handleMessage(String message)
	{
		Broadcaster.broadcast(client, message);

		if (message.startsWith(QUIT_COMMAND))
		{
			client.close();
			close();
		}
	}

	private void close()
	{
		try
		{
			in.close();
		}
		catch (Exception e)
		{
			// TODO log
		}
	}
}
