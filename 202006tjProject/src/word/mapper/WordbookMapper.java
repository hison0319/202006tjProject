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

	// 단어장 리스트 갯수 조회
	public int selectWordbookCountByOwnerId(@Param("id")int id);

	// 단어장 리스트 갯수 조회
	public int selectWordbookCountByGuestId(@Param("id")int id);

	// 단어장 리스트 갯수 조회
	public int selectWordbookCountByOwnerIdOrGuestId(@Param("id")int id);

	// 단어장 추가
	public void insertWordbook(WordbookDto wordbookDto);

	// 단어장 전체 조회
	public List<WordbookDto> selectWordbookAll();

	// 단어장 수정
	public void updateWordbook(WordbookDto wordbookDto);

	// 단어장 삭제
	public void deleteWordbook(int id);
}
