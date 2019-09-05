/**
 *
 * @Title: DES.java 
 * @package：com.ai.realnamesearch.addkey TODO
 * @author：wangyy
 * @date：2014-8-25
 * @version：V1.0
 *
 */
package com.sunian.utils;

import java.io.BufferedInputStream;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * Function： demo Author：wangyy CreateDate：2014-8-25
 * 
 * @version:V1.0
 * 
 */
public class Decipher {

//	 private static Logger logger = LoggerFactory.getLogger(Decipher.class);
	
	// select为0时AES ； select为1时3DES
	private static Key key;

	/**
	 * 
	 * 该方法描述的是：文本转换成byte数组
	 * @param file
	 * @return
	 * @throws IOException
	 *
	 */
	public static byte[] file2Byte(File file) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				file));
		byte[] bytIn = new byte[(int) file.length()];
		bis.read(bytIn);
		bis.close();
		return bytIn;
	}

	/**
	 * 
	 *
	 * 该方法描述的是：byte转换成文本
	 * @param byt
	 * @param name
	 * @param codePath
	 * @return
	 * @throws IOException
	 *
	 */
	public static String byte2File(byte[] byt, String name, String codePath)
			throws IOException {
		
		File outFile = new File(codePath + name);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFile));
		bos.write(byt);
		
		//System.out.println(bytesToHex(byt));
		
		bos.close();
		return name;
	}

	/**
	 * 
	 *
	 * 该方法描述的是：MD5加密
	 * 				AES密钥：abc经过MD5加密后的byte，
					AES密钥：[-112, 1, 80, -104, 60, -46, 79, -80, -42, -106, 63, 125, 40, -31, 127, 114]
	 * 				3DES密钥：abc经MD5加密后的32位16进制字符串 + 截取该字符串的前10位字符串
	 * 				转换成byte，即为：AES密钥 + byte数组的前5个字符
	 * 				3DES密钥：[-112, 1, 80, -104, 60, -46, 79, -80, -42, -106, 63, 125, 40, -31, 127, 114, -112, 1, 80, -104, 60]
	 * 				
	 * @param keyStr
	 * @param select
	 * @return
	 *
	 */
	public static byte[] md5(String keyStr, int select) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			// AES密钥：abc经过MD5加密后直接使用
			if (select == 0) {
				byte[] AESkey = md5.digest(keyStr.getBytes());
				//System.out.println("AES密钥为：" + AESkey);
				//logger.info("-----AES密钥为：" + AESkey);
				return AESkey;
			} else {
				// 3DES密钥为168位的16进制串：900150983cd24fb0d6963f7d28e17f72900150983c900150983c，
				// 转换成byte：abc经过MD5加密后的16位byte + 前5位byte

				byte[] md5Bytes = md5.digest(keyStr.getBytes());
				byte[] DESkey = new byte[21];
				if (md5Bytes.length < DESkey.length) {
					System.arraycopy(md5Bytes, 0, DESkey, 0, md5Bytes.length);
					for (int i = md5Bytes.length, j = 0; i < DESkey.length; i++, j++) {
						DESkey[i] = md5Bytes[j];
					}
				}
				//System.out.println("DES密钥为：" + DESkey);
				//logger.info("-----DES密钥为：" + DESkey);
				return DESkey;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("-----MD5密钥转换失败：" + e);
			//logger.info("-----MD5密钥转换失败：" + e);

			return null;
		}
	}

	/**
	 * 
	 *
	 * 该方法描述的是：加密算法：
	 * 				select为0：AES加密；select为1：3DES加密
	 * @param content
	 * @param encryKey
	 * @param select
	 * @return
	 *
	 */
	public static byte[] encrypt(byte[] content, String encryKey, int select) {

		SecretKey key;
		Cipher cipher;
		byte[] result;

		try {
			byte[] md5Key = Decipher.md5(encryKey, select);
			//System.out.println(bytesToHex(md5Key));
			
			if (select == 0) {
				key = new SecretKeySpec(md5Key, "AES");
				cipher = Cipher.getInstance("AES");// 创建密码器
				cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
				result = cipher.doFinal(content);

				return result; // 加密
			} else {

				key = new SecretKeySpec(build3DesKey(md5Key), "DESede");
				//cipher = Cipher.getInstance("DESede");// 创建密码器
				cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");// 创建密码器
				cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
				result = cipher.doFinal(content);
				return result; // 加密

			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {	
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 *
	 * 该方法描述的是：解密算法：
	 * 				select为0：AES解密；select为1：3DES解密
	 * @param content
	 * @param encryKey
	 * @param select
	 * @return
	 *
	 */
	public static byte[] decrypt(byte[] content, String decryKey, int select) {

		SecretKey deskey;
		Cipher cipher;
		byte[] result;

		try {
			byte[] md5Key = Decipher.md5(decryKey, select);
			if (select == 0) {
				key = new SecretKeySpec(md5Key, "AES");
				cipher = Cipher.getInstance("AES");// 创建密码器
				cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
				result = cipher.doFinal(content);

				return result; // 解密
			} else {
				deskey = new SecretKeySpec(build3DesKey(md5Key), "DESede");
				//cipher = Cipher.getInstance("DESede");// 创建密码器
				cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");// 创建密码器
				cipher.init(Cipher.DECRYPT_MODE, deskey);// 初始化
				result = cipher.doFinal(content);

				return result; // 解密
			}
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 *
	 * 该方法描述的是：根据参数数组，生成符合条件的密钥字节数组
	 * 				DES密钥长度为56位+8位的校验位
	 *              3DES长度应该是56*3+8*3=192位  24个字节 
	 * @param temp
	 * @return
	 * @throws UnsupportedEncodingException
	 *
	 */
	public static byte[] build3DesKey(byte[] temp)throws UnsupportedEncodingException {
		//logger.info("-----DES密钥转换，调用方法是：build3DesKey");
		byte[] key = new byte[24];
		// byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组

		// 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		
		return key;
	}
	/**
	 * String to byte[]
	 * @param str
	 * @return
	 */
	public static byte[] strToByteArray(String str) {
	    if (str == null) {
	        return null;
	    }
	    byte[] byteArray = str.getBytes();
	    return byteArray;
	}
	/**
	 * byte[] to String
	 * @param byteArray
	 * @return
	 */
	public static String byteArrayToStr(byte[] byteArray) {
	    if (byteArray == null) {
	        return null;
	    }
	    String str = new String(byteArray);
	    return str;
	}
	

	public static String bytesToHex(byte[] bytes) {  
	    StringBuffer sb = new StringBuffer();  
	    for(int i = 0; i < bytes.length; i++) {  
	        String hex = Integer.toHexString(bytes[i] & 0xFF);  
	        if(hex.length() < 2){  
	            sb.append(0);  
	        } 
	        sb.append(hex);
	    }
	    return sb.toString();  
	}
	
	// 字节数组转换为 16 进制
    public static String byteArrayToHexString(byte[] bytes) {
        final String HEX = "0123456789abcdef";
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt((b >> 4) & 0x0f));
            // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt(b & 0x0f));
        }

        return sb.toString();
    }
    
    public static byte[] hexStringToByteArray(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }   
    
    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    
    /**
     * 解压缩文件
     * @param zip
     * @param outputDir
     * @param charsetName
     */
    public static void unpack(File zip, File outputDir, String charsetName) {

        FileOutputStream out = null;
        InputStream in = null;
        //读出文件数据
        ZipFile zipFileData = null;
        ZipFile zipFile = null;
        try {
            //若目标保存文件位置不存在
            if (outputDir != null) if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            if (charsetName != null && charsetName != "") {
                zipFile = new ZipFile(zip.getPath(), Charset.forName(charsetName));
            } else {
                zipFile = new ZipFile(zip.getPath(), Charset.forName("utf8"));
            }
            //zipFile = new ZipFile(zip.getPath(), Charset.forName(charsetName));
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            //处理创建文件夹
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String filePath = "";

                if (outputDir == null) {
                    filePath = zip.getParentFile().getPath() + File.separator + entry.getName();
                } else {
                    filePath = outputDir.getPath() + File.separator + entry.getName();
                }
                File file = new File(filePath);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                if (parentFile.isDirectory()) {
                    continue;
                }
            }
            if (charsetName != null && charsetName != "") {
                zipFileData = new ZipFile(zip.getPath(), Charset.forName(charsetName));
            } else {
                zipFileData = new ZipFile(zip.getPath(), Charset.forName("utf8"));
            }
            Enumeration<? extends ZipEntry> entriesData = zipFileData.entries();
            while (entriesData.hasMoreElements()) {
                ZipEntry entry = entriesData.nextElement();
                in = zipFile.getInputStream(entry);
                String filePath = "";
                if (outputDir == null) {
                    filePath = zip.getParentFile().getPath() + File.separator + entry.getName();
                } else {
                    filePath = outputDir.getPath() + File.separator + entry.getName();
                }
                File file = new File(filePath);
                if (file.isDirectory()) {
                    continue;
                }
                out = new FileOutputStream(filePath);
                int len = -1;
                byte[] bytes = new byte[1024];
                while ((len = in.read(bytes)) != -1) {
                    out.write(bytes, 0, len);
                }
                out.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
                zipFile.close();
                zipFileData.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	public static void main(String[] args) throws InvalidKeyException,NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException,UnsupportedEncodingException, IOException {
		
		String fileName = "";
		String key = "shlty8d8pzi2fxdt4tguj7uv";

		/*File file = new File("H:\\baoleiji\\3_shlt_20190902172946_9027de.txt");
		byte[] bytes = Decipher.file2Byte(file);
		byte[] s = Decipher.encrypt(bytes,key,2);
		fileName = byte2File(s,"3_shlt_20190902172946_9027.txt","H:\\baoleiji\\");*/
		//
//		File sourceFile = new File("d:/3_fjlt_20190610151844_1234");
		File sourceFile = new File("H:\\baoleiji\\3_shlt_20190902172946_9027.txt");
//		File sourceFile = new File("d:/zip/2_xzyd_20190813113800_5444/2_xzyd_20190813114726_49102053778801_47.jpg");
//		File sourceFile = new File("/bjngbss/intf/interface/other/REALNAME/codebak/YXKJ_20140827_2_0.txt");
		// 解密
//		fileName = Decipher.byte2File(Decipher.decrypt(Decipher.file2Byte(sourceFile),key,1),sourceFile.getName(),"d:/zip/2_xzyd_20190813113800_5444");
		fileName = Decipher.byte2File(Decipher.decrypt(Decipher.file2Byte(sourceFile),key,1),sourceFile.getName(),"H:\\");
		System.out.println(fileName);
		//		fileName = Decipher.byte2File(Decipher.decrypt(Decipher.file2Byte(sourceFile),key,0),sourceFile.getName(),"e:/");
//		 fileName = Decipher.byte2File(Decipher.decrypt(Decipher.file2Byte(sourceFile),key,1),sourceFile.getName(),"f:/upload/");
		
		
		
		// 加密
//		fileName = Decipher.byte2File(Decipher.encrypt(Decipher.file2Byte(sourceFile),key,1),sourceFile.getName(),"d:/upload/");
//		fileName = Decipher.byte2File(Decipher.encrypt(Decipher.file2Byte(sourceFile),key,1),sourceFile.getName(),"d:/");
//		System.out.println(fileName);
		
		/*
		String req = "hellsdadadao";
		String a = new String(Decipher.encrypt(req.getBytes("GBK"), key, 1), "GBK");
		
		System.out.println("a="+a + ", a.length="+ a.length());
		System.out.println(a.getBytes( "GBK"));
		System.out.println(new String(Decipher.decrypt(a.getBytes( "GBK"), key, 1)));
		*/		
		
		/*
		String req = "hellsdadadao";
		String a = new String(Decipher.encrypt(req.getBytes(), key, 1), "utf-8");
		
		System.out.println("a="+a + ", a.length="+ a.length());
		//System.out.println(a.getBytes( "utf-8"));
		System.out.println(new String(Decipher.decrypt(a.getBytes(), key, 1), "utf-8"));
		*/
		
		
		
		/*String req = "hellsdadada";
		String a = new String(Decipher.encrypt(req.getBytes(), key, 1), "GBK");
		
		System.out.println("a="+a + ", a.length="+ a.length());
		System.out.println(new String(Decipher.decrypt(a.getBytes("GBK"), key, 1)));*/
		
	}

}
