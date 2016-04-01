package com.yuanbosu.client.fastdfs.client;

import java.io.IOException;

import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class TrackerClientFactory implements KeyedPooledObjectFactory<String, TrackerClient> {
	private Integer connectTimeout = Integer.valueOf(5000);
	private Integer networkTimeout = Integer.valueOf(30000);

	public TrackerClientFactory() {
	}

	public TrackerClientFactory(Integer connectTimeout, Integer networkTimeout) {
		this.connectTimeout = connectTimeout;
		this.networkTimeout = networkTimeout;
	}

	public PooledObject<TrackerClient> makeObject(String key) {
		TrackerClient trackerClient = new TrackerClientImpl(key, this.connectTimeout, this.networkTimeout);
		PooledObject<TrackerClient> pooledTrackerClient = new DefaultPooledObject<>(trackerClient);
		return pooledTrackerClient;
	}

	public void destroyObject(String key, PooledObject<TrackerClient> pooledTrackerClient) throws IOException {
		TrackerClient trackerClient = (TrackerClient) pooledTrackerClient.getObject();
		trackerClient.close();
	}

	public boolean validateObject(String key, PooledObject<TrackerClient> p) {
		return true;
	}

	public void activateObject(String key, PooledObject<TrackerClient> p) throws Exception {
	}

	public void passivateObject(String key, PooledObject<TrackerClient> p) throws Exception {
	}
}
