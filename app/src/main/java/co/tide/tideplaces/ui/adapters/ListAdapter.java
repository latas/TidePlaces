package co.tide.tideplaces.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.tide.tideplaces.R;
import co.tide.tideplaces.data.models.ListItem;

/**
 * Created by Antonis Latas
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {


    final LayoutInflater inflater;
    final List<ListItem> items = new ArrayList<>();

    public ListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(items.get(position).name());
        holder.distance.setText(items.get(position).distance());
        holder.cellParent.setOnClickListener(items.get(position).itemClickListener());
    }

    public void addItems(List<ListItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_text)
        TextView name;
        @BindView(R.id.distance_text)
        TextView distance;
        @BindView(R.id.cell_parent)
        LinearLayout cellParent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
