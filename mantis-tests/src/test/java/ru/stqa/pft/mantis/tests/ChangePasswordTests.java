package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws IOException, MessagingException {
        HttpSession session = app.newSession();
        String newPassword = "newPassword";
        app.session().login("administrator", "root");
        app.passwordHelper().goToManageUsersPage();
        app.passwordHelper().chooseUser(3);
        String user = app.passwordHelper().getUserName();
        String email = app.passwordHelper().getMail();
        app.passwordHelper().resetPassword();
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 20000);
        String resetPasswordLink = findResetPasswordLink(mailMessages, email);

        app.passwordHelper().updatePassword(resetPasswordLink, newPassword);
        assertTrue(session.login(user, newPassword));
        assertTrue(session.isLoggedInAs(user));
    }

    private String findResetPasswordLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }

}