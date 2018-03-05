package net.yeoubi.example;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private ArrayList<String> items = new ArrayList<>();

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        if (holder.itemView instanceof TextView) {
            ((TextView) holder.itemView).setText(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addAll(List<String> items) {
        this.items.addAll(items);
    }

    class ExampleViewHolder extends RecyclerView.ViewHolder {

        ExampleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
