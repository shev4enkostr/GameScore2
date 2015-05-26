package su.shev4enkostr.gamescore2;

import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AbsListView.*;
import java.util.*;

import android.view.View.OnClickListener;

/**
 * Created by stas on 20.05.15.
 */
public class MainListFragment extends ListFragment implements OnClickListener, MultiChoiceModeListener
{
    private Button btnSubmit;

	private ArrayList<Players> data;
    private AppListAdapter adapter;

    private String[] name = new String[] {"Stas", "Oksana", "Igor", "Nadya", "Alex", "Sasha", "Lena", "Radik"};
    private String[] score = new String[] {"10", "35", "75", "95", "40", "-20", "65", "100"};

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        data = new ArrayList<>();

        for (int i = 0; i < name.length; i++)
        {
            data.add(new Players(name[i], Integer.parseInt(score[i])));
        }

        setHasOptionsMenu(true);
		
		adapter = new AppListAdapter(getActivity(), data);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
       	View view = inflater.inflate(R.layout.fragment, null);
		
		btnSubmit = (Button) view.findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(this);
		
		return view;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		getListView().setMultiChoiceModeListener(this);
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.btn_submit)
			Toast.makeText(getActivity(), "MainScreen", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		// TODO: Implement this method
		super.onListItemClick(l, v, position, id);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu_fragment_main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu)
	{
		mode.getMenuInflater().inflate(R.menu.context_menu_fragment, menu);
		mode.setTitle(R.string.action_mode_title);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode p1, Menu p2)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode p1, MenuItem p2)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public void onDestroyActionMode(ActionMode p1)
	{
		// TODO: Implement this method
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked)
	{
		int checkedCount = getListView().getCheckedItemCount();
		
		switch (checkedCount)
		{
			case 0:
				mode.setSubtitle(null);
				break;
			case 1:
				mode.setSubtitle(R.string.action_mode_subtitle_one_player);
				break;
			default:
				mode.setSubtitle(checkedCount + " " + getResources().getString(R.string.action_mode_subtitle_more_players));
				break;
		}
	}
}
