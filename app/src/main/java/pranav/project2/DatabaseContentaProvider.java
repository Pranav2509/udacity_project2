package pranav.project2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Pranav on 19/06/16.
 */
public class DatabaseContentaProvider extends ContentProvider {

    private static final int ALL_MOVIES = 1;
    private static final int SINGLE_MOVIE_DETAILS = 2;

    static final String PROVIDER_NAME = "pranav.project2";
    static final String MOVIES_CONTENT = "content://" + PROVIDER_NAME + "/movies";
    static final String TRAILER_CONTENT = "content://" + PROVIDER_NAME + "/trailers";
    static final String REVIEWS_CONTENT = "content://" + PROVIDER_NAME + "/reviews";
    static final String FAVOURITE_CONTENT = "content://" + PROVIDER_NAME + "/favourite";

    static final Uri MOVIES_URI = Uri.parse(MOVIES_CONTENT);
    static final Uri TRAILER_URI = Uri.parse(TRAILER_CONTENT);
    static final Uri REVIEWS_URI = Uri.parse(REVIEWS_CONTENT);
    static final Uri FAVOURITE_URI = Uri.parse(FAVOURITE_CONTENT);
    static final int MOVIES_CODE = 1;
    static final int MOVIE_SINGLE_CODE = 5;
    static final int TRAILER_CODE = 2;
    static final int REVIEWS_CODE = 3;
    static final int FAVOURITE_CODE = 4;

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(PROVIDER_NAME, "movies", MOVIES_CODE);
        uriMatcher.addURI(PROVIDER_NAME, "movies/*", MOVIE_SINGLE_CODE);
        uriMatcher.addURI(PROVIDER_NAME, "trailers", TRAILER_CODE);
        uriMatcher.addURI(PROVIDER_NAME, "reviews", REVIEWS_CODE);
        uriMatcher.addURI(PROVIDER_NAME, "favourite", FAVOURITE_CODE);
    }

    Context mContext;
    MovieDatabaseSQLiteHelper movieDatabaseSQLiteHelper;
    SQLiteDatabase db;
    private String TAG = "DatabaseContentProvider";

    @Override
    public boolean onCreate() {

        mContext = getContext();
        movieDatabaseSQLiteHelper = new MovieDatabaseSQLiteHelper(mContext);

        db = movieDatabaseSQLiteHelper.getWritableDatabase();
        return db == null ? false : true ;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor = null;

        switch (uriMatcher.match(uri)){
            case MOVIES_CODE :
                cursor = db.query(DatabaseContractDetails.MOVIE_DETAILS_TABLE,
                    null, selection, selectionArgs, null, null, sortOrder);
                break;

            case TRAILER_CODE :
                cursor = db.query(DatabaseContractDetails.TRAILER_DETAILS_TABLE,
                        null, selection, selectionArgs, null, null, sortOrder);
                break;

            case REVIEWS_CODE :
                cursor = db.query(DatabaseContractDetails.REVIEWS_DETAILS_TABLE,
                        null, selection, selectionArgs, null, null, sortOrder);
                break;

            case FAVOURITE_CODE :
                cursor = db.query(DatabaseContractDetails.FAVOURITE_MOVIES_TABLE,
                        null, selection, selectionArgs, null, null, sortOrder);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)){
            case ALL_MOVIES:

                break;

            case SINGLE_MOVIE_DETAILS:

                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = movieDatabaseSQLiteHelper.getWritableDatabase();
        long id = 0L;

        Log.i(TAG, "insert : " + values.toString());

        switch (uriMatcher.match(uri)) {
            case MOVIES_CODE:
                id = db.insert(DatabaseContractDetails.MOVIE_DETAILS_TABLE, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(MOVIES_URI + "/" + id);

            case TRAILER_CODE:
                id = db.insert(DatabaseContractDetails.TRAILER_DETAILS_TABLE, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(MOVIES_URI + "/" + id);

            case REVIEWS_CODE:
                id = db.insert(DatabaseContractDetails.REVIEWS_DETAILS_TABLE, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(MOVIES_URI + "/" + id);

            case FAVOURITE_CODE:
                id = db.insert(DatabaseContractDetails.FAVOURITE_MOVIES_TABLE, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(MOVIES_URI + "/" + id);
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        /*long id = db.insert(DatabaseContractDetails.MOVIE_DETAILS_TABLE, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(MOVIES_URI + "/" + id);*/
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
