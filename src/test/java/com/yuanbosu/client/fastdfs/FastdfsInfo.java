package com.yuanbosu.client.fastdfs;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuanbosu.client.fastdfs.data.GroupInfo;
import com.yuanbosu.client.fastdfs.data.StorageInfo;

public class FastdfsInfo {
	private List<GroupInfo> groups = Lists.newLinkedList();

	private Map<String, List<StorageInfo>> storages = Maps.newHashMap();

	public List<GroupInfo> getGroups() {
		return this.groups;
	}

	public void setGroups(List<GroupInfo> groups) {
		this.groups = groups;
	}

	public Map<String, List<StorageInfo>> getStorages() {
		return this.storages;
	}

	public void setStorages(String group, List<StorageInfo> storage) {
		this.storages.put(group, storage);
	}
}