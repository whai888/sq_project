package com.sq.exception;

import org.apache.log4j.Logger;

public class SystemException extends Exception {
	private Logger log ;
	private String message ;
	private String class1 ;
	
	public SystemException(Class class1, String message) {
		this.class1 = class1.getName();
		log = Logger.getLogger(class1);
		log.error(message);
		this.message = message ;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getClass1() {
		return class1;
	}

	public void setClass1(String class1) {
		this.class1 = class1;
	}

}
