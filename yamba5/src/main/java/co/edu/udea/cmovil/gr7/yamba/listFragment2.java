package co.edu.udea.cmovil.gr7.yamba;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class listFragment2 extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = listFragment2.class.getSimpleName();
    private static final String[] FROM = { StatusContract.Column.USER,StatusContract.Column.MESSAGE, StatusContract.Column.CREATED_AT};
    private static final int[] TO = { R.id.list_user,R.id.list_message, R.id.list_created_at};
    private static final int LOADER_ID = 42;
    private SimpleCursorAdapter mAdapter;


    public static listFragment2 newInstance() {
        listFragment2 fragment = new listFragment2();
        return fragment;
    }

    public listFragment2() {
        // Required empty public constructor
    }


    static final SimpleCursorAdapter.ViewBinder VIEW_BINDER = new SimpleCursorAdapter.ViewBinder() {
        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            long timestamp;
            switch (view.getId()) {
                case R.id.list_created_at:
                    timestamp = cursor.getLong(columnIndex);
                    CharSequence relTime = DateUtils.getRelativeTimeSpanString(timestamp);
                    ((TextView) view).setText(relTime);
                    return true;
                case R.id.list_item_content:
                    timestamp = cursor.getLong(columnIndex);
                    //((FreshnessView) view).setTimestamp(timestamp);
                    return true;
                default:
                    return false;
            }
        }
    };
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item,
                null, FROM, TO, 0);
        mAdapter.setViewBinder((SimpleCursorAdapter.ViewBinder) VIEW_BINDER);
        setListAdapter(mAdapter);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        /*DetailsFragment fragment = (DetailsFragment) getFragmentManager().findFragmentById(R.layout.fragment_details); //
        if (fragment != null && fragment.isVisible()) {
            fragment.updateView(id);
        } else {
            startActivity(new Intent(getActivity(), DetailsActivity.class)
                    .putExtra(StatusContract.Column.ID, id));
        }*/
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id != LOADER_ID)
            return null;
        Log.d(TAG, "onCreateLoader");
        return new CursorLoader(getActivity(), StatusContract.CONTENT_URI,
                null, null, null, StatusContract.DEFAULT_SORT);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        /*
        DetailsFragment fragment = (DetailsFragment) getFragmentManager().findFragmentById(R.layout.fragment_details);
        if (fragment != null && fragment.isVisible() && cursor.getCount()
                == 0) {
            fragment.updateView(-1);
            Toast.makeText(getActivity(), "No data",
                    Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "onLoadFinished with cursor: " + cursor.getCount());
        mAdapter.swapCursor(cursor);
        */
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
