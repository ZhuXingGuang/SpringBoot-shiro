package com.zxg.plustest.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5Util是一个用来产生MD5签名和校验MD5签名的工具类
 *
 * 目前提供的功能如下：
 * 1. public static final String md5(String... plainTexts)
 * 对输入的字符串列表产生MD5签名
 *
 * 2. public static final boolean md5Check(String md5, String... plainTexts)
 * 根据输入的MD5签名和字符串列表进行校验
 *
 * 3.public final static String md5(String s)
 * 对密码进行加密
 *
 */
public class MD5Util {
	private final static Logger LOG = LoggerFactory.getLogger(MD5Util.class);
	/**
	 * 对输入的字符串列表产生MD5签名
	 *
	 * @param plainTexts 用来产生MD5签名的字符串列表
	 * @return 输入字符串列表的MD5签名
	 * @throws NoSuchAlgorithmException 当用户的JDK不支持MD5哈希算法时
	 * @throws UnsupportedEncodingException 当输入的字符串不是UTF-8编码时
	 */
	public static final String md5(String... plainTexts) {
		byte bytes[] = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			for (String plainText : plainTexts) {
				md.update(plainText.getBytes("UTF-8"));
			}
			bytes = md.digest();
		}catch (Exception e){
			LOG.error("生成MD5发生异常，生成字符串为:{}",plainTexts,e);
		}
		return Base16Util.byteToBase16(bytes);
	}

	/**
	 * 根据输入的MD5签名和字符串列表进行校验
	 *
	 * @param md5 用来进行校验的MD5签名
	 * @param plainTexts 用来进行校验的字符串列表
	 * @return 如果通过返回true，失败返回false
	 * @throws NoSuchAlgorithmException 当用户的JDK不支持MD5哈希算法时
	 * @throws UnsupportedEncodingException 当输入的字符串不是UTF-8编码时
	 */
	public static final boolean md5Check(String md5, String... plainTexts)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		for (String plainText : plainTexts) {
			md.update(plainText.getBytes("UTF-8"));
		}
		byte bytes[] = md.digest();

		return Base16Util.byteToBase16(bytes).equalsIgnoreCase(md5);
	}

	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		System.out.println(md5("E6f1-36E8-4"));
	}

	/**
	 * 广为流传的MD5加密方法
	 * @param s
	 * @return
	 */
	public final static String md5(String s){
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				// 将没个数(int)b进行双字节加密
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}


}
