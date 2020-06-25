package word.dto;

public class SharingDto {
	private int id;
	private int wordbookId;
	private int ownerId;
	private int guestId;
	public SharingDto() {}
	public SharingDto(int wordbookId, int ownerId, int guestId) {
		this.wordbookId = wordbookId;
		this.ownerId = ownerId;
		this.guestId = guestId;
	}
	public SharingDto(int id, int wordbookId, int ownerId, int guestId) {
		this.id = id;
		this.wordbookId = wordbookId;
		this.ownerId = ownerId;
		this.guestId = guestId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWordbookId() {
		return wordbookId;
	}
	public void setWordbookId(int wordbookId) {
		this.wordbookId = wordbookId;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public int getGuestId() {
		return guestId;
	}
	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}
	@Override
	public String toString() {
		return "WordbookSharing [id=" + id + ", wordbookId=" + wordbookId + ", ownerId=" + ownerId + ", guestId="
				+ guestId + "]"+"\n";
	}
}
