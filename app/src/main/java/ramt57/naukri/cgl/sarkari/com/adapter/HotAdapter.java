package ramt57.naukri.cgl.sarkari.com.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ramt57.naukri.cgl.sarkari.com.R;
import ramt57.naukri.cgl.sarkari.com.pojo.LinkPojo;

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.HotViewHolder> {
    private static ArrayList<LinkPojo> hotlist=new ArrayList<>();
    private static HOTClick listner;
    public HotAdapter(ArrayList<LinkPojo> temp){
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
        holder.title.setText(hotlist.get(position).getTitle()+"");
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner!=null){
                        listner.onClick(hotlist.get(getAdapterPosition()).getLink()+"");
                    }
                }
            });
        }
    }
    public void setHotclicklistner(HOTClick hotClick){
        listner=hotClick;
    }
    public interface HOTClick{
        void onClick(String URl);
    }
}
