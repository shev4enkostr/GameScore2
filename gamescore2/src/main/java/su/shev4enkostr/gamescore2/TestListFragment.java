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
public class TestListFragment extends Fragment implements OnClickListener//, MultiChoiceModeListener
{
    private Button btnSubmit;
	private ListView list;

	private ArrayList<Players> data;
    private ArrayAdapter<String> adapter;

    private String[] name = new String[] {"Stas", "Oksana", "Igor", "Nadya", "Alex", "Sasha", "Lena", "Radik"};
    private String[] score = new String[] {"10", "35", "75", "95", "40", "-20", "65", "100"};

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        data = new ArrayList<>();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
       	View view = inflater.inflate(R.layout.test_fragment, null);

		btnSubmit = (Button) view.findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(this);
		
		list = (ListView) view.findViewById(R.id.list);
		
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, name);
        list.setAdapter(adapter);
		
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		list.setMultiChoiceModeListener(new MultiChoiceModeListener()
			{
				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu)
				{
					mode.getMenuInflater().inflate(R.menu.context_menu_fragment, menu);
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
				public void onItemCheckedStateChanged(ActionMode p1, int p2, long p3, boolean p4)
				{
					// TODO: Implement this method
				}
			});
		
		//list = (ListView) view.findViewById(R.id.list_item);
		return view;
    }

	/*@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		
		//getListView().setLongClickable(true);
	}*/

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.btn_submit)
			Toast.makeText(getActivity(), "MainScreen", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu_fragment_main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	/*@Override
	 public boolean onCreateActionMode(ActionMode mode, Menu menu)
	 {
	 mode.getMenuInflater().inflate(R.menu.context_menu_fragment, menu);
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
	 public void onItemCheckedStateChanged(ActionMode p1, int p2, long p3, boolean p4)
	 {
	 // TODO: Implement this method
	 }*/
}
