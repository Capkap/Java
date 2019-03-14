package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.contact().all().size() == 0) {
            app.goTo().page("add new");
            app.contact().create(new ContactData()
                    .withFirstname("Ivan").withMiddlename("Ivanovich").withLastname("Ivanov").withNickname("Iva66").withCompany("MFI").withAddress("Nartova").withMobilePhone("9100000001")
                    .withEmail("Ivanov66@mail.ru").withBday("13").withBmonth("February").withByear("2000").withGroup("test1"));
            app.goTo().page("home page");
        }
    }

    @Test
    public void testContactDeletion() {
        Contacts before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        app.goTo().page("home");
        assertThat(app.contact().count(), equalTo(before.size() - 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.without(deletedContact)));
    }
}
