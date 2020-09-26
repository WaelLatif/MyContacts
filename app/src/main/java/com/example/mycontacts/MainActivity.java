package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    TextView txtName;
    TextView txtNum;
    EditText txtContacts;
    Button btnGoToContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkSelfPermission(Manifest.permission.READ_CONTACTS);

        Button btnOpen = findViewById(R.id.btnOpen);
        Button btnGet = findViewById(R.id.btnContact);
        btnGoToContact = findViewById(R.id.btnGoToContact);

        txtNum = findViewById(R.id.txtNum);
        txtName = findViewById(R.id.txtName);
        txtContacts = findViewById(R.id.txtContacts);


        final Intent icontact = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        final Intent iContScreen = new Intent(this, MainActivity2.class);

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  startActivity(icontact);
                startActivityForResult(icontact, 1);
            }

        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillContact();
            }
        });
        btnGoToContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(iContScreen);
            }


        });


    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        Uri uri = data.getData();
        Cursor Cur = getContentResolver().query(uri, null, null, null, null);
        Cur.moveToFirst();
        int nameIndex = Cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int numIndex = Cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        String StrNumber = Cur.getString(numIndex);
        String StrName = Cur.getString(nameIndex);

        txtName.setText(StrName);
        txtNum.setText(StrNumber);

    }


    protected void fillContact() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        try {
            Uri uri1 = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            Cursor phone = getContentResolver().query(uri1, null, null, null, null);
            while (phone.moveToNext()) {
                int phoneIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int nameIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String strPhone = phone.getString(phoneIndex);
                String strName = phone.getString(nameIndex);
                txtContacts.setText(txtContacts.getText() + "\n" + strName + ": " + strPhone + "\n");

            }

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();

        }


    }


}

