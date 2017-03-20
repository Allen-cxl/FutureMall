package com.futuremall.android.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.text.DecimalFormat;

public class FileUtil {

    private static final String DOWNLOAD_FILE_DIR_NAME = "FutureMall_download";
    private static final String COMPRESS_FILE_DIR_NAME = "compress";
    private static final int IO_BUFFER_SIZE = 2048;

    public static boolean isFileIsExists(String strFile) {

        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static String getFileDirName(Context context) {

        File file;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            file = new File(context.getExternalCacheDir().getPath()
                    + File.separator + DOWNLOAD_FILE_DIR_NAME);
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            file = new File(context.getCacheDir().getPath()
                    + File.separator + DOWNLOAD_FILE_DIR_NAME);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return file.getAbsolutePath();
    }

    public static String getDownloadFileDirName(Context context) {

        File file;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            file = new File(context.getExternalCacheDir().getPath());
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            file = new File(context.getCacheDir().getPath());
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return file.getAbsolutePath();
    }

    public static String createCompressFileDirName(Context context) {

        File file;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            file = new File(context.getExternalCacheDir().getPath()
                    + File.separator + COMPRESS_FILE_DIR_NAME);
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            file = new File(context.getCacheDir().getPath()
                    + File.separator + COMPRESS_FILE_DIR_NAME);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return file.getAbsolutePath();
    }

    public static void removeFile(String fileDir) {

        if (StringUtil.isEmpty(fileDir)) return;
        File file = new File(fileDir);
        if (!file.exists()) {
            return;
        }

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
                f.delete();
            }
            file.delete();
        }
    }

    public static long getFolderSize(java.io.File file) throws Exception {
        long size = 0;
        java.io.File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFolderSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return size;
    }

    /**
     * 文件大小单位转换
     *
     * @param size
     * @return
     */
    public static String setFileSize(long size) {
        DecimalFormat df = new DecimalFormat("###.##");
        float f = ((float) size / (float) (1024 * 1024));

        if (f < 1.0) {
            float f2 = ((float) size / (float) (1024));

            return df.format(new Float(f2).doubleValue()) + "KB";

        } else {
            return df.format(new Float(f).doubleValue()) + "M";
        }
    }

    public static void deleteDir(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (!file.isDirectory()) {
                file.delete();
            } else {
                File[] files = file.listFiles();
                for (File subFile : files) {
                    if (!subFile.isDirectory())
                        subFile.delete();
                    else
                        deleteDir(subFile.getPath());
                }
            }
        }
    }

}