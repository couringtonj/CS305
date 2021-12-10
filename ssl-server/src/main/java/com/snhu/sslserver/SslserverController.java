package com.snhu.sslserver;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.catalina.connector.Connector;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class SslserverController {
	private static final String data="Jonathan Courington";
	private String IV="AAAAAAAAA";
	private byte[] ary=data.getBytes();
	
	public byte[] encrypt(byte[] ary, String data) throws Exception{
		Cipher encrypt=Cipher.getInstance("AES","SunJCE");
		SecretKeySpec key=new SecretKeySpec(data.getBytes("UTF-8"),"AES");
		encrypt.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
		return encrypt.doFinal(ary);
		
		
	}
	@GetMapping("/checkSum")
	public byte[] getCheckSum() throws Exception {
		return encrypt(ary,data);
	}
	
	
	private Connector redirectConnector() {
		Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("https");
		connector.setPort(8080);
		connector.setSecure(true);
		connector.setRedirectPort(8443);
		return connector;
	}
}
