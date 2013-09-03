package com.sq.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SQMD5 {
	/**
	 * MD5加密
	 * @param plainText
	 * @return
	 */
	public static String getEncryMD5(String plainText) {
		  try {
		   MessageDigest md = MessageDigest.getInstance("MD5");
		   md.update(plainText.getBytes());
		   byte b[] = md.digest();

		   int i;

		   StringBuffer buf = new StringBuffer("");
		   for (int offset = 0; offset < b.length; offset++) {
		    i = b[offset];
		    if (i < 0)
		     i += 256;
		    if (i < 16)
		     buf.append("0");
		    buf.append(Integer.toHexString(i));
		   }
		   //System.out.println("result: " + buf.toString());// 32位的加密
		   return  buf.toString().substring(8, 24).toUpperCase();// 16位的加密
		  } catch (NoSuchAlgorithmException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();

		  }
		  return null ;
	}

	public static void main(String agrs[]) {
		SQMD5 md51 = new SQMD5();
		md51.getEncryMD5("888888");// 加密4
	}

}
