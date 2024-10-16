package com.laiutil.cloudinary;

import com.cloudinary.Cloudinary;
public class CloudinaryUtil {
	static Cloudinary cloudinary = init();
	public static Cloudinary init() {
		Cloudinary cloudinary = new Cloudinary(System.getenv("CLOUDINARY_URL"));
		return cloudinary;
	}
	public static Cloudinary getCloudinary() {
		return cloudinary;
	}
}
