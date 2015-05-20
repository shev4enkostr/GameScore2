package su.shev4enkostr.gamescore2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by stas on 20.05.15.
 */
public class AppListAdapter extends BaseAdapter
{
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Players> data;

    public AppListAdapter(Context context, ArrayList<Players> data)
    {
        this.context = context;
        this.data = data;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        View view = convertView;
        if (view == null)
        {
            view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
        }

        Players player = (Players) getItem(position);

        ((TextView) view.findViewById(R.id.tv_name_player)).setText(player.getName());
        ((TextView) view.findViewById(R.id.tv_score_player)).setText(String.valueOf(player.getScore()));

        //((ProgressBar) view.findViewById(R.id.pr_bar_load)).getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);

        int i = player.getScore();
        ProgressBar pb = (ProgressBar) view.findViewById(R.id.pr_bar_load);
        pb.setProgress(i);

        if (i < 40)
            pb.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);

        else if (i < 80)
            pb.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

        else
            pb.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        return view;
    }

    @Override
    public Object getItem(int position)
    {
        return data.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }
}
