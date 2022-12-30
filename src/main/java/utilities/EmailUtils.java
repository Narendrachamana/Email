package utilities;

import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;

import java.io.IOException;

import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

public class EmailUtils {

	String searchText = null;
	Message message;
	String emailSubjectContent;
	String emailLink = null;

	public String readMails(String userName, String password, String foldername, String emailSubject)
			throws IOException {
		try {

			Properties properties = System.getProperties();
			properties.setProperty("mail.store.protocol", "imap");
			properties.setProperty("mail.imaps.partialfetch", "false");
			properties.put("mail.imap.ssl.enable", "true");
			properties.put("mail.mime.base64.ignoreerrors", "true");
			properties.setProperty("mail.imaps.connectiontimeout", "5000");
			properties.put("mail.imaps.timeout", "10000");

			Session session = Session.getDefaultInstance(properties, null);
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", 993, userName, password);
			Folder inbox = store.getFolder(foldername);

			int unreadMailCount = inbox.getUnreadMessageCount();
			System.out.println("No. of Unread Mails = " + unreadMailCount);

			inbox.open(Folder.READ_WRITE);

			int messageCount = inbox.getMessageCount();

			System.out.println("Total Message Count: " + messageCount);

			for (int i = messageCount - 1; i > (messageCount - unreadMailCount); i--)

			{
				message = inbox.getMessages()[i];

				emailSubjectContent = message.getSubject();

				if (emailSubjectContent.contains(emailSubject))

				{

					System.out.println("OTP mail found");

					String result = "";
					MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();

					int count = mimeMultipart.getCount();
					for (int j = 0; j < count; j++) {
						BodyPart bodyPart = mimeMultipart.getBodyPart(j);
						result = bodyPart.getContent().toString();
						Pattern p = Pattern.compile("\\d+");
						Matcher m = p.matcher(result);
						while (m.find()) {
							System.out.println(m.group());
							break;
						}

						System.out.println("Test text OTP: " + m.group());
						message.setFlag(Flags.Flag.SEEN, true);
						message.setFlag(Flags.Flag.DELETED, true);
						break;
					}
					break;
				}

				message.setFlag(Flags.Flag.SEEN, true);
				message.setFlag(Flags.Flag.DELETED, true);

			}

			inbox.close(true);

			store.close();

		} catch (Exception mex) {

			mex.printStackTrace();

			System.out.println("OTP Not found");

		}

		return searchText;

	}

	public String readHyperLinksFromMails(String userName, String password, String foldername, String emailSubject)
			throws IOException {
		try {
			Properties properties = System.getProperties();
			properties.setProperty("mail.store.protocol", "imap");
			properties.setProperty("mail.imaps.partialfetch", "false");
			properties.put("mail.imap.ssl.enable", "true");
			properties.put("mail.mime.base64.ignoreerrors", "true");
			properties.setProperty("mail.imaps.connectiontimeout", "5000");
			properties.put("mail.imaps.timeout", "10000");

			Session session = Session.getDefaultInstance(properties, null);
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", 993, userName, password);
			Folder inbox = store.getFolder(foldername);

			int unreadMailCount = inbox.getUnreadMessageCount();
			System.out.println("No. of Unread Mails = " + unreadMailCount);

			inbox.open(Folder.READ_WRITE);

			int messageCount = inbox.getMessageCount();

			System.out.println("Total Message Count: " + messageCount);

			for (int i = messageCount - 1; i > (messageCount - unreadMailCount); i--)

			{
				message = inbox.getMessages()[i];
				emailSubjectContent = message.getSubject();
				ArrayList<String> links = new ArrayList<String>();

				if (emailSubjectContent.contains(emailSubject))

				{
					MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();

					String desc = mimeMultipart.getBodyPart(1).getContent().toString();

					Pattern linkPattern = Pattern.compile("href=\"(.*?)\"", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
					Matcher pageMatcher = linkPattern.matcher(desc);
					while (pageMatcher.find()) {
						links.add(pageMatcher.group(1));
					}

					for (String temp : links) {
						emailLink = temp;

						System.out.println("Test text URL: " + emailLink);
						message.setFlag(Flags.Flag.SEEN, true);
						// message.setFlag(Flags.Flag.DELETED, true);
						 break;
					}
					break;
				}

				message.setFlag(Flags.Flag.SEEN, true);
				// message.setFlag(Flags.Flag.DELETED, true);

			}

			inbox.close(true);

			store.close();

		} catch (Exception mex) {

			mex.printStackTrace();

			System.out.println("URL Not found");

		}

		emailLink = Jsoup.parse((emailLink).toString()).text();
		System.out.println(emailLink);
		return emailLink;

	}

}
