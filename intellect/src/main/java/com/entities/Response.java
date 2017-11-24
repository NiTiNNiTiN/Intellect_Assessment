package com.entities;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Response {

	private String 					resMsg;
	private String 					userId;
	private List<ValidationError> 	valErrors;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<ValidationError> getValErrors() {
		return valErrors;
	}

	public void setValErrors(ValidationError valError) {
		valErrors.add(valError);
	}	

}
