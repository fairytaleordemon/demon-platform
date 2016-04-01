package com.yuanbosu.client.fastdfs;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanbosu.client.fastdfs.client.StorageClient;
import com.yuanbosu.client.fastdfs.client.StorageClientFactory;
import com.yuanbosu.client.fastdfs.client.TrackerClient;
import com.yuanbosu.client.fastdfs.client.TrackerClientFactory;

public class FastdfsClientFactory {
	private static Logger logger = LoggerFactory.getLogger(FastdfsClientFactory.class);
	private static volatile FastdfsClient fastdfsClient;
	private static FastdfsClientConfig config = null;
	private static final String configFile = "application.properties";

	public static FastdfsClient getFastdfsClient() {
		if (fastdfsClient == null) {
			synchronized (FastdfsClient.class) {
				if (fastdfsClient == null) {
					try {
						logger.debug("FastDFS init config -----------------");
						config = new FastdfsClientConfig(configFile);
					} catch (ConfigurationException e) {
						logger.warn("Load fastdfs config failed.", e);
					}
					int connectTimeout = config.getConnectTimeout();
					int networkTimeout = config.getNetworkTimeout();
					TrackerClientFactory trackerClientFactory = new TrackerClientFactory(
							Integer.valueOf(connectTimeout), Integer.valueOf(networkTimeout));
					StorageClientFactory storageClientFactory = new StorageClientFactory(
							Integer.valueOf(connectTimeout), Integer.valueOf(networkTimeout));
					GenericKeyedObjectPoolConfig trackerClientPoolConfig = config.getTrackerClientPoolConfig();
					GenericKeyedObjectPoolConfig storageClientPoolConfig = config.getStorageClientPoolConfig();
					GenericKeyedObjectPool<String, TrackerClient> trackerClientPool = new GenericKeyedObjectPool<String, TrackerClient>(trackerClientFactory,
							trackerClientPoolConfig);
					GenericKeyedObjectPool<String, StorageClient> storageClientPool = new GenericKeyedObjectPool<String, StorageClient>(storageClientFactory,
							storageClientPoolConfig);
					List<String> trackerAddrs = config.getTrackerAddrs();
					fastdfsClient = new FastdfsClientImpl(trackerAddrs, trackerClientPool, storageClientPool);
				}
			}
		}
		return fastdfsClient;
	}

	public static void release() {
		if (fastdfsClient != null) {
			fastdfsClient.close();
			fastdfsClient = null;
		}
	}
}
