package com.yuanbosu.client.fastdfs.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import com.yuanbosu.client.fastdfs.command.CloseCmd;
import com.yuanbosu.client.fastdfs.command.Command;
import com.yuanbosu.client.fastdfs.command.GroupInfoCmd;
import com.yuanbosu.client.fastdfs.command.QueryDownloadCmd;
import com.yuanbosu.client.fastdfs.command.QueryUpdateCmd;
import com.yuanbosu.client.fastdfs.command.QueryUploadCmd;
import com.yuanbosu.client.fastdfs.command.StorageInfoCmd;
import com.yuanbosu.client.fastdfs.data.GroupInfo;
import com.yuanbosu.client.fastdfs.data.StorageInfo;
import com.yuanbosu.client.fastdfs.data.UploadStorage;
import com.yuanbosu.common.domain.Result;

public class TrackerClientImpl implements TrackerClient {
	private Socket socket;
	private String host;
	private Integer port;
	private Integer connectTimeout = Integer.valueOf(5000);
	private Integer networkTimeout = Integer.valueOf(30000);

	public TrackerClientImpl(String address) {
		String[] hostport = address.split(":");
		this.host = hostport[0];
		this.port = Integer.valueOf(hostport[1]);
	}

	public TrackerClientImpl(String address, Integer connectTimeout, Integer networkTimeout) {
		this(address);
		this.connectTimeout = connectTimeout;
		this.networkTimeout = networkTimeout;
	}

	private Socket getSocket() throws IOException {
		if (this.socket == null) {
			this.socket = new Socket();
			this.socket.setSoTimeout(this.networkTimeout.intValue());
			this.socket.connect(new InetSocketAddress(this.host, this.port.intValue()), this.connectTimeout.intValue());
		}
		return this.socket;
	}

	public void close() throws IOException {
		Socket socket = getSocket();
		Command command = new CloseCmd();
		command.exec(socket);
		socket.close();
		socket = null;
	}

	public Result<UploadStorage> getUploadStorage() throws IOException {
		Socket socket = getSocket();
		Command<UploadStorage> command = new QueryUploadCmd();
		return command.exec(socket);
	}

	public Result<String> getUpdateStorageAddr(String group, String fileName) throws IOException {
		Socket socket = getSocket();
		Command<String> cmd = new QueryUpdateCmd(group, fileName);
		return cmd.exec(socket);
	}

	public Result<String> getDownloadStorageAddr(String group, String fileName) throws IOException {
		Socket socket = getSocket();
		Command<String> cmd = new QueryDownloadCmd(group, fileName);
		return cmd.exec(socket);
	}

	public Result<List<GroupInfo>> getGroupInfos() throws IOException {
		Socket socket = getSocket();
		Command<List<GroupInfo>> cmd = new GroupInfoCmd();
		return cmd.exec(socket);
	}

	public Result<List<StorageInfo>> getStorageInfos(String group) throws IOException {
		Socket socket = getSocket();
		Command<List<StorageInfo>> cmd = new StorageInfoCmd(group);
		return cmd.exec(socket);
	}
}