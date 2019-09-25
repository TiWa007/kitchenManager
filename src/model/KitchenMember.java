package model;

public class KitchenMember {

	private Integer id;
	private Integer room;
	private String name;
	private String email;

	
	public KitchenMember(Integer id, String name, String email, Integer room) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.room = room;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getRoom() {
		return room;
	}

	public void setRoom(Integer room) {
		this.room = room;
	}

	public KitchenMember() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

	@Override
	public String toString() {
		return "KitchenMember [id=" + id + ", room=" + room + ", name=" + name + ", email=" + email + "]";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
