package com.ulex.apps.pinnedheaderletterlistview.test.pinletter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ulex.apps.pinnedheaderletterlistview.R;
import com.ulex.apps.pinnedheaderletterlistview.lib.PinnedHeaderLetterListView;
import com.ulex.apps.pinnedheaderletterlistview.lib.pinnedheaderlistview.PinnedHeaderListView;

import java.util.List;

public class PinLetterActivity extends Activity {
    private PinnedHeaderLetterListView listView;

    public static void start(Context context) {
        Intent starter = new Intent(context, PinLetterActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_letter);
        initView();
        initData();
    }

    private void initView() {
        listView = (PinnedHeaderLetterListView) findViewById(R.id.pinned_indicated_listView);
    }

    private void initData() {
        List<ContactsPinLetterEntity> list = ContactsPinLetterModel.getContactsList();
        ContactsPinLetterListAdapter listAdapter = new ContactsPinLetterListAdapter(list);
        TextView textView = new TextView(this);
        textView.setText("This is a headerView...");
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        textView.setLayoutParams(params);
        listView.addHeaderView(textView);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {

            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

            }
        });
    }
}
