package com.example.ggalgom_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

public class PhoneBook
{
    private String id;
    private String name;
    private String tel;

    private void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    private void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return this.tel;
    }

    public List<PhoneBook> getcontacts(Context context) {
        //Data structure to save Contacts
        List<PhoneBook> datas = new ArrayList<>();

        //1. Bring Resolver
        //By using Content Provider, we can get the contacts
        //Content resolver is kind of communication method to bring Content provider
        ContentResolver resolver = context.getContentResolver();

        //2. Bring address (Uri) where contacts are stored
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        //3. Bring the column which is declared in table
        //Column is defined in path "ContactsContract.CommonDataKinds.Phone
        //Contact id is index value and it can be overlapped
        String[] projection = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                , ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                , ContactsContract.CommonDataKinds.Phone.NUMBER};

        //4. Request query to the Content Resolver. Then Resolver will query from provider
        //query means requesting data from database
        Cursor cursor = resolver.query(phoneUri, projection, null, null, null);

        //Datas are stored in cursor. Extract data from cursor by iteration
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //4.1 find index by name
                int idIndex = cursor.getColumnIndex(projection[0]);
                int nameIndex = cursor.getColumnIndex(projection[1]);
                int numberIndex = cursor.getColumnIndex(projection[2]);

                //4.2 bring the real value by using each index
                String id = cursor.getString(idIndex);
                String name = cursor.getString(nameIndex);
                String number = cursor.getString(numberIndex);

                PhoneBook phoneBook = new PhoneBook();
                phoneBook.setId(id);
                phoneBook.setName(name);
                phoneBook.setTel(number);

                datas.add(phoneBook);
            }
        }

        cursor.close();
        return datas;
    }
}

