package com.yuanbosu.client.fastdfs.client;

import com.yuanbosu.client.fastdfs.command.AbstractCmd;

public abstract class AbstractClient {
	protected String[] splitFileId(String fileid) {
		return AbstractCmd.splitFileId(fileid);
	}
}
