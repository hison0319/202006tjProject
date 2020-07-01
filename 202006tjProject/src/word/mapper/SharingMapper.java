package word.mapper;

import java.util.List;

import word.dto.SharingDto;

public interface SharingMapper {
	//공유 조회
	public SharingDto selectSharingById(int id);
	
	//공유 추가
	public void insertSharing(SharingDto sharingDto);
	
	//공유 전체 조회
	public List<SharingDto> selectSharingAll();
	
	//단어장 사용자 조회
	public List<SharingDto> selectSharingByWordbookId(int wordbookId);
	
	//공유 수정
	public void updateSharing(SharingDto sharingDto);
	
	//공유 삭제
	public void deleteSharing(int id);
}
