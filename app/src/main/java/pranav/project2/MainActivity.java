package pranav.project2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {


    boolean mTwoPane = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.details_fragment_fl)!=null){
            mTwoPane = true;
        }else{
            mTwoPane = false;
        }


        if(mTwoPane){

            MovieListFragment movieListFragment = new MovieListFragment();
            getFragmentManager().beginTransaction().add(R.id.items_fl,
                    movieListFragment).commit();

            /*DetailsFragment detailsFragment = new DetailsFragment();
            getFragmentManager().beginTransaction().add(R.id.details_fragment_fl,
                    detailsFragment).commit();*/

        }else{
            if(getFragmentManager().getBackStackEntryCount() == 0) {
                MovieListFragment movieListFragment = new MovieListFragment();
                getFragmentManager().beginTransaction().add(R.id.fragment_container_fl,
                        movieListFragment).commit();
            }
        }


    }
    public void showDetailsFragment(MovieParcelable movie){

        if(mTwoPane){

            DetailsFragment detailsFragment = new DetailsFragment();
            Bundle arguementsBundle = new Bundle();
            arguementsBundle.putParcelable("singleMovie", movie);
            detailsFragment.setArguments(arguementsBundle);
            getFragmentManager().beginTransaction().replace(R.id.details_fragment_fl,
                    detailsFragment).commit();

        }else{
            DetailsFragment detailsFragment = new DetailsFragment();
            Bundle arguementsBundle = new Bundle();
            arguementsBundle.putParcelable("singleMovie", movie);
            detailsFragment.setArguments(arguementsBundle);
            getFragmentManager().beginTransaction().add(R.id.fragment_container_fl, detailsFragment).addToBackStack(null).commit();

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movie_review_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.setting_menu :
                Intent in = new Intent(this, SettingsPreferences.class);
                startActivity(in);


                return true;

            case R.id.refresh_1 :
                //performWebCall();
                recreate();


                return true;

            default: return super.onOptionsItemSelected(item);

        }


    }

}
