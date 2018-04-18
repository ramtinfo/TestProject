package ramt57.naukri.cgl.sarkari.com.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ramt57.naukri.cgl.sarkari.com.R;

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.HotViewHolder> {
    ArrayList<String> hotlist=new ArrayList<>();

    public HotAdapter(ArrayList<String> temp){
        hotlist=temp;
    }
    @NonNull
    @Override
    public HotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_layout,parent,false);
        return new HotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotViewHolder holder, int position) {
        holder.title.setText(hotlist.get(position)+"");
    }

    @Override
    public int getItemCount() {
        return hotlist.size();
    }

    public static class HotViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public HotViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
        }
    }
}
