package com.yuanbosu.client.fastdfs.command;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;

import com.yuanbosu.common.domain.Result;

public class SetMetaDataCmd extends AbstractCmd<Boolean> {
	public Result<Boolean> exec(Socket socket) throws IOException {
		request(socket.getOutputStream());
		AbstractCmd.Response response = response(socket.getInputStream());
		if (response.isSuccess()) {
			return new Result(response.getCode(), Boolean.valueOf(true));
		}
		return new Result(response.getCode(), "SetMetaData Error");
	}

	public SetMetaDataCmd(String group, String fileName, Map<String, String> metaData) {
		byte[] groupByte = group.getBytes(charset);
		int group_len = groupByte.length;
		if (group_len > 16) {
			group_len = 16;
		}
		byte[] fileNameByte = fileName.getBytes(charset);
		byte[] fileNameSizeByte = long2buff(fileNameByte.length);
		byte[] metaDataByte = metaDataToStr(metaData).getBytes(charset);
		byte[] metaDataSizeByte = long2buff(metaDataByte.length);

		this.body1 = new byte[33 + fileNameByte.length + metaDataByte.length];

		Arrays.fill(this.body1, new Byte("0"));
		int pos = 0;
		System.arraycopy(fileNameSizeByte, 0, this.body1, pos, fileNameSizeByte.length);
		pos += 8;
		System.arraycopy(metaDataSizeByte, 0, this.body1, pos, metaDataSizeByte.length);
		pos += 8;
		this.body1[pos] = 79;
		pos++;
		System.arraycopy(groupByte, 0, this.body1, pos, group_len);
		pos += 16;
		System.arraycopy(fileNameByte, 0, this.body1, pos, fileNameByte.length);
		pos += fileNameByte.length;
		System.arraycopy(metaDataByte, 0, this.body1, pos, metaDataByte.length);

		this.requestCmd = 13;
		this.responseCmd = 100;
		this.responseSize = 0L;
	}
}
