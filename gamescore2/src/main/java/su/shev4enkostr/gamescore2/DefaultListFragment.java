package su.shev4enkostr.gamescore2;

import android.content.*;
import android.os.*;
import android.preference.*;
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
	
	private ArrayList<Players> players;
	private ArrayList<Players> data;
    private AppListAdapter adapter;

    private SharedPreferences sharedPref;
    private int maxNumberOfPlayers;

	private static int minSeekPosition = 2;
	private static int maxSeekPosition = 20;

    //private static final String NUMBER_OF_PLAYERS = "preference_dialog";
	private static final String ARGUMENT_SAVE_INSTANCE_STATE = "players_default";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
		maxNumberOfPlayers = sharedPref.getInt(getString(R.string.pref_dialog_seek_key), 6);

		data = new ArrayList<>();
		
		if (savedInstanceState == null || ! savedInstanceState.containsKey(ARGUMENT_SAVE_INSTANCE_STATE))
			createPlayers();
		else
			restorePlayers(savedInstanceState);
		
        for (int i = 0; i < maxNumberOfPlayers; i++)
        {
        	data.add(i, players.get(i));
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

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(ARGUMENT_SAVE_INSTANCE_STATE, players);
	}
	
	public void createPlayers()
	{
		players = new ArrayList<>();
		
		for (int i = 0; i < maxSeekPosition; i++)
		{
			Players temp = new Players();
			//String name = "Player" + " " +String.valueOf(temp.getNumberOfPlayer());
			//String name = "Player" + " " + String.valueOf(Players.getNextNumberOfPlayer());
			String name = getResources().getString(R.string.default_name_player) + " " + (i + 1);
			temp.setName(name);
			players.add(temp);
		}
		players.trimToSize();
	}
	
	public void restorePlayers(Bundle savedInstanceState)
	{
		players = savedInstanceState.getParcelableArrayList(ARGUMENT_SAVE_INSTANCE_STATE);
	}
}
