package com.chs.test;

import java.util.zip.DataFormatException;

import org.junit.Assert;
import org.junit.Test;
import com.tls.tls_sigature.*;

public class TlsSigTest {
    @Test
    public void genAndVerify() {
        try {
            //Use pemfile keys to test
            String privStr = "-----BEGIN PRIVATE KEY-----\n" +
                "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgkTfHxPa8YusG+va8\n" +
                "1CRztNQBOEr90TBEjlQBZ5d1Y0ChRANCAAS9isP/xLib7EZ1vS5OUy+gOsYBwees\n" +
                "PMDvWiTygPAUsGZv1PHLoa0ciqsElkO1fMGwNrzOKJx1Oo194Ri+SypV\n" +
                "-----END PRIVATE KEY-----";

            //change public pem string to public string
            String pubStr = "-----BEGIN PUBLIC KEY-----\n" +
                "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEvYrD/8S4m+xGdb0uTlMvoDrGAcHn\n" +
                "rDzA71ok8oDwFLBmb9Txy6GtHIqrBJZDtXzBsDa8ziicdTqNfeEYvksqVQ==\n" +
                "-----END PUBLIC KEY-----";

            // generate signature
            tls_sigature.GenTLSSignatureResult result = tls_sigature.GenTLSSignatureEx(1400000000, "xiaojun", privStr);
            Assert.assertNotEquals(null, result);
            Assert.assertNotEquals(null, result.urlSig);
            Assert.assertNotEquals(0, result.urlSig.length());

            // check signature
            tls_sigature.CheckTLSSignatureResult checkResult = tls_sigature.CheckTLSSignatureEx(result.urlSig, 1400000000, "xiaojun", pubStr);
            Assert.assertNotEquals(null, checkResult);
            Assert.assertTrue(checkResult.verifyResult);

            checkResult = tls_sigature.CheckTLSSignatureEx(result.urlSig, 1400000000, "xiaojun2", pubStr);
            Assert.assertNotEquals(null, checkResult);
            Assert.assertFalse( checkResult.verifyResult);


            // new interface generate signature
            result = tls_sigature.genSig(1400000000, "xiaojun", privStr);
            Assert.assertNotEquals(null, result);
            Assert.assertNotEquals(null, result.urlSig);
            Assert.assertNotEquals(0, result.urlSig.length());

            // check signature
            checkResult = tls_sigature.CheckTLSSignatureEx(result.urlSig, 1400000000, "xiaojun", pubStr);
            Assert.assertNotEquals(null, checkResult);
            Assert.assertTrue(checkResult.verifyResult);

            checkResult = tls_sigature.CheckTLSSignatureEx(result.urlSig, 1400000000, "xiaojun2", pubStr);
            Assert.assertNotEquals(null, checkResult);
            Assert.assertFalse( checkResult.verifyResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
    	//私钥
    	String privStr = "-----BEGIN PRIVATE KEY-----\n"+
				"MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgzkj1SXLdX+MnzUSK\n"+
				"cnIJ55iHd2sI/9Th6GJDZfw3jnWhRANCAASdgnnR9fO8WSI3l0zbETS8COIbFjgn\n"+
				"L2Kmj7LJi+jdDFxI3eswRtWSkoCtlFmtiSe/cuzj/DUCVlHgcEAxjW78\n"+
				"-----END PRIVATE KEY-----";
    	//公钥
    	String pubStr = "-----BEGIN PUBLIC KEY-----\n"+
				"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEnYJ50fXzvFkiN5dM2xE0vAjiGxY4\n"+
				"Jy9ipo+yyYvo3QxcSN3rMEbVkpKArZRZrYknv3Ls4/w1AlZR4HBAMY1u/A==\n"+
				"-----END PUBLIC KEY-----";
    	//加密
    	tls_sigature.GenTLSSignatureResult result = tls_sigature.genSig(1400190410, "user", privStr);
        System.out.println(result.urlSig);
        
        try {
        	//根据公钥验证登陆凭证
			tls_sigature.CheckTLSSignatureResult checkResult = tls_sigature.CheckTLSSignatureEx(result.urlSig, 1400190410, "xiaojun", pubStr);
			System.out.println(checkResult.verifyResult);
		} catch (DataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
