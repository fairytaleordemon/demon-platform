package com.yuanbosu.client.fastdfs.command;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yuanbosu.client.fastdfs.data.StorageInfo;
import com.yuanbosu.common.domain.Result;

public class StorageInfoCmd extends AbstractCmd<List<StorageInfo>> {
	public Result<List<StorageInfo>> exec(Socket socket) throws IOException {
		request(socket.getOutputStream());
		AbstractCmd.Response response = response(socket.getInputStream());
		if (response.isSuccess()) {
			byte[] data = response.getData();
			int dataLength = data.length;
			if (dataLength % 612 != 0) {
				throw new IOException("recv body length: " + data.length + " is not correct");
			}
			List<StorageInfo> storageInfos = new ArrayList<>();
			int offset = 0;
			while (offset < dataLength) {
				StorageInfo storageInfo = new StorageInfo(data, offset);
				storageInfos.add(storageInfo);
				offset += 612;
			}
			return new Result<>(response.getCode(), storageInfos);
		}
		return new Result<>(response.getCode(), "Error");
	}

	public StorageInfoCmd(String group) throws UnsupportedEncodingException {
		byte[] bs = group.getBytes("UTF-8");
		this.body1 = new byte[16];
		int group_len;
		if (bs.length <= 16)
			group_len = bs.length;
		else {
			group_len = 16;
		}
		Arrays.fill(this.body1, new Byte("0"));
		System.arraycopy(bs, 0, this.body1, 0, group_len);
		this.requestCmd = 92;
		this.responseCmd = 100;
		this.responseSize = -1L;
	}

	public StorageInfoCmd(String group, String ip) throws UnsupportedEncodingException {
		byte[] groupByte = group.getBytes("UTF-8");
		byte[] ipByte = ip.getBytes("UTF-8");
		this.body1 = new byte[16 + ipByte.length];
		int group_len;
		if (groupByte.length <= 16)
			group_len = groupByte.length;
		else {
			group_len = 16;
		}
		Arrays.fill(this.body1, new Byte("0"));
		System.arraycopy(groupByte, 0, this.body1, 0, group_len);
		System.arraycopy(ipByte, 0, this.body1, 16, ipByte.length);
		this.requestCmd = 92;
		this.responseCmd = 100;
		this.responseSize = -1L;
	}
}
