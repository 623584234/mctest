package com.zhaohang.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.security.InvalidKeyException;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;

import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;

import java.security.cert.CertificateException;

import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Properties;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.springframework.core.io.support.PropertiesLoaderUtils;


public class PkiUtils {
	
	 static{
         try{
               Security.addProvider(new BouncyCastleProvider());
             }catch(Exception e){
               e.printStackTrace();
             }
    }

	public static String sign(String plaintext,String clientId) throws IOException, KeyStoreException, UnrecoverableKeyException, InvalidKeyException, NoSuchAlgorithmException, CertificateException, NoSuchProviderException, SignatureException {
		 
		Properties properties = properties = PropertiesLoaderUtils.loadAllProperties("properties/sign.properties");
		String keyStoreName = properties.getProperty("keyStoreName-"+clientId);
		String keyStorePaw = properties.getProperty("keyStorePaw-"+clientId);
		String signAlg = properties.getProperty("signAlg-"+clientId);
		String keyStoreType = properties.getProperty("keyStoreType-"+clientId);
		//String filePathPrefix = PkiUtils.class.getClassLoader().getResource("pki/").getPath();
		String filePathPrefix = PkiUtils.class.getResource("").getPath() + File.separator + "pki" + File.separator;
		keyStoreName = filePathPrefix + keyStoreName;
		String type = getTypefromPFX(keyStoreName, keyStorePaw, signAlg, keyStoreType);
		//知道类型，进行签名
		if (type.equals("RSA")) {
		    return bulidPrivateKeyFromPFXRSA(plaintext,keyStoreName, keyStorePaw, signAlg, keyStoreType);
		}else if (type.equals("SM2")) {
			return bulidPrivateKeyFromPFXSM2(plaintext,keyStoreName, keyStorePaw, signAlg, keyStoreType);
		}
		
		return clientId;
	}
	private static String getTypefromPFX(String keyStoreName,String keyStorePaw,String signAlg,String keyStoreType) throws KeyStoreException, NoSuchProviderException, FileNotFoundException {
		
	
	    	FileInputStream fis = new FileInputStream(keyStoreName);
		    KeyStore ks2 = null;
			String []a =keyStoreType.split(",");
			if (a.length>1) {
				ks2 = KeyStore.getInstance(a[0],a[1]);
			}else {
				ks2 = KeyStore.getInstance(keyStoreType);
			}
		
			try {
				ks2.load(fis, keyStorePaw.toCharArray());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String keyAlias = null;
			Enumeration enum1 = ks2.aliases();
		    if (enum1.hasMoreElements()) // we are readin just one certificate.
		    {
		        keyAlias = (String)enum1.nextElement();
		    }
			X509Certificate convertCert  = (X509Certificate) ks2.getCertificate(keyAlias);
			if (convertCert.getSigAlgName().toUpperCase().indexOf("RSA") < 0) {
				return "SM2";
			}else{
				return "RSA";
			}
		
	
	}
	
	//sm2
	public  static String bulidPrivateKeyFromPFXSM2(String plaintext,String keyStoreName,String keyStorePaw,String signAlg,String keyStoreType) throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, NoSuchProviderException, InvalidKeyException, SignatureException {
		

		FileInputStream fis = new FileInputStream(keyStoreName);
		String []a =keyStoreType.split(",");
	    KeyStore ks2 = KeyStore.getInstance(a[0],a[1]);
	    ks2.load(fis, keyStorePaw.toCharArray());
		Enumeration enum1 = ks2.aliases();
	    String keyAlias = null;
	    if (enum1.hasMoreElements()) // we are readin just one certificate.
	    {
	        keyAlias = (String)enum1.nextElement();
	    }
	    
	    String []b =signAlg.split(",");
	    Signature sig = Signature.getInstance(b[0],b[1]);
	    sig.initSign((PrivateKey) ks2.getKey(keyAlias, null), new SecureRandom());
		sig.update(plaintext.getBytes());
		byte[] rs = sig.sign();
		String signedtext = org.apache.commons.codec.binary.Base64.encodeBase64String(rs).replace("\r", "").replace("\n", "");
		return signedtext;
	}
	
	
	
	
	
	public  static String bulidPrivateKeyFromPFXRSA(String plaintext,String keyStoreName,String keyStorePaw,String signAlg,String keyStoreType) throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, NoSuchProviderException, InvalidKeyException, SignatureException {
	
		String keystoreAlias = "testpay";
		
		FileInputStream fis = new FileInputStream(keyStoreName);
	    KeyStore ks2 = KeyStore.getInstance(keyStoreType);
	    ks2.load(fis, keyStorePaw.toCharArray());
	    
	    fis.close();
		PrivateKey priv = (PrivateKey) ks2.getKey(keystoreAlias, keyStorePaw.toCharArray());

		Signature rsa = Signature.getInstance(signAlg);
		rsa.initSign(priv);
		rsa.update(plaintext.getBytes("UTF-8"));
		byte[] sig = rsa.sign();

		String signedtext = org.apache.commons.codec.binary.Base64.encodeBase64String(sig).replace("\r", "").replace("\n", "");
		return signedtext;
	}
	
	
	

}
