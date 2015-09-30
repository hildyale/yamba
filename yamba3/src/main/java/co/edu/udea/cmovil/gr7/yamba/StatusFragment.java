package co.edu.udea.cmovil.gr7.yamba;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.thenewcircle.yamba.client.YambaClient;


public class StatusFragment extends Fragment {
    private static final String TAG = StatusFragment.class.getSimpleName();
    private Button mButtonTweet;
    private EditText mTextStatus;
    private TextView mTextCount;
    private int mDefaultColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_status, container, false);

        mButtonTweet = (Button) v.findViewById(R.id.status_button_tweet);
        mTextStatus = (EditText) v.findViewById(R.id.status_text);
        mTextCount = (TextView) v.findViewById(R.id.status_text_count);
        mTextCount.setText(Integer.toString(140));
        mButtonTweet.setEnabled(false);
        mDefaultColor = mTextCount.getTextColors().getDefaultColor();
        mButtonTweet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String status = mTextStatus.getText().toString();
                PostTask postTask = new PostTask();
                postTask.execute(status);
                Log.d(TAG, "onClicked");
            }

        });


        mTextStatus.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                int count = 140 - s.length();
                mTextCount.setText(Integer.toString(count));

                if (count < 50) {
                    mTextCount.setTextColor(Color.RED);
                } else {
                    mTextCount.setTextColor(mDefaultColor);
                }
                String status=mTextStatus.getText().toString();
                if(status.length()>0){
                    mButtonTweet.setEnabled(true);
                }else{
                    mButtonTweet.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                String status = mTextStatus.getText().toString();
                if (status.length() > 0) {
                    mButtonTweet.setEnabled(true);
                } else {
                    mButtonTweet.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String status=mTextStatus.getText().toString();
                if(status.length()>01){
                    mButtonTweet.setEnabled(true);
                }else{
                    mButtonTweet.setEnabled(false);
                }
            }

        });

        Log.d(TAG, "onCreated");

        return v;
    }

    class PostTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(getActivity(), getString(R.string.posting),
                    getString(R.string.wait));
            progress.setCancelable(true);
        }

        // Executes on a non-UI thread
        @Override
        protected String doInBackground(String... params) {
            try {
                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(getActivity());
                String username = prefs.getString("username", "");
                String password = prefs.getString("password", "");

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    getActivity().startActivity(
                            new Intent(getActivity(), SettingsActivity.class));
                    return getString(R.string.update);
                }


                YambaClient cloud = new YambaClient(username, password);
                cloud.postStatus(params[0]);

                Log.d(TAG, getString(R.string.goodposting)+" : " + params[0]);
                return getString(R.string.goodposting);
            } catch (Exception e) {
                Log.e(TAG, getString(R.string.badposting), e);
                e.printStackTrace();
                return getString(R.string.badposting);
            }
        }

        // Called after doInBackground() on UI thread
        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();
            if (getActivity() != null && result != null)
                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
        }

    }


}

