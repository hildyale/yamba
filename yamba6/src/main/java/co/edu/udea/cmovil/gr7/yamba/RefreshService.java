package co.edu.udea.cmovil.gr7.yamba;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.thenewcircle.yamba.client.YambaClient;
import com.thenewcircle.yamba.client.YambaClientException;
import com.thenewcircle.yamba.client.YambaStatus;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class RefreshService extends IntentService {
    boolean isEmpty;

    private static final String TAG = RefreshService.class.getSimpleName();
    public RefreshService() {
        super("RefreshService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreated");
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this); //*
        final String username = prefs.getString("username", ""); //*
        final String password = prefs.getString("password", ""); //*

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            isEmpty = true;
            return;
        } //Verificar que no hayan campos vacíos

        Log.d(TAG, "onStarted");

        //DbHelper dbHelper= new DbHelper(this);//Instancia de DbHelper
        //SQLiteDatabase db=dbHelper.getWritableDatabase();//Obtener instancia de BD
        ContentValues values = new ContentValues();

        YambaClient cloud = new YambaClient(username, password); /*Se crea un nuevo
    cliente yamba*/
        try {
            int count=0;
            List<YambaStatus> timeline = cloud.getTimeline(20); /* Obtener linea de tiempo, los últimos 20 estados*/
            for (YambaStatus status : timeline) {
                Log.d(TAG, String.format("%s: %s", status.getUser(), status.getMessage())); //Imprimir estados en consola
                values.clear();
                values.put(StatusContract.Column.ID, status.getId());
                values.put(StatusContract.Column.USER,status.getUser());
                values.put(StatusContract.Column.MESSAGE,status.getMessage());
                values.put(StatusContract.Column.CREATED_AT, status.getCreatedAt().getTime());

                //db.insertWithOnConflict(StatusContract.TABLE, null, values,
                //SQLiteDatabase.CONFLICT_IGNORE);

                Uri uri = getContentResolver().insert(StatusContract.CONTENT_URI, values);

                if (uri != null) {
                    count++;
                    Log.d(TAG,String.format("%s: %s", status.getUser(), status.getMessage()));
                }
            }
            if(count>0){
                sendBroadcast(new Intent("compumovil.udea.edu.co.yamba.action.NEW_STATUSES").putExtra("count", count));

            }
        } catch (YambaClientException e) { //
            Log.e(TAG, "Failed to fetch the timeline", e);
            e.printStackTrace();
        }
        return;
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        if(isEmpty){
            Intent i = new Intent();
            i.setClass(this, SettingsActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            Toast.makeText(this, getString(R.string.update), Toast.LENGTH_LONG).show();
            isEmpty=false;
        }
        Log.d(TAG, "onDestroyed");
    }
}
