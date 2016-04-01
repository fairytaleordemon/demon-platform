package com.yuanbosu.client.fastdfs.data;

import java.util.Date;

import com.yuanbosu.client.fastdfs.command.AbstractCmd;

public class StorageInfo {
	protected byte status;
	protected String id;
	protected String ipAddr;
	protected String domainName;
	protected String srcIpAddr;
	protected String version;
	protected Date joinTime;
	protected Date upTime;
	protected long totalMB;
	protected long freeMB;
	protected int uploadPriority;
	protected int storePathCount;
	protected int subdirCountPerPath;
	protected int currentWritePath;
	protected int storagePort;
	protected int storageHttpPort;
	protected long totalUploadCount;
	protected long successUploadCount;
	protected long totalAppendCount;
	protected long successAppendCount;
	protected long totalModifyCount;
	protected long successModifyCount;
	protected long totalTruncateCount;
	protected long successTruncateCount;
	protected long totalSetMetaCount;
	protected long successSetMetaCount;
	protected long totalDeleteCount;
	protected long successDeleteCount;
	protected long totalDownloadCount;
	protected long successDownloadCount;
	protected long totalGetMetaCount;
	protected long successGetMetaCount;
	protected long totalCreateLinkCount;
	protected long successCreateLinkCount;
	protected long totalDeleteLinkCount;
	protected long successDeleteLinkCount;
	protected long totalUploadBytes;
	protected long successUploadBytes;
	protected long totalAppendBytes;
	protected long successAppendBytes;
	protected long totalModifyBytes;
	protected long successModifyBytes;
	protected long totalDownloadloadBytes;
	protected long successDownloadloadBytes;
	protected long totalSyncInBytes;
	protected long successSyncInBytes;
	protected long totalSyncOutBytes;
	protected long successSyncOutBytes;
	protected long totalFileOpenCount;
	protected long successFileOpenCount;
	protected long totalFileReadCount;
	protected long successFileReadCount;
	protected long totalFileWriteCount;
	protected long successFileWriteCount;
	protected Date lastSourceUpdate;
	protected Date lastSyncUpdate;
	protected Date lastSyncedTimestamp;
	protected Date lastHeartBeatTime;
	protected boolean ifTrunkServer;
	public static final int BYTE_SIZE = 612;

	public StorageInfo(byte[] data, int offset) {
		this.status = data[offset];
		offset++;
		this.id = new String(data, offset, 16).trim();
		offset += 16;
		this.ipAddr = new String(data, offset, 16).trim();
		offset += 16;
		this.domainName = new String(data, offset, 128).trim();
		offset += 128;
		this.srcIpAddr = new String(data, offset, 16).trim();
		offset += 16;
		this.version = new String(data, offset, 6).trim();
		offset += 6;
		this.joinTime = new Date(AbstractCmd.buff2long(data, offset) * 1000L);
		offset += 8;
		this.upTime = new Date(AbstractCmd.buff2long(data, offset) * 1000L);
		offset += 8;
		this.totalMB = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.freeMB = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.uploadPriority = (int) AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.storePathCount = (int) AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.subdirCountPerPath = (int) AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.currentWritePath = (int) AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.storagePort = (int) AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.storageHttpPort = (int) AbstractCmd.buff2long(data, offset);

		offset += 8;
		this.totalUploadCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successUploadCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalAppendCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successAppendCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalModifyCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successModifyCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalTruncateCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successTruncateCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalSetMetaCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successSetMetaCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalDeleteCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successDeleteCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalDownloadCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successDownloadCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalGetMetaCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successGetMetaCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalCreateLinkCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successCreateLinkCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalDeleteLinkCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successDeleteLinkCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalUploadBytes = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successUploadBytes = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalAppendBytes = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successAppendBytes = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalModifyBytes = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successModifyBytes = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalDownloadloadBytes = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successDownloadloadBytes = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalSyncInBytes = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successSyncInBytes = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalSyncOutBytes = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successSyncOutBytes = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalFileOpenCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successFileOpenCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalFileReadCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successFileReadCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.totalFileWriteCount = AbstractCmd.buff2long(data, offset);
		offset += 8;
		this.successFileWriteCount = AbstractCmd.buff2long(data, offset);

		offset += 8;
		this.lastSourceUpdate = new Date(AbstractCmd.buff2long(data, offset) * 1000L);
		offset += 8;
		this.lastSyncUpdate = new Date(AbstractCmd.buff2long(data, offset) * 1000L);
		offset += 8;
		this.lastSyncedTimestamp = new Date(AbstractCmd.buff2long(data, offset) * 1000L);
		offset += 8;
		this.lastHeartBeatTime = new Date(AbstractCmd.buff2long(data, offset) * 1000L);

		this.ifTrunkServer = (data[offset] != 0);
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getSrcIpAddr() {
		return this.srcIpAddr;
	}

	public void setSrcIpAddr(String srcIpAddr) {
		this.srcIpAddr = srcIpAddr;
	}

	public String getDomainName() {
		return this.domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public long getTotalMB() {
		return this.totalMB;
	}

	public void setTotalMB(long totalMB) {
		this.totalMB = totalMB;
	}

	public long getFreeMB() {
		return this.freeMB;
	}

	public void setFreeMB(long freeMB) {
		this.freeMB = freeMB;
	}

	public int getUploadPriority() {
		return this.uploadPriority;
	}

	public void setUploadPriority(int uploadPriority) {
		this.uploadPriority = uploadPriority;
	}

	public Date getJoinTime() {
		return this.joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public Date getUpTime() {
		return this.upTime;
	}

	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}

	public int getStorePathCount() {
		return this.storePathCount;
	}

	public void setStorePathCount(int storePathCount) {
		this.storePathCount = storePathCount;
	}

	public int getSubdirCountPerPath() {
		return this.subdirCountPerPath;
	}

	public void setSubdirCountPerPath(int subdirCountPerPath) {
		this.subdirCountPerPath = subdirCountPerPath;
	}

	public int getStoragePort() {
		return this.storagePort;
	}

	public void setStoragePort(int storagePort) {
		this.storagePort = storagePort;
	}

	public int getStorageHttpPort() {
		return this.storageHttpPort;
	}

	public void setStorageHttpPort(int storageHttpPort) {
		this.storageHttpPort = storageHttpPort;
	}

	public int getCurrentWritePath() {
		return this.currentWritePath;
	}

	public void setCurrentWritePath(int currentWritePath) {
		this.currentWritePath = currentWritePath;
	}

	public long getTotalUploadCount() {
		return this.totalUploadCount;
	}

	public void setTotalUploadCount(long totalUploadCount) {
		this.totalUploadCount = totalUploadCount;
	}

	public long getSuccessUploadCount() {
		return this.successUploadCount;
	}

	public void setSuccessUploadCount(long successUploadCount) {
		this.successUploadCount = successUploadCount;
	}

	public long getTotalAppendCount() {
		return this.totalAppendCount;
	}

	public void setTotalAppendCount(long totalAppendCount) {
		this.totalAppendCount = totalAppendCount;
	}

	public long getSuccessAppendCount() {
		return this.successAppendCount;
	}

	public void setSuccessAppendCount(long successAppendCount) {
		this.successAppendCount = successAppendCount;
	}

	public long getTotalModifyCount() {
		return this.totalModifyCount;
	}

	public void setTotalModifyCount(long totalModifyCount) {
		this.totalModifyCount = totalModifyCount;
	}

	public long getSuccessModifyCount() {
		return this.successModifyCount;
	}

	public void setSuccessModifyCount(long successModifyCount) {
		this.successModifyCount = successModifyCount;
	}

	public long getTotalTruncateCount() {
		return this.totalTruncateCount;
	}

	public void setTotalTruncateCount(long totalTruncateCount) {
		this.totalTruncateCount = totalTruncateCount;
	}

	public long getSuccessTruncateCount() {
		return this.successTruncateCount;
	}

	public void setSuccessTruncateCount(long successTruncateCount) {
		this.successTruncateCount = successTruncateCount;
	}

	public long getTotalSetMetaCount() {
		return this.totalSetMetaCount;
	}

	public void setTotalSetMetaCount(long totalSetMetaCount) {
		this.totalSetMetaCount = totalSetMetaCount;
	}

	public long getSuccessSetMetaCount() {
		return this.successSetMetaCount;
	}

	public void setSuccessSetMetaCount(long successSetMetaCount) {
		this.successSetMetaCount = successSetMetaCount;
	}

	public long getTotalDeleteCount() {
		return this.totalDeleteCount;
	}

	public void setTotalDeleteCount(long totalDeleteCount) {
		this.totalDeleteCount = totalDeleteCount;
	}

	public long getSuccessDeleteCount() {
		return this.successDeleteCount;
	}

	public void setSuccessDeleteCount(long successDeleteCount) {
		this.successDeleteCount = successDeleteCount;
	}

	public long getTotalDownloadCount() {
		return this.totalDownloadCount;
	}

	public void setTotalDownloadCount(long totalDownloadCount) {
		this.totalDownloadCount = totalDownloadCount;
	}

	public long getSuccessDownloadCount() {
		return this.successDownloadCount;
	}

	public void setSuccessDownloadCount(long successDownloadCount) {
		this.successDownloadCount = successDownloadCount;
	}

	public long getTotalGetMetaCount() {
		return this.totalGetMetaCount;
	}

	public void setTotalGetMetaCount(long totalGetMetaCount) {
		this.totalGetMetaCount = totalGetMetaCount;
	}

	public long getSuccessGetMetaCount() {
		return this.successGetMetaCount;
	}

	public void setSuccessGetMetaCount(long successGetMetaCount) {
		this.successGetMetaCount = successGetMetaCount;
	}

	public long getTotalCreateLinkCount() {
		return this.totalCreateLinkCount;
	}

	public void setTotalCreateLinkCount(long totalCreateLinkCount) {
		this.totalCreateLinkCount = totalCreateLinkCount;
	}

	public long getSuccessCreateLinkCount() {
		return this.successCreateLinkCount;
	}

	public void setSuccessCreateLinkCount(long successCreateLinkCount) {
		this.successCreateLinkCount = successCreateLinkCount;
	}

	public long getTotalDeleteLinkCount() {
		return this.totalDeleteLinkCount;
	}

	public void setTotalDeleteLinkCount(long totalDeleteLinkCount) {
		this.totalDeleteLinkCount = totalDeleteLinkCount;
	}

	public long getSuccessDeleteLinkCount() {
		return this.successDeleteLinkCount;
	}

	public void setSuccessDeleteLinkCount(long successDeleteLinkCount) {
		this.successDeleteLinkCount = successDeleteLinkCount;
	}

	public long getTotalUploadBytes() {
		return this.totalUploadBytes;
	}

	public void setTotalUploadBytes(long totalUploadBytes) {
		this.totalUploadBytes = totalUploadBytes;
	}

	public long getSuccessUploadBytes() {
		return this.successUploadBytes;
	}

	public void setSuccessUploadBytes(long successUploadBytes) {
		this.successUploadBytes = successUploadBytes;
	}

	public long getTotalAppendBytes() {
		return this.totalAppendBytes;
	}

	public void setTotalAppendBytes(long totalAppendBytes) {
		this.totalAppendBytes = totalAppendBytes;
	}

	public long getSuccessAppendBytes() {
		return this.successAppendBytes;
	}

	public void setSuccessAppendBytes(long successAppendBytes) {
		this.successAppendBytes = successAppendBytes;
	}

	public long getTotalModifyBytes() {
		return this.totalModifyBytes;
	}

	public void setTotalModifyBytes(long totalModifyBytes) {
		this.totalModifyBytes = totalModifyBytes;
	}

	public long getSuccessModifyBytes() {
		return this.successModifyBytes;
	}

	public void setSuccessModifyBytes(long successModifyBytes) {
		this.successModifyBytes = successModifyBytes;
	}

	public long getTotalDownloadloadBytes() {
		return this.totalDownloadloadBytes;
	}

	public void setTotalDownloadloadBytes(long totalDownloadloadBytes) {
		this.totalDownloadloadBytes = totalDownloadloadBytes;
	}

	public long getSuccessDownloadloadBytes() {
		return this.successDownloadloadBytes;
	}

	public void setSuccessDownloadloadBytes(long successDownloadloadBytes) {
		this.successDownloadloadBytes = successDownloadloadBytes;
	}

	public long getTotalSyncInBytes() {
		return this.totalSyncInBytes;
	}

	public void setTotalSyncInBytes(long totalSyncInBytes) {
		this.totalSyncInBytes = totalSyncInBytes;
	}

	public long getSuccessSyncInBytes() {
		return this.successSyncInBytes;
	}

	public void setSuccessSyncInBytes(long successSyncInBytes) {
		this.successSyncInBytes = successSyncInBytes;
	}

	public long getTotalSyncOutBytes() {
		return this.totalSyncOutBytes;
	}

	public void setTotalSyncOutBytes(long totalSyncOutBytes) {
		this.totalSyncOutBytes = totalSyncOutBytes;
	}

	public long getSuccessSyncOutBytes() {
		return this.successSyncOutBytes;
	}

	public void setSuccessSyncOutBytes(long successSyncOutBytes) {
		this.successSyncOutBytes = successSyncOutBytes;
	}

	public long getTotalFileOpenCount() {
		return this.totalFileOpenCount;
	}

	public void setTotalFileOpenCount(long totalFileOpenCount) {
		this.totalFileOpenCount = totalFileOpenCount;
	}

	public long getSuccessFileOpenCount() {
		return this.successFileOpenCount;
	}

	public void setSuccessFileOpenCount(long successFileOpenCount) {
		this.successFileOpenCount = successFileOpenCount;
	}

	public long getTotalFileReadCount() {
		return this.totalFileReadCount;
	}

	public void setTotalFileReadCount(long totalFileReadCount) {
		this.totalFileReadCount = totalFileReadCount;
	}

	public long getSuccessFileReadCount() {
		return this.successFileReadCount;
	}

	public void setSuccessFileReadCount(long successFileReadCount) {
		this.successFileReadCount = successFileReadCount;
	}

	public long getTotalFileWriteCount() {
		return this.totalFileWriteCount;
	}

	public void setTotalFileWriteCount(long totalFileWriteCount) {
		this.totalFileWriteCount = totalFileWriteCount;
	}

	public long getSuccessFileWriteCount() {
		return this.successFileWriteCount;
	}

	public void setSuccessFileWriteCount(long successFileWriteCount) {
		this.successFileWriteCount = successFileWriteCount;
	}

	public Date getLastSourceUpdate() {
		return this.lastSourceUpdate;
	}

	public void setLastSourceUpdate(Date lastSourceUpdate) {
		this.lastSourceUpdate = lastSourceUpdate;
	}

	public Date getLastSyncUpdate() {
		return this.lastSyncUpdate;
	}

	public void setLastSyncUpdate(Date lastSyncUpdate) {
		this.lastSyncUpdate = lastSyncUpdate;
	}

	public Date getLastSyncedTimestamp() {
		return this.lastSyncedTimestamp;
	}

	public void setLastSyncedTimestamp(Date lastSyncedTimestamp) {
		this.lastSyncedTimestamp = lastSyncedTimestamp;
	}

	public Date getLastHeartBeatTime() {
		return this.lastHeartBeatTime;
	}

	public void setLastHeartBeatTime(Date lastHeartBeatTime) {
		this.lastHeartBeatTime = lastHeartBeatTime;
	}

	public boolean isIfTrunkServer() {
		return this.ifTrunkServer;
	}

	public void setIfTrunkServer(boolean ifTrunkServer) {
		this.ifTrunkServer = ifTrunkServer;
	}
}
