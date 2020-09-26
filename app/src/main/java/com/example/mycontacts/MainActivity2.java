package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.IntEvaluator;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    Cursor phones;
    Button btnGet;
    Button btnGoToScreen1;
    ListView lv;
    List<String> cont = new ArrayList<String>();
    TextView NumberOfContacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnGoToScreen1 = findViewById(R.id.btnGoToScreen1);
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        phones = getContentResolver().query(uri, null, null, null, null);
        btnGet = findViewById(R.id.getSomeCont);
        lv = findViewById(R.id.LV);
        final Intent iContScreen1 = new Intent(this, MainActivity.class);
        NumberOfContacts = findViewById(R.id.txtNumberOfContacts);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSomeContacts(Integer.parseInt(NumberOfContacts.getText().toString()));
            }
        });
        btnGoToScreen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(iContScreen1);
            }
        });


    }


    protected void getSomeContacts(int count) {

        int x = 0;
        while (phones.moveToNext()) {

            int phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            String strPhone = phones.getString(phoneIndex);
            String strName = phones.getString(nameIndex);
            cont.add(strName + " : " + strPhone);

            x++;

            if (x == count)
                break;
        }
        ArrayAdapter<String> data = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cont);
        lv.setAdapter(data);
    }
}