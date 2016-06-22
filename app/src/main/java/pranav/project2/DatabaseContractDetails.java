package pranav.project2;

/**
 * Created by Pranav on 19/06/16.
 */
public class DatabaseContractDetails {

    static final String DATABASE_NAME = "MoviesDB";
    static final String MOVIE_DETAILS_TABLE = "movies_details";
    static final String TRAILER_DETAILS_TABLE = "trailer_details";
    static final String REVIEWS_DETAILS_TABLE = "review_details";
    static final String FAVOURITE_MOVIES_TABLE = "favourite_table";
    static final int DATABASE_VERSION = 3;

    static final String MOVIE_DETIALS_COLUMN_1 = "movie_id";
    static final String MOVIE_DETIALS_COLUMN_2 = "title";
    static final String MOVIE_DETIALS_COLUMN_3 = "poster_path";
    static final String MOVIE_DETIALS_COLUMN_4 = "release_date";
    static final String MOVIE_DETIALS_COLUMN_5 = "vote_average";
    static final String MOVIE_DETIALS_COLUMN_6 = "overview";

    static final String TRAILER_DETIALS_COLUMN_1 = "movie_id";
    static final String TRAILER_DETIALS_COLUMN_2 = "key";
    static final String TRAILER_DETIALS_COLUMN_3 = "name";

    static final String REVIEW_DETIALS_COLUMN_1 = "movie_id";
    static final String REVIEW_DETIALS_COLUMN_2 = "author";
    static final String REVIEW_DETIALS_COLUMN_3 = "content";

    static final String FAVOURITE_TABLE_COLUMN_1 = "movie_id";

    static final String CREATE_TABLE = "CREATE TABLE";

    static final String MOVIE_DETAILS_TABLE_CREATE_QUERY = CREATE_TABLE+" " + MOVIE_DETAILS_TABLE+
            "(" + MOVIE_DETIALS_COLUMN_1 + " LONG PRIMARY KEY ON CONFLICT REPLACE, "
            + MOVIE_DETIALS_COLUMN_2 + " TEXT NOT NULL, "
            + MOVIE_DETIALS_COLUMN_3 + " TEXT NOT NULL, "
            + MOVIE_DETIALS_COLUMN_4 + " TEXT NOT NULL, "
            + MOVIE_DETIALS_COLUMN_5 + " TEXT NOT NULL, "
            + MOVIE_DETIALS_COLUMN_6 + " TEXT NOT NULL"+");";

    static final String TRAILER_DETAILS_TABLE_CREATE_QUERY = CREATE_TABLE + " "+TRAILER_DETAILS_TABLE+
            "(" + TRAILER_DETIALS_COLUMN_1+ " LONG , "
            + TRAILER_DETIALS_COLUMN_2 + " TEXT NOT NULL, "
            + TRAILER_DETIALS_COLUMN_3 + " TEXT NOT NULL "
            +");";

    static final String REVIEWS_DETAILS_TABLE_CREATE_QUERY = CREATE_TABLE + " "+REVIEWS_DETAILS_TABLE+
            "(" + REVIEW_DETIALS_COLUMN_1 + " LONG , "
            + REVIEW_DETIALS_COLUMN_2 + " TEXT NOT NULL, "
            + REVIEW_DETIALS_COLUMN_3 + " TEXT NOT NULL "
            +");";

    static final String FAVOURITE_TABLE_CREATE_QUERY = CREATE_TABLE + " "+FAVOURITE_MOVIES_TABLE+
            "(" + FAVOURITE_TABLE_COLUMN_1 + " LONG PRIMARY KEY "

            +");";
}
