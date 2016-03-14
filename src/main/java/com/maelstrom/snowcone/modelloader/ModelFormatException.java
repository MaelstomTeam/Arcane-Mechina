package com.maelstrom.snowcone.modelloader;

@SuppressWarnings("all")
public class ModelFormatException extends RuntimeException
{

	public ModelFormatException()
	{
		super();
	}

	public ModelFormatException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ModelFormatException(String message)
	{
		super(message);
	}

	public ModelFormatException(Throwable cause)
	{
		super(cause);
	}

}