package com.yuanbosu.client.fastdfs.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;

import com.yuanbosu.common.domain.Result;

public class UploadSlaveCmd extends AbstractCmd<String>
{
  private File file;

  public UploadSlaveCmd(File file, String masterfilename, String prefix, String ext)
  {
    this.file = file;
    this.requestCmd = 21;
    this.body2Len = file.length();
    this.responseCmd = 100;
    this.responseSize = -1L;

    byte[] masterfileNameLenByte = long2buff(masterfilename.length());
    byte[] fileSizeLenByte = long2buff(file.length());
    byte[] prefixByte = prefix.getBytes(charset);
    byte[] fileExtNameByte = getFileExtNameByte(ext);
    int fileExtNameByteLen = fileExtNameByte.length;
    if (fileExtNameByteLen > 6) {
      fileExtNameByteLen = 6;
    }

    byte[] masterfilenameBytes = masterfilename.getBytes(charset);

    this.body1 = new byte[38 + masterfilenameBytes.length];

    Arrays.fill(this.body1, new Byte("0"));

    System.arraycopy(masterfileNameLenByte, 0, this.body1, 0, masterfileNameLenByte.length);
    System.arraycopy(fileSizeLenByte, 0, this.body1, 8, fileSizeLenByte.length);
    System.arraycopy(prefixByte, 0, this.body1, 16, prefixByte.length);
    System.arraycopy(fileExtNameByte, 0, this.body1, 32, fileExtNameByteLen);
    System.arraycopy(masterfilenameBytes, 0, this.body1, 38, masterfilenameBytes.length);
  }

  public Result<String> exec(Socket socket)
    throws IOException
  {
    InputStream is = new FileInputStream(this.file);
    request(socket.getOutputStream(), is);
    AbstractCmd.Response response = response(socket.getInputStream());
    if (response.isSuccess()) {
      byte[] data = response.getData();
      String group = new String(data, 0, 16).trim();
      String remoteFileName = new String(data, 16, data.length - 16);
      Result<String> result = new Result<String>(response.getCode());
      result.setData(group + "/" + remoteFileName);
      return result;
    }
    Result<String> result = new Result<String>(response.getCode());
    result.setMessage("Error");
    return result;
  }

  private byte[] getFileExtNameByte(String extName)
  {
    if ((extName != null) && (extName.length() > 0)) {
      return extName.getBytes(charset);
    }
    return new byte[0];
  }
}
