package su.shev4enkostr.gamescore2;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.DialogFragment;
import android.widget.*;
import android.view.View.*;

public class AddPlayerDialogFragment2 extends DialogFragment implements OnClickListener, OnFocusChangeListener
{
	private EditText etAddPlayer;
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
		String name = etAddPlayer.getText().toString();
		if (id == Dialog.BUTTON_POSITIVE)
			Toast.makeText(getActivity(), "Positive " + name, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onFocusChange(View view, boolean hasFocus)
	{
		if (hasFocus)
			dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}
}
