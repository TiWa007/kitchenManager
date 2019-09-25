package model;

public class Cashier {
	private Integer id;
	private KitchenMember member;
	private String email;
	private String password;
	private String emailSMTPHost;
	private String mobilePay;
	private String bankReg;
	private String bankAccount;
	private Double monthFee;
	
	public Cashier() {
	}
	
	public Cashier(KitchenMember member, String email, String password, String emailSMTPHost, String mobilePay,
			String bankAccount, Double monthFee) {
		super();
		this.member = member;
		this.email = email;
		this.password = password;
		this.emailSMTPHost = emailSMTPHost;
		this.mobilePay = mobilePay;
		this.bankAccount = bankAccount;
		this.monthFee = monthFee;
	}
	
	@Override
	public String toString() {
		return "Cashier [id=" + id + ", member=" + member.toString() + ", email=" + email + ", password=" + password
				+ ", emailSMTPHost=" + emailSMTPHost + ", mobilePay=" + mobilePay + ", bankReg=" + bankReg
				+ ", bankAccount=" + bankAccount + ", monthFee=" + monthFee + "]";
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailSMTPHost() {
		return emailSMTPHost;
	}
	public void setEmailSMTPHost(String emailSMTPHost) {
		this.emailSMTPHost = emailSMTPHost;
	}
	public String getMobilePay() {
		return mobilePay;
	}
	public void setMobilePay(String mobilePay) {
		this.mobilePay = mobilePay;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public KitchenMember getMember() {
		return member;
	}

	public void setMember(KitchenMember member) {
		this.member = member;
	}

	public String getBankReg() {
		return bankReg;
	}

	public void setBankReg(String bankReg) {
		this.bankReg = bankReg;
	}

	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Double getMonthFee() {
		return monthFee;
	}

	public void setMonthFee(Double monthFee) {
		this.monthFee = monthFee;
	}
}
