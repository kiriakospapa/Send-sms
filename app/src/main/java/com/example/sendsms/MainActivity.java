package com.example.sendsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_READ_CONTACTS = 79;
    public ListView list;
    ArrayList phonesArray;
    ArrayList mobileArray;
    ArrayList<Contact> contacts;
    AlertDialog.Builder builder;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        builder = new AlertDialog.Builder(this);

        intent = new Intent(this, messagesActivity.class);

        checkPermission();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.actionbar, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    //functions for the contacts


    private ArrayList getAllContacts() {
        ArrayList<String> nameList = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                nameList.add(name);
                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("HEY", phoneNo);

                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        return nameList;
    }

    private ArrayList getAllNumbers() {
        ArrayList<String> numberList = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("HEY", phoneNo);
                        numberList.add(phoneNo);

                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        return numberList;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        // When request is cancelled, the results array are empty
        if (
                (grantResults.length > 0) &&
                        (grantResults[0] + grantResults[1]
                                == PackageManager.PERMISSION_GRANTED
                        )
        ) {
            // Permissions are granted
            Toast.makeText(this, "Permissions granted.", Toast.LENGTH_SHORT).show();
            mobileArray = getAllContacts();
            phonesArray = getAllNumbers();
            contacts = new ArrayList<>();
            for (int i = 0; i < mobileArray.size(); i++) {
                contacts.add(new Contact(mobileArray.get(i).toString(), phonesArray.get(i).toString()));
                Log.i("COULD", mobileArray.get(i).toString());
            }

            Collections.sort(contacts);

            for (int i = 0; i < mobileArray.size(); i++) {
                Log.i("COULD2", mobileArray.get(i).toString());
            }
            list = findViewById(R.id.list);

            setlistlistener();

        // ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mobileArray);

        ContactAdapter contactAdapter = new ContactAdapter(contacts, MainActivity.this);

        list.setAdapter(contactAdapter);




        return;
    }
}



    protected void checkPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_CONTACTS)
                + ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    MainActivity.this, Manifest.permission.READ_CONTACTS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.SEND_SMS)) {
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Camera, Read Contacts are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                Manifest.permission.SEND_SMS,
                                Manifest.permission.READ_CONTACTS,}, 1
                        );
                    }
                });
                builder.setNeutralButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS,}, 1
                );
            }
        } else {
            // Do something, when permissions are already granted
            Toast.makeText(this, "Permissions already granted", Toast.LENGTH_SHORT).show();
        }
    }



    public void setlistlistener()
    {
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            intent.putExtra("Name", mobileArray.get(position).toString());
                 intent.putExtra("Phone", contacts.get(position).getPhone());
              startActivity(intent);
            }
            });


        return;
    }


}

