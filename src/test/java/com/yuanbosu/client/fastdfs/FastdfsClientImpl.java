package com.yuanbosu.client.fastdfs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanbosu.client.fastdfs.client.AbstractClient;
import com.yuanbosu.client.fastdfs.client.StorageClient;
import com.yuanbosu.client.fastdfs.client.StorageClientFactory;
import com.yuanbosu.client.fastdfs.client.TrackerClient;
import com.yuanbosu.client.fastdfs.client.TrackerClientFactory;
import com.yuanbosu.client.fastdfs.data.FileInfo;
import com.yuanbosu.client.fastdfs.data.GroupInfo;
import com.yuanbosu.client.fastdfs.data.StorageInfo;
import com.yuanbosu.client.fastdfs.data.UploadStorage;
import com.yuanbosu.common.domain.Result;

public class FastdfsClientImpl extends AbstractClient implements FastdfsClient {
	private static Logger logger = LoggerFactory.getLogger(FastdfsClientImpl.class);
	private GenericKeyedObjectPool<String, TrackerClient> trackerClientPool;
	private GenericKeyedObjectPool<String, StorageClient> storageClientPool;
	private List<String> trackerAddrs = new ArrayList<>();
	private Map<String, String> storageIpMap = new ConcurrentHashMap<String, String>();

	private FastdfsInfo dfsInfo = new FastdfsInfo();

	public FastdfsClientImpl(List<String> trackerAddrs) throws Exception {
		this.trackerAddrs = trackerAddrs;
		this.trackerClientPool = new GenericKeyedObjectPool<String, TrackerClient>(new TrackerClientFactory());
		this.storageClientPool = new GenericKeyedObjectPool<String, StorageClient>(new StorageClientFactory());
		updateStorageIpMap();
	}

	public FastdfsClientImpl(List<String> trackerAddrs, GenericKeyedObjectPool<String, TrackerClient> trackerClientPool,
			GenericKeyedObjectPool<String, StorageClient> storageClientPool) {
		this.trackerAddrs = trackerAddrs;
		this.trackerClientPool = trackerClientPool;
		this.storageClientPool = storageClientPool;
	}

	public void close() {
		this.trackerClientPool.close();
		this.storageClientPool.close();
	}

	public String upload(File file, String ext, Map<String, String> meta) throws Exception {
		return upload(file, ext, meta, false).getFileId();
	}

	public String upload(File file) throws Exception {
		String fileName = file.getName();
		return upload(file, fileName);
	}

	public String upload(File file, String fileName) throws Exception {
		return upload(file, fileName, null);
	}

	public FileInfo uploadAndGetInfo(File file, Map<String, String> meta) throws Exception {
		return upload(file, null, meta, true);
	}

	public FileInfo uploadAndGetInfo(File file) throws Exception {
		return upload(file, null, null, true);
	}

	public String uploadSlave(File file, String fileid, String prefix, String ext) throws Exception {
		String trackerAddr = getTrackerAddr();
		TrackerClient trackerClient = null;
		StorageClient storageClient = null;
		String storageAddr = null;
		String fileId = null;
		try {
			trackerClient = (TrackerClient) this.trackerClientPool.borrowObject(trackerAddr);

			if (fileid != null) {
				String[] tupple = splitFileId(fileid);
				String groupname = tupple[0];
				String filename = tupple[1];

				Result<String> result = trackerClient.getUpdateStorageAddr(groupname, filename);
				if (result.getCode() == 0) {
					storageAddr = (String) result.getData();
					storageClient = (StorageClient) this.storageClientPool.borrowObject(storageAddr);
					Result<String> result2 = storageClient.uploadSlave(file, filename, prefix, ext, null);
					if (result2.getCode() == 0)
						fileId = (String) result2.getData();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), ExceptionUtils.getStackTrace(e));
			throw e;
		} finally {
			if (storageClient != null) {
				this.storageClientPool.returnObject(storageAddr, storageClient);
			}
			if (trackerClient != null) {
				this.trackerClientPool.returnObject(trackerAddr, trackerClient);
			}
		}
		return fileId;
	}

	public Boolean setMeta(String fileId, Map<String, String> meta) throws Exception {
		String trackerAddr = getTrackerAddr();
		TrackerClient trackerClient = null;
		StorageClient storageClient = null;
		Boolean result = null;
		String storageAddr = null;
		try {
			FastDfsFile fastDfsFile = new FastDfsFile(fileId);
			trackerClient = (TrackerClient) this.trackerClientPool.borrowObject(trackerAddr);
			Result<String> result2 = trackerClient.getUpdateStorageAddr(fastDfsFile.group, fastDfsFile.fileName);
			if (result2.getCode() == 0) {
				storageAddr = (String) result2.getData();
				storageClient = (StorageClient) this.storageClientPool.borrowObject(storageAddr);
				Result<Boolean> result3 = storageClient.setMeta(fastDfsFile.group, fastDfsFile.fileName, meta);
				if ((result3.getCode() == 0) || (result3.getCode() == 0))
					result = (Boolean) result3.getData();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), ExceptionUtils.getStackTrace(e));
			throw e;
		} finally {
			if (storageClient != null) {
				this.storageClientPool.returnObject(storageAddr, storageClient);
			}
			if (trackerClient != null) {
				this.trackerClientPool.returnObject(trackerAddr, trackerClient);
			}
		}
		return result;
	}

	public Map<String, String> getMeta(String fileId) throws Exception {
		String trackerAddr = getTrackerAddr();
		TrackerClient trackerClient = null;
		StorageClient storageClient = null;
		Map<String, String> meta = null;
		String storageAddr = null;
		try {
			FastDfsFile fastDfsFile = new FastDfsFile(fileId);
			trackerClient = (TrackerClient) this.trackerClientPool.borrowObject(trackerAddr);
			Result<String> result2 = trackerClient.getUpdateStorageAddr(fastDfsFile.group, fastDfsFile.fileName);
			if (result2.getCode() == 0) {
				storageAddr = (String) result2.getData();
				storageClient = (StorageClient) this.storageClientPool.borrowObject(storageAddr);
				Result<Map<String, String>> result3 = storageClient.getMeta(fastDfsFile.group, fastDfsFile.fileName);
				if ((result3.getCode() == 0) || (result3.getCode() == 0))
					meta = (Map<String, String>) result3.getData();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), ExceptionUtils.getStackTrace(e));
			throw e;
		} finally {
			if (storageClient != null) {
				this.storageClientPool.returnObject(storageAddr, storageClient);
			}
			if (trackerClient != null) {
				this.trackerClientPool.returnObject(trackerAddr, trackerClient);
			}
		}
		return meta;
	}

	public String getUrl(String fileId) throws Exception {
		String trackerAddr = getTrackerAddr();
		TrackerClient trackerClient = null;
		String url = null;
		try {
			trackerClient = (TrackerClient) this.trackerClientPool.borrowObject(trackerAddr);
			url = getUrl(trackerClient, fileId);
		} catch (Exception e) {
			logger.error(e.getMessage(), ExceptionUtils.getStackTrace(e));
			throw e;
		} finally {
			if (trackerClient != null) {
				this.trackerClientPool.returnObject(trackerAddr, trackerClient);
			}
		}
		return url;
	}

	public Boolean delete(String fileId) throws Exception {
		String trackerAddr = getTrackerAddr();
		logger.debug("FastDFS (delete) fileId [{}] with Tracker [{}]", fileId, trackerAddr);

		TrackerClient trackerClient = null;
		StorageClient storageClient = null;
		Boolean result = Boolean.valueOf(false);
		String storageAddr = null;
		try {
			FastDfsFile fastDfsFile = new FastDfsFile(fileId);
			trackerClient = (TrackerClient) this.trackerClientPool.borrowObject(trackerAddr);
			Result<String> result2 = trackerClient.getUpdateStorageAddr(fastDfsFile.group, fastDfsFile.fileName);
			if (result2.getCode() == 0) {
				storageAddr = (String) result2.getData();
				logger.debug("FastDFS (delete) fileId [{}] with Storage [{}]", fileId, storageAddr);

				storageClient = (StorageClient) this.storageClientPool.borrowObject(storageAddr);
				Result<Boolean> result3 = storageClient.delete(fastDfsFile.group, fastDfsFile.fileName);
				logger.debug("FastDFS (delete) fileId [{}] result code [{}] data [{}]",
						new Object[] { fileId, Integer.valueOf(result3.getCode()), result3.getData() });

				if ((result3.getCode() == 0) || (((Boolean) result3.getData()).booleanValue()))
					result = Boolean.valueOf(true);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (storageClient != null) {
				this.storageClientPool.returnObject(storageAddr, storageClient);
			}
			if (trackerClient != null) {
				this.trackerClientPool.returnObject(trackerAddr, trackerClient);
			}
		}
		return result;
	}

	public String getTrackerAddr() {
		Random r = new Random();
		int i = r.nextInt(this.trackerAddrs.size());
		return (String) this.trackerAddrs.get(i);
	}

	private String getFileExtName(File file) {
		String name = file.getName();
		if (name != null) {
			int i = name.lastIndexOf(46);
			if (i > -1) {
				return name.substring(i + 1);
			}
			return null;
		}

		return null;
	}

	private String getDownloadHostPort(String storageAddr) throws Exception {
		String downloadHostPort = (String) this.storageIpMap.get(storageAddr);
		logger.debug("FastDFS get download host [{}] with storage address [{}]", downloadHostPort, storageAddr);

		if (downloadHostPort == null) {
			updateStorageIpMap();
			downloadHostPort = (String) this.storageIpMap.get(storageAddr);
		}
		return downloadHostPort;
	}

	private String getUrl(TrackerClient trackerClient, String fileId) throws Exception {
		FastDfsFile fastDfsFile = new FastDfsFile(fileId);
		Result<String> result = trackerClient.getDownloadStorageAddr(fastDfsFile.group, fastDfsFile.fileName);
		if (result.getCode() == 0) {
			String hostPort = getDownloadHostPort((String) result.getData());
			return "http://" + hostPort + "/" + fastDfsFile.fileName;
		}
		return null;
	}

	private void updateStorageIpMap() throws Exception {
		String trackerAddr = getTrackerAddr();
		TrackerClient trackerClient = null;
		try {
			logger.debug("FastDFS update Storage Ip Map -----------------");
			trackerClient = (TrackerClient) this.trackerClientPool.borrowObject(trackerAddr);

			Result<List<GroupInfo>> result = trackerClient.getGroupInfos();

			this.dfsInfo.setGroups((List<GroupInfo>) result.getData());
			String groupName;
			if (result.getCode() == 0) {
				List<GroupInfo> groupInfos = (List<GroupInfo>) result.getData();
				logger.debug("FastDFS count Storage groups: [{}]", Integer.valueOf(groupInfos.size()));

				for (GroupInfo groupInfo : groupInfos) {
					groupName = groupInfo.getGroupName();
					logger.debug("FastDFS init Storage for group [{}]  -----------------", groupName);
					Result<List<StorageInfo>> result2 = trackerClient.getStorageInfos(groupName);

					if (result2.getCode() == 0) {
						List<StorageInfo> storageInfos = (List<StorageInfo>) result2.getData();
						logger.debug("FastDFS count [{}] Storage servers: [{}]", groupName,
								Integer.valueOf(storageInfos.size()));

						this.dfsInfo.setStorages(groupName, storageInfos);

						for (StorageInfo storageInfo : storageInfos) {
							String ip = storageInfo.getIpAddr();
							int storagePort = storageInfo.getStoragePort();
							int httpPort = storageInfo.getStorageHttpPort();
							String domain = storageInfo.getDomainName();
							String hostPort = domain.isEmpty() ? ip : domain;
							Long freeMB = Long.valueOf(storageInfo.getFreeMB());
							Long totalMB = Long.valueOf(storageInfo.getTotalMB());

							if (httpPort != 80)
								hostPort = hostPort + ":" + httpPort;

							logger.debug(
									"FastDFS get [{}] Storage[{}/{}] domain [{}] / ip [{}] / port [{}] / hostPort [{}]",
									new Object[] { groupName, freeMB, totalMB, domain, ip, Integer.valueOf(storagePort),
											hostPort });

							this.storageIpMap.put(ip + ":" + storagePort, hostPort);
						}
					}
				}
			} else {
				throw new Exception("Get getGroupInfos Error");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), ExceptionUtils.getStackTrace(e));
			throw e;
		} finally {
			if (trackerClient != null)
				this.trackerClientPool.returnObject(trackerAddr, trackerClient);
		}
	}

	private FileInfo upload(File file, String ext, Map<String, String> meta, boolean getUrl) throws Exception {
		String trackerAddr = getTrackerAddr();
		logger.debug("FastDFS (upload) file [{}] / size [{}] with Tracker [{}] --------------------",
				new Object[] { file.getName(), Long.valueOf(file.length()), trackerAddr });
		TrackerClient trackerClient = null;
		StorageClient storageClient = null;
		String storageAddr = null;
		String fileId = null;
		String url = null;
		Map<String, String>  storeMeta = null;
		try {
			trackerClient = (TrackerClient) this.trackerClientPool.borrowObject(trackerAddr);

			Result<?> storageInfo = trackerClient.getUploadStorage();

			if (storageInfo.getCode() == 0) {
				storageAddr = ((UploadStorage) storageInfo.getData()).getAddress();
				logger.debug("FastDFS (upload) get storage address [{}]", storageAddr);

				storageClient = (StorageClient) this.storageClientPool.borrowObject(storageAddr);

				String extname = ext;
				if (ext == null) {
					extname = getFileExtName(file);
				}

				Result<?> uploadResult = storageClient.upload(file, extname,
						((UploadStorage) storageInfo.getData()).getPathIndex());
				if (uploadResult.getCode() == 0) {
					fileId = (String) uploadResult.getData();
					logger.debug("FastDFS (upload) success with fileId [{}]", fileId);

					if (meta != null) {
						FastDfsFile fastDfsFile = new FastDfsFile(fileId);

						logger.debug("FastDFS (upload) sotre meta info [{}] ", meta);
						Result<?> setMetaResult = storageClient.setMeta(fastDfsFile.group, fastDfsFile.fileName, meta);
						if ((setMetaResult.getCode() == 0) || (((Boolean) setMetaResult.getData()).booleanValue())) {
							storeMeta = meta;
						}

					}

					if (getUrl)
						url = getUrl(trackerClient, fileId);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), ExceptionUtils.getStackTrace(e));
			throw e;
		} finally {
			if (storageClient != null) {
				this.storageClientPool.returnObject(storageAddr, storageClient);
			}
			if (trackerClient != null) {
				this.trackerClientPool.returnObject(trackerAddr, trackerClient);
			}
		}
		return new FileInfo(fileId, url, storeMeta);
	}

	public FastdfsInfo getDfsInfo() throws Exception {
		if (this.dfsInfo.getGroups().isEmpty()) {
			updateStorageIpMap();
		}
		return this.dfsInfo;
	}

	private class FastDfsFile {
		private String group;
		private String fileName;

		public FastDfsFile(String fileId) {
			int pos = fileId.indexOf("/");
			this.group = fileId.substring(0, pos);
			this.fileName = fileId.substring(pos + 1);
		}
	}
}
