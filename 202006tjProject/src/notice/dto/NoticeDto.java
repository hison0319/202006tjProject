package notice.dto;

import java.sql.Timestamp;

public class NoticeDto {
	private int id;
	private String title;
	private int writerId;
	private String contents;
	private Timestamp regDate;
	private String regDateStr;
	private String memberId;
	public NoticeDto() {}
	public NoticeDto(String title, int writerId, String contents) {
		this.title = title;
		this.writerId = writerId;
		this.contents = contents;
	}
	public NoticeDto(int id, String title, int writerId, String contents, Timestamp regDate) {
		this.id = id;
		this.title = title;
		this.writerId = writerId;
		this.contents = contents;
		this.regDate = regDate;
	}
	public NoticeDto(int id, String title, int writerId, String contents, Timestamp regDate, String regDateStr,
			String memberId) {
		this.id = id;
		this.title = title;
		this.writerId = writerId;
		this.contents = contents;
		this.regDate = regDate;
		this.regDateStr = regDateStr;
		this.memberId = memberId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getWriterId() {
		return writerId;
	}
	public void setWriterId(int writerId) {
		this.writerId = writerId;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	public String getRegDateStr() {
		return regDateStr;
	}
	public void setRegDateStr(String regDateStr) {
		this.regDateStr = regDateStr;
	}
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String joinMemberId) {
		this.memberId = joinMemberId;
	}
	@Override
	public String toString() {
		return "noticeDto [id=" + id + ", title=" + title + ", writerId=" + writerId + ", contents=" + contents
				+ ", regDate=" + regDate + "]"+"\n";
	}
}
