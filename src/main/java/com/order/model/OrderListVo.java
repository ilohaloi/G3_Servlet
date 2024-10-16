package com.order.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.BatchSize;
import com.google.gson.annotations.Expose;
import com.prod.model.ProdVo;

@Entity
@Table(name = "order_list")
@Access(AccessType.FIELD)
public class OrderListVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8492234429618426293L;

	@Id
	@Column(name = "order_id")
	@Expose
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orid;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@BatchSize(size = 10)
	private List<OrderDetailVo> orderDetails;

	@Expose
	@Column(name = "memb_id")
	private Integer membId;

	@Column(name = "order_time", insertable = false)
	private Date time;

	@Expose
	@Column(name = "order_status")
	private String status;

	@Expose
	@Column(name = "order_payment")
	private String payment;

	@Expose
	@Column(name = "cust_name")
	private String name;

	@Expose
	@Column(name = "cust_email")
	private String email;

	@Expose
	@Column(name = "cust_tell")
	private String tell;

	@Expose
	@Column(name = "del_addr")

	private String addr;
	@Expose
	@Column(name = "order_amount")
	private Integer amount;


	@Transient
	private	List<ProdVo> prods;



	public OrderListVo() {
		super();
	}

	public OrderListVo(Integer id, Integer membId, Date time, String status, String payment, String name, String email,
			String tell, String addr, Integer amount, List<OrderDetailVo> orderDetails) {
		super();
		this.orid = id;
		this.membId = membId;
		this.time = time;
		this.status = status;
		this.payment = payment;
		this.name = name;
		this.email = email;
		this.tell = tell;
		this.addr = addr;
		this.amount = amount;
		this.orderDetails = orderDetails;
	}

	public Integer getOrid() {
		return orid;
	}

	public Integer getMembId() {
		return membId;
	}

	public Date getTime() {
		return time;
	}

	public String getStatus() {
		return status;
	}

	public String getPayment() {
		return payment;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getTell() {
		return tell;
	}

	public String getAddr() {
		return addr;
	}

	public Integer getAmount() {
		return amount;
	}

	public List<OrderDetailVo> getOrderDetails() {
		return orderDetails;
	}

	public List<ProdVo> getProds() {
		return prods;
	}

	public void setOrid(Integer id) {
		this.orid = id;
	}

	public void setMembId(Integer membId) {
		this.membId = membId;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public void setOrderDetails(List<OrderDetailVo> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public void setProds(List<ProdVo> prods) {
		this.prods = prods;
	}

	@Override
	public String toString() {
		return "OrderListVo [id=" + orid + ", membId=" + membId + ", time=" + time + ", status=" + status + ", payment="
				+ payment + ", name=" + name + ", email=" + email + ", tell=" + tell + ", addr=" + addr + ", amount="
				+ amount + "]";
	}

}
