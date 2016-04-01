package com.yuanbosu.client.fastdfs.client;

import java.io.IOException;

import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class StorageClientFactory
implements KeyedPooledObjectFactory<String, StorageClient>
{
private Integer connectTimeout = Integer.valueOf(5000);
private Integer networkTimeout = Integer.valueOf(30000);

public StorageClientFactory()
{
}

public StorageClientFactory(Integer connectTimeout, Integer networkTimeout)
{
  this.connectTimeout = connectTimeout;
  this.networkTimeout = networkTimeout;
}

public PooledObject<StorageClient> makeObject(String key)
{
  StorageClientImpl storageClient = new StorageClientImpl(key, this.connectTimeout, this.networkTimeout);
  PooledObject pooledStorageClient = new DefaultPooledObject(storageClient);
  return pooledStorageClient;
}

public void destroyObject(String key, PooledObject<StorageClient> pooledStorageClient) throws IOException
{
  StorageClient storageClient = (StorageClient)pooledStorageClient.getObject();
  storageClient.close();
}

public boolean validateObject(String key, PooledObject<StorageClient> p)
{
  StorageClient storageClient = (StorageClient)p.getObject();

  return !storageClient.isClosed();
}

public void activateObject(String key, PooledObject<StorageClient> p)
  throws Exception
{
}

public void passivateObject(String key, PooledObject<StorageClient> p)
  throws Exception
{
}
}
