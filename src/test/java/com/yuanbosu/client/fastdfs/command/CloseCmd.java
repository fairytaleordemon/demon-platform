package com.yuanbosu.client.fastdfs.command;

import java.io.IOException;
import java.net.Socket;

import com.yuanbosu.common.domain.Result;

public class CloseCmd extends AbstractCmd<Boolean>
{
  public CloseCmd()
  {
    this.requestCmd = 82;
  }

  public Result<Boolean> exec(Socket socket) throws IOException
  {
    request(socket.getOutputStream());
    return new Result<Boolean>(0, Boolean.valueOf(true));
  }
}