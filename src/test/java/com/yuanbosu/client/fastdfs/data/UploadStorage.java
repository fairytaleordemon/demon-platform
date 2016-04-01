package com.yuanbosu.client.fastdfs.data;

public class UploadStorage {
	private String address;
	private byte pathIndex;

	public UploadStorage(String address, byte pathIndex) {
		this.address = address;
		this.pathIndex = pathIndex;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public byte getPathIndex() {
		return this.pathIndex;
	}

	public void setPathIndex(byte pathIndex) {
		this.pathIndex = pathIndex;
	}
}
