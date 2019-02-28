package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification(){
        if (!app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().goToPage("add new");
            app.getContactHelper().createContact(new ContactData("Ivan", "Ivanovich", "Ivanov", "Iva66", "MFI", "Nartova", "9100000001", "Ivanov66@mail.ru", "13", "February", "2000", "test1"));
            app.getNavigationHelper().goToPage("home page");
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactInfo(new ContactData("Oleg", "Olegovich", "Olegov", "Ole22", "Garda", "Gagarina", "9220000022", "Oleg22@mail.ru", "22", "February", "1992", null), false);
        app.getContactHelper().submitContactModification();
    }

}
