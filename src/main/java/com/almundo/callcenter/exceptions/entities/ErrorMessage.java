package com.almundo.callcenter.exceptions.entities;

import java.io.Serializable;

public class ErrorMessage implements Serializable {
	
	private static final long serialVersionUID = 8188521335494726566L;

	private Integer httpStatus;
    private String message;
    private Integer code;
 
    public ErrorMessage() {
    }
 
    public ErrorMessage(Integer httpStatus, String message, Integer code) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = code;
    }
 
    public Integer getHttpStatus() {
        return httpStatus;
    }
 
    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
 
    public Integer getCode() {
        return code;
    }
 
    public void setCode(Integer code) {
        this.code = code;
    }
}
