package com.befasoft.common.tools;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.FileDataSource;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;
import java.io.*;

/**
 * Clase base para el enviar email
 */
public class Mail {

    private static Log log = LogFactory.getLog(Mail.class);

    public static final String HEADER_MESSAGE_ID    = "Message-ID" ;
    public static final String HEADER_IN_REPLY_TO   = "In-Reply-To" ;
    public static final String HEADER_RETURN_PATH   = "Return-Path" ;

    protected String host_smtp;
    protected String from;
    protected String username;
    protected String password;
    protected int smtp_port;
    protected boolean ssl;

    /**
     * Crea una instancia de la clase
     *
     * @param host_smtp Servidor SMTP
     * @param from Usuario que envia el correo
     * @param username Usuario de correo
     * @param password Password del usuario
     */
    public Mail(String host_smtp, String from, String username, String password) {
        log.debug("Mail("+host_smtp+", "+from+", "+username+", "+password+")");
        setParams(host_smtp, 25, from, username, password,false);
    }

    /**
     * Crea una instancia de la clase
     *
     * @param host_smtp Servidor SMTP
     * @param smtp_port Puerto del Servidor SMTP
     * @param from Usuario que envia el correo
     * @param username Usuario de correo
     * @param password Password del usuario
     */
    public Mail(String host_smtp, int smtp_port, String from, String username, String password) {
        setParams(host_smtp, smtp_port, from, username, password, false);
    }

    public Mail(String host_smtp, int smtp_port, String from, String username, String password, boolean ssl) {
        setParams(host_smtp, smtp_port, from, username, password, ssl);
    }

    private void setParams(String host_smtp, int smtp_port, String from, String username, String password, boolean ssl) {
        log.debug("Mail("+host_smtp+", "+smtp_port+", "+from+", "+username+", "+password+")");
        this.host_smtp = host_smtp;
        this.smtp_port = smtp_port;
        this.from = from;
        this.username = username;
        this.password = password;
        this.ssl = ssl;
    }

    /**
     * Obtiene la seccion para enviar y recibir los mails
     *
     * @return Retorna la sesion de correo
     */
    protected Session getSession() {
        Authenticator authenticator = new Authenticator();

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
        properties.setProperty("mail.smtp.auth", "true");

        properties.setProperty("mail.smtp.host", host_smtp);
        properties.setProperty("mail.smtp.port",  Integer.toString(smtp_port));

        return Session.getInstance(properties, authenticator);
    }

    /**
     * Clase privada para autentificar con el servidor de correo
     */
    private class Authenticator extends javax.mail.Authenticator {
        private PasswordAuthentication authentication;

        public Authenticator() {
            authentication = new PasswordAuthentication(username, password);
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return authentication;
        }
    }

    /**
     * Crea una lista de direcciones a partir de una cadena
     * @param address Lista de email separada por , o ;
     * @return Lista de direcciones
     * @throws AddressException Error en la direccion
     */
    private InternetAddress[] getAddressList(String address) throws AddressException {
        address = address.replaceAll(",", ";");
        StringTokenizer st = new StringTokenizer(address, ";");
        InternetAddress[] addrList = new InternetAddress[st.countTokens()];
        int i = 0;
        while (st.hasMoreElements()) {
            addrList[i++] = new InternetAddress(st.nextToken());
        }
        return addrList;
    }

    /**
     * Inicializa el mensaje
     * @param session Sesion de correo
     * @param msg Mensaje a enviar
     * @return Mensaje
     * @throws Exception Error
     */
    private PrivMessage initMessage(Session session, MailMessage msg) throws Exception {
        log.debug("Enviando mensaje");
        log.debug("Para: "+msg.to);
        if (msg.replayTo != null)
            log.debug("Resp.: "+msg.replayTo);
        log.debug("Asunto: "+msg.subject);
        if (Converter.isEmpty(from)){
            from = username;
        }
        if (Converter.isEmpty(msg.replayTo)){
            msg.replayTo = username;
        }

        PrivMessage message = new PrivMessage(session);
        message.setSentDate(new Date());
        message.setMessageID(generateMessageID());
        message.setFrom(new InternetAddress(from));
        message.addRecipients(Message.RecipientType.TO, getAddressList(msg.to));
        if (msg.cc != null)
            message.addRecipients(Message.RecipientType.CC, getAddressList(msg.cc));
        if (msg.bcc != null)
            message.addRecipients(Message.RecipientType.BCC, getAddressList(msg.bcc));
        if (null != msg.replayTo) {
            InternetAddress[] addrReplyTo = new InternetAddress[1];
            addrReplyTo[0] = new InternetAddress(msg.replayTo) ;
            message.setReplyTo(addrReplyTo);
        }
        message.setSubject(msg.subject);
        if (null != msg.replayToID) {
            message.setHeader(HEADER_IN_REPLY_TO, msg.replayToID);
        }
        return message;
    }

    /**
     * Envia un mensaje por e-mail
     *
     * @param msg Mensaje a enviar
     * @return true si se envio el mensaje
     */
    public boolean sendMsg(MailMessage msg) {
        try {
            Session session = getSession();
            // Define el mensaje
            Message message = initMessage(session, msg);
            message.setText(msg.text);
            message.saveChanges();
            // Envia el mensaje
            Transport transport = session.getTransport("smtp");
            transport.connect(host_smtp, username, password);
            Transport.send(message, message.getAllRecipients());
            transport.close();

            return true;
        } catch (Exception e) {
            log.error("Exception " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Envia un mensaje por e-mail (en formato HTML)
     *
     * @param msg Mensaje a enviar
     * @return Id. del mensaje enviado, null sino se envia
     */
    public String sendHTMLMsg(MailMessage msg) {
        try {
            Session session = null;
            BodyPart msgBodyPart = null;
            Multipart multipart = null;
            PrivMessage message = null;
            if (!ssl) {
                session = getSession();
            } else {
                Authenticator authenticator = new Authenticator();
                Properties props = new Properties();
                props.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
                props.setProperty("mail.smtp.auth", "true");
                props.setProperty("mail.smtp.host", host_smtp);
                props.setProperty("mail.smtp.port",  Integer.toString(smtp_port));
                props.setProperty("mail.smtp.user",username);
                props.setProperty("mail.debug", "true");
                props.setProperty("mail.smtp.starttls.enable","true");
                props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.setProperty("mail.smtp.socketFactory.fallback", "false");
                props.setProperty("mail.smtp.socketFactory.port", Integer.toString(smtp_port));

                session = Session.getInstance(props, authenticator);
                session.setDebug(true);
            }
            // Define el mensaje
            message = initMessage(session, msg);
            // Adjunta el HTML
            msgBodyPart = new MimeBodyPart();
            msgBodyPart.setContent(msg.text, "text/html; charset=utf-8");
            multipart = new MimeMultipart();
            multipart.addBodyPart(msgBodyPart);
            if (msg.fileAttachments != null) {
                // Adjunta los ficheros
                for (int i = 0; i < msg.fileAttachments.length; i++) {
                    String fn = msg.fileAttachments[i];
                    log.debug("Adjunto: "+fn);
                    msgBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(fn);
                    msgBodyPart.setDataHandler(new DataHandler(source));
                    msgBodyPart.setDisposition(BodyPart.ATTACHMENT);
                    File f = new File(fn);
                    msgBodyPart.setFileName(f.getName());
                    multipart.addBodyPart(msgBodyPart);
                }
            }

            if (msg.imageAttachments != null) {
                // Adjunta las imagenes
                for (int i = 0; i < msg.imageAttachments.length; i++) {
                    MailMessageImages img = msg.imageAttachments[i];
                    log.debug("Id.: "+img.getImageId()+", Imagen: "+img.getImageFile());
                    msgBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(img.getImageFile());
                    msgBodyPart.setDataHandler(new DataHandler(source));
                    msgBodyPart.setHeader("Content-ID", "<"+img.getImageId()+">");
                    File f = new File(img.getImageFile());
                    msgBodyPart.setFileName(f.getName());
                    multipart.addBodyPart(msgBodyPart);
                }
            }
            message.setContent(multipart);
            message.saveChanges();

            // Envia el mensaje
            Transport.send(message, message.getAllRecipients());

            return message.getMessageID();

        } catch (Exception e) {
            log.error("Exception: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Genera el un ID de mensaje unico
     * @return Id. del mensaje
     * @throws AddressException Error en la direccion
     */
    private String generateMessageID() throws AddressException {
        String result = null ;
        if (null != from) {
            Date date = new Date() ;
            result = ("<" + String.valueOf(date.getTime()) + "." + (new InternetAddress(from)).getAddress() + ">") ;
        }
        return result ;
    }

    /**
     * Retorna el valor de un parametro del header
     * @param msg Mensaje de correo
     * @param headerName Nombre del parametro
     * @return Valor del parametro
     * @throws MessagingException Error
     * @throws UnsupportedEncodingException Error
     */
    public String getHeader(Message msg, String headerName) throws MessagingException, UnsupportedEncodingException {
        String result = null ;
        String[] headerValues = msg.getHeader(headerName) ;
        if ((null != headerValues) && (0 < headerValues.length)) {
            result = headerValues[0] ;
        }
        return result ;
    }
    /**
     * Clase que permite asignar el message-id
     */
    private class PrivMessage extends MimeMessage {

        private String messageID = null ;

        public PrivMessage(Session session) {
            super(session);
        }

        protected void updateMessageID() throws MessagingException {
            setHeader(HEADER_MESSAGE_ID, messageID);
        }

        @Override
        protected void updateHeaders() throws MessagingException {
            super.updateHeaders();
            updateMessageID();
        }

        public String getMessageID() {
            return messageID;
        }

        public void setMessageID(String messageID) {
            this.messageID = messageID;
        }
    }
}




