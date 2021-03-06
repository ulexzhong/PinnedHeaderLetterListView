package com.ulex.apps.pinnedheaderletterlistview.test.pin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ulex.apps.pinnedheaderletterlistview.R;
import com.ulex.apps.pinnedheaderletterlistview.lib.pinnedheaderlistview.SectionedBaseAdapter;

import java.util.List;

/**
 * Created by ulex on 2016/6/15.
 */
public class ContactsListAdapter extends SectionedBaseAdapter {
    private List<ContactsEntity> list;

    public ContactsListAdapter(List<ContactsEntity> list) {
        this.list = list;
    }

    @Override
    public Object getItem(int section, int position) {
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }

    @Override
    public int getSectionCount() {
        return list.size();
    }

    @Override
    public int getCountForSection(int section) {
        return list.get(section).getContactsItemEntityList().size();
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        ItemViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ItemViewHolder(parent);
            convertView = viewHolder.getView();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder) convertView.getTag();
        }
        ContactsItemEntity entity = list.get(section).getContactsItemEntityList().get(position);
        viewHolder.render(entity.getName());
        return convertView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        HeaderViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new HeaderViewHolder(parent);
            convertView = viewHolder.getView();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HeaderViewHolder) convertView.getTag();
        }
        ContactsEntity entity = list.get(section);
        viewHolder.render(entity.getTitle());
        return convertView;
    }

    private static class ItemViewHolder {
        private TextView contentTxt;
        private View view;

        public ItemViewHolder(ViewGroup parent) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts_view, parent, false);
            initView();
        }

        public View getView() {
            return view;
        }

        private void initView() {
            contentTxt = (TextView) view.findViewById(R.id.item_contacts_content_txt);
        }

        public void render(String content) {
            contentTxt.setText(content);
        }
    }

    private static class HeaderViewHolder {
        private TextView titleTxt;
        private View view;

        public HeaderViewHolder(ViewGroup parent) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts_head_view, parent, false);
            initView();
        }

        public View getView() {
            return view;
        }

        private void initView() {
            titleTxt = (TextView) view.findViewById(R.id.item_contacts_head_txt);
        }

        public void render(String title) {
            titleTxt.setText(title);
        }
    }

}
