package com.togocms.pns.api;

import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

public class KeyStoreStringer {

	public static void main(String[] args) throws Exception {
		byte[] bytes = FileUtils.readFileToByteArray(new File("etc/__.p12"));
		System.out.println(new String(Base64.encodeBase64(bytes)));
	}

}
