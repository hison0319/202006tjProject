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

	// 단어장 리스트 갯수 조회
	public int selectWordbookCountByOwnerId(int id) {
		return wordbookMapper.selectWordbookCountByOwnerId(id);
	}

	// 단어장 리스트 갯수 조회
	public int selectWordbookCountByGuestId(int id) {
		return wordbookMapper.selectWordbookCountByGuestId(id);
	}

	// 단어장 리스트 갯수 조회
	public int selectWordbookCountByOwnerIdOrGuestId(int id) throws NullPointerException {
		return wordbookMapper.selectWordbookCountByOwnerIdOrGuestId(id);
	}

	// 단어장 추가
	public void insertWordbook(WordbookDto wordbookDto) {
		wordbookMapper.insertWordbook(wordbookDto);
//		//단어장 생성 시 uDate에 regDate값을 초기화 함(For순서정렬)
//		WordbookDto wordbook =  wordbookMapper.selectWordbookById(wordbookDto.getId());
//		wordbook.setuDate(wordbook.getRegDate());
//		wordbookMapper.updateWordbook(wordbook);
	}

	// 단어장 전체 조회
	public List<WordbookDto> selectWordbookAll() {
		return wordbookMapper.selectWordbookAll();
	}

	// 단어장 수정
	public void updateWordbook(WordbookDto wordbookDto) {
		wordbookMapper.updateWordbook(wordbookDto);
	}

	// 단어장 삭제
	public void deleteWordbook(int id) {
		wordbookMapper.deleteWordbook(id);
	}
}
