package com.utstar.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

public class UploadImage {
	public static final String JPGFORAMT = "jpg";
	public static final String PNGFORAMT = "png";
	public static final String JPEGFORAMT = "jpeg";
	public static final String GITFORAMT = "git";
	public static String uploadImage(MultipartFile file,String path,String newImageName,String suffix){
		String output = null;
		//1.get upload image name and rebulid the new image name
		if(JPGFORAMT.equals(suffix)||PNGFORAMT.equals(suffix
				)||JPEGFORAMT.equals(suffix)||GITFORAMT.equals(suffix)){
			output = path + "/" +newImageName;
			File dest = new File(output);
			try {
				//2.get file byte and write to output image 
				file.transferTo(dest);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("image upload faild");
				output = "faild";
			} 
		}
		//3.return new image name
		return output;
	}
	public static String getNewImageName(String suffix){
		String timePostfix = TimeConvert.timeConvert(new Date(), TimeConvert.FORMAT);
		Random random = new Random();
		int randomNum = random.nextInt(100);
		String newImageName = timePostfix + randomNum + "."+suffix;
		return newImageName;
	}
	public static String getSuffix(MultipartFile file){
		String originalFilename = file.getOriginalFilename();
		int indexOf = originalFilename.indexOf(".");
		String suffix = originalFilename.substring(indexOf+1, originalFilename.length());
		return suffix;
		
	}
}
