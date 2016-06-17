package com.ulex.apps.pinnedheaderletterlistview.lib.entity;

import java.util.List;

/**
 * Created by ulex on 2016/6/15.
 */
public class PinLetterBaseEntity {
    protected String letter;
    protected List<? extends PinLetterBaseItemEntity> itemEntityList;

    public PinLetterBaseEntity(String letter, List<? extends PinLetterBaseItemEntity> itemEntityList) {
        this.letter = letter;
        this.itemEntityList = itemEntityList;
    }

    public String getLetter() {
        return letter;
    }

    public List<? extends PinLetterBaseItemEntity> getItemEntityList() {
        return itemEntityList;
    }
}
