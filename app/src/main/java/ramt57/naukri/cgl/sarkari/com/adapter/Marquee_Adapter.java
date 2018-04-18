package ramt57.naukri.cgl.sarkari.com.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ramt57.naukri.cgl.sarkari.com.R;


public class Marquee_Adapter  extends RecyclerView.Adapter<Marquee_Adapter.Marquee_viewholder>{
    ArrayList<String> titles=new ArrayList<>();
    public Marquee_Adapter(ArrayList<String> temp){
        titles=temp;
    }
    @NonNull
    @Override
    public Marquee_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_dashboard,parent,false);
        return new Marquee_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Marquee_viewholder holder, int position) {
        holder.title.setText(titles.get(position)+"");
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public static class Marquee_viewholder extends RecyclerView.ViewHolder{
        TextView title;
        public Marquee_viewholder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "welcome to my world", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
