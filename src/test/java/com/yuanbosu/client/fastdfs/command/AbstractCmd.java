package com.yuanbosu.client.fastdfs.command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;

public abstract class AbstractCmd<T> implements Command<T> {
	protected byte requestCmd;
	protected byte responseCmd;
	protected long responseSize;
	protected byte[] body1;
	protected long body2Len;

	public AbstractCmd() {
		this.body2Len = 0L;
	}

	protected void request(OutputStream socketOut) throws IOException {
		socketOut.write(getRequestHeaderAndBody1());
	}

	protected void request(OutputStream socketOut, InputStream is) throws IOException {
		request(socketOut);

		byte[] buff = new byte[262144];
		int readBytes;
		while ((readBytes = is.read(buff)) >= 0) {
			if (readBytes == 0) {
				continue;
			}
			socketOut.write(buff, 0, readBytes);
		}
		is.close();
	}

	protected byte[] getRequestHeaderAndBody1() {
		if (this.body1 == null) {
			this.body1 = new byte[0];
		}
		byte[] header = new byte[10 + this.body1.length];
		Arrays.fill(header, new Byte("0"));
		byte[] hex_len = long2buff(this.body1.length + this.body2Len);
		System.arraycopy(hex_len, 0, header, 0, hex_len.length);
		System.arraycopy(this.body1, 0, header, 10, this.body1.length);
		header[8] = this.requestCmd;
		header[9] = 0;
		return header;
	}

	protected AbstractCmd<T>.Response response(InputStream socketIn) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		int code = response(socketIn, os);
		return new Response(code, os.toByteArray());
	}

	protected int response(InputStream socketIn, OutputStream os) throws IOException {
		byte[] header = new byte[10];

		int bytes = socketIn.read(header);

		if (bytes != header.length) {
			throw new IOException("recv package size " + bytes + " != " + header.length);
		}

		if (header[8] != this.responseCmd) {
			throw new IOException("recv cmd: " + header[8] + " is not correct, expect cmd: " + this.responseCmd);
		}

		if (header[9] != 0) {
			return header[9];
		}

		long respSize = buff2long(header, 0);
		if (respSize < 0L) {
			throw new IOException("recv body length: " + respSize + " < 0!");
		}

		if ((this.responseSize >= 0L) && (respSize != this.responseSize)) {
			throw new IOException(
					"recv body length: " + respSize + " is not correct, expect length: " + this.responseSize);
		}

		byte[] buff = new byte[2048];
		int totalBytes = 0;
		int remainBytes = (int) respSize;

		while (totalBytes < respSize) {
			int len = remainBytes;
			if (len > buff.length) {
				len = buff.length;
			}

			if ((bytes = socketIn.read(buff, 0, len)) < 0) {
				break;
			}
			os.write(buff, 0, bytes);
			totalBytes += bytes;
			remainBytes -= bytes;
		}

		if (totalBytes != respSize) {
			throw new IOException("recv package size " + totalBytes + " != " + respSize);
		}
		os.close();
		return 0;
	}

	protected String metaDataToStr(Map<String, String> metaData) {
		StringBuffer sb = new StringBuffer();
		for (String key : metaData.keySet()) {
			sb.append("\001");
			sb.append(key);
			sb.append("\002");
			sb.append((String) metaData.get(key));
		}

		return sb.toString().substring("\001".length());
	}

	public static byte[] long2buff(long n) {
		byte[] bs = new byte[8];
		bs[0] = (byte) (int) (n >> 56 & 0xFF);
		bs[1] = (byte) (int) (n >> 48 & 0xFF);
		bs[2] = (byte) (int) (n >> 40 & 0xFF);
		bs[3] = (byte) (int) (n >> 32 & 0xFF);
		bs[4] = (byte) (int) (n >> 24 & 0xFF);
		bs[5] = (byte) (int) (n >> 16 & 0xFF);
		bs[6] = (byte) (int) (n >> 8 & 0xFF);
		bs[7] = (byte) (int) (n & 0xFF);

		return bs;
	}

	public static long buff2long(byte[] bs, int offset) {
		return (bs[offset] >= 0 ? bs[offset] : 256 + bs[offset]) << 56
				| (bs[(offset + 1)] >= 0 ? bs[(offset + 1)] : 256 + bs[(offset + 1)]) << 48
				| (bs[(offset + 2)] >= 0 ? bs[(offset + 2)] : 256 + bs[(offset + 2)]) << 40
				| (bs[(offset + 3)] >= 0 ? bs[(offset + 3)] : 256 + bs[(offset + 3)]) << 32
				| (bs[(offset + 4)] >= 0 ? bs[(offset + 4)] : 256 + bs[(offset + 4)]) << 24
				| (bs[(offset + 5)] >= 0 ? bs[(offset + 5)] : 256 + bs[(offset + 5)]) << 16
				| (bs[(offset + 6)] >= 0 ? bs[(offset + 6)] : 256 + bs[(offset + 6)]) << 8
				| (bs[(offset + 7)] >= 0 ? bs[(offset + 7)] : 256 + bs[(offset + 7)]);
	}

	public static String[] splitFileId(String file_id) {
		int pos = file_id.indexOf("/");
		if ((pos <= 0) || (pos == file_id.length() - 1)) {
			return null;
		}

		String[] results = new String[2];
		results[0] = file_id.substring(0, pos);
		results[1] = file_id.substring(pos + 1);
		return results;
	}

	protected class Response {
		private int code;
		private byte[] data;

		public Response(int code) {
			this.code = code;
		}

		public Response(int code, byte[] data) {
			this.code = code;
			this.data = data;
		}

		public boolean isSuccess() {
			return this.code == 0;
		}

		public int getCode() {
			return this.code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public byte[] getData() {
			return this.data;
		}

		public void setData(byte[] data) {
			this.data = data;
		}
	}
}
