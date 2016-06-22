package pranav.project2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pranav on 19/06/16.
 */
public class MovieDatabaseSQLiteHelper extends SQLiteOpenHelper {



    public MovieDatabaseSQLiteHelper(Context context){

        super(context, DatabaseContractDetails.DATABASE_NAME, null, DatabaseContractDetails.DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //Movie details table create query
        db.execSQL(DatabaseContractDetails.MOVIE_DETAILS_TABLE_CREATE_QUERY);

        //Trailer details table create query
        db.execSQL(DatabaseContractDetails.TRAILER_DETAILS_TABLE_CREATE_QUERY);

        //Reviews details table create query
        db.execSQL(DatabaseContractDetails.REVIEWS_DETAILS_TABLE_CREATE_QUERY);

        //Favourite details table create query
        db.execSQL(DatabaseContractDetails.FAVOURITE_TABLE_CREATE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  DatabaseContractDetails.MOVIE_DETAILS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +  DatabaseContractDetails.TRAILER_DETAILS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +  DatabaseContractDetails.REVIEWS_DETAILS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +  DatabaseContractDetails.FAVOURITE_MOVIES_TABLE);
        onCreate(db);
    }
}
