package su.shev4enkostr.gamescore2;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.*;

/**
 * Created by stas on 20.05.15.
 */
public class AppFragmentPagerAdapter extends FragmentPagerAdapter
{
    private static final int PAGE_COUNT = 2;
	
	private FragmentTransaction frTransaction;
    private Context context;

    public AppFragmentPagerAdapter(FragmentManager fm, Context context)
    {
        super(fm);
		this.frTransaction = fm.beginTransaction();
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        if (position == 0)
            return new MainListFragment();

        else if (position == 1)
            return new DefaultListFragment();

        else return null;
    }

    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position)
   	{
        if (position == 0)
            return context.getText(R.string.page_title_main);

        else if (position == 1)
            return context.getText(R.string.page_title_default);

        else return null;
    }

	@Override
	public int getItemPosition(Object object)
	{
		// TODO: Implement this method
		//return super.getItemPosition(object);
		
		return POSITION_NONE;
	}
}
