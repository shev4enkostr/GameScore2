package su.shev4enkostr.gamescore2;

import android.app.Activity;
import android.content.Context;
import android.preference.*;
import android.os.*;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class AppPreferenceActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new AppPreferenceFragment()).commit();
	}

	public static class AppPreferenceFragment extends PreferenceFragment
	{
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.layout.preference);

			//EditTextPreference editTextProgressBarMin = (EditTextPreference) findPreference(getResources().getString(R.string.pref_ed_tx_pr_bar_min_key));
			//EditTextPreference editTextProgressBarMax = (EditTextPreference) findPreference(getResources().getString(R.string.pref_ed_tx_pr_bar_max_key));
			CheckBoxPreference checkBoxBridge = (CheckBoxPreference) findPreference(getResources().getString(R.string.pref_ch_box_bridge_key));

			//int maxScore = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(getResources().getString(R.string.pref_ed_tx_pr_bar_max_key), 0);
			//checkBoxBridge.setSummary(String.valueOf(maxScore));

		}
	}
}
