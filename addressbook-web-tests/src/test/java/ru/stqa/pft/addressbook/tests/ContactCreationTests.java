package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        List<ContactData> before = app.contact().list();
        app.goTo().page("add new");
        ContactData contact = new ContactData()
                .withFirstname("Ivan").withMiddlename("Ivanovich").withLastname("Ivanov").withNickname("Iva66").withCompany("MFI").withAddress("Nartova").withMobile("9100000001")
                .withEmail("Ivanov66@mail.ru").withBday("13").withBmonth("February").withByear("2000").withGroup("test1");
        app.contact().create(contact);
        app.goTo().page("home page");
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() + 1);

        before.add(contact);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }

}
