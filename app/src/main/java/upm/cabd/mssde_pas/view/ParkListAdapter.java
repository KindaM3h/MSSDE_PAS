package upm.cabd.mssde_pas.view;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import upm.cabd.mssde_pas.DatosAbiertosParques.Graph;
import upm.cabd.mssde_pas.R;

public class ParkListAdapter extends RecyclerView.Adapter<ParkListAdapter.ParkViewHolder> {

    class ParkViewHolder extends RecyclerView.ViewHolder{
        private final TextView parkTitleTextView;
        private final TextView parkDescriptionTextView;
        private final ProgressBar parkAccessibilityProgressBar;
        public ParkViewHolder(@NonNull View itemView) {
            super(itemView);
            parkTitleTextView = itemView.findViewById(R.id.parkTitleTextView);
            parkDescriptionTextView = itemView.findViewById(R.id.parkDescriptionTextView);
            parkAccessibilityProgressBar = itemView.findViewById(R.id.parkAccessibilityProgressBar);
        }
    }

    private final LayoutInflater layoutInflater;
    List<Graph> parkList;
    private OnParkClick onParkClick;
    private Context context;

    public ParkListAdapter (Context context, OnParkClick onParkClick){
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.onParkClick = onParkClick;
    }

    public void setItems(List<Graph> externalParkList){
        parkList = externalParkList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ParkListAdapter.ParkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerviewmapitem, parent, false);
        return new ParkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkListAdapter.ParkViewHolder holder, int position) {
        if (null != parkList){
            Graph graph = parkList.get(position);
            holder.parkTitleTextView.setText(graph.getTitle());
            holder.parkDescriptionTextView.setText(graph.getOrganization().getOrganizationDesc());
            holder.parkDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());
            holder.parkAccessibilityProgressBar.setProgress(parkList.get(position).getOrganization().getAccesibility());
            holder.itemView.setOnClickListener(view -> {
                Log.i("Adapter", "Description: " + parkList.get(position).getOrganization().getOrganizationDesc());
                onParkClick.onItemClick(parkList.get(position));
                });
        } else {
            holder.parkTitleTextView.setText("Sample Text!");
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (null != parkList){
            size = parkList.size();
        }
        return size;
    }
}
