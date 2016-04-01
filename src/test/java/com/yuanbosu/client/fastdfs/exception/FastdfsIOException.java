package com.yuanbosu.client.fastdfs.exception;

public class FastdfsIOException extends Exception {
	private static final long serialVersionUID = 4234899139606659965L;

	public FastdfsIOException() {
	}

	public FastdfsIOException(String message, Throwable cause) {
		super(message, cause);
	}

	public FastdfsIOException(String message) {
		super(message);
	}

	public FastdfsIOException(Throwable cause) {
		super(cause);
	}
}
