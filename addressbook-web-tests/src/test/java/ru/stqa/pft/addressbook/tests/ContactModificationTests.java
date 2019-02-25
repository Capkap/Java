package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification(){
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactInfo(new ContactData("Oleg", "Olegovich", "Olegov", "Ole22", "Garda", "Gagarina", "9220000022", "Oleg22@mail.ru", "22", "February", "1992"));
        app.getContactHelper().submitContactModification();
    }

}
