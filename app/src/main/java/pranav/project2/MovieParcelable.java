package pranav.project2;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Pranav on 11/06/16.
 */
public class MovieParcelable implements Parcelable {

    String title;
    String release_date;
    String movie_poster;
    Double vote_average;
    String overview;
    Long movie_id;
    String TAG = "MovieParcelable";


    public MovieParcelable(String title, String release_date,
                           String movie_poster, Double vote_average, String overview
                            , Long movie_id) {

        this.title = title;
        this.release_date = release_date;
        this.movie_poster = movie_poster;
        this.vote_average = vote_average;
        this.overview = overview;
        this.movie_id = movie_id;
        Log.i(TAG, "overview : " +overview);

    }

    protected MovieParcelable(Parcel in){
        title = in.readString();
        release_date = in.readString();
        movie_poster = in.readString();
        overview = in.readString();
        vote_average = in.readDouble();
        movie_id = in.readLong();

        Log.i(TAG, " MovieParcelable overview : " +overview);

    }

    public static final Creator<MovieParcelable> CREATOR = new Creator<MovieParcelable>() {
        @Override
        public MovieParcelable createFromParcel(Parcel in) {
            return new MovieParcelable(in);
        }

        @Override
        public MovieParcelable[] newArray(int size) {
            return new MovieParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(movie_poster);
        dest.writeString(overview);
        dest.writeDouble(vote_average);
        dest.writeLong(movie_id);
        Log.i(TAG, "writeToParcel overview : " +overview);
    }
}
