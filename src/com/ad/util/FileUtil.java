package com.ad.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

import android.text.TextUtils;

public class FileUtil {

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			} else {// 删除子目录
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	public static void DeleteDir(File file) {
		if (file.exists() == false) {
			return;
		} else {
			if (file.isFile()) {
				file.delete();
				return;
			}
			if (file.isDirectory()) {
				File[] childFile = file.listFiles();
				if (childFile == null || childFile.length == 0) {
					file.delete();
					return;
				}
				for (File f : childFile) {
					DeleteDir(f);
				}
				file.delete();
			}
		}
	}

	/**
	 * 文件重新命名
	 * 
	 * @param sourcePath
	 *            [in]源文件路径
	 * @param destPath
	 *            [in]目的文件路径
	 */
	public static void renameFile(final String sourcePath, final String destPath) {
		if (TextUtils.isEmpty(sourcePath) || TextUtils.isEmpty(destPath))
			return;

		File file = new File(sourcePath);
		if (file.isFile() && file.exists()) {

			File destFile = new File(destPath);
			if (!destFile.exists()) {
				try {
					destFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			file.renameTo(destFile);
			file.delete();
		}
	}

	/**
	 * 文件拷贝
	 * 
	 * @param sourcePath
	 *            [in]源文件路径
	 * @param destPath
	 *            [in]目的文件路径
	 */
	public static void copyFile(final String sourcePath, final String destPath) {
		if (TextUtils.isEmpty(sourcePath) || TextUtils.isEmpty(destPath))
			return;

		File file = new File(sourcePath);
		if (file.isFile() && file.exists()) {

			try {
				FileChannel sourceChannel = new FileInputStream(file).getChannel();
				FileChannel destChannel = new FileOutputStream(destPath).getChannel();
				// 采取文件管道的方式进行拷贝
				destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
				sourceChannel.close();
				destChannel.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @Description: 获取当前文件的大小
	 * @param file
	 * @return
	 * @throws IOException
	 *             long
	 */
	public long onGetCurFileSize(final File file) throws IOException {
		long s = 0;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			s = fis.available();
		} else {
			System.out.println("文件不存在");
		}
		return s;
	}

	/**
	 * 
	 * @Description: 获取该目录的大小
	 * @param dirFile
	 * @return long
	 */
	public long onGetDirSize(final File dirFile) {
		long size = 0;
		File flist[] = dirFile.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + onGetDirSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	/**
	 * 
	 * @Description: 获取该目录的文件个数
	 * @param f
	 * @return long
	 */
	public long onGetDirFileCount(final File f) {
		long size = 0;
		File flist[] = f.listFiles();
		size = flist.length;
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + onGetDirFileCount(flist[i]);
				size--;
			}
		}
		return size;
	}

	/**
	 * 
	 * @Description:文件格式化大小为KB
	 * @param fileSize
	 * @return String
	 */
	public static String formatToKB(final long fileSize) {

		if (fileSize < 1024)
			return fileSize + "B";
		else if (fileSize < 1024 * 1024) {
			return fileSize / 1024 + "KB";
		} else {
			double size = (double) fileSize / (1024 * 1024);
			return format(size) + "M";
		}
	}

	static DecimalFormat dataformat = new DecimalFormat("####.0");
	static DecimalFormat dataformat1 = new DecimalFormat("0.0");
	public static DecimalFormat dataformat2 = new DecimalFormat("00");

	/**
	 * 保留两位小数点
	 * **/
	public static String format(double data) {
		if (data >= 1.00)
			return dataformat.format(data);
		else
			return dataformat1.format(data);

	}
}
