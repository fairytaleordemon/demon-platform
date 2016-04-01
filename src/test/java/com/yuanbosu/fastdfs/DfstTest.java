package com.yuanbosu.fastdfs;

import org.apache.commons.lang3.StringUtils;

public class DfstTest {
	public static void main(String[] args) {
		String address = "";
		String[] ip = StringUtils.split(address, ";");

		System.out.println(ip[0]);
	}
}