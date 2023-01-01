package com.klawund.server;

public final class User
{
	private final String nickname;

	public User(String nickname)
	{
		this.nickname = nickname;
	}

	@Override
	public String toString()
	{
		return nickname;
	}
}
