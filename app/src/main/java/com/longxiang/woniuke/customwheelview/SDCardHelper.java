package com.longxiang.woniuke.customwheelview;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

public class SDCardHelper {
	
	/**
	 * �ж�sd���Ƿ����
	 * @return
	 */
	public static boolean isSDCardMounted(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	/***
	 * ����sd���ĸ�Ŀ¼
	 * @return
	 */
	public static String getSDCardBasePath(){
		if (isSDCardMounted()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}else {
			return null;
		}
	}
	/**
	 * ����sd��������
	 * */
	public static long getSDCardTotalSize() {
		long size = 0;
		if (isSDCardMounted()) {
			StatFs statFs = new StatFs(getSDCardBasePath());
			if (Build.VERSION.SDK_INT>=18) {
				size = statFs.getTotalBytes();
			}else {
				size = statFs.getBlockCountLong()*statFs.getBlockSizeLong();
			}
			return size/1024/1024;
		}else {
			return 0;
		}
	}
	/**
	 * ����sd�����õ�����
	 * */
	public static long getSDCardAvailableSize() {
		long size = 0;
		if (isSDCardMounted()) {
			StatFs statFs = new StatFs(getSDCardBasePath());
			if (Build.VERSION.SDK_INT>=18) {
				size = statFs.getAvailableBytes();
			}else {
				size = statFs.getAvailableBlocksLong()*statFs.getBlockSizeLong();
			}
			return size/1024/1024;
		}else {
			return 0;
		}
	}
	/**
	 * ����sd�����е�����
	 * */
	public static long getSDCardFreeSize() {
		long size = 0;
		if (isSDCardMounted()) {
			StatFs statFs = new StatFs(getSDCardBasePath());
			if (Build.VERSION.SDK_INT>=18) {
				size = statFs.getFreeBytes();
			}else {
				size = statFs.getFreeBlocksLong()*statFs.getBlockSizeLong();
			}
			return size/1024/1024;
		}else {
			return 0;
		}
	}
	/**
	 * �洢�ļ���sd���Ĺ���Ŀ¼֮��
	 * */
	public static boolean saveFileToSDCardPublicDir(byte[]data,String type,String fileName){
		if (isSDCardMounted()) {
			BufferedOutputStream bos  = null;
			//��ù���Ŀ¼
			File file = Environment.getExternalStoragePublicDirectory(type);
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
				bos.write(data,0,data.length);
				bos.flush();
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		return false;
	}
	/**
	 * �����ļ���sd�����Զ���Ŀ¼��
	 * */
	public static boolean saveFileToSDCardCustomDir(byte[]data,String dir,String fileName){
		if (isSDCardMounted()) {
			BufferedOutputStream bos = null;
			File file = new File(getSDCardBasePath()+File.separator+dir);
			if (!file.exists()) {
				file.mkdirs();  
			}
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(file,fileName)));
				bos.write(data, 0, data.length);
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if (bos!=null) {
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		return false;
	}
	
	/**
	 * �����ļ���sd������Ŀ��˽��Ŀ¼��
	 * */
	public static boolean saveFileToSDCardPrivateDir(byte[]data,String type,String fileName,Context context){
		if (isSDCardMounted()) {
			BufferedOutputStream bos = null;
			File file = context.getExternalFilesDir(type);
			//����·��:/mnt/sdcard/Android/data/<��İ���>/files/type
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(file,fileName)));
				bos.write(data, 0, data.length);
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if (bos!=null) {
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		return false;
	}
	
	
	/**
	 * �����ļ���sd������Ŀ�Ļ���Ŀ¼��
	 * */
	public static boolean saveFileToSDCardCacheDir(byte[]data,String fileName,Context context){
		if (isSDCardMounted()) {
			BufferedOutputStream bos = null;
			File file = context.getExternalCacheDir();
			//����·��:/mnt/sdcard/Android/data/<��İ���>/cache
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(file,fileName)));
				bos.write(data, 0, data.length);
				bos.flush();
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if (bos!=null) {
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		
		}
		return false;
	}
	
	/**
	 * ����ͼƬ��sd������Ŀ�Ļ���Ŀ¼��
	 * */
	public static boolean saveBitmapToSDCardCacheDir(Bitmap bitmap,String fileName,Context context){
		if (isSDCardMounted()) {
			BufferedOutputStream bos = null;
			File file = context.getExternalCacheDir();
			//����·��:/mnt/sdcard/Android/data/<��İ���>/cache
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(file,fileName)));
				if (fileName!=null&&(fileName.contains(".png")||fileName.contains(".PNG"))) {
					//format���洢ͼƬ�ĸ���     quilaty������      stream ��д�����
					bitmap.compress(Bitmap.CompressFormat.PNG, 90, bos);
				}else {
					bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
				}
				bos.flush();
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if (bos!=null) {
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}
	/**
	 * ����ָ���ļ����е��ļ�����������ʾ����
	 * */
	public static byte[] loadFileFromSDCard(String filePath){
		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		File file = new File(filePath);
		if (file.exists()) {
			try {
				bis = new BufferedInputStream(new FileInputStream(file));
				int len=0;
				byte buffer[] = new  byte[1024*8];
				while ((len=bis.read(buffer))!=-1) {
					baos.write(buffer, 0, len);
				}
				return baos.toByteArray();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if (bis!=null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
}
