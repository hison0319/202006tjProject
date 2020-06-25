package word.mapper;

import java.util.List;

import word.dto.WordbookDto;

public interface WordbookMapper {
	//단어장 조회
	public WordbookDto selectWordbookById(int id);
	
	//단어장 추가
	public void insertWordbook(WordbookDto wordbookDto);
	
	//단어장 전체 조회
	public List<WordbookDto> selectWordbookAll();
	
	//단어장 수정
	public void updateWordbook(WordbookDto wordbookDto);
	
	//단어장 삭제
	public void deleteWordbook(int id);
}
