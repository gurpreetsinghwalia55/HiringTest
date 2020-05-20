package com.example.sampleproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TestApi {
    @GET("email/")
    Call<List<Email>> getEmails();

    @POST("email/")
    Call<Email> createEmail(@Body Email email);

    @DELETE("/email/{idtableEmail}")
    Call<Void> deleteEmail(@Path("idtableEmail") int idtableEmail);
}
