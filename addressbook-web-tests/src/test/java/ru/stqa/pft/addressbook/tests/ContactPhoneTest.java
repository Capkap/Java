package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTest extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.db().contacts().size() == 0) {
            app.goTo().page("add new");
            app.contact().create(new ContactData()
                    .withFirstname("Ivan").withMiddlename("Ivanovich").withLastname("Ivanov").withNickname("Iva66").withCompany("MFI").withAddress("Nartova")
                    .withMobilePhone("9100000001").withWorkPhone("+79021231022").withHomePhone("(090)022 23").withEmail1("Ivanov66@mail.ru").withEmail2("Email2@@mail.rus").withEmail3("Email43.com")
                    .withBday("13").withBmonth("February").withByear("2000").withGroup("test1"));
            app.goTo().page("home page");
        }
    }

    @Test
    public void testContactPhone(){
        app.goTo().page("home");
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter((s) -> ! s.equals(""))
                .map(ContactPhoneTest::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned (String phone){
        return phone.replaceAll("\\s","").replaceAll("[-()]","");
    }
}
