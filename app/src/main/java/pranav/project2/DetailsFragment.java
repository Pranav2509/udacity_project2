package pranav.project2;

import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Pranav on 17/06/16.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener{

    LinearLayout base_ll;

    View mV;
    Context mContext;
    private ImageView movie_thumbnail;
    private TextView movie_title_tv, overview_tv, release_date_tv, vote_averagae_tv;
    private String TAG = "DetailsFragment";
    ListView movie_trailer_lv, movie_reviews_lv;
    private int videoHeight = 0;
    LinearLayout details_base_ll, details_ll;
    Button show_reviews_button;
    Button mark_as_favourite_button;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        base_ll = (LinearLayout) inflater.inflate(R.layout.movie_detailed_layout, container, false);

        return base_ll;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mV = getView();
        mContext = getActivity().getBaseContext();

        movie_thumbnail = (ImageView) mV.findViewById(R.id.movie_thumbnail);
        movie_title_tv = (TextView) mV.findViewById(R.id.movie_title_tv);
        overview_tv = (TextView) mV.findViewById(R.id.overview_tv);
        release_date_tv = (TextView) mV.findViewById(R.id.release_date_tv);
        vote_averagae_tv = (TextView) mV.findViewById(R.id.vote_averagae_tv);
        movie_trailer_lv = (ListView) mV.findViewById(R.id.movie_trailer_lv);

        details_base_ll = (LinearLayout) mV.findViewById(R.id.details_base_ll);
        details_ll = (LinearLayout) mV.findViewById(R.id.details_ll);
        show_reviews_button = (Button) mV.findViewById(R.id.show_reviews_button);
        mark_as_favourite_button = (Button) mV.findViewById(R.id.mark_as_favourite_button);

        Bundle bundle = getArguments();
        MovieParcelable movie = bundle.getParcelable("singleMovie");

        //Log.i(TAG, movie.overview);
        Log.i(TAG, ConstantVariablesAndFunctions.base_url+movie.movie_poster);

        Picasso.with(mContext).load(ConstantVariablesAndFunctions.base_url+movie.movie_poster).into(movie_thumbnail);
        movie_title_tv.setText(movie.title);
        overview_tv.setText(movie.overview);
        release_date_tv.setText(movie.release_date);
        vote_averagae_tv.setText(movie.vote_average+"/10");
        show_reviews_button.setTag(movie.movie_id);
        show_reviews_button.setOnClickListener(this);
        mark_as_favourite_button.setTag(movie.movie_id);
        mark_as_favourite_button.setOnClickListener(this);

        String selection = DatabaseContractDetails.TRAILER_DETIALS_COLUMN_1+"='"+movie.movie_id+"'";
        Cursor cursor = getActivity().getContentResolver().query(DatabaseContentaProvider.
                TRAILER_URI, null, selection, null, null);

        if(cursor.getCount()>0) {
            while (cursor.moveToNext())
                Log.i(TAG, cursor.getString(1));
        }

        checkMarkFavouriteMovieStatus(movie.movie_id);

        loadMovieTrailers(movie.movie_id);
    }

    private void checkMarkFavouriteMovieStatus(Long movie_id) {
        String selection = DatabaseContractDetails.FAVOURITE_TABLE_COLUMN_1+"='"+movie_id+"'";
        Cursor cursor = getActivity().getContentResolver().query(DatabaseContentaProvider.
                FAVOURITE_URI, null, selection, null, null);

        if(cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                Log.i(TAG, cursor.getLong(0)+"");
                mark_as_favourite_button.setText("Already marked as favourite");
                mark_as_favourite_button.setEnabled(false);
            }
        }

    }

    private void loadMovieTrailers(final Long movie_id) {

        String videos_url = ConstantVariablesAndFunctions.api_base_url+movie_id+"/"+"videos?api_key=" + ConstantVariablesAndFunctions.api_key;
        Log.i(TAG,"videos_url : "+ videos_url);

        StringRequest videoRequest = new StringRequest(Request.Method.GET, videos_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i(TAG,"videos_url : response : "+ response);

                try {
                    ArrayList<VideosDomain> videosDomainArrayList = ResponseParser.parseMovieVideosResponse(response);

                    if(videosDomainArrayList!=null){
                        setVideoAdapter(videosDomainArrayList, movie_id);
                        Log.i(TAG,"videos_url : response : adapter set");

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueueSingleton.getInstance(mContext).addToRequestQueue(videoRequest);

    }

    private void setVideoAdapter(ArrayList<VideosDomain> videosDomainArrayList, Long movie_id) {
        VideosAdapter adapter = new VideosAdapter(mContext, videosDomainArrayList, getActivity());
        movie_trailer_lv.setAdapter(adapter);
        ConstantVariablesAndFunctions.setListViewHeightBasedOnChildren(movie_trailer_lv);
        for(int i=0; i< videosDomainArrayList.size(); i++){
            ContentValues values = new ContentValues();
            values.put(DatabaseContractDetails.TRAILER_DETIALS_COLUMN_1, movie_id);
            values.put(DatabaseContractDetails.TRAILER_DETIALS_COLUMN_2, videosDomainArrayList.get(i).getKey());
            values.put(DatabaseContractDetails.TRAILER_DETIALS_COLUMN_3, videosDomainArrayList.get(i).getName());

            getActivity().getContentResolver().insert(DatabaseContentaProvider.TRAILER_URI, values);
        }

    }


    private void loadMovieReviews(final String movie_id) {

        String reviews_url = ConstantVariablesAndFunctions.api_base_url+movie_id+"/"+"reviews?api_key=" + ConstantVariablesAndFunctions.api_key;
        Log.i(TAG,"reviews_url : "+ reviews_url);

        StringRequest reviewsRequest = new StringRequest(Request.Method.GET, reviews_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i(TAG,"reviews_url : response : "+ response);

                try {
                    ArrayList<ReviewsDomain> reviewsDomainList = ResponseParser.parseMovieReviewsResponse(response);

                    if(reviewsDomainList!=null){
                        ReviewsAdapter adapter = new ReviewsAdapter(mContext, reviewsDomainList);
                        movie_reviews_lv.setAdapter(adapter);
                    }

                    for(int i=0; i< reviewsDomainList.size(); i++){
                        ContentValues values = new ContentValues();
                        values.put(DatabaseContractDetails.REVIEW_DETIALS_COLUMN_1, movie_id);
                        values.put(DatabaseContractDetails.REVIEW_DETIALS_COLUMN_2, reviewsDomainList.get(i).getAuthor());
                        values.put(DatabaseContractDetails.REVIEW_DETIALS_COLUMN_3, reviewsDomainList.get(i).getContent());

                        getActivity().getContentResolver().insert(DatabaseContentaProvider.REVIEWS_URI, values);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueueSingleton.getInstance(mContext).addToRequestQueue(reviewsRequest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_reviews_button :

                showReviewsDialogPopup(v.getTag().toString());
                break;

            case R.id.mark_as_favourite_button :

                ContentValues values = new ContentValues();
                values.put(DatabaseContractDetails.FAVOURITE_TABLE_COLUMN_1, v.getTag().toString());
                getActivity().getContentResolver().insert(DatabaseContentaProvider.FAVOURITE_URI, values);
                Toast.makeText(mContext, "Movie marked as favourite", Toast.LENGTH_LONG).show();
                checkMarkFavouriteMovieStatus(Long.parseLong(v.getTag().toString()));
                break;
        }

    }

    private void showReviewsDialogPopup(String movie_id) {

        Dialog reviewsDialog = new Dialog(getActivity());

        reviewsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reviewsDialog.setContentView(R.layout.reviews_layout);
        movie_reviews_lv  = (ListView) reviewsDialog.findViewById(R.id.movie_reviews_lv);
        loadMovieReviews(movie_id);
        reviewsDialog.setCanceledOnTouchOutside(true);

        reviewsDialog.show();

    }
}
