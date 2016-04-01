package com.yuanbosu.client.fastdfs.command;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.yuanbosu.client.fastdfs.data.GroupInfo;
import com.yuanbosu.common.domain.Result;

public class GroupInfoCmd extends AbstractCmd<List<GroupInfo>> {
	public Result<List<GroupInfo>> exec(Socket socket) throws IOException {
		request(socket.getOutputStream());
		AbstractCmd.Response response = response(socket.getInputStream());
		if (response.isSuccess()) {
			byte[] data = response.getData();
			int dataLength = data.length;
			if (dataLength % 105 != 0) {
				throw new IOException("recv body length: " + data.length + " is not correct");
			}
			List<GroupInfo> groupInfos = new ArrayList<>();
			int offset = 0;
			while (offset < dataLength) {
				GroupInfo groupInfo = new GroupInfo(data, offset);
				groupInfos.add(groupInfo);
				offset += 105;
			}
			return new Result<>(response.getCode(), groupInfos);
		}
		return new Result<>(response.getCode(), "Error");
	}

	public GroupInfoCmd() {
		this.requestCmd = 91;
		this.responseCmd = 100;
		this.responseSize = -1L;
	}
}

