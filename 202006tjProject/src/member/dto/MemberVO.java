package member.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MemberVO {
	private int id;
	@NotNull(message="m")
	@Pattern(regexp = "/^[A-Za-z]{2,15}[0-9]{1,15}$/",message="m")
	private String memberId;
	@Pattern(regexp="/(?=.*\\d{1,15})(?=.*[~`!@#$%\\^&*()-+=]{1,15})(?=.*[a-zA-Z]{1,50}).{8,15}$/",message="m")
	private String password;
	@Pattern(regexp="/^[0-9a-zA-Z]+@[0-9a-zA-Z]+\\.[a-zA-z]{2,3}$/",message="m")
	private String email;
	@Pattern(regexp ="/^\\d{3}\\d{4}\\d{4}$/", message="m")
	private String phone;
	private String address;
	private Timestamp regDate;
	private int certified;
	public MemberVO() {}
	
//	public MemberVO(
//			@Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "m") String email,
//			@Pattern(regexp = "^^\\d{3}\\d{3,4}\\d{4}$", message = "m") String phone,
//			String address) {
//		this.email = email;
//		this.phone = phone;
//		this.address = address;
//	}
	
	public MemberVO(
			@NotNull(message = "공백입니다.") @Pattern(regexp = "/^[A-Za-z]{2,15}[0-9]{1,15}$/", message = "m") String memberId,
			@Pattern(regexp = "/(?=.*\\d{1,15})(?=.*[~`!@#$%\\^&*()-+=]{1,15})(?=.*[a-zA-Z]{1,50}).{8,15}$/", message = "m") String password,
			@Pattern(regexp = "/^[0-9a-zA-Z]+@[0-9a-zA-Z]+\\.[a-zA-z]{2,3}$/", message = "m") String email,
			@Pattern(regexp = "/^\\d{3}\\d{4}\\d{4}$/", message = "m") String phone,
			String address) {
		this.memberId = memberId;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}
	
	public MemberVO(int id,
			@NotNull(message = "공백입니다.") @Pattern(regexp = "/^[A-Za-z]{2,15}[0-9]{1,15}$/", message = "m") String memberId,
			@Pattern(regexp = "/(?=.*\\d{1,15})(?=.*[~`!@#$%\\^&*()-+=]{1,15})(?=.*[a-zA-Z]{1,50}).{8,15}$/", message = "m") String password,
			@Pattern(regexp = "/^[0-9a-zA-Z]+@[0-9a-zA-Z]+\\.[a-zA-z]{2,3}$/", message = "m") String email,
			@Pattern(regexp = "/^\\d{3}\\d{4}\\d{4}$/", message = "m") String phone,
			String address, Timestamp regDate, int certified) {
		super();
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
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	public int getCertified() {
		return certified;
	}
	public void setCertified(int certified) {
		this.certified = certified;
	}
	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", memberId=" + memberId + ", password=" + password + ", email=" + email
				+ ", phone=" + phone + ", address=" + address + ", regDate=" + regDate + ", certified=" + certified
				+ "]";
	}
}
