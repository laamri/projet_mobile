package com.example.tp20_application_number_book_avec_android_contacts_et_api_distante_via_retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ContactApi {

    @POST("insertContact.php")
    Call<ApiResponse> insertContact(@Body Contact contact);

    @GET("getAllContacts.php")
    Call<List<Contact>> getAllContacts();

    @GET("searchContact.php")
    Call<List<Contact>> searchContacts(@Query("keyword") String keyword);
}
