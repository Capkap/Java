package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContact() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillContactInfo(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("middlename"), contactData.getMiddlename());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("nickname"), contactData.getNickname());
        type(By.name("company"), contactData.getCompany());
        type(By.name("address"), contactData.getAddress());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("email"), contactData.getEmail());
        selectByText(By.name("bday"), contactData.getBday());
        click(By.xpath("//option[@value='13']"));
        selectByText(By.name("bmonth"), contactData.getBmonth());
        click(By.xpath("//option[@value='February']"));
        type(By.name("byear"), contactData.getByear());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void initContactModificationById(int id) {
        wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
    }

    public void submitContactModification() {
        click(By.xpath("(//input[@name='update'])[2]"));
    }

    public void deleteContact() {
        click(By.xpath("//input[@value='Delete']"));
        wd.switchTo().alert().accept();
    }

    public void create(ContactData contact) {
        fillContactInfo(contact, true);
        submitContact();
        contactCache = null;
    }

    public void modify(ContactData contact) {
        initContactModificationById(contact.getId());
        fillContactInfo(contact, false);
        submitContactModification();
        contactCache = null;
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteContact();
        contactCache = null;
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }


    public boolean isThereAContact() {
        return isElementPresent(By.xpath("//td/input"));
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null){
            return new Contacts(contactCache);
        }

        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            String name = cells.get(2).getText();
            String lastname = cells.get(1).getText();
            String allPhones = cells.get(5).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contactCache.add(new ContactData().withId(id).withFirstname(name).withLastname(lastname)
                    .withAllPhones(allPhones));
        }
        return new Contacts(contactCache);
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
    }
}
