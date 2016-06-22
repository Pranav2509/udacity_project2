package pranav.project2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Pranav on 11/06/16.
 */
public class MoviesGridViewAdapter extends BaseAdapter {

    private static final String TAG = "MoviesGridViewAdapter";
    ArrayList<MovieDescriptionDomain> mMoviesList;
    Context mContext;



    public MoviesGridViewAdapter(ArrayList<MovieDescriptionDomain> moviesList, Context context){

        mMoviesList = moviesList;
        mContext = context;

    }


    @Override
    public int getCount() {
        return mMoviesList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMoviesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;


        gridView = inflater.inflate(R.layout.movie_grid_single, null);
        ImageView movie_poster = (ImageView) gridView.findViewById(R.id.movie_poster);

        Picasso.with(mContext).load(ConstantVariablesAndFunctions.base_url_342+mMoviesList.get(position).getPoster_path()).into(movie_poster);

        Log.i(TAG, "Image Urls : " + ConstantVariablesAndFunctions.base_url_342+mMoviesList.get(position).getPoster_path());


        return gridView;
    }
}
