package com.ulex.apps.pinnedheaderletterlistview.test.pinletter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ulex on 2016/6/15.
 */
public class ContactsPinLetterModel {

    public static List<ContactsPinLetterEntity> getContactsList() {
        List<ContactsPinLetterEntity> contactsList = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            contactsList.add(getContactEntity(String.valueOf((char) ('A' + i))));
        }
        return contactsList;
    }

    private static ContactsPinLetterEntity getContactEntity(String title) {
        ArrayList<ContactsPinLetterItemEntity> itemList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            itemList.add(new ContactsPinLetterItemEntity(title + i));
        }
        return new ContactsPinLetterEntity(title, itemList);
    }
}
