package com.example.sushovon.contact;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sushovon.contact.tasks.GetContactsTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetContactsTask task = new GetContactsTask("", "", this);
        task.getContactList().subscribe(contacts -> {
            if (contacts != null && contacts.getContacts().size() > 0) {
                // Do something

            } else {

            }
        }, e -> {
            e.printStackTrace();

        });

    }
}
