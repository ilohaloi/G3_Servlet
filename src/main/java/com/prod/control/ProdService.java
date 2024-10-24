package com.prod.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import com.outherutil.cloudinary.CloudinaryFunction;
import com.outherutil.cloudinary.CloudinaryUtil;
import com.prod.model.ProdDaoImpl;
import com.prod.model.ProdVo;

import kotlin.Pair;
import redis.clients.jedis.JedisPool;

public class ProdService implements CloudinaryFunction {
	private ProdDaoImpl pDaoImpl;

	public ProdService() {
		pDaoImpl = new ProdDaoImpl();
	}

	public List<ProdVo> getProds(JedisPool pool){
		@SuppressWarnings("unchecked")
		var prods = (List<ProdVo>)pDaoImpl.redisGetAllByKey(pool, "prod:*");
		if(prods == null) {
			prods =  pDaoImpl.getAll("products", ProdVo.class);
			System.out.println("from sql");
		}
		return prods;
	}
	public ProdVo getProd(JedisPool pool,String pk) {
		var prod = (ProdVo)pDaoImpl.redisGetByKey(pool, "prod:"+pk);
		if(prod==null) {
			prod = pDaoImpl.getByPk(Integer.valueOf(pk), ProdVo.class);
			System.out.println("from sql");
		}
		return prod;
	}

	public void insert(ProdVo prod) {
		pDaoImpl.insert(prod);
	}

	public void insert(JedisPool pool, List<ProdVo> prod) {
		//第一個string是裝PK用的,第二個是KEY,第三個是VALUE
		List<Pair<String,Map<String,String>>> data = new ArrayList<Pair<String,Map<String,String>>>();
		prod.forEach(p->{
			
			Map<String, String> element = new HashMap<String, String>();
			//要輸入鍵對值
			element.put("name", p.getName());
			element.put("categroy",p.getCategory());
			element.put("price", String.valueOf(p.getPrice()));
			element.put("stock", String.valueOf(p.getStock()));
			//三個if不需要
			if(p.getImg1()!=null)
				element.put("img1", p.getImg1());
			if(p.getImg2()!=null)
				element.put("img2", p.getImg2());
			if(p.getImg3()!=null)
				element.put("img3", p.getImg3());
			//三個if不需要										//改這段(String.valueOf(p.getId())
			data.add(new Pair<String,Map<String,String>>(String.valueOf(p.getId()),element));
		});
		pDaoImpl.redisInsert(pool,"prod:", data);//"prod:"冒號前的字要改資料夾名
	}

	public void update(ProdVo prod) {
		pDaoImpl.update(prod.getId(), prod);
	}

	public void uploadImgs(Collection<Part> part, ProdVo prod) throws IOException {
		List<String> fileUrl = new LinkedList<String>();
		for (Part part2 : part) {
			if (part2.getName().startsWith("image")) {
				byte[] file = part2.getInputStream().readAllBytes();
				fileUrl.add(uploadImg(CloudinaryUtil.getCloudinary(), file));
			}
		}
		switch (fileUrl.size()) {
		case 1:
			prod.setImg1(fileUrl.get(0));
			break;
		case 2:
			prod.setImg1(fileUrl.get(0));
			prod.setImg2(fileUrl.get(1));
			break;
		case 3:
			prod.setImg1(fileUrl.get(0));
			prod.setImg2(fileUrl.get(1));
			prod.setImg3(fileUrl.get(2));
			break;
		default:
			break;
		}
	}
}
