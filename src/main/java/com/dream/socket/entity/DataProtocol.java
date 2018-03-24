package com.dream.socket.entity;


import com.dream.socket.utils.ByteUtil;

/**
 * @author 陶乐乐(wangyiqianyi@qq.com)
 * @ClassName: DataProtocol.java
 * @Description:
 * @date 2018-01-22 上午10:55
 */
public class DataProtocol {
	private byte[] S;//同步字   固定为0xef，0x01
	private byte[] T;//指令字  功能码
	// 第一个字节：代表上行下行（数据流方向），01代表上行，02代表下行；
	//第二个字节：02代表上行密文；03代表下行密文；

	private byte[] L;//数据内容+校验位字节数 m+v+c 2字节
	private byte[] M;//MAC地址
	private byte[] V;//数据内容
	private byte[] C;//校验位   T+L+M+V

	public DataProtocol(){
	}
	public DataProtocol(byte[] t,byte[] m,byte [] v){
		this.S= new byte[]{(byte) 0xef, (byte) 0x01};
		this.T=t;
		this.M=m;
		this.V=v;
		short st= (short)(16+getV().length+2);
		this.L=ByteUtil.toHH(st);
        String cc= ByteUtil.bytesToHex(getT())
		        +ByteUtil.bytesToHex(getL())
		        +ByteUtil.bytesToHex(getM())
		        +ByteUtil.bytesToHex(getV());
        cc=ByteUtil.makeChecksum(cc);
        int ccc=Integer.parseInt(cc,16);
		this.C=ByteUtil.toHH((short)~ccc);

	}
	public byte[] getS() {
		return S;
	}

	public void setS(byte[] s) {
		S = s;
	}

	public byte[] getT() {
		return T;
	}

	public void setT(byte[] t) {
		T = t;
	}

	public byte[] getL() {
		return L;
	}

	public void setL(byte[] l) {
		L = l;
	}

	public byte[] getV() {
		return V;
	}

	public void setV(byte[] v) {
		V = v;
	}

	public byte[] getC() {
		return C;
	}

	public void setC(byte[] c) {
		C = c;
	}

	public byte[] getM() {
		return M;
	}

	public void setM(byte[] m) {
		M = m;
	}

	@Override
	public String toString() {
		return ByteUtil.bytesToHex(getS())
				+ByteUtil.bytesToHex(getT())
				+ByteUtil.bytesToHex(getL())
				+ByteUtil.bytesToHex(getM())
				+ByteUtil.bytesToHex(getV())
				+ByteUtil.bytesToHex(getC());
	}
	public static void main(String[] args) {
		//ef 01 00 01 00 19 00 00 00 00 00 00 00 00 00 00 dd 17 16 65 fb 33 fa fb 06 00 02 ff f7 f9 55
		String  mac="00:00:00:00:00:00:00:00:00:00:DD:17:16:65:FB:33".replace(":","");
		String authModel=new AuthModel(new byte[]{6}).toString();

		DataProtocol dataProtocol=new DataProtocol(new byte[]{00,01},ByteUtil.hexToBytes(mac),ByteUtil.hexToBytes(authModel));
		System.out.println(dataProtocol.toString());
		//EF010001001900000000000000000000DD171665FB33FAFB060002FFF7F955

        String result1="EF01010200320000000000EF010102003200000000000000000000DD171665FB33FAFB06001B646300010132303138303132393131323732";
		/*String responseData=result1.substring(44,result1.length()-4);
		//FAFB0D0008F3779A386D3BFD06
		String responseData2=responseData.substring(10,responseData.length()-4);
		System.out.println(responseData2);*/
		System.out.println(result1.length());
		System.out.println(Integer.parseInt(result1.substring(8,12),16));

	}


}
