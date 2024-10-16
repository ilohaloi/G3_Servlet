package com.laiutil.cloudinary;

import java.io.IOException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


public interface CloudinaryFunction {

	public default String uploadImg(Cloudinary cloudinary,byte[] file ) throws IOException {



		var params1 = ObjectUtils.asMap("use_filename", true, "unique_filename", true, "overwrite", false);
		var uploadResult = cloudinary.uploader().upload(file,params1);
		return (String) uploadResult.get("url");
	}
}
