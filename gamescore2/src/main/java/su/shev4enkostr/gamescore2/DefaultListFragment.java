package su.shev4enkostr.gamescore2;

import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;

/**
 * Created by stas on 20.05.15.
 */
public class DefaultListFragment extends ListFragment implements OnClickListener
{
    private Button btnSubmit;
	
	private ArrayList<Players> data;
    private AppListAdapter adapter;

    private String[] name = new String[] {"Stas", "Oksana", "Igor", "Nadya"};
    private String[] score = new String[] {"10", "35", "75", "95"};

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
	public void onClick(View view)
	{
		if (view.getId() == R.id.btn_submit)
			Toast.makeText(getActivity(), "DefaultScreen", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu_fragment_default, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
}
