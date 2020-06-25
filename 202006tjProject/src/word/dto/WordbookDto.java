package word.dto;

import java.sql.Timestamp;

public class WordbookDto {
	private int id;
	private int ownerId;
	private int favorite;
	private int shared;
	private String title;
	private Timestamp regDate;
	private Timestamp uDate;
	private String wordbookAddress;
	public WordbookDto() {}
	public WordbookDto(int ownerId, int favorite, String title, String wordbookAddress) {
		this.ownerId = ownerId;
		this.favorite = favorite;
		this.title = title;
		this.wordbookAddress = wordbookAddress;
	}
	public WordbookDto(int id, int ownerId, int favorite, int shared, String title, Timestamp regDate, Timestamp uDate,
			String wordbookAddress) {
		this.id = id;
		this.ownerId = ownerId;
		this.favorite = favorite;
		this.shared = shared;
		this.title = title;
		this.regDate = regDate;
		this.uDate = uDate;
		this.wordbookAddress = wordbookAddress;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public int getFavorite() {
		return favorite;
	}
	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}
	public int getShared() {
		return shared;
	}
	public void setShared(int shared) {
		this.shared = shared;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	public Timestamp getuDate() {
		return uDate;
	}
	public void setuDate(Timestamp uDate) {
		this.uDate = uDate;
	}
	public String getWordbookAddress() {
		return wordbookAddress;
	}
	public void setWordbookAddress(String wordbookAddress) {
		this.wordbookAddress = wordbookAddress;
	}
	@Override
	public String toString() {
		return "wordbookDto [id=" + id + ", ownerId=" + ownerId + ", favorite=" + favorite + ", shared=" + shared
				+ ", title=" + title + ", regDate=" + regDate + ", uDate=" + uDate + ", wordbookAddress="
				+ wordbookAddress + "]"+"\n";
	}
}
