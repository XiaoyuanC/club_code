package club.third.email;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/24 15:26
 */
public class ClubSimpleEmail {
    //Mail service provider
    private final static String HOSTNAME = "smtp.163.com";
    //sender
    private final static String ADDRESS = "clubsystem@163.com";
    //Character Encoding
    private final static String CHARSET = "utf-8";
    //Authorization code
    private final static String AUTHENTICATION = "TWWQZFNLPZBLXUIT";    

    public static void send(String address, String subject, String content) throws EmailException {
        SimpleEmail email = new SimpleEmail();
        //Mail service provider
        email.setHostName(HOSTNAME);
        //Set the type of characters sent
        email.setCharset(CHARSET);
        //Authorization information for sending mail
        email.setAuthentication(ADDRESS, AUTHENTICATION);
        email.setSSLOnConnect(true);
        //sender
        email.setFrom(ADDRESS);
        //Email Subject
        email.setSubject(subject);
        //content of email
        email.setMsg(content);
        //Receiver
        email.addTo(address);
        //send
        email.send();
    }
}
