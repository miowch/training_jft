package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by anaximines on 26/07/16.
 */
public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();

        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().
                    withFirstName("firstName").
                    withLastName("lastName").
                    withAddress("address").
                    withMobileTel("mobileTel").
                    withGroup("test1"));
            app.contact().create(new ContactData().
                    withFirstName("firstName").
                    withLastName("lastName").
                    withAddress("address").
                    withMobileTel("mobileTel").
                    withGroup("[none]"));
        }

        app.timeout(5);
    }

    @Test
    public void testSelectedContactDeletion() {

        Contacts before = app.contact().all();
        ContactData deletedContact = before.iterator().next();

        app.contact().select(deletedContact);
        app.contact().deleteSomeContacts();

        Contacts after = app.contact().all();

        assertThat(after, equalTo(before.without(deletedContact)));
    }

    @Test
    public void testContactDeletion() {

        Contacts before = app.contact().all();
        ContactData deletedContact = before.iterator().next();

        app.contact().openEditForm(deletedContact);
        app.contact().delete();
        app.timeout(5);

        Contacts after = app.contact().all();

        assertThat(after, equalTo(before.without(deletedContact)));
    }

    @Test
    public void testAllContactsDeletion() {

        app.contact().selectAll();
        app.contact().deleteSomeContacts();
        Contacts after = app.contact().all();

        assertThat(after.size(), equalTo(0));
    }
}