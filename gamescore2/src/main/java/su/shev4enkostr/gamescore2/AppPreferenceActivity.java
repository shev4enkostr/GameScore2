package su.shev4enkostr.gamescore2;

import android.preference.*;
import android.os.*;

public class AppPreferenceActivity extends PreferenceActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.preference);
	}
}
