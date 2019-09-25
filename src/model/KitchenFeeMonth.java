package model;

import java.util.Date;

public class KitchenFeeMonth {

	private Integer monthId;
	private Date month;
	
	public KitchenFeeMonth() {
		
	}
	
	public KitchenFeeMonth(Date month) {
		super();
		this.month = month;
	}



	@Override
	public String toString() {
		return "KitchenFeeMonth [monthId=" + monthId + ", month=" + month + "]";
	}



	public Integer getMonthId() {
		return monthId;
	}



	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}



	public Date getMonth() {
		return month;
	}



	public void setMonth(Date month) {
		this.month = month;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
