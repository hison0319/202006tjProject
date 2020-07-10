package word.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import word.dto.WordbookDto;
import word.mapper.WordbookMapper;

@Service
public class WordbookService {
	@Autowired
	WordbookMapper wordbookMapper;

	// 단어장 조회
	public WordbookDto selectWordbookById(int id) {
		return wordbookMapper.selectWordbookById(id);
	}

	// 사용자 단어장 조회(소유)
	public List<WordbookDto> selectWordbookByOwnerId(int id, int first, int ea) {
		return wordbookMapper.selectWordbookByOwnerId(id, first, ea);
	}

	// 사용자 단어장 조회(공유)
	public List<WordbookDto> selectWordbookByGuestId(int id, int first, int ea) {
		return wordbookMapper.selectWordbookByGuestId(id, first, ea);
	}

	// 사용자 단어장 조회(소유, 공유)
	public List<WordbookDto> selectWordbookByOwnerIdOrGuestId(int id, int first, int ea) {
		return wordbookMapper.selectWordbookByOwnerIdOrGuestId(id, first, ea);
	}

	// 사용자 단어장 조인 조회(소유)
	public List<WordbookDto> selectWordbookByOwnerIdJoin(int id, int first, int ea) {
		return wordbookMapper.selectWordbookByOwnerIdJoin(id, first, ea);
	}

	// 사용자 단어장 조인 조회(공유)
	public List<WordbookDto> selectWordbookByGuestIdJoin(int id, int first, int ea) {
		return wordbookMapper.selectWordbookByGuestIdJoin(id, first, ea);
	}

	// 사용자 단어장 조인 조회(소유, 공유)
	public List<WordbookDto> selectWordbookByOwnerIdOrGuestIdJoin(int id, int first, int ea) {
		return wordbookMapper.selectWordbookByOwnerIdOrGuestIdJoin(id, first, ea);
	}

	// 사용자 단어장 조인 조회(소유, 공유, 중요)
	public List<WordbookDto> selectWordbookByOwnerIdOrGuestIdFavoriteJoin(int id, int first, int ea) {
		return wordbookMapper.selectWordbookByOwnerIdOrGuestIdFavoriteJoin(id, first, ea);
	}

	// 공유해준 단어장 조인 조회
	public List<WordbookDto> selectWordbookSharingJoin(int id, int first, int ea) {
		return wordbookMapper.selectWordbookSharingJoin(id, first, ea);
	}
	
	// 공유 단어장 조회(공유 체크)
	public List<WordbookDto> selectWordbookByGuestIdCheck(int id) {
		return wordbookMapper.selectWordbookByGuestIdCheck(id);
	}

	// 단어장 검색(조인, 소유, 공유, 최신순)
	public List<WordbookDto> selectWordbookBySearchJoin(int id, String keyword, int first, int ea) {
		return wordbookMapper.selectWordbookBySearchJoin(id, keyword, first, ea);
	}

	// 단어장 리스트 갯수 조회(for search)
	public int selectWordbookCountBySearchJoin(int id, String keyword) {
		return wordbookMapper.selectWordbookCountBySearchJoin(id, keyword);
	}

	// 단어장 리스트 갯수 조회(for ownerId)
	public int selectWordbookCountByOwnerId(int id) {
		return wordbookMapper.selectWordbookCountByOwnerId(id);
	}

	// 단어장 리스트 갯수 조회(for guestId)
	public int selectWordbookCountByGuestId(int id) {
		return wordbookMapper.selectWordbookCountByGuestId(id);
	}

	// 단어장 리스트 갯수 조회(for ownerId, guestId)
	public int selectWordbookCountByOwnerIdOrGuestId(int id) throws NullPointerException {
		return wordbookMapper.selectWordbookCountByOwnerIdOrGuestId(id);
	}

	// 단어장 리스트 갯수 조회(for ownerId, guestId, favorite)
	public int selectWordbookCountByOwnerIdOrGuestIdFavorite(int id) {
		return wordbookMapper.selectWordbookCountByOwnerIdOrGuestIdFavorite(id);
	};

	// 공유해준 단어장 리스트 갯수 조회
	public int selectWordbookCountSharing(int id) {
		return wordbookMapper.selectWordbookCountSharing(id);
	}
	
	// 소유자 단어장 제목 별 공유 인원 명수 체크
	public List<Integer> selectSharingCheckGroupByTitle(int id, int first, int ea) {
		return wordbookMapper.selectSharingCheckGroupByTitle(id, first, ea);
	}
		
	// 소유자 단어장 제목 별 공유 멤버 체크
	public List<WordbookDto> selectSharingMemberCheckByTitle(int id, String title) {
		return wordbookMapper.selectSharingMemberCheckByTitle(id, title);
	}

	// 단어장 추가
	public void insertWordbook(WordbookDto wordbookDto) {
		wordbookMapper.insertWordbook(wordbookDto);
	}

	// 단어장 전체 조회
	public List<WordbookDto> selectWordbookAll() {
		return wordbookMapper.selectWordbookAll();
	}

	// 단어장 수정
	public void updateWordbook(WordbookDto wordbookDto) {
		wordbookMapper.updateWordbook(wordbookDto);
	}

	// 단어장 공유 키 수정(sharingKey, uDtae)
	public void updateWordbookSharingKey(WordbookDto wordbookDto) {
		wordbookMapper.updateWordbookSharingKey(wordbookDto);
	}

	// 단어장 선호도만 수정(favorite, u_date)
	public void updateWordbookFavorite(WordbookDto wordbookDto) {
		wordbookMapper.updateWordbookFavorite(wordbookDto);
	}

	// 단어장 삭제
	public void deleteWordbook(int id) {
		wordbookMapper.deleteWordbook(id);
	}
}
