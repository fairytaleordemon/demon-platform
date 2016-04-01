package com.yuanbosu.client.fastdfs.command;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.yuanbosu.common.domain.Result;

public class GetMetaDataCmd extends AbstractCmd<Map<String, String>> {
	public Result<Map<String, String>> exec(Socket socket) throws IOException {
		request(socket.getOutputStream());
		AbstractCmd.Response response = response(socket.getInputStream());
		if (response.isSuccess()) {
			String metaStr = new String(response.getData(), charset);
			Map<String, String> metaData = new HashMap<>();
			String[] rows = metaStr.split("\001");
			for (String row : rows) {
				String[] cols = row.split("\002");
				metaData.put(cols[0], cols[1]);
			}
			return new Result<>(response.getCode(), metaData);
		}
		return new Result<>(response.getCode(), "GetMetaData Error");
	}

	public GetMetaDataCmd(String group, String fileName) {
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
		this.requestCmd = 15;
		this.responseCmd = 100;
		this.responseSize = -1L;
	}
}