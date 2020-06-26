package notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import notice.dto.NoticeDto;

public interface NoticeMapper {
	//공지 조회
	public NoticeDto selectNoticeById(int id);
	
	//공지 추가
	public void insertNotice(NoticeDto noticeDto);
	
	//공지 전체 조회
	public List<NoticeDto> selectNoticeList(@Param("first")int first, @Param("ea")int ea);
	
	//공지 수정
	public void updateNotice(NoticeDto noticeDto);
	
	//공지 삭제
	public void deleteNotice(int id);
	
	//공지 총 갯수 조회
	public int selectNoticeCount();
}
