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
import com.outherutil.redis.RedisInterface;
import com.outherutil.redis.RedisUtil;
import com.prod.model.ProdDaoImpl;
import com.prod.model.ProdVo;

import kotlin.Pair;
import redis.clients.jedis.JedisPool;

public class ProdService implements CloudinaryFunction, ProdElementStringDefine{
	private ProdDaoImpl pDaoImpl;

	public ProdService() {
		pDaoImpl = new ProdDaoImpl();
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
			element.put(prodName, p.getName());
			element.put(prodCategory,p.getCategory());
			element.put(prodPrice, String.valueOf(p.getPrice()));
			element.put(prodStock, String.valueOf(p.getStock()));
			//三個if不需要
			if(p.getImg1()!=null)
				element.put(prodImg1, p.getImg1());
			if(p.getImg2()!=null)
				element.put(prodImg2, p.getImg2());
			if(p.getImg3()!=null)
				element.put(prodImg3, p.getImg3());
			//三個if不需要										//改這段(String.valueOf(p.getId())
			data.add(new Pair<String,Map<String,String>>(String.valueOf(p.getId()),element));
		});
		pDaoImpl.redisInsert(pool,"prod:", data,2 * 60);//"prod:"冒號前的字要改資料夾名
	}

	public List<ProdVo> getProds(JedisPool pool){
		var prods = (List<ProdVo>)pDaoImpl.redisGetAllByKey(pool, "prod:*");
		if(prods==null || prods.isEmpty()) {
			prods =  pDaoImpl.getAll("products", ProdVo.class);
			insert(pool, prods);
			//TODO 添加生命週期
			System.out.println("from sql");
		}
		System.out.println("from redis");
		prods.sort((p1,p2)->Integer.compare(p1.getId(), p2.getId()));
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
	public List<ProdVo> getMultipleQuery(List<String>query, List<String> value) {
		Map<String,String> quertMap = new HashMap<String, String>();

		for (int i = 0; i < query.size(); i++) {
			quertMap.put(query.get(i),value.get(i));
		}
		return pDaoImpl.getMultipleQuery(quertMap);
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
