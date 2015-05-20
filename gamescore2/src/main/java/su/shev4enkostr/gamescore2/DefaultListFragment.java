package su.shev4enkostr.gamescore2;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by stas on 20.05.15.
 */
public class DefaultListFragment extends ListFragment
{
    private ArrayList<Players> data;
    private AppListAdapter adapter;

    private String[] name = new String[] {"Stas", "Oksana", "Igor", "Nadya"};
    private String[] score = new String[] {"10", "35", "75", "95"};

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        data = new ArrayList<>();

        for (int i = 0; i < name.length; i++)
        {
            data.add(new Players(name[i], Integer.parseInt(score[i])));
        }

        adapter = new AppListAdapter(getActivity(), data);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment, null);
    }
}
