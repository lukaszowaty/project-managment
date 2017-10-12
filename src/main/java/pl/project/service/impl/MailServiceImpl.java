package pl.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import pl.project.model.Mail;
import pl.project.service.MailService;

@Service
public class MailServiceImpl implements MailService{

	@Autowired
	JavaMailSender javaMailSender;
	
	@Override
	public boolean sendMail(Mail mail) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setSubject(mail.getSubject());
		mailMessage.setText(mail.getEmail() +"\n "+ mail.getMessage());
		mailMessage.setTo("lzwolan92@gmail.com"); // change and put your personal address email
		
		try {
			javaMailSender.send(mailMessage);
			return true;
		} catch (MailException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
}
