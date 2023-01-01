package com.klawund.server;

public final class MessageHandler
{
	private static final String QUIT_COMMAND = "/quit";

	private MessageHandler()
	{
	}

	public static void handle(Connection conn, String message)
	{
		if (message.startsWith(QUIT_COMMAND))
		{
			Broadcaster.broadcastUserLeftChannelMessage(conn);
			conn.close();
		}
		else
		{
			Broadcaster.broadcast(conn.getChannel(), createMessage(conn, message));
		}
	}

	private static String createMessage(Connection conn, String message)
	{
		return String.format("%s: %s", conn.getUser(), message);
	}
}
