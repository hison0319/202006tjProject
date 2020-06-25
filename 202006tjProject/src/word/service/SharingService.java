package word.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import word.dto.SharingDto;
import word.mapper.SharingMapper;

@Service
public class SharingService {
	@Autowired
	SharingMapper sharingMapper;
	// 공유 조회
	public SharingDto selectSharingById(int id) {
		return sharingMapper.selectSharingById(id);
	}

	// 공유 추가
	public void insertSharing(SharingDto sharingDto) {
		sharingMapper.insertSharing(sharingDto);
	}

	// 공유 전체 조회
	public List<SharingDto> selectSharingAll() {
		return sharingMapper.selectSharingAll();
	}

	// 공유 수정
	public void updateSharing(SharingDto sharingDto) {
		sharingMapper.updateSharing(sharingDto);
	}

	// 공유 삭제
	public void deleteSharing(int id) {
		sharingMapper.deleteSharing(id);
	}
}
