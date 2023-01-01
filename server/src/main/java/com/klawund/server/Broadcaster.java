package com.klawund.server;

public final class Broadcaster
{
	private Broadcaster()
	{
	}

	public static void broadcast(Channel channel, String message)
	{
		channel.getConnections().forEach(conn -> conn.print(message));
	}

	public static void broadcastNewUserMessage(Connection conn)
	{
		Broadcaster.broadcast(conn.getChannel(), getNewConnectionMessage(conn));
	}

	static String getNewConnectionMessage(Connection conn)
	{
		return String.format("%s has entered the channel.", conn.getUser());
	}

	public static void broadcastUserLeftChannelMessage(Connection conn)
	{
		Broadcaster.broadcast(conn.getChannel(), getDisconnectionMessage(conn));
	}

	static String getDisconnectionMessage(Connection conn)
	{
		return String.format("%s has left the channel.", conn.getUser());
	}
}
