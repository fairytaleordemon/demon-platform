package com.yuanbosu.client.fastdfs.command;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Arrays;

import com.yuanbosu.client.fastdfs.data.UploadStorage;
import com.yuanbosu.common.domain.Result;

public class QueryUploadCmd extends AbstractCmd<UploadStorage> {
	public Result<UploadStorage> exec(Socket socket) throws IOException {
		request(socket.getOutputStream());
		AbstractCmd.Response response = response(socket.getInputStream());
		if (response.isSuccess()) {
			byte[] data = response.getData();
			String ip_addr = new String(data, 16, 15).trim();
			int port = (int) buff2long(data, 31);
			byte storePath = data[39];
			UploadStorage uploadStorage = new UploadStorage(ip_addr + ":" + String.valueOf(port), storePath);
			return new Result<>(response.getCode(), uploadStorage);
		}
		return new Result<>(response.getCode(), "Error");
	}

	public QueryUploadCmd() {
		this.requestCmd = 101;
		this.responseCmd = 100;
		this.responseSize = 40L;
	}

	public QueryUploadCmd(String group) throws UnsupportedEncodingException {
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
		this.requestCmd = 104;
		this.responseCmd = 100;
		this.responseSize = 40L;
	}
}