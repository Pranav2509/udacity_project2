package pranav.project2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pranav on 18/06/16.
 */
public class VideosAdapter extends BaseAdapter{

    Context mContext;
    ArrayList<VideosDomain> videosDomainArrayList;
    LayoutInflater inflater;
    String TAG = "VideosAdapter";
    Activity activity;

    public VideosAdapter(Context context, ArrayList<VideosDomain> videosDomainArrayList, Activity activity) {

        mContext = context;
        this.videosDomainArrayList = videosDomainArrayList;
        inflater = LayoutInflater.from(context);
        this.activity = activity;
        Log.i(TAG, "VideosAdapter Constructor Called");

    }

    @Override
    public int getCount() {
        return videosDomainArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return videosDomainArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = inflater.inflate(R.layout.videos_layout_single, null);

        Log.i(TAG, "VideosAdapter getView Called" + videosDomainArrayList.get(position).getKey());

        TextView small_desc_tv = (TextView) v.findViewById(R.id.small_desc_tv);
        LinearLayout base_ll = (LinearLayout) v.findViewById(R.id.base_ll);

        small_desc_tv.setText(videosDomainArrayList.get(position).getName());
        base_ll.setTag(videosDomainArrayList.get(position).getKey());

        base_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + v.getTag().toString())));

            }
        });

        return v;
    }
}
