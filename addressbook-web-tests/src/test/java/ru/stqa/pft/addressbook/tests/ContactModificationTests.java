package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.db().contacts().size() == 0) {
            app.goTo().page("add new");
            app.contact().create(new ContactData()
                    .withFirstname("Ivan").withMiddlename("Ivanovich").withLastname("Ivanov").withNickname("Iva66").withCompany("MFI").withAddress("Nartova").withMobilePhone("9100000001")
                    .withEmail1("Ivanov66@mail.ru").withBday("9").withBmonth("January").withByear("2000"));
            app.goTo().page("home page");
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstname("Oleg").withMiddlename("Olegovich").withLastname("Olegov").withNickname("Ole22").withCompany("Garda").withAddress("Gagarina")
                .withMobilePhone("9220000022").withHomePhone("923123").withWorkPhone("333").withEmail1("Oleg22@mail.ru").withBday("22").withBmonth("December").withByear("1992");
        app.contact().modify(contact);
        app.goTo().page("home page");
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
        verifyContactListInUI();
    }
}
