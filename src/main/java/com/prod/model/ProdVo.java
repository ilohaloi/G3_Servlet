package com.prod.model;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

import com.prod.control.ProdElementStringDefine;

@Entity
@Table(name = "products")
@Access(AccessType.FIELD)
public class ProdVo  implements Serializable,ProdElementStringDefine{

	/**
	 *
	 */
	private static final long serialVersionUID = -7684382807818397436L;

	@Id
	@Column(name = "prod_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "prod_name")
	private String name;

	@Column(name = "prod_category")
	private String category;

<<<<<<< Updated upstream
	@Column(name ="pord_describe")
=======
	@Column(name ="prod_describe")
>>>>>>> Stashed changes
	private String describe;

	@Column(name = "prod_stock")
	private Integer stock;

	@Column(name = "prod_price")
	private Integer price;

	@Column(name = "prod_img_1")
	private String img1;

	@Column(name = "prod_img_2")
	private String img2;

	@Column(name = "prod_img_3")
	private String img3;

	// 訂單用
	@Transient
	private Integer qty;

	public ProdVo() {}
	public ProdVo(Integer id, String name, String category, int stock, int price, String img1, String img2,
			String img3) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.stock = stock;
		this.price = price;
		this.img1 = img1;
		this.img2 = img2;
		this.img3 = img3;
	}
	public ProdVo(HttpServletRequest req) {
		name = req.getParameter(prodName);
		category = req.getParameter(prodCategory);
		stock = Integer.valueOf(req.getParameter("stock"));
		price = Integer.valueOf(req.getParameter("price"));
	}

	public ProdVo(Map<String , String> data) {
		data.forEach((k,v)->{
			switch (k) {
            case prodName:
                name = v;
                break;
            case prodCategory:
                category = v;
                break;
            case prodStock:
                stock = Integer.valueOf(v);
                break;
            case prodPrice:
                price = Integer.valueOf(v);
                break;
            case prodImg1:
                img1 = v;
                break;
            case prodImg2:
                img2 = v;
                break;
            case prodImg3:
                img3 = v;
                break;
			}
		});
	}

	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getCategory() {
		return category;
	}
	public Integer getStock() {
		return stock;
	}
	public Integer getPrice() {
		return price;
	}
	public String getImg1() {
		return img1;
	}
	public String getImg2() {
		return img2;
	}
	public String getImg3() {
		return img3;
	}
	public Integer getQty() {
		return qty;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}
	@Override
	public String toString() {
		return "ProdVo [id=" + id + ", name=" + name + ", category=" + category + ", stock=" + stock + ", price="
				+ price + ", img1=" + img1 + ", img2=" + img2 + ", img3=" + img3 + ", qty=" + qty + "]";
	}


}