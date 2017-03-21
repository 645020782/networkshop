package com.utstar.networkshop.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class Jtest {

	public static void main(String[] args) throws Exception {
		Client c = new Client();
		String url = "http://192.168.1.168:8080/imgweb/image/12.jpg";
		String path = "C:/Users/utsc0471/Desktop/5.jpg";
		byte[] readFileToByteArray = FileUtils.readFileToByteArray(new File(path));
		WebResource resource = c.resource(url);
		resource.put(String.class,readFileToByteArray);
		System.out.println("fa song wan bi!");
	}

}
