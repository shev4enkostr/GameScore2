package su.shev4enkostr.gamescore2;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.view.*;
import android.widget.*;
import android.text.*;
import android.content.*;
import android.preference.*;

public class MainActivity extends FragmentActivity
{
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
	private Toast backPressed;

    private static final String LOG = "gamescore2";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(LOG, "MainActivity onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.ViewPager);
        pagerAdapter = new AppFragmentPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(pagerAdapter);
		
		PagerTabStrip pts = (PagerTabStrip) findViewById(R.id.tab_strip);
		pts.setDrawFullUnderline(true);
		pts.setTabIndicatorColorResource(android.R.color.holo_blue_bright);
    }

	@Override
	protected void onResume()
	{
		Log.d(LOG, "MainActivity onResume()");
        pagerAdapter.notifyDataSetChanged();
		super.onResume();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            item.setIntent(new Intent(this, AppPreferenceActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onBackPressed()
	{
		if (backPressed != null && backPressed.getView().getWindowToken() != null)
		{
			super.onBackPressed();
		}
			
		else
		{
			backPressed = Toast.makeText(this, R.string.toast_exit, Toast.LENGTH_SHORT);
			backPressed.show();
		}
	}
}
