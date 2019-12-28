package com.example.ggalgom_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TabHost;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1) ;
        tabHost1.setup();

        // 첫번째 탭
        final int img[] = {R.drawable.minieon01, R.drawable.minieon02, R.drawable.minieon03,
                R.drawable.minieon04, R.drawable.minieon05, R.drawable.minieon06,
                R.drawable.minieon07, R.drawable.minieon08, R.drawable.minieon09, R.drawable.minieon10, R.drawable.minieon11,
                R.drawable.minieon12, R.drawable.minieon13, R.drawable.minieon14, R.drawable.minieon15, R.drawable.minieon16,
                R.drawable.minieon17, R.drawable.minieon18, R.drawable.minieon19, R.drawable.minieon20};

        GalleryAdapter grid_adapter = new GalleryAdapter(
                getApplicationContext(),
                R.layout.row,
                img);

        Gallery g = (Gallery)findViewById(R.id.real_gallery);
        g.setAdapter(grid_adapter);

        final ImageView iv = (ImageView)findViewById(R.id.imageView1);

        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.contacts);
        ts1.setIndicator("CONTACTS");
        tabHost1.addTab(ts1);

        //두번째 탭
        Context context = getApplicationContext();
        ContactUtil contactUtil = new ContactUtil(context);
        List<Contact> contacts = contactUtil.getContactList();


        PhoneBook phoneBook = new PhoneBook();
        // context = getApplicationContext();
        List<PhoneBook> datas = phoneBook.getcontacts(this); // it might should changd this to context

        RecyclerView recyclerView = findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerAdapter adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        for(int i=datas.size()-1; i>=0; i--)
        {
            PhoneBook temp = datas.get(i);
            adapter.addItem(temp);
        }
        adapter.notifyDataSetChanged();

        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.real_gallery);
        ts2.setIndicator("GALLERY");
        tabHost1.addTab(ts2);

        //세번째 탭
        Button button = (Button) findViewById(R.id.BtnToNewAct);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GGALGOM_Activity.class);
                startActivity(intent);
            }
        });

        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
        ts3.setContent(R.id.ggalgom);
        ts3.setIndicator("GGALGOM");
        tabHost1.addTab(ts3);


    }
}