package com.zxg.plustest.common.util;

/**
 * <p>Title: Base16Util</p >
 * <p>Description: </p >
 * <p>Company: http://www.taiyuejinfu.com</p >
 * <p>Project: TyjfSms</p >
 *
 * @author: WEIQI
 * @Date: @Date: 2019/03/04 6:32 PM
 * @Version: 1.0
 */
/**
 * Base16Util是一个用来实现在byte数组和16进制字符串之间相互转换的工具类
 *
 * 目前提供的功能如下：
 * 1. public static final String byteToBase16(byte[] bytes)
 * 将byte数组转换成16进制字符串
 *
 * 2. public static final byte[] base16toByte(String str)
 * 将16进制字符串转换成byte数组
 * @version 1.0.0
 */
public class Base16Util {

	/**
	 * 将byte数组转换成16进制字符串
	 *
	 * @param bytes 待转换的byte数组
	 * @return 转换后的16进制字符串
	 * @throws IllegalArgumentException 假如byte数组是null
	 */
	public static final String byteToBase16(byte[] bytes) {
		if (bytes == null)
			throw new IllegalArgumentException("The parameter should not be null!");
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			sb.append(Integer.toHexString((b & 0xF0) >> 4));
			sb.append(Integer.toHexString(b & 0x0F));
		}
		return sb.toString();
	}

	/**
	 * 将16进制字符串转换成byte数组
	 *
	 * @param str 待转换的16进制字符串
	 * @return 转换后的byte数组，假如待转换的16进制字符串不可以被2整除则返回空数组
	 * @throws IllegalArgumentException 假如输入字符串是null
	 * @throws NumberFormatException 假如待转换的16进制字符串含有不可以解析成16进制数字的字符
	 */
	public static final byte[] base16toByte(String str) {
		if (str == null)
			throw new IllegalArgumentException("The parameter should not be null!");
		if (str.length() % 2 != 0) {
			return new byte[] {};
		} else {
			char[] hex = str.toCharArray();
			byte[] bytes = new byte[hex.length / 2];
			for (int i = 0, j = 0; i < hex.length; i += 2, ++j) {
				bytes[j] = (byte) ((Integer.parseInt(String.valueOf(hex[i]), 16) << 4)
						| (Integer.parseInt(String.valueOf(hex[i + 1]), 16)));
			}
			return bytes;
		}
	}

}

