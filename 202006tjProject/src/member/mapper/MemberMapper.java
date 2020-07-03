package member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	
	//회원 인증 수정
	public void updateMemberCertify(MemberDto memberDto);
	
	//회원 삭제
	public void deleteMember(int id);
	
	//로그인용 회원 조회
	public MemberDto selectMemberByMemberId(@Param("memberId")String memberId);
	public MemberDto selectMemberByMemberIdPw(@Param("memberId")String memberId, @Param("password")String password);
	
	//회원가입용 아이디, 이메일, 전화번호 조회
	public String selectMemberByEmail(@Param("email")String email);
	public String selectMemberByPhone(@Param("phone")String phone);
	
	//아이디 찾기용 이메일 조회
	public String selectMemberIdByEmail(@Param("email")String email);
	
	//비밀번호 찾기용 아이디 이메일 조회
	public String selectMemberPwByIDEmail(@Param("memberId")String memberId, @Param("email")String email);
	
	//API 로그인용 아이디찾기
	public List<MemberDto> selectMemberByMemberIdforApi(@Param("memberId")String memberId);
}
