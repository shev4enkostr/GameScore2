package su.shev4enkostr.gamescore2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stas on 16.07.15.
 */
public class DefaultListFragment extends Fragment implements View.OnClickListener
{
    private AbsListView listView;

    private ArrayList<Players> players; // for order to not create new instances of the Players.class always when the Fragment is created
    private ArrayList<Players> data; // for list adapter
    private AppListAdapter adapter; // custom adapter

    private HashMap<Integer, ArrayList<Integer>> historyScore; // for save history score
    private ArrayList<Integer> tempHistoryScoreList; // for put/get score in/from history
    private int undoRedoCount = 0; // count for undo/redo score from history

    private SharedPreferences sharedPref;
    private int maxNumberOfPlayers; // max number of players from preferences

    private boolean isBridge;
    private int resetScoreBridgeGame;

    private static int maxSeekPosition = 20; // max number of players in preferences

    //private static final String NUMBER_OF_PLAYERS = "preference_dialog";
    private static final String ARGUMENT_SAVE_PLAYERS = "players_main";
    private static final String ARGUMENT_SAVE_HISTORY_SCORE = "history_score";
    private static final String ARGUMENT_SAVE_HISTORY_SCORE_SIZE = "history_score_size";
    private static final String ARGUMENT_SAVE_UNDO_REDO_COUNT = "undo_redo_count";
    private static final String ARGUMENT_SAVE_PLAYER_NAME = "player_name";
    private static final String ARGUMENT_SAVE_PLAYER_SCORE = "player_score";
    private static final String ARGUMENT_PREFERENCE = "default_preference";

    private static final String LOG = "gamescore2";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        //Log.d(LOG, "MainListFragment onCreate()");
        super.onCreate(savedInstanceState);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //maxSeekPosition = AppDialogPreferenceSeekBar.getMaxValue();
        maxNumberOfPlayers = sharedPref.getInt(getString(R.string.pref_dialog_seek_key), 6);

        // for playing in Bridge Game
        isBridge = sharedPref.getBoolean(getString(R.string.pref_ch_box_bridge_key), false);
        resetScoreBridgeGame = (Integer.parseInt(sharedPref.getString(getString(R.string.pref_ed_tx_pr_bar_max_key), "100")) - 5);

        setHasOptionsMenu(true);

        data = new ArrayList<>();

        if (savedInstanceState == null || ! savedInstanceState.containsKey(ARGUMENT_SAVE_PLAYERS))
        {
            createPlayers();
            loadScore();
            historyScore = new HashMap<>();
        }
        else
            restorePlayers(savedInstanceState);

        adapter = new AppListAdapter(getActivity(), data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Log.d(LOG, "MainListFragment onCreateView()");
        View view = inflater.inflate(R.layout.fragment, null);
        Button btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        listView = (AbsListView) view.findViewById(R.id.my_list);
        listView.setAdapter(adapter);

        return view;
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
        //if (view.getId() == R.id.btn_submit)
        if (isScoreEntered())
            addScore();
        else
            Toast.makeText(getActivity(), R.string.toast_no_score_entered, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_fragment_default, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu)
    {
        if (undoRedoCount <= 0)
            menu.findItem(R.id.action_undo).setEnabled(false);
        else
            menu.findItem(R.id.action_undo).setEnabled(true);

        if (undoRedoCount >= (historyScore.size() - 1))
            menu.findItem(R.id.action_redo).setEnabled(false);
        else
            menu.findItem(R.id.action_redo).setEnabled(true);

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_undo:
                undoScore();
                break;
            case R.id.action_redo:
                redoScore();
                break;
            case R.id.action_clear:
                clearScore();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARGUMENT_SAVE_PLAYERS, players);
        //outState.putInt(ARGUMENT_SAVE_NUMBER_OF_PLAYER, numberOfPlayer);
        // Save history
        outState.putInt(ARGUMENT_SAVE_UNDO_REDO_COUNT, undoRedoCount);
        outState.putInt(ARGUMENT_SAVE_HISTORY_SCORE_SIZE, historyScore.size());
        for (int i = 0; i < historyScore.size(); i++)
        {
            outState.putIntegerArrayList((ARGUMENT_SAVE_HISTORY_SCORE + i), historyScore.get(i));
        }
    }

    // Create new Players.class objects
    public void createPlayers()
    {
        players = new ArrayList<>();

        for (int i = 0; i < maxSeekPosition; i++)
        {
            Players temp = new Players();
            //String name = "Player" + " " + String.valueOf(Players.getNextNumberOfPlayer());
            String name = getString(R.string.default_name_player) + " " + (i + 1);
            temp.setName(name);
            players.add(temp);
        }
        players.trimToSize();
    }

    // Restore players from savedInstanceState
    public void restorePlayers(Bundle savedInstanceState)
    {
        players = savedInstanceState.getParcelableArrayList(ARGUMENT_SAVE_PLAYERS);
        //numberOfPlayer = savedInstanceState.getInt(ARGUMENT_SAVE_NUMBER_OF_PLAYER);
        // Restore players in list
        for (int i = 0; i < maxNumberOfPlayers; i++)
            data.add(i, players.get(i));
        // Restore history
        undoRedoCount = savedInstanceState.getInt(ARGUMENT_SAVE_UNDO_REDO_COUNT, 0);
        historyScore = new HashMap<>();
        int historyScoreSize = savedInstanceState.getInt(ARGUMENT_SAVE_HISTORY_SCORE_SIZE, 0);
        for (int i = 0; i < historyScoreSize; i++)
        {
            historyScore.put(i, savedInstanceState.getIntegerArrayList(ARGUMENT_SAVE_HISTORY_SCORE + i));
        }
        getActivity().invalidateOptionsMenu();
    }

    // Restore players from SharedPreference (after then app was killed)
    public void restorePlayers(int score, int position)
    {
        //players.get(position).setName(name);
        players.get(position).setScore(score);
        data.add(position, players.get(position));
    }

    public void addScore()
    {
        tempHistoryScoreList = new ArrayList<>();
        for (int i = 0; i < maxNumberOfPlayers; i++)
        {
            // Save score in history
            tempHistoryScoreList.add(data.get(i).getScore());

            // Add score
            View view = listView.getChildAt(i);
            EditText etScore = (EditText) view.findViewById(R.id.et_enter_score_player);

            if (etScore.getText().length() != 0)
                data.get(i).addScore(Integer.parseInt(etScore.getText().toString()));

            // reset score if play in Bridge Game
            if (isBridge)
                resetScoreInBridgeGame(data.get(i));

            etScore.setText("");
        }
        // Update list
        adapter.notifyDataSetChanged();
        // Put history data to HashMap
        historyScore.put(undoRedoCount, tempHistoryScoreList);
        undoRedoCount++;
        // Необходимо для того, чтобы невозможно было выполнить операцию redo после добавления очков
        if (undoRedoCount < historyScore.size())
            undoRedoCount = historyScore.size();
        // Update ActionBar
        getActivity().invalidateOptionsMenu();
    }

    // Checking for entered score
    private boolean isScoreEntered()
    {
        boolean scoreEntered = false;
        for (int i = 0; i < maxNumberOfPlayers && !scoreEntered; i++)
        {
            EditText et = (EditText) listView.getChildAt(i).findViewById(R.id.et_enter_score_player);
            if (et.getText().length() != 0)
                scoreEntered = true;
        }
        return scoreEntered;
    }

    public void clearScore()
    {
        saveScoreInHistory();
        for (int i = 0; i < adapter.getCount(); i++)
        {
            data.get(i).setScore(0);
        }
        adapter.notifyDataSetChanged();
    }

    private void resetScoreInBridgeGame(Players player)
    {
        if (resetScoreBridgeGame == player.getScore())
            player.resetScore();
    }

    public void undoScore()
    {
        // Save last score
        saveScoreInHistory();
        undoRedoCount--;
        // Undo previous score
        undoRedoCount--;
        getScoreFromHistory();
    }

    public void redoScore()
    {
        undoRedoCount++;
        getScoreFromHistory();
    }

    public void saveScoreInHistory()
    {
        tempHistoryScoreList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++)
        {
            tempHistoryScoreList.add(data.get(i).getScore());
        }
        historyScore.put(undoRedoCount, tempHistoryScoreList);
        undoRedoCount++;
        getActivity().invalidateOptionsMenu();
    }

    public void getScoreFromHistory()
    {
        tempHistoryScoreList = new ArrayList<>();
        tempHistoryScoreList = historyScore.get(undoRedoCount);
        for (int i = 0; i < data.size(); i++)
        {
            data.get(i).setScore(tempHistoryScoreList.get(i));
        }
        adapter.notifyDataSetChanged();
        getActivity().invalidateOptionsMenu();
    }

    public void clearHistory()
    {
        historyScore = new HashMap<>();
        undoRedoCount = 0;
        getActivity().invalidateOptionsMenu();
    }

    // Before killing app
    public void saveScore()
    {
        sharedPref = getActivity().getSharedPreferences(ARGUMENT_PREFERENCE, getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //editor.putInt(ARGUMENT_SAVE_NUMBER_OF_PLAYER, numberOfPlayer);
        //editor.apply();
        for (int i = 0; i < maxNumberOfPlayers; i++)
        {
            //editor.putString(ARGUMENT_SAVE_PLAYER_NAME + i, data.get(i).getName());
            //editor.apply();
            editor.putInt(ARGUMENT_SAVE_PLAYER_SCORE + i, data.get(i).getScore());
            editor.apply();
        }
    }

    // After then app was killed
    public void loadScore()
    {
        //String name;
        int score;

        sharedPref = getActivity().getSharedPreferences(ARGUMENT_PREFERENCE, getActivity().MODE_PRIVATE);

        //numberOfPlayer = sharedPref.getInt(ARGUMENT_SAVE_NUMBER_OF_PLAYER, 0);
        for (int i = 0; i < maxNumberOfPlayers; i++)
        {
            //name = sharedPref.getString(ARGUMENT_SAVE_PLAYER_NAME + i, "");
            score = sharedPref.getInt(ARGUMENT_SAVE_PLAYER_SCORE + i, 0);
            restorePlayers(score, i);
        }
    }
}
