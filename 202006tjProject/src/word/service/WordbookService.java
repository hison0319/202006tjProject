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

	// 단어장 삭제
	public void deleteWordbook(int id) {
		wordbookMapper.deleteWordbook(id);
	}
}
