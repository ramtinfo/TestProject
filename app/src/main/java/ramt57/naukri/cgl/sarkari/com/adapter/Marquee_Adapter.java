package ramt57.naukri.cgl.sarkari.com.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ramt57.naukri.cgl.sarkari.com.DetailListActivity;
import ramt57.naukri.cgl.sarkari.com.R;
import ramt57.naukri.cgl.sarkari.com.pojo.LinkPojo;

import static ramt57.naukri.cgl.sarkari.com.DetailListActivity.dictionaryWords;
import static ramt57.naukri.cgl.sarkari.com.DetailListActivity.filteredList;
import static ramt57.naukri.cgl.sarkari.com.DetailListActivity.marquee_adapter;


public class Marquee_Adapter  extends RecyclerView.Adapter<Marquee_Adapter.Marquee_viewholder> implements Filterable{
    private static ArrayList<LinkPojo> titles=new ArrayList<>();
    private static MarqueeClick listner;
    private CustomFilter mFilter;
    public Marquee_Adapter(ArrayList<LinkPojo> temp){
        titles=temp;
        mFilter = new CustomFilter(Marquee_Adapter.this);
    }
    @NonNull
    @Override
    public Marquee_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_dashboard,parent,false);
        return new Marquee_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Marquee_viewholder holder, int position) {
        holder.title.setText(titles.get(position).getTitle()+"");
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
    @Override
    public Filter getFilter() {
        return mFilter;
    }
    public static class Marquee_viewholder extends RecyclerView.ViewHolder{
        TextView title;
        public Marquee_viewholder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(listner!=null){
                       listner.onClick(titles.get(getAdapterPosition()).getLink()+"");
                   }
                }
            });
        }
    }
    public void setMarqueeclicklistner(MarqueeClick hotClick){
        listner=hotClick;
    }
    public interface MarqueeClick{
        void onClick(String URl);
    }
    public class CustomFilter extends Filter {
        private CustomFilter(Marquee_Adapter mAdapter) {
            super();
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           filteredList.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                filteredList.addAll(dictionaryWords);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final LinkPojo mWords : dictionaryWords) {
                    if (mWords.getTitle().toLowerCase().startsWith(filterPattern)) {
                        filteredList.add(mWords);
                    }
                }
            }
            System.out.println("Count Number " + filteredList.size());
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            marquee_adapter.notifyDataSetChanged();
        }
    }
}
