package com.zxt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 
 * @author h
 *
 */
public class GetMACAddress {
/**
 * 直接获取网卡地址
 * @return
 */
	public static String getMACAddress() {

		String address = "";
		String os = System.getProperty("os.name");
		if (os != null) {
			if (os.startsWith("Windows")) {
				try {
					ProcessBuilder pb = new ProcessBuilder("ipconfig", "/all");
					Process p = pb.start();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(p.getInputStream()));
					String line;
					while ((line = br.readLine()) != null) {
						if ((line.indexOf("Physical Address") != -1)||(line.indexOf("物理地址") != -1)) {
							int index = line.indexOf(":");
							address = line.substring(index + 1);
							break;
						}
					}
					br.close();
					if (address!=null) {
						address.trim();
						address= MD5andKL.MD5(address);
					}
					return address.trim();
				} catch (IOException e) {

				}
			} else if (os.startsWith("Linux")) {
				try {
					ProcessBuilder pb = new ProcessBuilder("ifconfig");
					Process p = pb.start();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(p.getInputStream()));
					String line;
					while ((line = br.readLine()) != null) {
						int index = line.indexOf("硬件地址");
						if (index != -1) {
							address = line.substring(index + 4);
							break;
						}
					}
					br.close();
					return address.trim();
				} catch (IOException ex) {
					// Logger.getLogger(Test.class.getName()).log(Level.SEVERE,
					// null, ex);
				}
			}
		}
		return address;
	}
	public static void main(String[] args) {
		System.out.println(getMACAddress());
		System.out.println(MD5andKL.MD5("sdfsdf"));
	}
}
