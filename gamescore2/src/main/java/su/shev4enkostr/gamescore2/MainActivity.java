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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            item.setIntent(new Intent(this, AppPreferenceActivity.class));
			//Intent intent = new Intent(this, AppPreferenceActivity.class);
			//startActivity(intent);
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
