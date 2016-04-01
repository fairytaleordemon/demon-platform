package com.yuanbosu.client.fastdfs.client;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.yuanbosu.common.domain.Result;

public abstract interface StorageClient
{
  public abstract Result<String> upload(File paramFile, String paramString, byte paramByte)
    throws IOException;

  public abstract Result<Boolean> delete(String paramString1, String paramString2)
    throws IOException;

  public abstract Result<Boolean> setMeta(String paramString1, String paramString2, Map<String, String> paramMap)
    throws IOException;

  public abstract Result<Map<String, String>> getMeta(String paramString1, String paramString2)
    throws IOException;

  public abstract void close()
    throws IOException;

  public abstract Result<String> uploadSlave(File paramFile, String paramString1, String paramString2, String paramString3, Map<String, String> paramMap)
    throws IOException;

  public abstract boolean isClosed();
}
