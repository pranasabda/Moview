package pranasabda.id.moview;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by prana on 15/02/18.
 */

public class ItemObject {
    //SerializedName?
    @SerializedName("results")
    List<Results> results;

    public class Results {
        @SerializedName("poster_path")
        public String poster_path;

        @SerializedName("original_title")
        public String original_title;

        @SerializedName("overview")
        public String overview;

        @SerializedName("release_date")
        public String release_date;

        @SerializedName("backdrop_path")
        public String backdrop_path;

        @SerializedName("vote_average")
        public String vote_average;

        @SerializedName("id")
        public String id;
    }

}
