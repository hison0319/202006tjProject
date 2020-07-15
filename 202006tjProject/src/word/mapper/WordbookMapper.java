package word.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import word.dto.WordbookDto;

public interface WordbookMapper {
	// 단어장 조회
	public WordbookDto selectWordbookById(int id);

	// 사용자 단어장 조회(소유)
	public List<WordbookDto> selectWordbookByOwnerId(@Param("id") int id, @Param("first") int first,
			@Param("ea") int ea);

	// 사용자 단어장 조회(공유)
	public List<WordbookDto> selectWordbookByGuestId(@Param("id") int id, @Param("first") int first,
			@Param("ea") int ea);

	// 사용자 단어장 조회(소유, 공유)
	public List<WordbookDto> selectWordbookByOwnerIdOrGuestId(@Param("id") int id, @Param("first") int first,
			@Param("ea") int ea);

	// 공유 단어장 조회(전체 체크)
	public List<WordbookDto> selectWordbookByGuestIdCheck(@Param("id") int id);
	
	// 소유 단어장 조회(전체 체크)
	public List<WordbookDto> selectWordbookByOwnerIdCheck(@Param("id") int id);
	
	// 소유, 공유 단어장 조회(전체 체크)
	public List<WordbookDto> selectWordbookByOwnerIdOrGuestIdCheck(@Param("id") int id);
	
	// 소유자 단어장 제목 별 공유 인원 명수 체크
	public List<Integer> selectSharingCheckGroupByTitle(@Param("id") int id, @Param("first") int first, @Param("ea") int ea);
	
	// 소유자 단어장 제목 별 공유 멤버 체크
	public List<WordbookDto> selectSharingMemberCheckByTitle(@Param("id") int id, @Param("title") String title);
	
	// 단어장 리스트 갯수 조회(for ownerId)
	public int selectWordbookCountByOwnerId(@Param("id") int id);

	// 단어장 리스트 갯수 조회(for guestId)
	public int selectWordbookCountByGuestId(@Param("id") int id);

	// 단어장 리스트 갯수 조회(for ownerId, guestId)
	public int selectWordbookCountByOwnerIdOrGuestId(@Param("id") int id);

	// 단어장 리스트 갯수 조회(for ownerId, guestId, favorite)
	public int selectWordbookCountByOwnerIdOrGuestIdFavorite(@Param("id") int id);

	// 공유해준 단어장 리스트 갯수 조회
	public int selectWordbookCountSharing(@Param("id") int id);

	// 단어장 추가
	public void insertWordbook(WordbookDto wordbookDto);

	// 단어장 전체 조회
	public List<WordbookDto> selectWordbookAll();

	// 단어장 수정
	public void updateWordbook(WordbookDto wordbookDto);

	// 단어장 선호도만 수정(favorite, uDtae)
	public void updateWordbookFavorite(WordbookDto wordbookDto);

	// 단어장 공유 키 수정(sharingKey, uDtae)
	public void updateWordbookSharingKey(WordbookDto wordbookDto);

	// 단어장 삭제
	public void deleteWordbook(int id);

	// 사용자 단어장 조인 조회(소유)
	public List<WordbookDto> selectWordbookByOwnerIdJoin(@Param("id") int id, @Param("first") int first,
			@Param("ea") int ea);

	// 사용자 단어장 조인 조회(공유)
	public List<WordbookDto> selectWordbookByGuestIdJoin(@Param("id") int id, @Param("first") int first,
			@Param("ea") int ea);

	// 사용자 단어장 조인 조회(소유, 공유)
	public List<WordbookDto> selectWordbookByOwnerIdOrGuestIdJoin(@Param("id") int id, @Param("first") int first,
			@Param("ea") int ea);

	// 공유해준 단어장 조인 조회
	public List<WordbookDto> selectWordbookSharingJoin(@Param("id") int id, @Param("first") int first,
			@Param("ea") int ea);

	// 사용자 단어장 조인 조회(소유, 공유, 중요)
	public List<WordbookDto> selectWordbookByOwnerIdOrGuestIdFavoriteJoin(@Param("id") int id,
			@Param("first") int first, @Param("ea") int ea);

	// 단어장 검색(조인, 소유, 공유, 최신순)
	public List<WordbookDto> selectWordbookBySearchJoin(@Param("id") int id, @Param("keyword") String keyword,
			@Param("first") int first, @Param("ea") int ea);

	// 단어장 리스트 갯수 조회(for search)
	public int selectWordbookCountBySearchJoin(@Param("id") int id, @Param("keyword") String keyword);

	// 회원 탈퇴 시 단어장 주소 전체 조회 (주소 그룸)
	public List<String> selectAddressGroupByAddressByOwnerId(@Param("id") int id);
	
	// 회원 탈퇴 시 회원이 보유한 단어장 data 모두 삭제
	public void deleteWordbookByOwnerIdOrGuestId(@Param("id") int id);
}
