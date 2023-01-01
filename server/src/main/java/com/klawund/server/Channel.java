package com.klawund.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Channel implements Runnable
{
	private static final int DEFAULT_PORT = 9999;

	private final List<Connection> connections = new ArrayList<>();

	private ServerSocket serverSocket;
	private ExecutorService pool;
	private boolean done = false;

	@Override
	public void run()
	{
		setupServerSocket();
		setupPool();

		while (!done)
		{
			final Socket client;
			try
			{
				client = serverSocket.accept();
			}
			catch (Exception e)
			{
				close();
				return;
			}

			final Connection conn = new Connection(this, client);
			addNewConnection(conn);
		}
	}

	public List<Connection> getConnections()
	{
		return connections;
	}

	private void setupServerSocket()
	{
		try
		{
			serverSocket = new ServerSocket(DEFAULT_PORT);
		}
		catch (Exception e)
		{
			close();
		}
	}

	private void setupPool()
	{
		pool = Executors.newCachedThreadPool();
	}

	private void addNewConnection(Connection conn)
	{
		connections.add(conn);
		addToPool(conn);
	}

	public void close()
	{
		done = true;
		closeConnections();

		if (serverSocket == null)
		{
			return;
		}

		if (!serverSocket.isClosed())
		{
			try
			{
				serverSocket.close();
			}
			catch (Exception e)
			{
				// TODO log
			}
		}

	}

	private void closeConnections()
	{
		getConnections().forEach(Connection::close);
	}

	private void addToPool(Connection conn)
	{
		pool.execute(conn);
	}
}
