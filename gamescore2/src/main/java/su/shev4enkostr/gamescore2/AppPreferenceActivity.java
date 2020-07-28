package su.shev4enkostr.gamescore2;

import android.app.*;
import android.os.*;
import android.preference.*;
import android.content.SharedPreferences.*;
import android.content.*;
import android.util.Log;

public class AppPreferenceActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.d("gamescore2", "onCreate() (Activity)");
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new AppPreferenceFragment()).commit();
	}

	/*@Override
	protected void onPause()
	{
		Log.d("gamescore2", "onPause() (Activity)");
		super.onPause();
	}

	@Override
	protected void onStop()
	{
		Log.d("gamescore2", "onStop() (Activity)");
		super.onStop();
	}

	@Override
	protected void onDestroy()
	{
		Log.d("gamescore2", "onDestroy() (Activity)");
		super.onDestroy();
	}

	@Override
	protected void onStart()
	{
		Log.d("gamescore2", "onStart() (Activity)");
		super.onStart();
	}

	@Override
	protected void onResume()
	{
		Log.d("gamescore2", "onResume() (Activity)");
		super.onResume();
	}*/

	public static class AppPreferenceFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener
	{
		private SharedPreferences sp;
		private CheckBoxPreference checkBoxBridge;
		private EditTextPreference editTextProgressBarMax;
		private AppDialogPreferenceSeekBar appDialogSeekBar;
		private int maxScore;
		
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			//Log.d("gamescore2", "onCreate() (AppPreferenceFragment)");
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.layout.preference);

			sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
			sp.registerOnSharedPreferenceChangeListener(this);
			
			appDialogSeekBar = (AppDialogPreferenceSeekBar) findPreference(getString(R.string.pref_dialog_seek_key));
			editTextProgressBarMax = (EditTextPreference) findPreference(getString(R.string.pref_ed_tx_pr_bar_max_key));
			checkBoxBridge = (CheckBoxPreference) findPreference(getString(R.string.pref_ch_box_bridge_key));
			
			updateDialogSeekBarSummary();
			updateCheckBoxBridgeSummary();
		}

		@Override
		public void onDestroy()
		{
			//Log.d("gamescore2", "onDestroy() (AppPreferenceFragment)");
			sp.unregisterOnSharedPreferenceChangeListener(this);
			super.onDestroy();
		}

		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
		{
			//Log.d("gamescore2", "if (appDialogSeekBar.equals(findPreference(key)))");
			if (appDialogSeekBar.equals(findPreference(key)))
				updateDialogSeekBarSummary();

			if (editTextProgressBarMax.equals(findPreference(key)))
				updateCheckBoxBridgeSummary();
		}
		
		private void updateDialogSeekBarSummary()
		{
			//Log.d("gamescore2", "updateDialogSeekBarSummary()");
			int numberOfPlayers = (sp.getInt(getString(R.string.pref_dialog_seek_key), 6)); //+ AppDialogPreferenceSeekBar.getMinValue());
			appDialogSeekBar.setSummary(getString(R.string.pref_dialog_seek_summary) + " " + numberOfPlayers);
		}

		private void updateCheckBoxBridgeSummary()
		{
			maxScore = (Integer.parseInt(sp.getString(getString(R.string.pref_ed_tx_pr_bar_max_key), "100")) - 5);
			checkBoxBridge.setSummary(getString(R.string.pref_ch_box_bridge_summary_start) + " " +
					maxScore + " " + getString(R.string.pref_ch_box_bridge_summary_end));
		}

		/*@Override
		public void onAttach(Activity activity)
		{
			Log.d("gamescore2", "onAttach() (AppPreferenceFragment)");
			super.onAttach(activity);
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState)
		{
			Log.d("gamescore2", "onActivityCreated() (AppPreferenceFragment)");
			super.onActivityCreated(savedInstanceState);
		}

		@Override
		public void onStart()
		{
			Log.d("gamescore2", "onStart() (AppPreferenceFragment)");
			super.onStart();
		}

		@Override
		public void onResume()
		{
			Log.d("gamescore2", "onResume() (AppPreferenceFragment)");
			super.onResume();
		}

		@Override
		public void onPause()
		{
			Log.d("gamescore2", "onPause() (AppPreferenceFragment)");
			super.onPause();
		}

		@Override
		public void onStop()
		{
			Log.d("gamescore2", "onStop() (AppPreferenceFragment)");
			super.onStop();
		}

		@Override
		public void onDetach()
		{
			Log.d("gamescore2", "onDetach() (AppPreferenceFragment)");
			super.onDetach();
		}*/
	}
}
