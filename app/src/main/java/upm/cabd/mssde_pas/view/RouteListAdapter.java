package upm.cabd.mssde_pas.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import upm.cabd.mssde_pas.R;
import upm.cabd.mssde_pas.localDb.ParkEntity;
import upm.cabd.mssde_pas.localDb.RouteEntity;

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.RouteViewHolder>{
    // TODO: Add a OnClick method from the Activity.
    private Context context;
    private final LayoutInflater layoutInflater;
    private List<RouteEntity> routeList;

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
        holder.routeTitleTextView.setText(routeList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (null != routeList){
            size = routeList.size();
        }
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

    public void setItems(List<RouteEntity> routeEntityList){
        routeList = routeEntityList;
        notifyDataSetChanged();
    }
    public RouteEntity returnCurrentRouteEntity (int position){
        return routeList.get(position);
    }
}
