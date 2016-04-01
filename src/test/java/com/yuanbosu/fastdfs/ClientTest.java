package com.yuanbosu.fastdfs;

import java.io.File;

import com.yuanbosu.client.fastdfs.FastdfsClientFactory;
import com.yuanbosu.client.fastdfs.data.FileInfo;

public class ClientTest {
	public static void main(String[] args) throws Exception {
		File file = new File("/opt/dfs_test/files/testplan.docx");
//		String fileId = "group1/M00/00/00/wKgAalWvsTOAME-0AABIee8OuNs64.docx";

		Long tm1 = Long.valueOf(System.currentTimeMillis());
		FileInfo fileInfo = FastdfsClientFactory.getFastdfsClient().uploadAndGetInfo(file);
		Long tm2 = Long.valueOf(System.currentTimeMillis());
		FileInfo fileInfo2 = FastdfsClientFactory.getFastdfsClient().uploadAndGetInfo(file);

		Long tm3 = Long.valueOf(System.currentTimeMillis());

//		Long tm4 = Long.valueOf(System.currentTimeMillis());

		System.out.println(tm2.longValue() - tm1.longValue() + ":" + fileInfo.getFileId() + " / " + fileInfo.getUrl());
		System.out
				.println(tm3.longValue() - tm2.longValue() + ":" + fileInfo2.getFileId() + " / " + fileInfo2.getUrl());
	}
}
