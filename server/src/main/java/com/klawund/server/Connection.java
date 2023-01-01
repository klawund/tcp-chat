package com.klawund.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable
{
	private final Channel channel;
	private final Socket client;

	private BufferedReader in;
	private PrintWriter out;

	private User user;

	public Connection(Channel channel, Socket client)
	{
		this.channel = channel;
		this.client = client;
	}

	@Override
	public void run()
	{
		setupIn();
		setupOut();

		setupUser();
		handleMessages();
	}

	public Channel getChannel()
	{
		return channel;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	private void setupIn()
	{
		try
		{
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		}
		catch (Exception e)
		{
			// TODO log
		}
	}

	private void setupOut()
	{
		try
		{
			out = new PrintWriter(client.getOutputStream(), true);
		}
		catch (Exception e)
		{
			// TODO log
		}
	}

	private void setupUser()
	{
		boolean valid = true;
		do
		{
			out.println("Enter your nickname: ");
			try
			{
				String nickname = in.readLine();
				if (nickname == null || nickname.isBlank())
				{
					valid = false;
					out.println("Invalid nickname!");
				}
				else
				{
					user = new User(nickname);
				}
			}
			catch (Exception e)
			{
				// TODO log
			}
		}
		while (!valid);
		Broadcaster.broadcastNewUserMessage(this);
	}

	public void print(String message)
	{
		out.println(message);
	}

	private void handleMessages()
	{
		String message;
		try
		{
			while ((message = in.readLine()) != null)
			{
				MessageHandler.handle(this, message);
			}
		}
		catch (Exception e)
		{
			// TODO log
		}
	}

	public void close()
	{
		try
		{
			in.close();
			out.close();

			if (!client.isClosed())
			{
				client.close();
			}
		}
		catch (Exception e)
		{
			// TODO log
		}
	}
}
