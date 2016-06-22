package pranav.project2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pranav on 11/06/16.
 */
public class ResponseParser {

    public static ArrayList<MovieDescriptionDomain> parseMovieDetails(String response) throws JSONException{

        ArrayList<MovieDescriptionDomain> movieList = new ArrayList<MovieDescriptionDomain>();

        MovieDescriptionDomain movieDescriptionDomain;

        JSONObject parsedJson = new JSONObject(response);
        if(parsedJson!=null){
            JSONArray result = parsedJson.has("results") ? parsedJson.getJSONArray("results") : null;

            if(result!=null){

                for(int i=0; i<result.length(); i++){

                    JSONObject singleMovie = result.getJSONObject(i);
                    movieDescriptionDomain = new MovieDescriptionDomain();

                    movieDescriptionDomain.setOriginalTitle(singleMovie.has("original_title") ? singleMovie.getString("original_title") : "");
                    movieDescriptionDomain.setOverview(singleMovie.has("overview") ? singleMovie.getString("overview") : "");
                    movieDescriptionDomain.setPoster_path(singleMovie.has("poster_path") ? singleMovie.getString("poster_path") : "");
                    movieDescriptionDomain.setThumbnail_link(singleMovie.has("backdrop_path") ? singleMovie.getString("backdrop_path") : "");
                    movieDescriptionDomain.setRelease_date(singleMovie.has("release_date") ? singleMovie.getString("release_date") : "");
                    movieDescriptionDomain.setVote_average(singleMovie.has("vote_average") ? singleMovie.getDouble("vote_average") : 0.0);
                    movieDescriptionDomain.setTitle(singleMovie.has("title") ? singleMovie.getString("title") : "");
                    movieDescriptionDomain.setMovie_id(singleMovie.has("id") ? singleMovie.getLong("id") : 0L);
                    movieList.add(movieDescriptionDomain);


                }
            }
        }

        return movieList;


    }

    public static ArrayList<ReviewsDomain> parseMovieReviewsResponse(String response) throws JSONException{
        ArrayList<ReviewsDomain> reviewList = new ArrayList<ReviewsDomain>();

        ReviewsDomain reviewsDomain;

        JSONObject parsedJson = new JSONObject(response);
        if(parsedJson!=null){
            JSONArray result = parsedJson.has("results") ? parsedJson.getJSONArray("results") : null;

            if(result!=null){

                for(int i=0; i<result.length(); i++){

                    JSONObject singleReview = result.getJSONObject(i);
                    reviewsDomain = new ReviewsDomain();

                    reviewsDomain.setAuthor(singleReview.has("author") ? singleReview.getString("author") : "");
                    reviewsDomain.setContent(singleReview.has("content") ? singleReview.getString("content") : "");

                    reviewList.add(reviewsDomain);
                }
            }
        }

        return reviewList;
    }


    public static ArrayList<VideosDomain> parseMovieVideosResponse(String response) throws JSONException{
        ArrayList<VideosDomain> videoList = new ArrayList<VideosDomain>();

        VideosDomain videosDomain;

        JSONObject parsedJson = new JSONObject(response);
        if(parsedJson!=null){
            JSONArray result = parsedJson.has("results") ? parsedJson.getJSONArray("results") : null;

            if(result!=null){

                for(int i=0; i<result.length(); i++){

                    JSONObject singleReview = result.getJSONObject(i);
                    videosDomain = new VideosDomain();

                    videosDomain.setKey(singleReview.has("key") ? singleReview.getString("key") : "");
                    videosDomain.setName(singleReview.has("name") ? singleReview.getString("name") : "");

                    videoList.add(videosDomain);
                }
            }
        }

        return videoList;
    }
}
