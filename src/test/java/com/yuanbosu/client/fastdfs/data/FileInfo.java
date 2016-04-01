package com.yuanbosu.client.fastdfs.data;

import java.util.Map;

public class FileInfo
{
  private String fileId;
  private String url;
  private Map<String, String> meta;

  public FileInfo(String fileId, String url)
  {
    this.fileId = fileId;
    this.url = url;
  }

  public FileInfo(String fileId, String url, Map<String, String> meta) {
    this.fileId = fileId;
    this.url = url;
    this.meta = meta;
  }

  public String getFileId() {
    return this.fileId;
  }

  public void setFileId(String fileId) {
    this.fileId = fileId;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Map<String, String> getMeta() {
    return this.meta;
  }

  public void setMeta(Map<String, String> meta) {
    this.meta = meta;
  }
}