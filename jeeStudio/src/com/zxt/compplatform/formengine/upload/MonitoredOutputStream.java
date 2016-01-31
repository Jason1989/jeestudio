package com.zxt.compplatform.formengine.upload;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 监听器输出流
 * 
 * @author 007
 */
public class MonitoredOutputStream extends OutputStream {
	/**
	 * 输出流
	 */
	private OutputStream outputStream;
	/**
	 * 输出流监听器
	 */
	private OutputStreamListener outputStreamListener;

	public MonitoredOutputStream(OutputStream outputStream,
			OutputStreamListener outputStreamListener) {
		this.outputStream = outputStream;
		this.outputStreamListener = outputStreamListener;
		this.outputStreamListener.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	public void write(byte b[], int off, int len) throws IOException {
		outputStream.write(b, off, len);
		outputStreamListener.bytesRead(len - off);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#write(byte[])
	 */
	public void write(byte b[]) throws IOException {
		outputStream.write(b);
		outputStreamListener.bytesRead(b.length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#write(int)
	 */
	public void write(int b) throws IOException {
		outputStream.write(b);
		outputStreamListener.bytesRead(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#close()
	 */
	public void close() throws IOException {
		outputStream.close();
		outputStreamListener.done();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#flush()
	 */
	public void flush() throws IOException {
		outputStream.flush();
	}
}
