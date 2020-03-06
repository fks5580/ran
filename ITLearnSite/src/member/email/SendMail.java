package member.email;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

	private String FROM;
	private String FROMNAME;
	private String SMTP_USERNAME;
	private String SMTP_PASSWORD;
	private String HOST;
	private String PORT;
	private String AUTH;
	
	private String SUBJECT;
	private String MESSAGE;
	private String TARGET;
	
	public SendMail()
	{
		
	}

	public String getFROM() {
		return FROM;
	}



	public String getFROMNAME() {
		return FROMNAME;
	}



	public String getSMTP_USERNAME() {
		return SMTP_USERNAME;
	}



	public String getSMTP_PASSWORD() {
		return SMTP_PASSWORD;
	}



	public String getHOST() {
		return HOST;
	}



	public String getPORT() {
		return PORT;
	}



	public String getAUTH() {
		return AUTH;
	}



	public String getSUBJECT() {
		return SUBJECT;
	}



	public String getMESSAGE() {
		return MESSAGE;
	}



	public String getTargetMail() {
		return TARGET;
	}

	public void setServer(String FROM, String FROMNAME, String SMTP_USERNAME, String SMTP_PASSWORD, String HOST, String PORT, String AUTH)
	{
		this.FROM = FROM;
		this.FROMNAME = FROMNAME;
		this.SMTP_USERNAME = SMTP_USERNAME;
		this.SMTP_PASSWORD = SMTP_PASSWORD;
		this.HOST = HOST;
		this.PORT = PORT;
		this.AUTH = AUTH;
	}
	
	public void setMessage(String SUBJECT, String MESSAGE, String TARGET)
	{
		this.SUBJECT = SUBJECT;
		this.MESSAGE = MESSAGE;
		this.TARGET = TARGET;
	}


	protected void set_SSL() throws MessagingException, UnsupportedEncodingException 
	{
	
		String BODY = String.join(System.getProperty("line.separator"), getMESSAGE());
		String TO = getTargetMail();
		
		Properties props = System.getProperties();
		props.put("mail.smtp.host", getHOST());
		props.put("mail.smtp.socketFactory.port", getPORT());
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", getAUTH());
		props.put("mail.smtp.port", getPORT());
		
		Session session = Session.getDefaultInstance(props);
		
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(getFROM(), getFROMNAME()));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
		msg.setSubject(getSUBJECT());
		msg.setContent(BODY, "text/html;charset=utf-8");

		Transport transport = session.getTransport();
		
		try {
			System.out.println("Sending...");
			
			System.out.println("getFROM = " + getFROM());
			System.out.println("getFROMNAME = " + getFROMNAME());
			System.out.println("get_SMTP_USERNAME = " + getSMTP_USERNAME());
			System.out.println("get_SMTP_USERPASSWORD = " + getSMTP_PASSWORD());
			System.out.println("getHOST = " + getHOST());
			System.out.println("getPORT = " + getPORT());
			System.out.println("getAUTH = " + getAUTH());
			
			transport.connect(getHOST(), getSMTP_USERNAME(), getSMTP_PASSWORD());
			transport.sendMessage(msg, msg.getAllRecipients());

			System.out.println("Email sent!");
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			transport.close();
		}
	}

	protected void set_TSL() throws MessagingException, UnsupportedEncodingException 
	{
		String BODY = String.join(System.getProperty("line.separator"), getMESSAGE());
		String TO = getTargetMail();
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", getAUTH());
		props.put("mail.smtp.starttls.enable", getAUTH());
		props.put("mail.smtp.host", getHOST());
		props.put("mail.smtp.port", getPORT());
		
		Session session = Session.getDefaultInstance(props);
		
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(getFROM(), getFROMNAME()));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
		msg.setSubject(getSUBJECT());
		msg.setContent(BODY, "text/html;charset=utf-8");

		Transport transport = session.getTransport();
		
		try {
			System.out.println("Sending...");

			transport.connect(getHOST(), getSMTP_USERNAME(), getSMTP_PASSWORD());
			transport.sendMessage(msg, msg.getAllRecipients());

			System.out.println("Email sent!");
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			transport.close();
		}
	}
}
