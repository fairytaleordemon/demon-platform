package com.yuanbosu.client.fastdfs.client;

import java.io.IOException;
import java.util.List;

import com.yuanbosu.client.fastdfs.data.GroupInfo;
import com.yuanbosu.client.fastdfs.data.StorageInfo;
import com.yuanbosu.client.fastdfs.data.UploadStorage;
import com.yuanbosu.common.domain.Result;

public abstract interface TrackerClient {
	public abstract Result<UploadStorage> getUploadStorage() throws IOException;

	public abstract Result<String> getUpdateStorageAddr(String paramString1, String paramString2) throws IOException;

	public abstract Result<String> getDownloadStorageAddr(String paramString1, String paramString2) throws IOException;

	public abstract Result<List<GroupInfo>> getGroupInfos() throws IOException;

	public abstract Result<List<StorageInfo>> getStorageInfos(String paramString) throws IOException;

	public abstract void close() throws IOException;
}
