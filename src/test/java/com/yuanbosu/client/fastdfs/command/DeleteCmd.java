package com.yuanbosu.client.fastdfs.command;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import com.yuanbosu.common.domain.Result;

public class DeleteCmd extends AbstractCmd<Boolean> {
	public Result<Boolean> exec(Socket socket) throws IOException {
		request(socket.getOutputStream());
		AbstractCmd.Response response = response(socket.getInputStream());
		if (response.isSuccess()) {
			return new Result<>(response.getCode(), Boolean.valueOf(true));
		}
		return new Result<>(response.getCode(), "Delete Error");
	}

	public DeleteCmd(String group, String fileName) {
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
		this.requestCmd = 12;
		this.responseCmd = 100;
		this.responseSize = 0L;
	}
}
