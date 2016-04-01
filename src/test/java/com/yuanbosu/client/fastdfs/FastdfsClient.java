package com.yuanbosu.client.fastdfs;

import java.io.File;
import java.util.Map;

import com.yuanbosu.client.fastdfs.data.FileInfo;

public abstract interface FastdfsClient {
	public abstract String upload(File paramFile) throws Exception;

	public abstract String upload(File paramFile, String paramString) throws Exception;

	public abstract String getUrl(String paramString) throws Exception;

	public abstract Boolean setMeta(String paramString, Map<String, String> paramMap) throws Exception;

	public abstract Map<String, String> getMeta(String paramString) throws Exception;

	public abstract Boolean delete(String paramString) throws Exception;

	public abstract void close();

	public abstract String upload(File paramFile, String paramString, Map<String, String> paramMap) throws Exception;

	public abstract String uploadSlave(File paramFile, String paramString1, String paramString2, String paramString3)
			throws Exception;

	public abstract FileInfo uploadAndGetInfo(File paramFile, Map<String, String> paramMap) throws Exception;

	public abstract FileInfo uploadAndGetInfo(File paramFile) throws Exception;

	public abstract FastdfsInfo getDfsInfo() throws Exception;
}
