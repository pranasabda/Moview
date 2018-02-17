package pranasabda.id.moview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pranasabda.id.moview.ItemObject;
import pranasabda.id.moview.R;
import pranasabda.id.moview.holder.RecyclerViewHolders;

/**
 * Created by prana on 15/02/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    public List<ItemObject.Results> itemList;
    public Context context;

    //??the Parameter constructor (Context,List<>) Position must be in same order such as in MainActivity / reverse
    public RecyclerViewAdapter(Context context, List<ItemObject.Results> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate? view itemlist
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist, null);
        RecyclerViewHolders recyclerViewHolders = new RecyclerViewHolders(layoutView);
        return recyclerViewHolders;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        //ItemObject.Result item = itemlist.get(position);

        //Using Picaso Dependencies Library for image processing.
        //placeholder Image is Show when there is no Image from the API
        Picasso.with(context).load
                ("https://image.tmdb.org/t/p/w185"+itemList.get(position).poster_path)
                .placeholder(R.drawable.icon_moview)
                .into(holder.image);

        holder.original_title.setText(itemList.get(position).original_title);
        holder.overview = itemList.get(position).overview;
        holder.year = itemList.get(position).release_date;
        holder.backdrop = "https://image.tmdb.org/t/p/w780" + itemList.get(position).backdrop_path;
        holder.vote = itemList.get(position).vote_average;
        holder.id_movie = itemList.get(position).id;
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

//    public void setFilter(List<ItemObject.Results> countryModels) {
//        itemList = new ArrayList<>();
//        itemList.addAll(countryModels);
//        notifyDataSetChanged();
//    }
}
