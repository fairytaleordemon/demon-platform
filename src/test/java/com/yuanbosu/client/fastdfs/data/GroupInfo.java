package com.yuanbosu.client.fastdfs.data;

import com.yuanbosu.client.fastdfs.command.AbstractCmd;

public class GroupInfo {
	private String groupName;
	private long totalMB;
	private long freeMB;
	private long trunkFreeMB;
	private int storageCount;
	private int storagePort;
	private int storageHttpPort;
	private int activeCount;
	private int currentWriteServer;
	private int storePathCount;
	private int subdirCountPerPath;
	private int currentTrunkFileId;
	public static final int BYTE_SIZE = 105;

	public GroupInfo(byte[] data, int offset) {
		this.groupName = new String(data, offset, 17).trim();
		offset += 17;
		this.totalMB = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.freeMB = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.trunkFreeMB = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.storageCount = (int) AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.storagePort = (int) AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.storageHttpPort = (int) AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.activeCount = (int) AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.currentWriteServer = (int) AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.storePathCount = (int) AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.subdirCountPerPath = (int) AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.currentTrunkFileId = (int) AbstractCmd.buff2long(data, offset);
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public long getTotalMB() {
		return this.totalMB;
	}

	public void setTotalMB(long totalMB) {
		this.totalMB = totalMB;
	}

	public long getFreeMB() {
		return this.freeMB;
	}

	public void setFreeMB(long freeMB) {
		this.freeMB = freeMB;
	}

	public long getTrunkFreeMB() {
		return this.trunkFreeMB;
	}

	public void setTrunkFreeMB(long trunkFreeMB) {
		this.trunkFreeMB = trunkFreeMB;
	}

	public int getStorageCount() {
		return this.storageCount;
	}

	public void setStorageCount(int storageCount) {
		this.storageCount = storageCount;
	}

	public int getStoragePort() {
		return this.storagePort;
	}

	public void setStoragePort(int storagePort) {
		this.storagePort = storagePort;
	}

	public int getStorageHttpPort() {
		return this.storageHttpPort;
	}

	public void setStorageHttpPort(int storageHttpPort) {
		this.storageHttpPort = storageHttpPort;
	}

	public int getActiveCount() {
		return this.activeCount;
	}

	public void setActiveCount(int activeCount) {
		this.activeCount = activeCount;
	}

	public int getCurrentWriteServer() {
		return this.currentWriteServer;
	}

	public void setCurrentWriteServer(int currentWriteServer) {
		this.currentWriteServer = currentWriteServer;
	}

	public int getStorePathCount() {
		return this.storePathCount;
	}

	public void setStorePathCount(int storePathCount) {
		this.storePathCount = storePathCount;
	}

	public int getSubdirCountPerPath() {
		return this.subdirCountPerPath;
	}

	public void setSubdirCountPerPath(int subdirCountPerPath) {
		this.subdirCountPerPath = subdirCountPerPath;
	}

	public int getCurrentTrunkFileId() {
		return this.currentTrunkFileId;
	}

	public void setCurrentTrunkFileId(int currentTrunkFileId) {
		this.currentTrunkFileId = currentTrunkFileId;
	}
}