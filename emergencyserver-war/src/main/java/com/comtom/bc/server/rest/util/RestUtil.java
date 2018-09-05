package com.comtom.bc.server.rest.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class RestUtil {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(RestUtil.class);
	
	public static void main(String[] args) {
		String urlString="http://192.168.107.30:880/media/0046903e-ce5d-419f-bc18-21b0a37aa925.mp3";
		try {
			URI uri=new URI(urlString);
			String fileName=new File(uri.getPath()).getName();
			String dirPath="D:/media/";
			String savePath = dirPath+fileName;
			download(uri,savePath);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public static void download(URI uri,String saveFile) {
		//据，包括数据格式，类型，编码方式，所以，要使用http 进行文件传输，头是必要的。restTemplate下载文件：
		RestTemplate restTemplate = new RestTemplate();

//		final String APPLICATION_PDF = "application/pdf";
		HttpHeaders headers = new HttpHeaders();
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
		    List<MediaType> list = new ArrayList<>();
//		    list.add(MediaType.valueOf(APPLICATION_PDF));
		    list.add(MediaType.APPLICATION_OCTET_STREAM);
		    headers.setAccept(list);

		    ResponseEntity<byte[]> response = restTemplate.exchange(
		        uri,
		        HttpMethod.GET,
		        new HttpEntity<byte[]>(headers),
		        byte[].class);

		    byte[] result = response.getBody();

		    inputStream = new ByteArrayInputStream(result);

		    File file = new File(saveFile);
		    if (!file.exists())
		    {
		        file.createNewFile();
		    }

		    outputStream = new FileOutputStream(file);
		    int len = 0;
		    byte[] buf = new byte[1024];
		    while ((len = inputStream.read(buf, 0, 1024)) != -1) {
		        outputStream.write(buf, 0, len);
		    }
		    outputStream.flush();
		}catch (ResourceAccessException e) {
			LOGGER.error("连接异常", e);
		}catch (HttpClientErrorException e) {
			LOGGER.error("资源不存在", e);
		}catch (IOException e) {
			LOGGER.error("IO异常", e);
		}finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
		}
	}
}
