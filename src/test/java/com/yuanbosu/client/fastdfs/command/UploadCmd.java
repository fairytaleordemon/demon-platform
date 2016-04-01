package com.yuanbosu.client.fastdfs.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;

import com.yuanbosu.common.domain.Result;

public class UploadCmd extends AbstractCmd<String>
{
  private File file;

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
      Result<String> result = new Result<>(response.getCode());
      result.setData(group + "/" + remoteFileName);
      return result;
    }
    Result<String> result = new Result<>(response.getCode());
    result.setMessage("Error");
    return result;
  }

  public UploadCmd(File file, String extName, byte storePathIndex)
  {
    this.file = file;
    this.requestCmd = 11;
    this.body2Len = file.length();
    this.responseCmd = 100;
    this.responseSize = -1L;
    this.body1 = new byte[15];
    Arrays.fill(this.body1, new Byte("0"));
    this.body1[0] = storePathIndex;
    byte[] fileSizeByte = long2buff(file.length());
    byte[] fileExtNameByte = getFileExtNameByte(extName);
    int fileExtNameByteLen = fileExtNameByte.length;
    if (fileExtNameByteLen > 6) {
      fileExtNameByteLen = 6;
    }
    System.arraycopy(fileSizeByte, 0, this.body1, 1, fileSizeByte.length);

    if (extName != null) System.arraycopy(fileExtNameByte, 0, this.body1, fileSizeByte.length + 1, fileExtNameByteLen); 
  }

  private byte[] getFileExtNameByte(String fileName)
  {
    if (fileName == null) return "".getBytes(charset);

    String fileExtName = null;
    int nPos = fileName.lastIndexOf(46);
    if ((nPos > 0) && (fileName.length() - nPos <= 7)) {
      fileExtName = fileName.substring(nPos + 1);
      if ((fileExtName != null) && (fileExtName.length() > 0)) {
        return fileExtName.getBytes(charset);
      }
      return new byte[0];
    }

    return fileName.getBytes(charset);
  }
}
