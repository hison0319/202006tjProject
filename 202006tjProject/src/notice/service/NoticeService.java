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
	public NoticeDto selectNoticeById(int id) throws Exception{
		return noticeMapper.selectNoticeById(id);
	}

	// 공지 추가
	public void insertNotice(NoticeDto noticeDto) throws Exception{
		noticeMapper.insertNotice(noticeDto);
	}

	// 공지 전체 조회
	public List<NoticeDto> selectNoticeList(int first, int ea) throws Exception{
		return noticeMapper.selectNoticeList(first, ea);
	}

	// 공지 수정
	public void updateNotice(NoticeDto noticeDto) throws Exception{
		noticeMapper.updateNotice(noticeDto);
	}

	// 공지 삭제
	public void deleteNotice(int id) throws Exception{
		noticeMapper.deleteNotice(id);
	}
	
	//공지 총 갯수 조회
	public int selectNoticeCount() throws Exception{
		return noticeMapper.selectNoticeCount();
	}
	
	//공지 조회 시 join 활용 멤버아이디 가져옴
	public List<NoticeDto> selectNoticeListJoin(int first, int ea) throws Exception{
		return noticeMapper.selectNoticeListJoin(first, ea);
	}
}
