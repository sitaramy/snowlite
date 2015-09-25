package com.sapient.snowlite.exception;

/**
 * Application exception
 * @author ssh150
 *
 */
public class SnowliteException extends Exception{
	
	private static final long serialVersionUID = -3173303889540987851L;

	public SnowliteException(String message){
		super(message);
	}
	
	public SnowliteException(String message, Throwable t){
		super(message, t);
	}
	
}
