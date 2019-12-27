package com.example.ggalgom_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1) ;
        tabHost1.setup();

        // 첫번째 탭
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("Tab 1");
        tabHost1.addTab(ts1);

        //두번째 탭
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("TAB 2");
        tabHost1.addTab(ts2);

        //세번째 탭
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("TAB 3");
        tabHost1.addTab(ts3);

        final int img[] = {R.drawable.minieon01, R.drawable.minieon02, R.drawable.minieon03,
                            R.drawable.minieon04, R.drawable.minieon05, R.drawable.minieon06,
                R.drawable.minieon07, R.drawable.minieon08, R.drawable.minieon09, R.drawable.minieon10, R.drawable.minieon11,
                R.drawable.minieon12, R.drawable.minieon13, R.drawable.minieon14, R.drawable.minieon15, R.drawable.minieon16,
                R.drawable.minieon17, R.drawable.minieon18, R.drawable.minieon19, R.drawable.minieon20};

        GalleryAdapter adapter = new GalleryAdapter(
                getApplicationContext(),
                R.layout.row,
                img);

        Gallery g = (Gallery)findViewById(R.id.gallery);
        g.setAdapter(adapter);

        final ImageView iv = (ImageView)findViewById(R.id.imageView1);
    }
}
