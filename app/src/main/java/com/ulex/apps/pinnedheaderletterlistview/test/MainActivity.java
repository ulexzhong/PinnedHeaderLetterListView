package com.ulex.apps.pinnedheaderletterlistview.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.ulex.apps.pinnedheaderletterlistview.R;
import com.ulex.apps.pinnedheaderletterlistview.test.pin.PinnedHeaderActivity;
import com.ulex.apps.pinnedheaderletterlistview.test.pinletter.PinLetterActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.pinned_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PinnedHeaderActivity.start(MainActivity.this);
            }
        });
        findViewById(R.id.pin_letter_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PinLetterActivity.start(MainActivity.this);
            }
        });
    }

}
