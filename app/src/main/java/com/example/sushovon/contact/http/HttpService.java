package com.example.sushovon.contact.http;

import com.example.sushovon.contact.models.Contacts;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface HttpService {

    //@POST("/login")
    //public Observable<LoginResponse> postLogin(@Path("tenantId") String tenantId, @Body LoginRequest loginRequest);

    @GET("contacts/")
    public Observable<Contacts> getContacts();

}
