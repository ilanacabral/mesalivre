package br.com.vibbra.mesalivre.email;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class EnviarEmail {

	@Autowired
	private JavaMailSender mailSender;

	private MimeMessageHelper mimeMessageHelper;

	/**
	 * Envia e-mail
	 * 
	 * @throws MessagingException
	 */
	public void enviar(String emailText, String para, String assunto, String de) throws MessagingException {
		this.mimeMessageHelper = new MimeMessageHelper(this.mailSender.createMimeMessage(), "UTF-8");
		this.mimeMessageHelper.setFrom(de);
		this.mimeMessageHelper.setTo(para);
		this.mimeMessageHelper.setSubject(assunto);
		this.mimeMessageHelper.setText(emailText, true);
		this.mailSender.send(this.mimeMessageHelper.getMimeMessage());
	}

}
