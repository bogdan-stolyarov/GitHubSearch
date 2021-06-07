package com.softteco.searchGitHub.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.softteco.searchGitHub.model.Root;
import com.softteco.searchGitHub.repository.ItemRepository;

public class ItemViewModel extends AndroidViewModel {

    private final ItemRepository itemRepository = new ItemRepository();
    public final LiveData<Root> itemsResponseLiveData = itemRepository.getItemsResponseLiveData();
    public final LiveData<Boolean> isLoading = itemRepository.isLoading();


    public ItemViewModel(@NonNull Application application) {
        super(application);
    }

    public void searchItems(String q, int page, int resultsPerPage) {
        itemRepository.fetchData(q, page, resultsPerPage);
    }

}

