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

	// 회원 삭제
	public void deleteMember(int id) {
		memberMapper.deleteMember(id);
	}
}
