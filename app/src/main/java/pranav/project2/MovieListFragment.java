package pranav.project2;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Pranav on 18/06/16.
 */
public class MovieListFragment extends Fragment {

    LinearLayout fragment_base_ll;
    View mV;
    private GridView movies_gv;

    private String TAG = "MovieListFragment";
    Context mContext;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        fragment_base_ll = (LinearLayout) inflater.inflate(R.layout.list_view_frament_layout, container, false);

        return fragment_base_ll;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mV = getView();
        mContext = getActivity().getBaseContext();
        movies_gv = (GridView) mV.findViewById(R.id.movies_gv);
        performWebCall();

        readFromDatabase();

    }

    private void readFromDatabase() {
        Cursor cursor = getActivity().getContentResolver().query(DatabaseContentaProvider.MOVIES_URI, null, null, null, null);

        while (cursor.moveToNext()){
            Log.i(TAG,"Cursor : " + cursor.getString(1));
        }

    }

    private void performWebCall() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String orderBy = sharedPreferences.getString("orderby","popular");

        if(!orderBy.equals("favourite")) {

            String url = ConstantVariablesAndFunctions.api_base_url + orderBy + "?api_key=" + ConstantVariablesAndFunctions.api_key;

            Log.i(TAG, url);

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.i(TAG, "response : " + response);

                    if (response != "") {

                        try {
                            final ArrayList<MovieDescriptionDomain> movieList = ResponseParser.parseMovieDetails(response);

                            if (movieList != null) {

                                MoviesGridViewAdapter adapter = new MoviesGridViewAdapter(movieList, mContext);
                                movies_gv.setAdapter(adapter);

                                for (int i = 0; i < movieList.size(); i++) {

                                    ContentValues values = new ContentValues();
                                    values.put(DatabaseContractDetails.MOVIE_DETIALS_COLUMN_1, movieList.get(i).getMovie_id());
                                    values.put(DatabaseContractDetails.MOVIE_DETIALS_COLUMN_2, movieList.get(i).getTitle());
                                    values.put(DatabaseContractDetails.MOVIE_DETIALS_COLUMN_3, movieList.get(i).getPoster_path());
                                    values.put(DatabaseContractDetails.MOVIE_DETIALS_COLUMN_4, movieList.get(i).getRelease_date());
                                    values.put(DatabaseContractDetails.MOVIE_DETIALS_COLUMN_5, movieList.get(i).getVote_average());
                                    values.put(DatabaseContractDetails.MOVIE_DETIALS_COLUMN_6, movieList.get(i).getOverview());
                                    getActivity().getContentResolver().insert(DatabaseContentaProvider.MOVIES_URI, values);
                                }

                                setClickListener(movieList);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, "On Error is called");

                }
            });

            RequestQueueSingleton.getInstance(mContext).addToRequestQueue(request);
        }else{

            Cursor favouriteMovieIds = getActivity().getContentResolver().query
                    (DatabaseContentaProvider.FAVOURITE_URI, null, null,null, null);
            if(favouriteMovieIds.getCount()>0){

                String selectionCriteria = "";
                while(favouriteMovieIds.moveToNext()){
                    if(favouriteMovieIds.getPosition() == 0)
                        selectionCriteria = selectionCriteria + "'"+favouriteMovieIds.getString(0)+ "'";

                    selectionCriteria = selectionCriteria + ",'"+favouriteMovieIds.getString(0)+ "'";
                }

                Log.i(TAG, selectionCriteria);

                selectionCriteria = DatabaseContractDetails.MOVIE_DETIALS_COLUMN_1 +" IN ("+selectionCriteria + ")";

                final ArrayList<MovieDescriptionDomain> movieList = new ArrayList<MovieDescriptionDomain>();
                MovieDescriptionDomain movieDescriptionDomain;

                /*values.put(DatabaseContractDetails.MOVIE_DETIALS_COLUMN_1, movieList.get(i).getMovie_id());
                values.put(DatabaseContractDetails.MOVIE_DETIALS_COLUMN_2, movieList.get(i).getTitle());
                values.put(DatabaseContractDetails.MOVIE_DETIALS_COLUMN_3, movieList.get(i).getPoster_path());
                values.put(DatabaseContractDetails.MOVIE_DETIALS_COLUMN_4, movieList.get(i).getRelease_date());
                values.put(DatabaseContractDetails.MOVIE_DETIALS_COLUMN_5, movieList.get(i).getVote_average());
                values.put(DatabaseContractDetails.MOVIE_DETIALS_COLUMN_6, movieList.get(i).getOverview());*/

                Cursor favMovieList = getActivity().getContentResolver().query(DatabaseContentaProvider.MOVIES_URI, null, selectionCriteria, null, null);
                while (favMovieList.moveToNext()){
                    movieDescriptionDomain = new MovieDescriptionDomain();
                    movieDescriptionDomain.setMovie_id(favMovieList.getLong(0));
                    movieDescriptionDomain.setTitle(favMovieList.getString(1));
                    movieDescriptionDomain.setPoster_path(favMovieList.getString(2));
                    movieDescriptionDomain.setRelease_date(favMovieList.getString(3));
                    movieDescriptionDomain.setVote_average(Double.parseDouble(favMovieList.getString(4)));
                    movieDescriptionDomain.setOverview(favMovieList.getString(5));

                    movieList.add(movieDescriptionDomain);
                }

                MoviesGridViewAdapter adapter = new MoviesGridViewAdapter(movieList, mContext);
                movies_gv.setAdapter(adapter);

                setClickListener(movieList);
            }



        }
    }

    private void setClickListener(final ArrayList<MovieDescriptionDomain> movieList) {
        movies_gv.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Intent in = new Intent(getActivity(), MovieDetailActivity.class);
                MovieDescriptionDomain movie = movieList.get(position);
                MovieParcelable movieParcelable = new MovieParcelable(movie.getTitle(),
                        movie.getRelease_date(),
                        movie.getPoster_path(),
                        movie.getVote_average(),
                        movie.getOverview(),
                        movie.getMovie_id());
                //in.putExtra("singleMovie", movieParcelable);
                //startActivity(in);

                ((MainActivity) getActivity()).showDetailsFragment(movieParcelable);

            }
        });

    }
}
