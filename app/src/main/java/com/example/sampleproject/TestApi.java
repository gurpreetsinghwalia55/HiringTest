package com.example.sampleproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TestApi {
    @GET("email/")
    Call<List<Email>> getEmails();
}
