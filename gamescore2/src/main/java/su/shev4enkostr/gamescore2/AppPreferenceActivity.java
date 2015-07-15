package su.shev4enkostr.gamescore2;

import android.app.*;
import android.os.*;
import android.preference.*;
import android.content.SharedPreferences.*;
import android.content.*;

public class AppPreferenceActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new AppPreferenceFragment()).commit();
	}

	public static class AppPreferenceFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener
	{
		private SharedPreferences sp;
		private CheckBoxPreference checkBoxBridge;
		private EditTextPreference editTextProgressBarMax;
		private int maxScore;
		
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.layout.preference);

			sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
			sp.registerOnSharedPreferenceChangeListener(this);
			
			editTextProgressBarMax = (EditTextPreference) findPreference(getResources().getString(R.string.pref_ed_tx_pr_bar_max_key));
			checkBoxBridge = (CheckBoxPreference) findPreference(getResources().getString(R.string.pref_ch_box_bridge_key));
			
			updateCheckBoxBridgeSummary();
		}

		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
		{
			if (editTextProgressBarMax.equals(findPreference(key)))
				updateCheckBoxBridgeSummary();
		}
		
		private void updateCheckBoxBridgeSummary()
		{
			maxScore = (Integer.parseInt(sp.getString(getResources().getString(R.string.pref_ed_tx_pr_bar_max_key), "100")) - 5);
			checkBoxBridge.setSummary(getResources().getString(R.string.pref_ch_box_bridge_summary_start) + " " + 
									  maxScore + " " + getResources().getString(R.string.pref_ch_box_bridge_summary_end));
		}
	}
}
