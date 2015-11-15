package co.edu.udea.cmovil.gr7.yamba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class MainActivity extends Activity {


    public void Reminder(int seconds) {
        Timer timer = new Timer();
        timer.schedule(hola, seconds*1000);

    }

    TimerTask hola = new TimerTask() {
        @Override
        public void run() {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, listFragment.newInstance())
                    .commit();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction()
                .replace(R.id.container, listFragment.newInstance())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_tweet:
                startActivity(new Intent("com.marakana.android.yamba.action.tweet"));
                return true;
            case R.id.action_refresh:
                startService(new Intent(this, RefreshService.class));
                Reminder(1);
                return true;
            case R.id.salir:
                System.exit(1);
                return true;
            default:
                return false;
        }
    }

}

