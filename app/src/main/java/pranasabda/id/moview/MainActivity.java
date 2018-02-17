package pranasabda.id.moview;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import pranasabda.id.moview.adapter.RecyclerViewAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private String MovieCategory;
    private final String API_KEY = "08377b1e0eea7a379ef9234add60fabf";

    private ItemObject data;
    private RecyclerViewAdapter adapter;

    //Todo : ProgressDialog
    private ProgressDialog progressDialog;
    //Todo : SwipeRefreshLayout
    private SwipeRefreshLayout refresh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Instance RecyclerView Layout
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        layoutManager = new GridLayoutManager(this,3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
//        requestJsonObject(0);

        //Function SwipeRefreshLayout
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(true);
                requestJsonObject(0);
                adapter.itemList.clear();
                adapter.notifyDataSetChanged();
            }
        });

        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                requestJsonObject(0);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    //Create method Request Json Object with parameter : int i

    private void requestJsonObject(int i){

        //Create Request By Category (by chosen on menu Option)
        if (i==0){
            setTitle("Moview - The Popular Movie");
            MovieCategory = "popular";
        }else if (i==1) {
            setTitle("Moview - Top Rated Movie");
            MovieCategory = "top_rated";
        }else if (i==2) {
            setTitle("Moview - Coming Soon");
            MovieCategory = "upcoming";
        }

        //Access API with LoopJ by set the RequestParams

        //instance RequestParams (class From library Loopj )
        RequestParams params = new RequestParams();
        params.put("api_key",API_KEY);
        String FullURL = "http://api.themoviedb.org/3/movie/"+MovieCategory;

        //Passing by Reference : params & Passing by value : FullURL to method MyParsingGSON
        myParsingGson(params,FullURL);

    }

    private void myParsingGson(RequestParams params, String url){
        //Todo : ProgressDialog
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //Make RESTful webservice call using AsyncHttpClient Object
        //Instance AsyncHttpClient
        AsyncHttpClient client = new AsyncHttpClient();

        //Method for get the response from  webservice
        client.get(url,params, new AsyncHttpResponseHandler(){


            //When the Response returned by REST has Http response code '200'

            @Override
            public void onSuccess(String response) {

                //Todo : Hide ProgressDialog
                progressDialog.hide();

                try{

                    try{
                        //todo:Refresh false
                        refresh.setRefreshing(false);

                        //Instance GsonBuilder for builder the gson
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();

                        //Builder create JSON to Pojo?
                        //POJO set from ItemObject.java
                       data = mGson.fromJson(response,ItemObject.class);

                       //Set data to the Adapter
                        // ??the Parameter (Context, List<>) Position must be in same order such as in RecyclerViewAdapter constructor / reverse
                        adapter = new RecyclerViewAdapter(MainActivity.this, data.results);
                        recyclerView.setAdapter(adapter);

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                refresh.setRefreshing(false);
            }

            // When the response returned by REST has Http response code other than '200'

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {

                refresh.setRefreshing(false);

                //Todo : Hide ProgressDialog
                progressDialog.hide();

                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Something went wrong with your connection.", Toast.LENGTH_LONG).show();
                }
                refresh.setRefreshing(false);
            }
        });


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.option_menu_populer:
                requestJsonObject(0); // Popular
                break;
            case R.id.option_menu_favorit:
                requestJsonObject(1); //Top Rated
                break;
            case R.id.option_menu_upcoming:
                requestJsonObject(2); //Up Coming
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_movie) {
            Intent home = new Intent(this,MainActivity.class);
            startActivity(home);
        } else if (id == R.id.nav_gallery) {
            fragment = new AboutUsFragment();
            setTitle("About Us");
        } else if (id == R.id.nav_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setType("text/plain");
            String shareSubject = "Download Moview App : " ;
            String shareLink = "https://play.google.com/store/apps/details?id=pranasabda.id.moview";
            String shareBody = "Enjoy! :D ";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareSubject+ "  " + shareLink + " | "+ shareBody );

            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(shareIntent,"Share Via :"));
            }

        }

        if(fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
