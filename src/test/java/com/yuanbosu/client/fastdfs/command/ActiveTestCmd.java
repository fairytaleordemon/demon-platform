package com.yuanbosu.client.fastdfs.command;

import java.io.IOException;
import java.net.Socket;

import com.yuanbosu.common.domain.Result;

public class ActiveTestCmd extends AbstractCmd<Boolean> {
	public ActiveTestCmd() {
		this.requestCmd = 111;
	}

	public Result<Boolean> exec(Socket socket) throws IOException {
		request(socket.getOutputStream());
		AbstractCmd.Response response = response(socket.getInputStream());
		if (response.isSuccess()) {
			return new Result<>(0, Boolean.valueOf(true));
		}
		return new Result<>(response.getCode(), Boolean.valueOf(false));
	}
}