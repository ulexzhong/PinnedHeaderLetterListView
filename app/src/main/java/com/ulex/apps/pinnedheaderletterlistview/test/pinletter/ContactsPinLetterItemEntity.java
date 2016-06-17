package com.ulex.apps.pinnedheaderletterlistview.test.pinletter;


import com.ulex.apps.pinnedheaderletterlistview.lib.entity.PinLetterBaseItemEntity;

/**
 * Created by ulex on 2016/6/15.
 */
public class ContactsPinLetterItemEntity extends PinLetterBaseItemEntity {
    private String name;

    public ContactsPinLetterItemEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
