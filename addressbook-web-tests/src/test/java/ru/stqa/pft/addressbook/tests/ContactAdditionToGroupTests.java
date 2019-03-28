package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAdditionToGroupTests extends TestBase{

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.db().contacts().size() == 0) {
            app.goTo().page("add new");
            app.contact().create(new ContactData()
                    .withFirstname("Ivan").withMiddlename("Ivanovich").withLastname("Ivanov").withNickname("Iva66").withCompany("MFI").withAddress("Nartova").withMobilePhone("9100000001")
                    .withEmail1("Ivanov66@mail.ru").withBday("13").withBmonth("February").withByear("2000"));
            app.goTo().page("home page");
        }
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testContactAdditionToGroup(){
        Contacts beforeContact = app.db().contacts();
        Groups beforeGroups = app.db().groups();
        ContactData selectedContact = beforeContact.iterator().next();
        GroupData selectedGroup = beforeGroups.iterator().next();
        app.contact().addToGroup(selectedContact,selectedGroup);
        Contacts afterContact = app.db().contacts();
        assertThat(afterContact.iterator().next().getGroups(), equalTo(beforeContact.iterator().next().getGroups().withAdded(selectedGroup)));
        verifyContactListInUI();
    }
}
