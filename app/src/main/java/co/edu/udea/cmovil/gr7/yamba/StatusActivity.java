package co.edu.udea.cmovil.gr7.yamba;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.thenewcircle.yamba.client.YambaClient;


public class StatusActivity extends Activity {
    private Button mButtonTweet;
    private EditText mTextStatus;
    private TextView mTextCount;
    private int mDefaultColor;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yamba);
        mButtonTweet = (Button) findViewById(R.id.status_button_tweet);
        mTextStatus = (EditText) findViewById(R.id.status_text);
        mTextCount =  (TextView) findViewById(R.id.status_text_count);

        mTextCount.setText("140");
        mDefaultColor = mTextCount.getTextColors().getDefaultColor();
        deshabiltar();
        Log.d("Yamba","onCreate");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_yamba, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void postear(View v){
        String status = mTextStatus.getText().toString();
        progress=new ProgressDialog(this);
        PostTask postTask = new PostTask();
        postTask.execute(status);
        InputMethodManager input=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        input.hideSoftInputFromWindow(mTextStatus.getWindowToken(),0);
        mTextStatus.setText("");
        Log.d("TAG", "onClicked");
    }

    public void deshabiltar(){
        mButtonTweet.setEnabled(false);
        mTextStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String status=mTextStatus.getText().toString();
                if(status.length()>0){
                    mButtonTweet.setEnabled(true);
                }else{
                    mButtonTweet.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String status=mTextStatus.getText().toString();
                if(status.length()>01){
                    mButtonTweet.setEnabled(true);
                }else{
                    mButtonTweet.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String status=mTextStatus.getText().toString();
                if(status.length()>0){
                    mButtonTweet.setEnabled(true);
                }else{
                    mButtonTweet.setEnabled(false);
                }

            }
        });
    }

    class PostTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progress.setTitle("posteando mensaje...");
            progress.show();
        }

        protected String doInBackground(String...params){
            try{
                YambaClient cloud = new YambaClient("student","password");
                cloud.postStatus(params[0]);
                Log.d("Yamba","se posteo satisfactoriamente: "+params[0]);
                return "posteo satisfactorio";
            }catch (Exception e){
                Log.e("Yamba","fallo el posteo", e);
                e.printStackTrace();
                return "fallo el posteo";
            }
        }

        @Override
        protected void onPostExecute(String result){
            progress.dismiss();
            if (this != null && result!=null){
                Toast.makeText(StatusActivity.this,result,Toast.LENGTH_LONG).show();
            }
        }
    }


}
