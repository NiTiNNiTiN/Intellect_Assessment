package com.entities;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class ValidationError {

	private String code;
	private String field;
	private String message;
	

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
