package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getNavigationHelper().goToPage("add new");
        ContactData contact = new ContactData("Ivan", "Ivanovich", "Ivanov", "Iva66", "MFI", "Nartova", "9100000001", "Ivanov66@mail.ru", "13", "February", "2000","test1");
        app.getContactHelper().createContact(contact);
        app.getNavigationHelper().goToPage("home page");
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() + 1);

        before.add(contact);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }

}
