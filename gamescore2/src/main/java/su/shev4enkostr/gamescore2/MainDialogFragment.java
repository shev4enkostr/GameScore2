package su.shev4enkostr.gamescore2;

import android.app.*;
import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.DialogFragment;
import android.widget.*;

public class MainDialogFragment extends DialogFragment implements OnClickListener
{
	private Button btnSubmit;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog dialog = new Dialog(getActivity());
		btnSubmit = (Button) dialog.findViewById(R.id.btn_submit);
		//btnSubmit.setOnClickListener(this);
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment, null);
	}

	@Override
	public void onClick(DialogInterface dialogInterface, int id)
	{
		if (id == R.id.btn_submit)
			Toast.makeText(getActivity(), "MainScreen", Toast.LENGTH_SHORT).show();
	}
}
