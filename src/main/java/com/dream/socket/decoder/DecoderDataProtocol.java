package com.dream.socket.decoder;


import com.dream.socket.utils.ByteUtil;

/**
 * @author 陶乐乐(wangyiqianyi@qq.com)
 * @ClassName: DecoderDataProtocol.java
 * @Description:
 * @date 2018-01-29 上午11:21
 */
public class DecoderDataProtocol {
	//解析指令
	public static int T(String data){
		String tStr=data.substring(4,6);
		return Integer.parseInt(tStr,16);
	}
	//解析长度
	public static int L(String data){
		String tStr=data.substring(6,10);
		return Integer.parseInt(tStr,16);
	}
	//解析Mac地址
	public static String M(String data){
		String tStr=data.substring(32,44);
		return ByteUtil.getMac(tStr);
	}
	//解析内容
	public static String V(String data){
		String tStr=data.substring(44,data.length()-4);
		return tStr;
	}

	public static void main(String[] args) {
		String str="EF010102001F00000000000000000000DD171665FB33FAFB0D0008EE568A87D95FFC5DF664";
		System.out.println(V(str));
		System.out.println("EF010001002900000000000000000000DD171665FB33FAFB0C0012852389CFEBD176A015D0BA1D9B084AA2F7C4F14D".length()-82);
	}
}
