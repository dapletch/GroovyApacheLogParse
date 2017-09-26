package com.pletcherwebdesign.logparse.services.sendemail

import com.pletcherwebdesign.logparse.beans.configuration.email.MessageBody
import com.pletcherwebdesign.logparse.beans.configuration.email.SmtpProperties
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import sun.rmi.transport.Transport

import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

/**
 * Created by Seth on 9/24/2017.
 * The EmailConfig, and SmtpPropertiesConfig classes must be in the same package
 * in order to for SendEmail Service to work. There is no way to effectively scan other packages (at least SpringBoot is concerned)
 * otherwise the EmailConfig, and SmtpPropertiesConfig classes would be within the following package:
 * com.pletcherwebdesign.logparse.beans.configuration.email
 */
@Service
@ComponentScan(basePackages = "com.pletcherwebdesign.logparse.beans.configuration.email.sendemail.*")
class SendEmail {

    private def log = LoggerFactory.getLogger(SendEmail.class)
    private JavaMailSender javaMailSender
    private SmtpProperties smtpProperties

    @Autowired
    SendEmail(JavaMailSender javaMailSender, SmtpProperties smtpProperties) {
        this.javaMailSender = javaMailSender
        this.smtpProperties = smtpProperties
    }

    def props = [
            'mail.smtp.starttls.enable' : smtpProperties.startTlsEnable,
            'mail.smtp.host' : smtpProperties.host,
            'mail.smtp.user' : smtpProperties.username,
            'mail.smtp.password' : smtpProperties.password,
            'mail.smtp.port' : smtpProperties.port,
            'mail.smtp.auth' : smtpProperties.authentication,
            'mail.smtp.debug' : smtpProperties.mailDebug
    ] as Properties

    void sendEmailNoAttachment(MessageBody messageBody)  {
        log.info("Properties: " + props.toString())
        def session = Session.getInstance(props)
        try {
            // create a default MimeMessage
            def message = new MimeMessage(session)
            // set FROM header field
            message.from = new InternetAddress(smtpProperties.username)
            // set TO header field of the header
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(messageBody.recipient))
            // set SUBJECT header field
            message.subject = messageBody.subject
            // set MESSAGE
            message.text = messageBody.message
            def transport = session.getTransport("smtp")
            transport.connect(smtpProperties.host, smtpProperties.username, smtpProperties.password)
            transport.sendMessage(message, message.getAllRecipients())
            transport.close()
            log.info("Message has been sent successfully.")
        } catch (MessagingException e) {
            log.error("There was an error sending the email: \n" + e)
        }
    }

    void sendEmailWithAttachment(MessageBody messageBody) {
        log.info("Properties: " + props.toString())
        try {
            def mimeMessage = javaMailSender.createMimeMessage()
            def helper = new MimeMessageHelper(mimeMessage, true)
            helper.setFrom(smtpProperties.username)
            helper.setTo(messageBody.recipient)
            helper.setSubject(messageBody.subject)
            helper.setText(messageBody.message)
            // Adding the attachment
            FileSystemResource attachment = new FileSystemResource(messageBody.attachment)
            helper.addAttachment(attachment.getFilename(), attachment)
            javaMailSender.send(mimeMessage)
            log.info("Message with attachment has been sent successfully.")
        } catch (MessagingException e) {
            log.error("There was error sending the email w/ attachment: \n" + e)
        }
    }

    static def sendEmailNoAttachmentIncluded(MessageBody messageBody) {
        def context = new AnnotationConfigApplicationContext(EmailConfig.class)
        def sendEmail = context.getBean(SendEmail.class)
        sendEmail.sendEmailNoAttachment(messageBody)
    }

    static def sendEmailAttachmentIncluded(MessageBody messageBody) {
        def context = new AnnotationConfigApplicationContext(EmailConfig.class)
        def sendEmail = context.getBean(SendEmail.class)
        sendEmail.sendEmailWithAttachment(messageBody)
    }
}