package com.softteco.searchGitHub.network;

import com.softteco.searchGitHub.model.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestClient {

    @GET("/search/repositories")
    Call<Root> searchRepos(
            @Query("q") String q,
            @Query("page") int page,
            @Query("per_page") int itemsPerPage);

}
