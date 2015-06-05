package su.shev4enkostr.gamescore2;

import android.content.*;
import android.graphics.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;
import android.preference.*;

/**
 * Created by stas on 20.05.15.
 */
public class AppListAdapter extends BaseAdapter implements OnTouchListener
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
        ViewHolder holder;
		View view = convertView;
		
        if (view == null)
        {
            view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
			holder = new ViewHolder();
			holder.etHolder = (EditText) view.findViewById(R.id.et_enter_score_player);
			view.setTag(holder);
        }
		
		else
			holder = (ViewHolder) view.getTag();

        Players player = (Players) getItem(position);
		
        ((TextView) view.findViewById(R.id.tv_name_player)).setText(player.getName());
        ((TextView) view.findViewById(R.id.tv_score_player)).setText(String.valueOf(player.getScore()));
		
        int i = player.getScore();
        ProgressBar pb = (ProgressBar) view.findViewById(R.id.pr_bar_load);
        pb.setProgress(i);

        if (i < 40)
            pb.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);

        else if (i < 80)
            pb.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

        else
            pb.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
		
		//ViewHolder holder = new ViewHolder();
		//holder.etHolder = et;
			
		holder.etHolder.setOnTouchListener(this);
		view.setOnTouchListener(this);
		view.setTag(holder);
			
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
	
	private static class ViewHolder
	{
		private EditText etHolder;
	}
	
	@Override
	public boolean onTouch(View view, MotionEvent motionEvent)
	{
		if (view instanceof EditText)
		{
			EditText et = (EditText) view;
			et.setFocusable(true);
			et.setFocusableInTouchMode(true);
		}
		else
		{
			ViewHolder holder = (ViewHolder) view.getTag();
			holder.etHolder.setFocusable(false);
			holder.etHolder.setFocusableInTouchMode(false);
		}
		return false;
	}
}
