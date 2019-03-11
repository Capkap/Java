package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        if (!app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().goToPage("add new");
            app.getContactHelper().createContact(new ContactData("Ivan", "Ivanovich", "Ivanov", "Iva66", "MFI", "Nartova", "9100000001", "Ivanov66@mail.ru", "13", "February", "2000", "test1"));
            app.getNavigationHelper().goToPage("home page");
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(0);
        app.getContactHelper().initContactModification();
        ContactData contact = new ContactData(before.get(0).getId(),"Oleg", "Olegovich", "Olegov", "Ole22", "Garda", "Gagarina", "9220000022", "Oleg22@mail.ru", "22", "February", "1992", null);
        app.getContactHelper().fillContactInfo(contact, false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().goToPage("home page");
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(0);
        before.add(contact);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }

}
