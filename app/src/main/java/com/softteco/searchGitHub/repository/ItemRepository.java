package com.softteco.searchGitHub.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softteco.searchGitHub.model.Root;
import com.softteco.searchGitHub.network.RestClient;
import com.softteco.searchGitHub.network.RestApiBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemRepository {

    private final MutableLiveData<Root> itemsResponseLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final RestClient restClient = new RestApiBuilder().getService();

    public void fetchData(String q, int page, int resultsPerPage) {
        loading.postValue(true);
        Call<Root> callItems = restClient.searchRepos(q, page, resultsPerPage);
        callItems.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                itemsResponseLiveData.postValue(response.body());
                loading.postValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<Root> call, @NonNull Throwable t) {
                loading.postValue(false);
            }
        });
    }

    public LiveData<Root> getItemsResponseLiveData() {
        return itemsResponseLiveData;
    }
    public LiveData<Boolean> isLoading() {
        return loading;
    }

}
