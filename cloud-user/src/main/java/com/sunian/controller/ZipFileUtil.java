package com.sunian.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by liull on 2019/8/22.
 */
public class ZipFileUtil {
    /*
  * inputFileName 输入一个文件夹 zipFileName 输出一个压缩文件夹
  */
    public static void zip(String inputFileName) throws Exception {
        String zipFileName = "H:\\liull.zip"; // 打包后文件名字
        //System.out.println(zipFileName);
        zip(zipFileName, new File(inputFileName));

    }

    private static void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                zipFileName));
        zip(out, inputFile, "");
        //System.out.println("zip done");
        out.close();
    }

    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + fl[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(f);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
        }
    }

    public static void main(String [] temp){
        try {
            zip("H:\\liull");//你要压缩的文件夹
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
