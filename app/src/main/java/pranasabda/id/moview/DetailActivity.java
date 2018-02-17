package pranasabda.id.moview;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private TextView textTitle;
    private TextView textDescription;
    private ImageView imageMovie;
    private TextView textVote;
    private TextView textYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textTitle = (TextView) findViewById(R.id.detail_text_title);
        textDescription = (TextView) findViewById(R.id.detail_text_desc);
        imageMovie = (ImageView) findViewById(R.id.detail_image_movie);
        imageMovie.setScaleType(ImageView.ScaleType.CENTER_CROP);
        textVote = (TextView) findViewById(R.id.detail_text_vote);
        textYear = (TextView) findViewById(R.id.detail_text_year);

        //Value in getStringExtra "name" must exactly same with intent.putExtra "name" in RecyclerViewHolder
        String backdrop = getIntent().getStringExtra("Backdrop");

        //Value in getStringExtra "name" must exactly same with intent.putExtra "name" in RecyclerViewHolder
        final String title = getIntent().getStringExtra("Title");
        final String description = getIntent().getStringExtra("Description");
        final String year = getIntent().getStringExtra("Year");
        final String vote = getIntent().getStringExtra("Vote");
        String id_movie = getIntent().getStringExtra("Id");

        //placeholder Image is Show when there is no Image from the API
        Picasso.with(this).load(backdrop).placeholder(R.drawable.banner).into(imageMovie);

        textTitle.setText(title);
        textDescription.setText(description);
        textYear.setText("Release date : " + year);
        textVote.setText("Rating : " + vote + " / 10");

        //Floting Button For Share
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,"Movie Title : "+title +" | "+" Release Date : "+ year +  " | " + "Rating : "+vote+" / 10  |"+" Description : "+description);
                startActivity(Intent.createChooser(shareIntent,"Share Via : "));

            }
        });

        //Create Back Arrow on Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Moview : "+title);
    }

    //Create onBackPressed for Back
    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return true;
    }

}
