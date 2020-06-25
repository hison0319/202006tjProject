package notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import notice.dto.NoticeDto;
import notice.mapper.NoticeMapper;

@Service
public class NoticeService {
	@Autowired
	NoticeMapper noticeMapper;
	// 공지 조회
	public NoticeDto selectNoticeById(int id) {
		return noticeMapper.selectNoticeById(id);
	}

	// 공지 추가
	public void insertNotice(NoticeDto noticeDto) {
		noticeMapper.insertNotice(noticeDto);
	}

	// 공지 전체 조회
	public List<NoticeDto> selectNoticeAll() {
		return noticeMapper.selectNoticeAll();
	}

	// 공지 수정
	public void updateNotice(NoticeDto noticeDto) {
		noticeMapper.updateNotice(noticeDto);
	}

	// 공지 삭제
	public void deleteNotice(int id) {
		noticeMapper.deleteNotice(id);
	}
}
