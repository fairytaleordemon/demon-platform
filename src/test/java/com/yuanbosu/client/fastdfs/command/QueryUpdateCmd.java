package com.yuanbosu.client.fastdfs.command;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import com.yuanbosu.common.domain.Result;

public class QueryUpdateCmd extends AbstractCmd<String> {
	public Result<String> exec(Socket socket) throws IOException {
		request(socket.getOutputStream());
		AbstractCmd.Response response = response(socket.getInputStream());
		if (response.isSuccess()) {
			byte[] data = response.getData();
			String ip = new String(data, 16, 15).trim();
			int port = (int) buff2long(data, 31);
			Result<String> result = new Result<>(response.getCode());
			result.setData(ip + ":" + String.valueOf(port));
			return result;
		}

		Result<String> result = new Result<>(response.getCode());
		result.setMessage("Error");
		return result;
	}

	public QueryUpdateCmd(String group, String fileName) {
		byte[] groupByte = group.getBytes(charset);
		int group_len = groupByte.length;
		if (group_len > 16) {
			group_len = 16;
		}
		byte[] fileNameByte = fileName.getBytes(charset);
		this.body1 = new byte[16 + fileNameByte.length];
		Arrays.fill(this.body1, new Byte("0"));
		System.arraycopy(groupByte, 0, this.body1, 0, group_len);
		System.arraycopy(fileNameByte, 0, this.body1, 16, fileNameByte.length);
		this.requestCmd = 103;
		this.responseCmd = 100;
		this.responseSize = 39L;
	}
}


