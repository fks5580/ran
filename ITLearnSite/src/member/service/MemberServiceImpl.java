package member.service;

import java.util.List;

import member.db.MemberBean;
import member.db.MemberDAOImpl;
import member.email.db.MailDAOImpl;

public class MemberServiceImpl implements MemberService{
	
	MemberDAOImpl dao;
	MailDAOImpl mailDAO;
	
	public MemberServiceImpl() {
		dao = new MemberDAOImpl();
		mailDAO = new MailDAOImpl();
	}	
	//회원가입
	@Override
	public int InsertMember(MemberBean bean) {
		int result = dao.insertMember(bean);
		return result;
	}
	//이메일 중복체크
	@Override
	public int emailDupChk(MemberBean bean) {
		int result = mailDAO.mailDupChk(bean);
		return result;
	}
	//멤버 목록 
	@Override
	public List<MemberBean> getMemberlist() {
		List<MemberBean> listMember = dao.getMemberlist();
		return listMember;
	}
	//회원탈퇴
	@Override
	public int deleteMember(String email) {
		int result = dao.deleteMember(email);
		return result;
	}
	//이메일 인증
	public void emailAuth(String email) {
		dao.emailAuth(email);
	}
	//본인 회원정보 얻기
	@Override
	public MemberBean callMember(String email) {
		MemberBean mBean = dao.callMember(email);
		return mBean;
	}
	//회원정보 수정
	@Override
	public int updateMember(MemberBean mBean) {
		int check = dao.updateMember(mBean);
		return check;
	}
	
	//관리자권한 회원삭제
	@Override
	public void  AdmindeleteMember(String email){
		dao.AdmindeleteMember(email);
	}
	
}
