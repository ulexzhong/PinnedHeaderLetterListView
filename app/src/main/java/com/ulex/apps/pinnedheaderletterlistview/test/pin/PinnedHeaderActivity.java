package com.ulex.apps.pinnedheaderletterlistview.test.pin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ulex.apps.pinnedheaderletterlistview.R;
import com.ulex.apps.pinnedheaderletterlistview.lib.pinnedheaderlistview.PinnedHeaderListView;

import java.util.List;

public class PinnedHeaderActivity extends Activity {
    private PinnedHeaderListView listView;

    public static void start(Context context) {
        Intent starter = new Intent(context, PinnedHeaderActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned_header);
        initView();
        initData();
    }

    private void initView() {
        listView = (PinnedHeaderListView) findViewById(R.id.listView);
    }

    private void initData() {
        List<ContactsEntity> list = ContactsModel.getContactsList();
        ContactsListAdapter listAdapter = new ContactsListAdapter(list);
        listView.setAdapter(listAdapter);
    }
}
