package upm.cabd.mssde_pas;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import upm.cabd.mssde_pas.DatosAbiertosParques.Graph;

public class ParkListAdapter extends RecyclerView.Adapter<ParkListAdapter.ParkViewHolder> {

    class ParkViewHolder extends RecyclerView.ViewHolder{
        private final TextView parkTitleTextView;
        public ParkViewHolder(@NonNull View itemView) {
            super(itemView);
            parkTitleTextView = itemView.findViewById(R.id.parkTitleTextView);
        }
    }

    private final LayoutInflater layoutInflater;
    List<Graph> parkList;

    public ParkListAdapter (Context context){
        layoutInflater = LayoutInflater.from(context);
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
