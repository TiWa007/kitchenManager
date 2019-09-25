package model;

import java.util.Date;

public class KitchenFeeRecipt {

	private Integer fee_id;
	private KitchenMember member;
	private KitchenFeeMonth month;
	private Date date;
	
	public KitchenFeeRecipt() {
		
	}
	
	public KitchenFeeRecipt(KitchenMember member, KitchenFeeMonth month, Date date) {
		super();
		this.member = member;
		this.month = month;
		this.date = date;
	}



	@Override
	public String toString() {
		return "KitchenFeeRecipt [fee_id=" + fee_id + ", member=" + member.toString() + ", month=" + month.toString() + ","
				+ " date=" + date.toString() + "]";
	}



	public Integer getFee_id() {
		return fee_id;
	}



	public void setFee_id(Integer fee_id) {
		this.fee_id = fee_id;
	}



	public KitchenMember getMember() {
		return member;
	}



	public void setMember(KitchenMember member) {
		this.member = member;
	}



	public KitchenFeeMonth getMonth() {
		return month;
	}



	public void setMonth(KitchenFeeMonth month) {
		this.month = month;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
