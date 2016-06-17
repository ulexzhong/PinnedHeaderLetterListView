package com.ulex.apps.pinnedheaderletterlistview.test.pinletter;

import com.ulex.apps.pinnedheaderletterlistview.lib.entity.PinLetterBaseEntity;

import java.util.List;

/**
 * Created by ulex on 2016/6/15.
 */
public class ContactsPinLetterEntity extends PinLetterBaseEntity{

    public ContactsPinLetterEntity(String title, List<ContactsPinLetterItemEntity> itemEntityList) {
        super(title, itemEntityList);
    }

    public List<ContactsPinLetterItemEntity> getItemEntityList() {
        return (List<ContactsPinLetterItemEntity>) itemEntityList;
    }
}
