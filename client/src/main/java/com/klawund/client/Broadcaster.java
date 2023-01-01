package com.klawund.client;

public final class Broadcaster
{
	private Broadcaster()
	{
	}

	public static void broadcast(Client client, String message)
	{
		client.getOut().println(message);
	}
}
