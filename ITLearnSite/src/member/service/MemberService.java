package member.service;

import java.util.List;

import member.db.MemberBean;

public interface MemberService {
	//회원 가입
	public int InsertMember(MemberBean bean);
	
	//이메일 중복 검사
	public int emailDupChk(MemberBean bean);

	//멤버리스트 가져오기
	public List<MemberBean> getMemberlist();
	
	//회원탈퇴
	public int deleteMember(String email);
	
	//이메일 인증 
	public void emailAuth(String email);
	
	//회원 수정 페이지 정보 얻기
	public MemberBean callMember(String email);
	
	//회원 정보 수정
	public int updateMember(MemberBean mBean);
	
	//관리자권한 회원삭제
	public void  AdmindeleteMember(String email);
}
