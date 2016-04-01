package com.yuanbosu.client.fastdfs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

public class FastdfsClientConfig {
	public static final int DEFAULT_CONNECT_TIMEOUT = 5;
	public static final int DEFAULT_NETWORK_TIMEOUT = 30;
	public static final String DEFAULT_CHARSET = "ISO8859-1";
	public static final int DEFAULT_TRACKER_HTTP_PORT = 80;
	private int connectTimeout = 5000;
	private int networkTimeout = 30000;
	private String charset;
	private int trackerHttpPort;
	private boolean antiStealToken;
	private String secretKey;
	private List<String> trackerAddrs = new ArrayList<String>();

	public FastdfsClientConfig() {
	}

	public FastdfsClientConfig(String configFile) throws ConfigurationException {
		Configuration config = new PropertiesConfiguration(configFile);
		this.connectTimeout = (config.getInt("fdfs.connect_timeout", 5) * 1000);
		this.networkTimeout = (config.getInt("fdfs.network_timeout", 30) * 1000);
		setCharset(config.getString("fdfs.charset", "ISO8859-1"));
		setTrackerHttpPort(config.getInt("fdfs.http.tracker_http_port", 80));
		setAntiStealToken(config.getBoolean("fdfs.http.anti_steal_token", false));
		setSecretKey(config.getString("fdfs.http.secret_key"));

		List<?> trackerServers = config.getList("fdfs.tracker_server");
		for (Iterator<?> localIterator = trackerServers.iterator(); localIterator.hasNext();) {
			Object trackerServer = localIterator.next();
			this.trackerAddrs.add((String) trackerServer);
		}
	}

	public int getConnectTimeout() {
		return this.connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getNetworkTimeout() {
		return this.networkTimeout;
	}

	public void setNetworkTimeout(int networkTimeout) {
		this.networkTimeout = networkTimeout;
	}

	public List<String> getTrackerAddrs() {
		return this.trackerAddrs;
	}

	public void setTrackerAddrs(List<String> trackerAddrs) {
		this.trackerAddrs = trackerAddrs;
	}

	public String getCharset() {
		return this.charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getTrackerHttpPort() {
		return this.trackerHttpPort;
	}

	public void setTrackerHttpPort(int trackerHttpPort) {
		this.trackerHttpPort = trackerHttpPort;
	}

	public boolean isAntiStealToken() {
		return this.antiStealToken;
	}

	public void setAntiStealToken(boolean antiStealToken) {
		this.antiStealToken = antiStealToken;
	}

	public String getSecretKey() {
		return this.secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public GenericKeyedObjectPoolConfig getTrackerClientPoolConfig() {
		GenericKeyedObjectPoolConfig poolConfig = new GenericKeyedObjectPoolConfig();
		return poolConfig;
	}

	public GenericKeyedObjectPoolConfig getStorageClientPoolConfig() {
		GenericKeyedObjectPoolConfig poolConfig = new GenericKeyedObjectPoolConfig();
		return poolConfig;
	}
}
