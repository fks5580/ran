package member.email.db;

import member.db.MemberBean;

public interface MailDAO {
	//이메일 중복 체크 
	public int mailDupChk(MemberBean bean);
}
