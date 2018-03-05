package net.yeoubi.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.yeoubi.paginator.PagingRecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PagingRecyclerView.OnPaginateListener {

    private PagingRecyclerView recyclerView;
    private ExampleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRecyclerView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recyclerView.unbind();
    }

    @Override
    public void onPaginate(int page) {
        recyclerView.postDelayed(() -> {
            ArrayList<String> items = new ArrayList<>();
            for (int i = (page - 1) * 20; i < page * 20; i++) {
                items.add("Item #" + (i + 1));
            }
            adapter.addAll(items);
            adapter.notifyDataSetChanged();
        }, 2000);
    }

    private void setRecyclerView() {
        adapter = new ExampleAdapter();
        recyclerView = findViewById(R.id.list_item);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnPaginateListener(this);
    }
}
