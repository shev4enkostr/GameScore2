package su.shev4enkostr.gamescore2;

import android.os.*;
import android.support.v4.app.ListFragment;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AbsListView.*;
import java.util.*;
import android.preference.*;
import android.content.*;
import android.app.*;

/**
 * Created by stas on 20.05.15.
 */
public class MainListFragment extends ListFragment implements View.OnClickListener, MultiChoiceModeListener
{
    private Button btnSubmit; // Button for add score
	private EditText etAddPlayer; // EditText for DialogFragment new player add

	private ArrayList<Players> players; // for order to not create new instances of the Players.class always when the Fragment is created
	private ArrayList<Players> data; // for list adapter
    private AppListAdapter adapter; // custom adapter
	
	private SharedPreferences sharedPref;
	private int maxNumberOfPlayers; // max number of players from preferences
	private int numberOfPlayer = 0; // current number of player
	
	private static final String NUMBER_OF_PLAYERS = "preference_dialog";
	private static final String ARGUMENT_SAVE_PLAYERS = "players_main";
	private static final String ARGUMENT_SAVE_NUMBER_OF_PLAYER = "number_of_player";
	private static final String ARGUMENT_SAVE_PLAYER_NAME = "player_name";
	private static final String ARGUMENT_SAVE_PLAYER_SCORE = "player_score";
	private static final int MIN_SEEK_POSITION = 2;
	private static final int MAX_SEEK_POSITION = 20;

	private static final String LOG = "gamescore2";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
		Log.d(LOG, "MainListFragment onCreate()");
		super.onCreate(savedInstanceState);
		
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
		maxNumberOfPlayers = (sharedPref.getInt(NUMBER_OF_PLAYERS, 1)) + MIN_SEEK_POSITION;
		
		setHasOptionsMenu(true);

		data = new ArrayList<>();
		
		if (savedInstanceState == null || ! savedInstanceState.containsKey(ARGUMENT_SAVE_PLAYERS))
		{
			createPlayers();
			loadScore();
		}
		else
			restorePlayers(savedInstanceState);
		
		adapter = new AppListAdapter(getActivity(), data);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
       	Log.d(LOG, "MainListFragment onCreateView()");

		View view = inflater.inflate(R.layout.fragment, null);
		
		btnSubmit = (Button) view.findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(this);
		
		return view;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		Log.d(LOG, "MainListFragment onActivityCreated()");
		super.onActivityCreated(savedInstanceState);
		
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		getListView().setMultiChoiceModeListener(this);
	}

	@Override
	public void onDestroy()
	{
		saveScore();
		super.onDestroy();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.btn_submit)
			addScore();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu_fragment_main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		
		switch (id)
		{
			case R.id.action_add:
				if (numberOfPlayer < maxNumberOfPlayers)
					new AddPlayerDialogFragment().show(getFragmentManager(), "add_dialog");
				else
					Toast.makeText(getActivity(), R.string.toast_max_number_of_players, Toast.LENGTH_SHORT).show();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(ARGUMENT_SAVE_PLAYERS, players);
		outState.putInt(ARGUMENT_SAVE_NUMBER_OF_PLAYER, numberOfPlayer);
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
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item)
	{
		if (item.getItemId() == R.id.action_delete)
			deletePlayers();
		
		mode.finish();
		return true;
	}

	@Override
	public void onDestroyActionMode(ActionMode p1)
	{}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked)
	{
		//getListView().getChildAt(position).setTag(checked);
		data.get(position).setChecked(checked);
		
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
	
	// Create new Players.class objects
	public void createPlayers()
	{
		players = new ArrayList<>();

		for (int i = 0; i < MAX_SEEK_POSITION; i++)
		{
			Players temp = new Players();
			String name = "Player" + " " + String.valueOf(Players.getNextNumberOfPlayer());
			//String name = getResources().getString(R.string.default_name_player) + " " + (i + 1);
			temp.setName(name);
			players.add(temp);
		}
		players.trimToSize();
	}

	// Restore players from savedInstanceState()
	public void restorePlayers(Bundle savedInstanceState)
	{
		players = savedInstanceState.getParcelableArrayList(ARGUMENT_SAVE_PLAYERS);
		numberOfPlayer = savedInstanceState.getInt(ARGUMENT_SAVE_NUMBER_OF_PLAYER);
		
		for (int i = 0; i < numberOfPlayer; i++)
			data.add(i, players.get(i));
	}
	
	// Restore players from SharedPreference (after then app was killed)
	public void restorePlayers(String name, int score, int position)
	{
		players.get(position).setName(name);
		players.get(position).setScore(score);
		data.add(position, players.get(position));
	}
	
	public void addPlayer()
	{
		if (etAddPlayer.getText().length() == 0)
			Toast.makeText(getActivity(), R.string.toast_no_name, Toast.LENGTH_SHORT).show();
		else
		{
			String name = etAddPlayer.getText().toString();
			players.get(numberOfPlayer).setName(name);
			data.add(numberOfPlayer, players.get(numberOfPlayer));
			numberOfPlayer++;
		}
	}
	
	public void addScore()
	{
		for (int i = 0; i < numberOfPlayer; i++)
		{
			View view = getListView().getChildAt(i);
			EditText etScore = (EditText) view.findViewById(R.id.et_enter_score_player);
			
			if (etScore.getText().length() != 0)
				data.get(i).addScore(Integer.parseInt(etScore.getText().toString()));
				
			etScore.setText("");
		}
		adapter.notifyDataSetChanged();
	}
	
	// Before killing app
	public void saveScore()
	{
		sharedPref = getActivity().getSharedPreferences("main_preference", getActivity().MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		
		editor.putInt(ARGUMENT_SAVE_NUMBER_OF_PLAYER, numberOfPlayer);
		editor.apply();
		for (int i = 0; i < numberOfPlayer; i++)
		{
			editor.putString(ARGUMENT_SAVE_PLAYER_NAME + i, data.get(i).getName());
			editor.apply();
			editor.putInt(ARGUMENT_SAVE_PLAYER_SCORE + i, data.get(i).getScore());
			editor.apply();
		}
	}
	
	// After then app was killed
	public void loadScore()
	{
		String name;
		int score;
		
		sharedPref = getActivity().getSharedPreferences("main_preference", getActivity().MODE_PRIVATE);
		
		numberOfPlayer = sharedPref.getInt(ARGUMENT_SAVE_NUMBER_OF_PLAYER, 0);
		for (int i = 0; i < numberOfPlayer; i++)
		{
			name = sharedPref.getString(ARGUMENT_SAVE_PLAYER_NAME + i, "");
			score = sharedPref.getInt(ARGUMENT_SAVE_PLAYER_SCORE + i, 0);
			restorePlayers(name, score, i);
		}
	}
	
	public void deletePlayers()
	{
		int count = 0;
		
		for (int i = 0; i < numberOfPlayer; i++)
		{
			if (data.get(i).isChecked())
			{
				data.remove(i);
				players.remove(i);
				numberOfPlayer--;
				count++;
			}
		}
		for (int i = 0; i < count; i++)
		{
			Players temp = new Players();
			String name = "Player" + " " + String.valueOf(Players.getNextNumberOfPlayer());
			//String name = getResources().getString(R.string.default_name_player) + " " + (i + 1);
			temp.setName(name);
			players.add(temp);
		}
		players.trimToSize();
	}
	
	//Dialog add player class
	class AddPlayerDialogFragment extends DialogFragment implements DialogInterface.OnClickListener, OnFocusChangeListener
	{
		private Dialog dialog;

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
				.setTitle(R.string.add_title)
				.setPositiveButton(R.string.add_btn_ok, this)
				.setNegativeButton(R.string.add_btn_cancel, this)
				.setView(getActivity().getLayoutInflater().inflate(R.layout.add_player_dialog, null));

			dialog = builder.show();
			etAddPlayer = (EditText) dialog.findViewById(R.id.et_add);
			etAddPlayer.setOnFocusChangeListener(this);
			return dialog;
		}

		@Override
		public void onClick(DialogInterface dialogInterface, int id)
		{
			if (id == Dialog.BUTTON_POSITIVE)
				addPlayer();
		}

		@Override
		public void onFocusChange(View view, boolean hasFocus)
		{
			if (hasFocus)
				dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		}
	}
}
