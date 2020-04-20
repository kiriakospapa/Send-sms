package com.example.sendsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.sendsms.MainActivity.REQUEST_READ_CONTACTS;

public class messagesActivity extends AppCompatActivity {

    SmsManager smsManager;

    ArrayList<String> messanges;

    ArrayAdapter arrayAdapter1;

    ListView  listView;

    Intent moveToWrite;

    Intent intent;

    String phone;

    public  void  buttonPressed(View view){
        startActivity(moveToWrite);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        getSupportActionBar().hide();

        listView=findViewById(R.id.list2);

        Set<String> set=new HashSet<String>();

        intent=getIntent();

        smsManager=SmsManager.getDefault();

        messanges=new ArrayList<String>();

        loadDatatomessanges();

        saveData();

        if(intent.getStringExtra("Messange")!=null){
            messanges.add(intent.getStringExtra("Messange"));
        }

        saveData();

        arrayAdapter1=new ArrayAdapter(messagesActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, messanges);

        listView.setAdapter(arrayAdapter1);

        moveToWrite=new Intent(this,WriteMessange.class);

        final Intent intent=getIntent();
        // Log.i("Name",intent.getStringExtra("Name"));
        // Log.i("Phone",intent.getStringExtra("Phone"));
        //smsManager=SmsManager.getDefault();

        phone=intent.getStringExtra("Phone");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



               try{

                  smsManager.sendTextMessage(intent.getStringExtra("Phone"),null,messanges.get(position),null,null);
                  Toast.makeText(messagesActivity.this,"The messange was sent",Toast.LENGTH_SHORT).show();
               }catch (Exception e){

                   Toast.makeText(messagesActivity.this,"The messange was not sent",Toast.LENGTH_SHORT).show();
                   Log.i("The error is:",e.toString());

               }

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

               final int i=position;

                AlertDialog.Builder builder = new AlertDialog.Builder(messagesActivity.this).setMessage("The messange will be deleted")
                        .setTitle("Are you sure you want to delete it?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                messanges.remove(i);
                                arrayAdapter1.notifyDataSetChanged();
                                saveData();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {



                            }
                        });

                builder.show();

                return true;
            }
        });
    }


        private void saveData(){

        SharedPreferences getsharedPreferences=getSharedPreferences("shared preferences",MODE_PRIVATE);

        SharedPreferences.Editor editor=getsharedPreferences.edit();

        Gson gson=new Gson();


        String json=gson.toJson(messanges);

        editor.putString("task list",json);

        editor.apply();
    }

        private void loadDatatomessanges(){
       SharedPreferences getsharedPreferences=getSharedPreferences("shared preferences",MODE_PRIVATE);

       Gson gson=new Gson();

       String json=getsharedPreferences.getString("task list",null);

       Type type=new TypeToken<ArrayList<String>>(){}.getType();

       messanges=gson.fromJson(json,type);


    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this,MainActivity.class);

        startActivity(intent);


    }
}
