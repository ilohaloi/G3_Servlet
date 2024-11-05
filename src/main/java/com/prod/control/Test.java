package com.prod.control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.outherutil.cloudinary.CloudinaryFunction;
import com.outherutil.cloudinary.CloudinaryUtil;
@WebServlet("/ser")
@MultipartConfig
public class Test extends HttpServlet implements CloudinaryFunction {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var part = req.getParts();

		List<String> fileUrl = new LinkedList<String>();
		for (Part part2 : part) {
			if (part2.getName().startsWith("image")) {
				byte[] file = part2.getInputStream().readAllBytes();
				fileUrl.add(uploadImg(CloudinaryUtil.getCloudinary(), file));
			}
		}

		//fileUrl.forEach(e->{
		//	System.out.println(e);
		//});
	}
}
