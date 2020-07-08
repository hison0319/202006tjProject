package word.dto;

import java.sql.Timestamp;

public class WordbookDto {
	private int id;
	private int ownerId;
	private int favorite;
	private int guestId;
	private String title;
	private Timestamp regDate;
	private Timestamp uDate;
	private String wordbookAddress;
	private String sharingKey;
	private String regDateStr;
	private String uDateStr;
	private String memberId;
	public WordbookDto() {}
	public WordbookDto(int ownerId, int favorite, String title, String wordbookAddress) {
		this.ownerId = ownerId;
		this.favorite = favorite;
		this.title = title;
		this.wordbookAddress = wordbookAddress;
	}
	public WordbookDto(int id, int ownerId, int favorite, int guestId, String title, String wordbookAddress) {
		super();
		this.id = id;
		this.ownerId = ownerId;
		this.favorite = favorite;
		this.guestId = guestId;
		this.title = title;
		this.wordbookAddress = wordbookAddress;
	}
	public WordbookDto(int id, int ownerId, int favorite, int guestId, String title, Timestamp regDate, Timestamp uDate,
			String wordbookAddress, String sharingKey) {
		this.id = id;
		this.ownerId = ownerId;
		this.favorite = favorite;
		this.guestId = guestId;
		this.title = title;
		this.regDate = regDate;
		this.uDate = uDate;
		this.wordbookAddress = wordbookAddress;
		this.sharingKey = sharingKey;
	}
	
	public WordbookDto(int id, int ownerId, int favorite, int guestId, String title, Timestamp regDate, Timestamp uDate,
			String wordbookAddress, String regDateStr, String uDateStr, String memberId) {
		this.id = id;
		this.ownerId = ownerId;
		this.favorite = favorite;
		this.guestId = guestId;
		this.title = title;
		this.regDate = regDate;
		this.uDate = uDate;
		this.wordbookAddress = wordbookAddress;
		this.regDateStr = regDateStr;
		this.uDateStr = uDateStr;
		this.memberId = memberId;
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
	public int getGuestId() {
		return guestId;
	}
	public void setGuestId(int guestId) {
		this.guestId = guestId;
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
	public String getRegDateStr() {
		return regDateStr;
	}
	public void setRegDateStr(String regDateStr) {
		this.regDateStr = regDateStr;
	}
	public String getuDateStr() {
		return uDateStr;
	}
	public void setuDateStr(String uDateStr) {
		this.uDateStr = uDateStr;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public String getSharingKey() {
		return sharingKey;
	}
	public void setSharingKey(String sharingKey) {
		this.sharingKey = sharingKey;
	}
	@Override
	public String toString() {
		return "WordbookDto [id=" + id + ", ownerId=" + ownerId + ", favorite=" + favorite + ", guestId=" + guestId
				+ ", title=" + title + ", regDate=" + regDate + ", uDate=" + uDate + ", wordbookAddress="
				+ wordbookAddress + ", sharingKey=" + sharingKey + ", regDateStr=" + regDateStr + ", uDateStr="
				+ uDateStr + ", memberId=" + memberId + "]\n";
	}
	
}
