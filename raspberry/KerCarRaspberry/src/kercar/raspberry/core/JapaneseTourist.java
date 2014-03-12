package kercar.raspberry.core;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * Watashi wa, nihonjin kankōkyakudesu
 * Watashi ga shashin o totte, messēji o sōshin suru koto ga dekimasu
 * @author kid
 *
 */
public class JapaneseTourist {
	
	private static int id = 0;

	public static void takePhoto(){
		System.out.println("Kawaii desu ne ?");
		ProcessBuilder pb = new ProcessBuilder("fswebcam", "-r 640x480", "/home/pi/photo_"+String.valueOf(id++)+".jpg");
		try {
			Process p = pb.start();
			p.waitFor();
			getProcessOutput(p);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Yameteeeeeeee!");
			e.printStackTrace();
		}
	}
	
	public static void sendPhotos(){
		final String username = "kercart2.2013@gmail.com";
		final String password = "canard56";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("kercart2.2013@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("kercart2.2013@gmail.com"));
			message.setSubject("Photos KerCar");
			
			// create and fill the first message part
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText("Hello ! En pièce jointe, la photo");
			
			// create the second message part
			MimeBodyPart mbp2 = new MimeBodyPart();

			// attach the file to the message
			FileDataSource fds = new FileDataSource("/home/pi/photo_0.jpg");
			mbp2.setDataHandler(new DataHandler(fds));
			mbp2.setFileName(fds.getName());
			
			// create the Multipart and add its parts to it
		    Multipart mp = new MimeMultipart();
		    mp.addBodyPart(mbp1);
		    mp.addBodyPart(mbp2);

  		    // add the Multipart to the message
		    message.setContent(mp);
		    
		    // set the Date: header
			message.setSentDate(new Date());
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (Exception e) {
			System.out.println("Erreur à l'envoi du mail");
			e.printStackTrace();
		}	
	}
	
	public static void clearPhotos(){
		System.out.println("Suppression des photos");
		ProcessBuilder pb = new ProcessBuilder("rm", "/home/pi/photo_*");
		try {
			Process p = pb.start();
			p.waitFor();
			getProcessOutput(p);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Yameteeeeeeee!");
			e.printStackTrace();
		}
	}
	
	public static void sendPhotos2(){
		System.out.println("Envoi des photos");
		ProcessBuilder pb = new ProcessBuilder("/opt/apache-tomcat-7.0.35/sendPhotos.sh");
		try {
			Process p = pb.start();
			p.waitFor();
			getProcessOutput(p);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Yameteeeeeeee!");
			e.printStackTrace();
		}
	}
	
	public static void sendLogs(){
		System.out.println("Envoi des logs");
		ProcessBuilder pb = new ProcessBuilder("/opt/apache-tomcat-7.0.35/sendLogs.sh");
		try {
			Process p = pb.start();
			p.waitFor();
			getProcessOutput(p);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Yameteeeeeeee!");
			e.printStackTrace();
		}
	}
	
    private static String getProcessOutput(Process p){
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String tmp;
        StringBuffer bf = new StringBuffer();

        try{
                while( (tmp = br.readLine()) != null ){
                        bf.append(tmp+System.getProperty("line.separator"));
                }
                br.close();
        } catch(Exception e){
                e.printStackTrace();
        }
        System.out.println(bf.toString());
        return bf.toString();
    }

}
