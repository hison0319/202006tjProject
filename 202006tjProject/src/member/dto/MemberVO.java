package member.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MemberVO {
	private int id;
	@NotNull(message="공백입니다.")
	@Pattern(regexp = "^[a-z | A-Z]{3,6}[0-9]{3,6}$",message="형식에 맞지 않습니다.")
	private String memberId;
	@Pattern(regexp="^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$",message="형식에 맞지 않습니다.")
	private String password;
	@Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",message="메일 형식에 맞지 않습니다.")
	private String email;
	@Pattern(regexp ="^(01[1|6|7|8|9|0])-(\\d{3,4})-(\\d{4})$", message="전화번호 형식에 맞지 않습니다.")
	private String phone;
	private String address;
	private Timestamp regDate;
	private int certified;
	public MemberVO() {}
	
	public MemberVO(
			@NotNull(message = "공백입니다.") @Pattern(regexp = "^[a-z | A-Z]{3,6}[0-9]{3,6}$", message = "형식에 맞지 않습니다.") String memberId,
			@Pattern(regexp = "^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$", message = "형식에 맞지 않습니다.") String password,
			@Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "메일 형식에 맞지 않습니다.") String email,
			@Pattern(regexp = "^(01[1|6|7|8|9|0])-(\\d{3,4})-(\\d{4})$", message = "전화번호 형식에 맞지 않습니다.") String phone,
			String address) {
		this.memberId = memberId;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}
	
	public MemberVO(int id,
			@NotNull(message = "공백입니다.") @Pattern(regexp = "^[a-z | A-Z]{3,6}[0-9]{3,6}$", message = "형식에 맞지 않습니다.") String memberId,
			@Pattern(regexp = "^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$", message = "형식에 맞지 않습니다.") String password,
			@Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "메일 형식에 맞지 않습니다.") String email,
			@Pattern(regexp = "^(01[1|6|7|8|9|0])-(\\d{3,4})-(\\d{4})$", message = "전화번호 형식에 맞지 않습니다.") String phone,
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
