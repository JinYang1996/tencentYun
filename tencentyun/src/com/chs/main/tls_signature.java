package com.chs.main;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.nio.charset.Charset;

import java.security.Signature;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.Arrays;
import org.json.JSONObject;

import com.chs.main.base64_url;

public class tls_signature {
	public static class GenTLSSignatureResult
	{
		public String errMessage;
		public String urlSig;
		public int expireTime;
		public int initTime;
		public GenTLSSignatureResult()
		{
			errMessage = "";
			urlSig = "";
		}
	}

	public static class CheckTLSSignatureResult
	{
		public String errMessage;
		public boolean verifyResult;
		public int expireTime;
		public int initTime;
		public CheckTLSSignatureResult()
		{
			errMessage = "";
			verifyResult = false;
		}
	}

	/**
	 * ���� tls Ʊ��
	 * @param expire ��Ч�ڣ���λ���룬�Ƽ�һ����
	 * @param strAppid3rd ��д�� sdkAppid һ���ַ�����ʽ��ֵ
	 * @param skdAppid Ӧ�õ� appid
	 * @param identifier �û� id
	 * @param accountType ����Ӧ�ú�������ҳ����չʾ�� acctype
	 * @param privStr ���� tls Ʊ��ʹ�õ�˽Կ����
	 * @return �������GenTLSSignatureResult �е� urlSigΪ�գ�errMsg Ϊ������Ϣ���ɹ�������Ч��Ʊ��
	 */
	@Deprecated
	public static GenTLSSignatureResult GenTLSSignature(long expire, 
			String strAppid3rd, long skdAppid, 
			String identifier, long accountType, 
			String privStr)
	{
		GenTLSSignatureResult result = new GenTLSSignatureResult();
		
        Security.addProvider(new BouncyCastleProvider());
        Reader reader = new CharArrayReader(privStr.toCharArray());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PEMParser parser = new PEMParser(reader);
		PrivateKey privKeyStruct;
        try{
			Object obj = parser.readObject();
			parser.close();
			privKeyStruct = converter.getPrivateKey((PrivateKeyInfo) obj);
		} catch (IOException e) {
			result.errMessage = "read pem error:" + e.getMessage();
			return result;
		}
		
		//Create Json string and serialization String 
		String jsonString = "{" 
		+ "\"TLS.account_type\":\"" + accountType +"\","
		+"\"TLS.identifier\":\"" + identifier +"\","
		+"\"TLS.appid_at_3rd\":\"" + strAppid3rd +"\","
	    +"\"TLS.sdk_appid\":\"" + skdAppid +"\","
		+"\"TLS.expire_after\":\"" + expire +"\""
		+"}";
		//System.out.println("#jsonString : \n" + jsonString);
		
		String time = String.valueOf(System.currentTimeMillis()/1000);
		String SerialString = 
			"TLS.appid_at_3rd:" + strAppid3rd + "\n" +
			"TLS.account_type:" + accountType + "\n" +
			"TLS.identifier:" + identifier + "\n" + 
			"TLS.sdk_appid:" + skdAppid + "\n" + 
			"TLS.time:" + time + "\n" +
			"TLS.expire_after:" + expire +"\n";
	
		
		//System.out.println("#SerialString : \n" + SerialString);
		//System.out.println("#SerialString Hex: \n" + Hex.encodeHexString(SerialString.getBytes()));
		
		try{
			//Create Signature by SerialString
			Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
			signature.initSign(privKeyStruct);
			signature.update(SerialString.getBytes(Charset.forName("UTF-8")));
			byte[] signatureBytes = signature.sign();
			
			String sigTLS = Base64.toBase64String(signatureBytes);
			//System.out.println("#sigTLS : " + sigTLS);
			
			//Add TlsSig to jsonString
		    JSONObject jsonObject= new JSONObject(jsonString);
		    jsonObject.put("TLS.sig", sigTLS);
		    jsonObject.put("TLS.time", time);
		    jsonString = jsonObject.toString();
		    
		   // System.out.println("#jsonString : \n" + jsonString);
		    
		    //compression
		    Deflater compresser = new Deflater();
		    compresser.setInput(jsonString.getBytes(Charset.forName("UTF-8")));

		    compresser.finish();
		    byte [] compressBytes = new byte [512];
		    int compressBytesLength = compresser.deflate(compressBytes);
		    compresser.end();

		    result.urlSig = new String(base64_url.base64EncodeUrl(Arrays.copyOfRange(compressBytes,0,compressBytesLength)));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result.errMessage = e.getMessage();
		}
		
		return result;
	}

	/**
	 * У�� tls Ʊ��
	 * @param urlSig ���� tls Ʊ��
	 * @param strAppid3rd ��д�� sdkAppid һ�µ��ַ�����ʽ��ֵ
	 * @param skdAppid Ӧ�� appid
	 * @param identifier �û� id
	 * @param accountType ����Ӧ�ú�������ҳ����չʾ�� acctype
	 * @param publicKey ����У�� tls Ʊ�ݵĹ�Կ���ݣ�������Ҫ�Ƚ���Կ�ļ�ת��Ϊ java ԭ�� api ʹ�õĸ�ʽ���������Ƽ�������
	 *         openssl pkcs8 -topk8 -in ec_key.pem -outform PEM -out p8_priv.pem -nocrypt
	 * @return ������� CheckTLSSignatureResult �е� verifyResult Ϊ false��������Ϣ�� errMsg��У��ɹ�Ϊ true
	 */
	@Deprecated
	public static CheckTLSSignatureResult CheckTLSSignature( String urlSig,
			String strAppid3rd, long skdAppid, 
			String identifier, long accountType, 
			String publicKey )
	{
		CheckTLSSignatureResult result = new CheckTLSSignatureResult();	
        Security.addProvider(new BouncyCastleProvider());

		//byte [] compressBytes = decoder.decode(urlSig.getBytes());
		byte [] compressBytes = base64_url.base64DecodeUrl(urlSig.getBytes(Charset.forName("UTF-8")));
		
		//System.out.println("#compressBytes Passing in[" + compressBytes.length + "] " + Hex.encodeHexString(compressBytes));
	
		//Decompression
		Inflater decompression =  new Inflater();
		decompression.setInput(compressBytes, 0, compressBytes.length);
		byte [] decompressBytes = new byte [1024];
		int decompressLength;
		try {
            decompressLength = decompression.inflate(decompressBytes);
        } catch (DataFormatException e){
		    result.errMessage = "uncompress data error:" + e.getMessage();
		    return  result;
        }
		decompression.end();
		
		String jsonString = new String(Arrays.copyOfRange(decompressBytes, 0, decompressLength));
		
		//System.out.println("#Json String passing in : \n" + jsonString);
		
		//Get TLS.Sig from json
		JSONObject jsonObject= new JSONObject(jsonString);
		String sigTLS = jsonObject.getString("TLS.sig");
		
		//debase64 TLS.Sig to get serailString
		byte[] signatureBytes = Base64.decode(sigTLS.getBytes(Charset.forName("UTF-8")));
		
		try{
			
			String sigTime = jsonObject.getString("TLS.time");
			String sigExpire = jsonObject.getString("TLS.expire_after");
			
			//checkTime
			//System.out.println("#time check: "+ System.currentTimeMillis()/1000 + "-" 
					//+ Long.parseLong(sigTime) + "-" + Long.parseLong(sigExpire));
			if( System.currentTimeMillis()/1000 - Long.parseLong(sigTime) > Long.parseLong(sigExpire))
			{
				result.errMessage = new String("TLS sig is out of date ");
				System.out.println("Timeout");
				return result;
			}
			
			//Get Serial String from json
			String SerialString = 
				"TLS.appid_at_3rd:" + strAppid3rd + "\n" +
				"TLS.account_type:" + accountType + "\n" +
				"TLS.identifier:" + identifier + "\n" + 
				"TLS.sdk_appid:" + skdAppid + "\n" + 
				"TLS.time:" + sigTime + "\n" + 
				"TLS.expire_after:" + sigExpire + "\n";
		
			//System.out.println("#SerialString : \n" + SerialString);
		
	        Reader reader = new CharArrayReader(publicKey.toCharArray());
	        PEMParser  parser = new PEMParser(reader);
	        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
	        Object obj = parser.readObject();
	        parser.close();
	        PublicKey pubKeyStruct  = converter.getPublicKey((SubjectPublicKeyInfo) obj);

			Signature signature = Signature.getInstance("SHA256withECDSA","BC");
			signature.initVerify(pubKeyStruct);
			signature.update(SerialString.getBytes(Charset.forName("UTF-8")));
			result.verifyResult = signature.verify(signatureBytes);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result.errMessage = "Failed in checking sig";
		}
		
		return result;
	}

	/**
	 * ���� tls Ʊ�ݣ���������б���Ч��Ĭ��Ϊ 180 ��
	 * @param skdAppid Ӧ�õ� sdkappid
	 * @param identifier �û� id
	 * @param privStr ˽Կ�ļ�����
	 * @return GenTLSSignatureResult
	 */
	public static GenTLSSignatureResult GenTLSSignatureEx(
			long skdAppid,
			String identifier,
			String privStr) {
		return GenTLSSignatureEx(skdAppid, identifier, privStr, 3600*24*180);
	}

	/**
	 * ���� tls Ʊ�ݣ���������б�
	 * @param skdAppid Ӧ�õ� sdkappid
	 * @param identifier �û� id
	 * @param privStr ˽Կ�ļ�����
	 * @param expire ��Ч�ڣ�����Ϊ��λ���Ƽ�ʱ��һ����
	 * @return GenTLSSignatureResult
	 */
	public static GenTLSSignatureResult GenTLSSignatureEx(
			long skdAppid,
			String identifier,
			String privStr,
			long expire) {
		return GenTLSSignature(expire, "0", skdAppid, identifier, 0, privStr);
	}
	
	public static CheckTLSSignatureResult CheckTLSSignatureEx(
			String urlSig,
			long sdkAppid, 
			String identifier, 
			String publicKey ) throws DataFormatException {

		CheckTLSSignatureResult result = new CheckTLSSignatureResult();	
        Security.addProvider(new BouncyCastleProvider());

		byte [] compressBytes = base64_url.base64DecodeUrl(urlSig.getBytes(Charset.forName("UTF-8")));
		
		//Decompression
		Inflater decompression =  new Inflater();
		decompression.setInput(compressBytes, 0, compressBytes.length);
		byte [] decompressBytes = new byte [1024];
		int decompressLength = decompression.inflate(decompressBytes);
		decompression.end();
		
		String jsonString = new String(Arrays.copyOfRange(decompressBytes, 0, decompressLength));
		
		//Get TLS.Sig from json
		JSONObject jsonObject= new JSONObject(jsonString);
		String sigTLS = jsonObject.getString("TLS.sig");
		
		//debase64 TLS.Sig to get serailString
		byte[] signatureBytes = Base64.decode(sigTLS.getBytes(Charset.forName("UTF-8")));
		
		try {
			String strSdkAppid = jsonObject.getString("TLS.sdk_appid");
			String sigTime = jsonObject.getString("TLS.time");
			String sigExpire = jsonObject.getString("TLS.expire_after");
			
			if (Integer.parseInt(strSdkAppid) != sdkAppid)
			{
				result.errMessage = new String(	"sdkappid "
						+ strSdkAppid
						+ " in tls sig not equal sdkappid "
						+ sdkAppid
						+ " in request");
				return result;
			}

			if ( System.currentTimeMillis()/1000 - Long.parseLong(sigTime) > Long.parseLong(sigExpire)) {
				result.errMessage = new String("TLS sig is out of date");
				return result;
			}
			
			//Get Serial String from json
			String SerialString = 
				"TLS.appid_at_3rd:" + 0 + "\n" +
				"TLS.account_type:" + 0 + "\n" +
				"TLS.identifier:" + identifier + "\n" + 
				"TLS.sdk_appid:" + sdkAppid + "\n" + 
				"TLS.time:" + sigTime + "\n" + 
				"TLS.expire_after:" + sigExpire + "\n";
		
	        Reader reader = new CharArrayReader(publicKey.toCharArray());
	        PEMParser  parser = new PEMParser(reader);
	        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
	        Object obj = parser.readObject();
	        parser.close();
	        PublicKey pubKeyStruct  = converter.getPublicKey((SubjectPublicKeyInfo) obj);

			Signature signature = Signature.getInstance("SHA256withECDSA","BC");
			signature.initVerify(pubKeyStruct);
			signature.update(SerialString.getBytes(Charset.forName("UTF-8")));
			boolean bool = signature.verify(signatureBytes);
            result.expireTime = Integer.parseInt(sigExpire);
            result.initTime = Integer.parseInt(sigTime);
			result.verifyResult = bool;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result.errMessage = "Failed in checking sig";
		}
		
		return result;
	}

	public static GenTLSSignatureResult genSig(
			long sdkappid,
			String identifier,
			String priKey) {
		// Ĭ�� 180 ��
		return GenTLSSignature(24*3600*180, "0", sdkappid, identifier, 0, priKey);
	}

	public static GenTLSSignatureResult genSig(
			long sdkappid,
			String identifier,
			int expire,
			String priKey) {
		return GenTLSSignature(expire, "0", sdkappid, identifier, 0, priKey);
	}
}
