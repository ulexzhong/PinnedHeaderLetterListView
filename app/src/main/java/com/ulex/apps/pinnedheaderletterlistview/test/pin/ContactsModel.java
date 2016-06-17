package com.ulex.apps.pinnedheaderletterlistview.test.pin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ulex on 2016/6/15.
 */
public class ContactsModel {

    public static List<ContactsEntity> getContactsList() {
        List<ContactsEntity> contactsList = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            contactsList.add(getContactEntity(String.valueOf((char) ('A' + i))));
        }
        return contactsList;
    }

    private static ContactsEntity getContactEntity(String title) {
        ArrayList<ContactsItemEntity> itemList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            itemList.add(new ContactsItemEntity(title + i));
        }
        return new ContactsEntity(title, itemList);
    }
}
