package su.shev4enkostr.gamescore2;
import android.preference.*;
import android.util.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import android.widget.SeekBar.*;

public class AppDialogPreferenceSeekBar extends DialogPreference implements OnSeekBarChangeListener
{
	private int defaultValue;
	private static int minValue;
	private static int maxValue;
	
	private int currentValue;
	
	private static final String PREFERENCE_NS = "http://schemas.android.com/apk/res/su.shev4enkostr.gamescore2";
	private static final String ANDROID_NS = "htyp://schemas.android.com/apk/res/android";
	
	private static final String ATTR_DEFAULT_VALUE = "defaultValue";
	private static final String ATTR_MIN_VALUE = "minValue";
	private static final String ATTR_MAX_VALUE = "maxValue";
	
	private static final int DEFAULT_CURRENT_VALUE = 6;
	private static final int DEFAULT_MIN_VALUE = 2;
	private static final int DEFAULT_MAX_VALUE = 20;
	
	private TextView valueText;
	private SeekBar seekBar;
	
	public AppDialogPreferenceSeekBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
		defaultValue = attrs.getAttributeIntValue(ANDROID_NS, ATTR_DEFAULT_VALUE, DEFAULT_CURRENT_VALUE);
		minValue = attrs.getAttributeIntValue(PREFERENCE_NS, ATTR_MIN_VALUE, DEFAULT_MIN_VALUE);
		maxValue = attrs.getAttributeIntValue(PREFERENCE_NS, ATTR_MAX_VALUE, DEFAULT_MAX_VALUE);
	}

	@Override
	protected View onCreateDialogView()
	{
		currentValue = (getPersistedInt(defaultValue) - minValue);
		
		View view = super.onCreateDialogView();
		
		((TextView) view.findViewById(R.id.pref_dialog_min_value)).setText(String.valueOf(minValue));
		((TextView) view.findViewById(R.id.pref_dialog_max_value)).setText(String.valueOf(maxValue));
		
		seekBar = (SeekBar) view.findViewById(R.id.pref_dialog_seek_bar);
		
		seekBar.setMax(maxValue - minValue);
		seekBar.setProgress(currentValue);
		seekBar.setOnSeekBarChangeListener(this);
		
		valueText = (TextView) view.findViewById(R.id.pref_dialog_current_value);
		valueText.setText(String.valueOf(currentValue + minValue));
		
		return view;
	}

	@Override
	protected void onDialogClosed(boolean positiveResult)
	{
		//Log.d("gamascore2", "if (shouldPersist())\n" + "\t\t\tpersistInt(currentValue + minValue);");

		super.onDialogClosed(positiveResult);
		
		if (!positiveResult)
			return;

		if (shouldPersist())
			persistInt(currentValue + minValue);
	}

	@Override
	public void onProgressChanged(SeekBar seek, int value, boolean fromTouch)
	{
		currentValue = value;
		valueText.setText(String.valueOf(currentValue + minValue));
	}

	@Override
	public void onStartTrackingTouch(SeekBar p1)
	{
		// TODO: Implement this method
	}

	@Override
	public void onStopTrackingTouch(SeekBar p1)
	{
		// TODO: Implement this method
	}

	public static int getMaxValue()
	{
		return maxValue;
	}
}
