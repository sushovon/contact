package com.example.sushovon.contact.tasks;

import android.content.Context;

import com.example.sushovon.contact.http.HttpService;
import com.example.sushovon.contact.http.ServiceGenerator;
import com.example.sushovon.contact.models.Contacts;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rsadmin on 4/1/17.
 */

public class GetContactsTask {
    private HttpService mService;

    public GetContactsTask(String username, String password, Context context) {
        mService = ServiceGenerator.createService(HttpService.class, username, password, context);
    }

    public Observable<Contacts> getContactList(){
        return mService.getContacts().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
