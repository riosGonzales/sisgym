package Correo;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class CorreoClass {
    
    //TULEMETELAPAPYA

    public static void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        final String username = "muslitofit@gmail.com"; // Cambia por tu dirección de correo
        final String password = "diwj jprp joeh lsue"; // Cambia por tu contraseña

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); 

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            msg.setSubject(asunto);
            msg.setText(cuerpo);

            Transport.send(msg);
            System.out.println("¡Correo enviado correctamente a "+destinatario+" !");
        } catch (MessagingException e) {
            e.printStackTrace(); // Imprimir la traza de error para depuración
        }
    }

    public static void main(String[] args) {
    }
}
