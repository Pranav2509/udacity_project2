package pranav.project2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pranav on 18/06/16.
 */
public class ReviewsAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<ReviewsDomain> reviewsDomainsList;
    LayoutInflater inflater;
    String TAG = "ReviewsAdapter";

    public ReviewsAdapter(Context context, ArrayList<ReviewsDomain> reviewsDomains){

        this.mContext = context;
        this.reviewsDomainsList = reviewsDomains;
        inflater = LayoutInflater.from(context);

        Log.i(TAG, "ReviewsAdapter contructore called");

    }

    @Override
    public int getCount() {
        return reviewsDomainsList.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewsDomainsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = inflater.inflate(R.layout.reviews_layout_single, null);

        Log.i(TAG, "ReviewsAdapter getView called" + reviewsDomainsList.get(position).getAuthor());

        TextView author_tv = (TextView) v.findViewById(R.id.author_tv);
        TextView content_tv = (TextView) v.findViewById(R.id.content_tv);

        author_tv.setText(reviewsDomainsList.get(position).getAuthor());
        content_tv.setText(reviewsDomainsList.get(position).getContent());

        return v;
    }
}
