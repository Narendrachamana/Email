package Tests;

import java.io.IOException;

import utilities.EmailUtils;

public class EmailTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		EmailUtils emailUtils = new EmailUtils();
		emailUtils.readHyperLinksFromMails("narendra.chamana66@gmail.com", "obthgyvaimzphrel","Inbox", "Contest for kids: Get their paintings featured!");
	}

}
