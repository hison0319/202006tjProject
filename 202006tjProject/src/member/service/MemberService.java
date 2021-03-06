package member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import member.dto.MemberDto;
import member.mapper.MemberMapper;

@Service
public class MemberService {
	@Autowired
	MemberMapper memberMapper;

	// 회원 조회
	public MemberDto selectMemberById(int id) {
		return memberMapper.selectMemberById(id);
	}

	// 회원 추가
	public void insertMember(MemberDto memberDto) {
		memberMapper.insertMember(memberDto);
	}

	// 회원 전체 조회
	public List<MemberDto> selectMemberAll() {
		return memberMapper.selectMemberAll();
	}

	// 회원 수정
	public void updateMember(MemberDto memberDto) {
		memberMapper.updateMember(memberDto);
	}

	// 회원 인증 수정
	public void updateMemberCertify(MemberDto memberDto) {
		memberMapper.updateMemberCertify(memberDto);
	}

	// 회원 삭제
	public void deleteMember(int id) {
		memberMapper.deleteMember(id);
	}

	// 로그인용 회원 조회
	public MemberDto selectMemberByMemberId(String memberId) {
		try {
			return memberMapper.selectMemberByMemberId(memberId);
		} catch (NullPointerException e) {
			return null;
		}
	}

	public MemberDto selectMemberByMemberIdPw(String memberId, String password) {
		try {
			return memberMapper.selectMemberByMemberIdPw(memberId, password);
		} catch (NullPointerException e) {
			return null;
		}
	}

	// 회원가입 용 이메일, 전화번호 조회
	public String selectMemberByEmail(String email) {
		try {
			return memberMapper.selectMemberByEmail(email);
		} catch (NullPointerException e) {
			return null;
		}
	}

	public String selectMemberByPhone(String phone) {
		try {
			return memberMapper.selectMemberByPhone(phone);
		} catch (NullPointerException e) {
			return null;
		}
	}

	// 이메일로 아이디 찾기
	public String selectMemberIdByEmail(String email) {
		return memberMapper.selectMemberIdByEmail(email);
	}
	
	// 아이디, 이메일로 비밀번호 찾기
	public MemberDto selectMemberInfoByIDEmail(String memberId, String email) {
		return memberMapper.selectMemberInfoByIDEmail(memberId, email);
	}

	// API 로그인용 아이디찾기
	public MemberDto selectMemberByMemberIdforApi(String memberId) throws IndexOutOfBoundsException {
		List<MemberDto> tempList = memberMapper.selectMemberByMemberIdforApi(memberId);
		MemberDto member = tempList.remove(0);
		return member;
	}
}
