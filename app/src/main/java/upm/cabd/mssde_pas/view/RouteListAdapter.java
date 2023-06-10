package upm.cabd.mssde_pas.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import upm.cabd.mssde_pas.R;

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.RouteViewHolder>{
    // TODO: Add a way to update data into here and a way to bind a OnClick method from the Activity.
    private Context context;
    private final LayoutInflater layoutInflater;

    public RouteListAdapter (Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerviewmainitem, parent, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        // TODO: Don't hardcode that.
        String string = "Sample Text " + position;
        holder.routeTitleTextView.setText(string);
    }

    @Override
    public int getItemCount() {
        // TODO: Don't hardcode that.
        int size = 10;
        return size;
    }

    static class RouteViewHolder extends RecyclerView.ViewHolder {
        private final CardView routeCardView;
        private final TextView routeTitleTextView;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            routeCardView = itemView.findViewById(R.id.routeCardView);
            routeTitleTextView = itemView.findViewById(R.id.routeTitleTextView);
        }
    }
}
