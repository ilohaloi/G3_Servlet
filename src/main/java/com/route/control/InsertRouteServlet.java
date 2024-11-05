package com.route.control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.outherutil.cloudinary.CloudinaryFunction;
import com.outherutil.cloudinary.CloudinaryUtil;
import com.route.model.RouteDAO;
import com.route.model.RouteVO;
@WebServlet("/repairRoute")
@MultipartConfig
public class InsertRouteServlet extends HttpServlet implements CloudinaryFunction {
	/**
	 *
	 */
	private static final long serialVersionUID = 7787606713687733600L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
		RouteVO r = null;
		var part = req.getParts();

		List<String> fileUrl = new LinkedList<String>();
		for (Part part2 : part) {
			if (part2.getName().startsWith("image")) {
				byte[] file = part2.getInputStream().readAllBytes();

				fileUrl.add(uploadImg(CloudinaryUtil.getCloudinary(), file));
			}
			else if(part2.getName().startsWith("route")){
				@SuppressWarnings("resource")
				String jsonString = new Scanner(part2.getInputStream()).useDelimiter("\\A").next();
				r = gson.fromJson(jsonString,RouteVO.class);
			}
		}

		String html = r.getDepiction();
		Document doc =  Jsoup.parse(html);
		Elements elementsWithSrc = doc.select("[src]");
		if(elementsWithSrc!=null) {

			for (int i = 0; i < fileUrl.size(); i++) {
				elementsWithSrc.get(i).attr("src",fileUrl.get(i));
			}

			r.setDepiction(doc.body().html());
			r.setImage(fileUrl.get(0));
		}

		RouteDAO routeDAO = new RouteDAO();
		routeDAO.insert(r);

		System.out.println(r.getDepiction());
	}
}
