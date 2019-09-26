package DAO;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.Cashier;
import model.KitchenMember;

public class ReminderEmail {
	
	/**
     * Send an email
     *
     * @param member : receiver
     * @param emailContent : email content
     * @param emailSubject : email subject
     * @return
     * @throws Exception
     */
	public static void sendIndividualReminderEmail(KitchenMember member, String emailContent, String emailSubject) throws Exception {
		
		Cashier cashier = CashierDAO.getCashier();
		
        // Properties setting
        Properties props = new Properties();                    
        props.setProperty("mail.transport.protocol", "smtp");   
        props.setProperty("mail.smtp.host", cashier.getEmailSMTPHost());   
        props.setProperty("mail.smtp.auth", "true");            

        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        
        // 2. Create an session
        Session session = Session.getInstance(props);
//        session.setDebug(true);                                 
//        sendReminderEmail(session, cashier, member, emailContent, emailSubject);
        
     // 3. Create an email
        MimeMessage message = createMimeMessage(session, cashier, member, emailContent, emailSubject);

        Transport transport = session.getTransport();

        transport.connect(cashier.getEmail(), cashier.getPassword());
 
        transport.sendMessage(message, message.getAllRecipients());

        transport.close();
    }
	
	
    private static MimeMessage createMimeMessage(Session session, Cashier cashier, KitchenMember member, String emailContent, String emailSubject) throws Exception {
        // 1. Create an email message
        MimeMessage message = new MimeMessage(session);

        // 2. From
        message.setFrom(new InternetAddress(cashier.getEmail(), cashier.getMember().getName()));

        // 3. To
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(member.getEmail(), member.getName()));

        // 4. Subject
//        message.setSubject("Kitchen Fee of Hempel A0");
        message.setSubject(emailSubject);
        
        // 5. Content
        message.setText(emailContent);

        // 6. Time
        message.setSentDate(new Date());

        // 7. Save change
        message.saveChanges();
        
        return message;
    }

	// Email Content 
    
    /**
     * Generate email content
     *
     * @param member : receiver
     * @param currentMonth : Kitchen fee to the current month
     * @return String : email content
     * @throws Exception
     */
    public static String generateEmailContent(KitchenMember member, Date currentMonth) throws Exception {
    	List<String> monthList = KitchenFeeDAO.getMissingMonthList(member, currentMonth);
    	Cashier cashier = CashierDAO.getCashier();
    	
		String message = "";
		String message_receiver = "Dear " + member.getName() + ",\n\n";
		String message_payment = "";
		String message_cashier = "";
		
		if (monthList.isEmpty()) {
			message_payment = "Your payment is up to date! \n\n";
		} else {
			String paymentDetail = "";
			Double total = 0.0;
			for (String key : monthList) {
				String.format("%1$"+1+ "s", key);
				paymentDetail =paymentDetail + "  - " + String.format("%-15s", key) + " " + cashier.getMonthFee() + " DKK \n";
				total += cashier.getMonthFee();
			}
			message_payment = "Your kitchen fee for the following months is missing: \n" +paymentDetail + "\n  Total: " 
					+ total.toString() + " DKK \n"
					+ "\n" + "Please send the kitchen fee either by Mobilepay (app) or bank transfer. "
					+ "Remember to write room number and month in the transaction. \n\n";
			message_cashier = "Cashier Account Details: \n" + 
					"Mobilepay: " + cashier.getMobilePay() + "\n" + 
					"Registration and account number: " + cashier.getBankReg() + " " + cashier.getBankAccount() +"\n" + 
					"If you are having any objections to this summary of your payments, please contact me.\n" + 
					"\n";
		}
	
		String message_account = "Our account balance is now " + ReceiptDAO.getAccountBalance() +" DKK. \n" + 
				"If you buy anything for the kitchen, please remember to notice the cashier and supply a receipt. \n\n"
				+ "Yours sincerely, \n\n" + cashier.getMember().getName();
		
		message = message_receiver + message_payment + message_cashier + message_account;
		return message;
    }
    
	public static void main(String[] args) throws Exception {

	}

}
