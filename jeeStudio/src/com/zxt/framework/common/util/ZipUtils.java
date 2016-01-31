package com.zxt.framework.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
	private static final int BUFFER_SIZE = 2048;

	public static void doZip(String sourceDirectory, String archiveFilepath) throws IOException {
		doZip(sourceDirectory, archiveFilepath, false);
	}

	public static void doZip(String sourceDirectory, String archiveFilepath, boolean deleteFile) throws IOException {
		File file = new File(sourceDirectory);
		String baseDirectory = file.getPath();
		FileOutputStream dest = new FileOutputStream(archiveFilepath);
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
		compressDir(file, out, baseDirectory);
		out.close();
		if (deleteFile)
			deleteFile(sourceDirectory);
	}

	public static void doUnzip(String targetArchiveFilepath, String expandDirectory) throws IOException {
		doUnzip(targetArchiveFilepath, expandDirectory, false);
	}

	public static void doUnzip(String targetArchiveFilepath, String expandDirectory, boolean deleteFile)
			throws IOException {
		if ((!expandDirectory.endsWith("\\")) && (!expandDirectory.endsWith("/"))) {
			expandDirectory = expandDirectory + "\\";
		}
		BufferedOutputStream dest = null;
		File targetArchiveFile = new File(targetArchiveFilepath);
		FileInputStream fis = new FileInputStream(targetArchiveFile);
		ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
		
		ZipEntry entry;
		while ((entry = zis.getNextEntry()) != null) {
			byte[] data = new byte[2048];
			File archive = assignFile(expandDirectory + entry.getName());
			FileOutputStream fos = new FileOutputStream(archive);
			dest = new BufferedOutputStream(fos, 2048);
			int count;
			while ((count = zis.read(data, 0, 2048)) != -1) {
				dest.write(data, 0, count);
			}
			dest.flush();
			dest.close();
		}
		zis.close();
		if (deleteFile)
			targetArchiveFile.delete();
	}

	private static void compressDir(File file, ZipOutputStream out, String baseDirectory) throws IOException {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; ++i) {
				if (files[i].isDirectory()) {
					compressDir(files[i], out, baseDirectory);
				}
				if (files[i].isFile())
					addOneFile(files[i], out, baseDirectory);
			}
		} else if (file.isFile()) {
			addOneFile(file, out, baseDirectory);
		}
	}

	private static void addOneFile(File file, ZipOutputStream out, String baseDirectory) throws IOException {
		FileInputStream in = new FileInputStream(file);
		ZipEntry entry = new ZipEntry(getEntryName(file, baseDirectory));
		out.putNextEntry(entry);

		byte[] data = new byte[2048];
		CRC32 crc32 = new CRC32();
		int count;
		while ((count = new BufferedInputStream(in).read(data, 0, 2048)) != -1) {
			crc32.update(data, 0, 2048);
			out.write(data, 0, count);
		}
		in.close();
	}

	private static String getEntryName(File file, String baseDirectory) {
		if (baseDirectory.equals(file.getPath())) {
			return file.getName();
		}
		String filepath = file.getPath();
		String entryName = filepath.substring(baseDirectory.length() + 1, filepath.length());
		if (file.isDirectory()) {
			entryName = entryName + "\\";
		}
		return entryName;
	}

	private static void deleteFile(String filePath) {
		File f = new File(filePath);
		if ((f.exists()) && (f.isDirectory())) {
			File[] delFiles = f.listFiles();
			for (int i = 0; i < delFiles.length; ++i) {
				deleteFile(delFiles[i].getAbsolutePath());
			}
		}
		f.delete();
	}

	public static File assignFile(String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists()) {
			if ((filename.endsWith("\\")) || (filename.endsWith("/"))) {
				file.mkdirs();
			} else {
				int sep = (filename.lastIndexOf("\\") == -1) ? filename.lastIndexOf("/") : filename.lastIndexOf("\\");
				String dir = filename.substring(0, sep + 1);
				String newfile = filename.substring(sep + 1, filename.length());
				new File(dir).mkdirs();
				new File(dir, newfile).createNewFile();
			}
		}
		return file;
	}
}