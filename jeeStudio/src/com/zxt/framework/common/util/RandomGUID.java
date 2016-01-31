package com.zxt.framework.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class RandomGUID {
	private String valueBeforeMD5 = "";
	private String valueAfterMD5 = "";
	private static Random myRand;
	private static SecureRandom mySecureRand = new SecureRandom();
	private static String s_id;

	private RandomGUID() {
		getRandomGUID(false);
	}

	private RandomGUID(boolean secure) {
		getRandomGUID(secure);
	}

	private void getRandomGUID(boolean secure) {
		MessageDigest md5 = null;
		StringBuffer sbValueBeforeMD5 = new StringBuffer();
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error:   " + e);
		}
		try {
			long time = System.currentTimeMillis();
			long rand = 0L;

			if (secure)
				rand = mySecureRand.nextLong();
			else {
				rand = myRand.nextLong();
			}

			sbValueBeforeMD5.append(s_id);
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(time));
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(rand));

			this.valueBeforeMD5 = sbValueBeforeMD5.toString();
			md5.update(this.valueBeforeMD5.getBytes());

			byte[] array = md5.digest();
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < array.length; ++j) {
				int b = array[j] & 0xFF;
				if (b < 16)
					sb.append('0');
				sb.append(Integer.toHexString(b));
			}

			this.valueAfterMD5 = sb.toString();
		} catch (Exception e) {
			System.out.println("Error:" + e);
		}
	}

	public static String geneGuid() {
		return new RandomGUID().valueAfterMD5;
	}

	public static String geneGuid(boolean secure) {
		return new RandomGUID(secure).valueAfterMD5;
	}

	static {
		long secureInitializer = mySecureRand.nextLong();
		myRand = new Random(secureInitializer);
		try {
			s_id = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}