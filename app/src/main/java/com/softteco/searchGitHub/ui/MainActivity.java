package com.softteco.searchGitHub.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.softteco.searchGitHub.R;
import com.softteco.searchGitHub.model.Item;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemViewModel itemViewModel;
    private TextInputEditText textInputEditText;
    private LinearLayoutManager layoutManager;
    private TextView textView;
    private String q;
    private ProgressBar progressBar;
    private int resultTotalCount;

    private int page = 1;
    private final int resultsPerPage = 50;
    private final ArrayList<Item> items = new ArrayList<>();
    private final ItemAdapter adapter = new ItemAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.result_count_text);
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        itemViewModel.isLoading.observe(this, isVisible -> {
            int visibility = GONE;
            if (isVisible) visibility = VISIBLE;
            progressBar.setVisibility(visibility);
        });
        itemViewModel.itemsResponseLiveData.observe(this, responseObject -> {
            if (responseObject != null) {
                setResultTotalCount(responseObject.getTotalCount());
                items.addAll(responseObject.getItems());
                adapter.setItems(items);
            } else {
                Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();
            }
        });

        pagesPagination();

        textInputEditText = findViewById(R.id.et_search);
        findViewById(R.id.iv_search).setOnClickListener(view -> {
            if (textInputEditText.getText() != null) {
                items.clear();
                adapter.setItems(items);
                textView.setVisibility(View.INVISIBLE);
                q = textInputEditText.getText().toString();
                itemViewModel.searchItems(q, page, resultsPerPage);
            }
        });
    }

    private void setResultTotalCount(int count) {
        resultTotalCount = count;
        String result = String.valueOf(count);
        String totalResults = getString(R.string.total_count, result);
        textView.setText(totalResults);
        textView.setVisibility(VISIBLE);
    }

    private void pagesPagination() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() ==
                            adapter.getItemCount() - 1) {
                        if (items.size() < resultTotalCount) {
                            page++;
                            itemViewModel.searchItems(q, page, resultsPerPage);
                        }
                    }
                }
            }
        });
    }

}