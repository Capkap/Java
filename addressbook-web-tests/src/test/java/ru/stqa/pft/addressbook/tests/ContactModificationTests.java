package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.contact().list().size() == 0) {
            app.goTo().page("add new");
            app.contact().create(new ContactData()
                    .withFirstname("Ivan").withMiddlename("Ivanovich").withLastname("Ivanov").withNickname("Iva66").withCompany("MFI").withAddress("Nartova").withMobile("9100000001")
                    .withEmail("Ivanov66@mail.ru").withBday("13").withBmonth("February").withByear("2000").withGroup("test1"));
            app.goTo().page("home page");
        }
    }

    @Test
    public void testContactModification() {
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        ContactData contact = new ContactData()
                .withId(before.get(index).getId()).withFirstname("Oleg").withMiddlename("Olegovich").withLastname("Olegov").withNickname("Ole22").withCompany("Garda").withAddress("Gagarina")
                .withMobile("9220000022").withEmail("Oleg22@mail.ru").withBday("22").withBmonth("February").withByear("1992");
        app.contact().modify(index, contact);
        app.goTo().page("home page");
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(contact);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }

}
