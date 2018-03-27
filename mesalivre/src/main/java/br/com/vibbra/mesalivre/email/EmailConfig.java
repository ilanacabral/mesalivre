package br.com.vibbra.mesalivre.email;

import javax.mail.Session;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource({ "classpath:application.properties" })
@ComponentScan(basePackages = { "br.com.vibbra.mesalivre.email" })
public class EmailConfig {

	private static final String PROPERTY_JNDI_NAME = "mail.jndiName";

	@Autowired
	private Environment env;

	@Bean(name = "smtpSession")
	public JavaMailSenderImpl javaMailSenderImpl() {
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		Session mailSession = mailSession();

		if (mailSession != null) {
			mailSenderImpl.setSession(mailSession);
		}

		return mailSenderImpl;
	}

	public Session mailSession() {
		String mailJndiName = env.getProperty(PROPERTY_JNDI_NAME);

		if (mailJndiName == null || mailJndiName.isEmpty()) {
			return null;
		}

		JndiTemplate template = new JndiTemplate();
		Session session = null;
		try {
			session = (Session) template.lookup(mailJndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return session;
	}

}