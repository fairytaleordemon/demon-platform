package com.yuanbosu.client.fastdfs.client;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;

import com.yuanbosu.client.fastdfs.command.ActiveTestCmd;
import com.yuanbosu.client.fastdfs.command.CloseCmd;
import com.yuanbosu.client.fastdfs.command.Command;
import com.yuanbosu.client.fastdfs.command.DeleteCmd;
import com.yuanbosu.client.fastdfs.command.GetMetaDataCmd;
import com.yuanbosu.client.fastdfs.command.SetMetaDataCmd;
import com.yuanbosu.client.fastdfs.command.UploadCmd;
import com.yuanbosu.client.fastdfs.command.UploadSlaveCmd;
import com.yuanbosu.common.domain.Result;

public class StorageClientImpl extends AbstractClient
  implements StorageClient
{
  private Socket socket;
  private String host;
  private Integer port;
  private Integer connectTimeout = Integer.valueOf(5000);
  private Integer networkTimeout = Integer.valueOf(30000);

  private Socket getSocket() throws IOException {
    if (this.socket == null) {
      this.socket = new Socket();
      this.socket.setSoTimeout(this.networkTimeout.intValue());
      this.socket.connect(new InetSocketAddress(this.host, this.port.intValue()), this.connectTimeout.intValue());
    }
    return this.socket;
  }

  public StorageClientImpl(String address)
  {
    String[] hostport = address.split(":");
    this.host = hostport[0];
    this.port = Integer.valueOf(hostport[1]);
  }

  public StorageClientImpl(String address, Integer connectTimeout, Integer networkTimeout) {
    this(address);
    this.connectTimeout = connectTimeout;
    this.networkTimeout = networkTimeout;
  }

  public void close() throws IOException {
    Socket socket = getSocket();
    Command command = new CloseCmd();
    command.exec(socket);
    socket.close();
    socket = null;
  }

  public Result<String> uploadSlave(File file, String fileid, String slavePrefix, String ext, Map<String, String> meta) throws IOException
  {
    Socket socket = getSocket();
    UploadSlaveCmd uploadSlaveCmd = new UploadSlaveCmd(file, fileid, slavePrefix, ext);
    Result<String> result = uploadSlaveCmd.exec(socket);

    if (meta != null) {
      String[] tupple = super.splitFileId(fileid);
      if (tupple != null) {
        String group = tupple[0];
        String fileName = tupple[1];
        setMeta(group, fileName, meta);
      }
    }
    return result;
  }

  public Result<String> upload(File file, String fileName, byte storePathIndex) throws IOException {
    Socket socket = getSocket();
    UploadCmd uploadCmd = new UploadCmd(file, fileName, storePathIndex);
    return uploadCmd.exec(socket);
  }

  public Result<Boolean> delete(String group, String fileName) throws IOException {
    Socket socket = getSocket();
    DeleteCmd deleteCmd = new DeleteCmd(group, fileName);
    return deleteCmd.exec(socket);
  }

  public Result<Boolean> setMeta(String group, String fileName, Map<String, String> meta)
    throws IOException
  {
    Socket socket = getSocket();
    SetMetaDataCmd setMetaDataCmd = new SetMetaDataCmd(group, fileName, meta);
    return setMetaDataCmd.exec(socket);
  }

  public Result<Map<String, String>> getMeta(String group, String fileName)
    throws IOException
  {
    Socket socket = getSocket();
    GetMetaDataCmd getMetaDataCmd = new GetMetaDataCmd(group, fileName);
    return getMetaDataCmd.exec(socket);
  }

  public boolean isClosed()
  {
    if (this.socket == null) {
      return true;
    }

    if (this.socket.isClosed()) {
      return true;
    }

    ActiveTestCmd atcmd = new ActiveTestCmd();
    try {
      Result result = atcmd.exec(getSocket());

      return !((Boolean)result.getData()).booleanValue();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    return true;
  }
}
