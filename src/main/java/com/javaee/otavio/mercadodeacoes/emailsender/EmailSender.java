package com.javaee.otavio.mercadodeacoes.emailsender;

public class EmailSender {
	public void SendEmail(String toEmail, String subject, String body) {
		final String fromEmail = "otavioareis@gmail.com";
		final String password = "Hjkhjk12";

		System.out.println("Initializing email send");

		EmailConfig config = new EmailConfig();
		config.sendEmail(fromEmail, password, toEmail, subject, body);
	}
}
