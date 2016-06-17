package com.ulex.apps.pinnedheaderletterlistview.test.pin;

import java.util.List;

/**
 * Created by ulex on 2016/6/15.
 */
public class ContactsEntity {
    private String title;
    private List<ContactsItemEntity> contactsItemEntityList;

    public ContactsEntity(String title, List<ContactsItemEntity> contactsItemEntityList) {
        this.title = title;
        this.contactsItemEntityList = contactsItemEntityList;
    }

    public String getTitle() {
        return title;
    }

    public List<ContactsItemEntity> getContactsItemEntityList() {
        return contactsItemEntityList;
    }
}
