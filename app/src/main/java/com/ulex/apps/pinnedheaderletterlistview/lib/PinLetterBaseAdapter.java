package com.ulex.apps.pinnedheaderletterlistview.lib;


import android.text.TextUtils;

import com.ulex.apps.pinnedheaderletterlistview.lib.entity.PinLetterBaseEntity;
import com.ulex.apps.pinnedheaderletterlistview.lib.pinnedheaderlistview.SectionedBaseAdapter;

import java.util.List;

/**
 * Created by ulex on 2016/6/15.
 */
public abstract class PinLetterBaseAdapter extends SectionedBaseAdapter {
    protected List<? extends PinLetterBaseEntity> list;

    public PinLetterBaseAdapter(List<? extends PinLetterBaseEntity> list) {
        this.list = list;
    }

    /**
     * for locate the position in the whole listview,
     * this index doesn't include with headViewCount
     *
     * @param section
     * @return
     */
    public int getWholePosition(int section) {
        if (section <= 0) {
            return section;
        }
        int index = 0;
        for (int i = 0; i < section; i++) {
            index += getCountForSection(i) + 1;
        }
        return index;
    }

    public int getWholePosition(String letter) {
        if (TextUtils.isEmpty(letter)) {
            return -1;
        }
        int section = -1;
        for (int i = 0, size = list.size(); i < size; i++) {
            if (letter.equals(list.get(i).getLetter())) {
                section = i;
                break;
            }
        }
        return getWholePosition(section);
    }

}
