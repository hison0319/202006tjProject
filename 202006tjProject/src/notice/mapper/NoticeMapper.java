package notice.mapper;

import java.util.List;

import notice.dto.NoticeDto;

public interface NoticeMapper {
	//공지 조회
	public NoticeDto selectNoticeById(int id);
	
	//공지 추가
	public void insertNotice(NoticeDto noticeDto);
	
	//공지 전체 조회
	public List<NoticeDto> selectNoticeAll();
	
	//공지 수정
	public void updateNotice(NoticeDto noticeDto);
	
	//공지 삭제
	public void deleteNotice(int id);
}
