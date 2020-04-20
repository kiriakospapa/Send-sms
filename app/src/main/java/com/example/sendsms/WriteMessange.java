package com.example.sendsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class WriteMessange extends AppCompatActivity {

    public TextView textView;

    public EditText editText;

    Intent intent;

    Intent intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_messange);

        textView = findViewById(R.id.text);

        editText = findViewById(R.id.Edittext);

        intent = new Intent(this, messagesActivity.class);

        intent2 = new Intent(this, messagesActivity.class);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int numberofletters = editText.getText().length();
                textView.setText(String.valueOf(numberofletters) + "/180");
                if (numberofletters == 180) {
                    Toast.makeText(WriteMessange.this, "You passed the 180 charachters", Toast.LENGTH_LONG).show();

                } else if (numberofletters <= 180) {

                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.actionbarforthethirdactivity, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (editText.getText().length() == 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("You didn't write something")
                    .setTitle("Are you sure you want to continue?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(intent);


                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {



                        }
                    });

            builder.show();
        } else {

            intent.putExtra("Messange", editText.getText().toString());

            startActivity(intent);



        }

        return super.onOptionsItemSelected(item);
    }


        @Override
        protected void onRestart () {
            editText.setText(null);
            super.onRestart();
        }



}
