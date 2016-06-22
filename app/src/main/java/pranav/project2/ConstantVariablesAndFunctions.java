package pranav.project2;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Pranav on 18/06/16.
 */
public class ConstantVariablesAndFunctions {

    public static String base_url = "http://image.tmdb.org/t/p/w185/";
    public static String base_url_342 = "http://image.tmdb.org/t/p/w342";
    public static String api_base_url = "https://api.themoviedb.org/3/movie/";
    public  static String api_key="********************";

    public static int setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();

            Log.i("Height : ","totalHeight : " + totalHeight);
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();

        return totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

    }


}
