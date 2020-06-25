package member.mapper;

import java.util.List;

import member.dto.MemberDto;

public interface MemberMapper {
	//회원 조회
	public MemberDto selectMemberById(int id);
	
	//회원 추가
	public void insertMember(MemberDto memberDto);
	
	//회원 전체 조회
	public List<MemberDto> selectMemberAll();
	
	//회원 수정
	public void updateMember(MemberDto memberDto);
	
	//회원 삭제
	public void deleteMember(int id);
}
