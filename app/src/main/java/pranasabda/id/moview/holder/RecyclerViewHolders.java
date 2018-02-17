package pranasabda.id.moview.holder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pranasabda.id.moview.DetailActivity;
import pranasabda.id.moview.R;

/**
 * Created by prana on 15/02/18.
 */

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView original_title;
    public ImageView image;

    public String overview;
    public String backdrop;
    public String year;
    public String vote;
    public String id_movie;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        image = (ImageView) itemView.findViewById(R.id.list_avatar);
        original_title = (TextView) itemView.findViewById(R.id.list_title);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(itemView.getContext(),DetailActivity.class);
        intent.putExtra("Title", original_title.getText().toString());
        intent.putExtra("Description",overview);
        intent.putExtra("Year", year);
        intent.putExtra("Backdrop", backdrop);
        intent.putExtra("Vote", vote);
        intent.putExtra("Id",id_movie);

        v.getContext().startActivity(intent);
    }
}
