package model;

import java.util.Date;

public class Receipt {
	private Integer id;
	private Date date;
	private Double amount;
	private KitchenMember member;
	private String comment;
	
	public Receipt() {
		
	}
	
	public Receipt(Integer id, Date date, Double amount, KitchenMember member, String comment) {
		super();
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.member = member;
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Recipt [id=" + id + ", date=" + date + ", amount=" + amount + ", member=" + member.toString() + ", comment="
				+ comment + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public KitchenMember getMember() {
		return member;
	}

	public void setMember(KitchenMember member) {
		this.member = member;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
