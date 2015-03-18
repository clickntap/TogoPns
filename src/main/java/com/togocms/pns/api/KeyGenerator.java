package com.togocms.pns.api;

import com.clickntap.utils.SecurityUtils;

public class KeyGenerator {

	public static void main(String[] args) throws Exception {
		System.out.println(SecurityUtils.sha1("__"));
	}

}
