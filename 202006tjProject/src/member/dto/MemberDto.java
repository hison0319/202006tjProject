package member.dto;

import java.sql.Timestamp;

public class MemberDto {
	private int id;
	private String memberId;
	private String password;
	private String email;
	private String phone;
	private String address;
	private Timestamp regDate;
	private int certified;
	public MemberDto() {}
	public MemberDto(String memberId, String password, String email, String phone, String address) {
		this.memberId = memberId;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}
	public MemberDto(int id, String memberId, String password, String email, String phone, String address,
			Timestamp regDate, int certified) {
		this.id = id;
		this.memberId = memberId;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.regDate = regDate;
		this.certified = certified;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getCertified() {
		return certified;
	}
	public void setCertified(int certified) {
		this.certified = certified;
	}
	
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", memberId=" + memberId + ", password=" + password + ", email=" + email
				+ ", phone=" + phone + ", address=" + address + ", regDate=" + regDate + ", certified=" + certified
				+ "]"+"\n";
	}
	
}
