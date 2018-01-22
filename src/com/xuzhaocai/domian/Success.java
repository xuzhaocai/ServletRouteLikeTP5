package com.xuzhaocai.domian;

public class Success {
	
	private String message;
	private String path;
	private int time;
	public Success(String message, String path, int time) {
		super();
		this.message = message;
		this.path = path;
		this.time = time;
	}
	public Success() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}

}
