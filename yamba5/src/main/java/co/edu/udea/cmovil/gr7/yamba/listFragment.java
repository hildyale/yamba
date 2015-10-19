package co.edu.udea.cmovil.gr7.yamba;


import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class listFragment extends Fragment {

    private ListView lista;
    String[] users;
    String[] messages;
    String[] created;

    public static listFragment newInstance() {
        listFragment fragment = new listFragment();
        return fragment;
    }

    public listFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        records();
        int[] TO = { R.id.list_user,R.id.list_message, R.id.list_created_at};
        lista = (ListView) inflater.inflate(R.layout.lista, container, false);
        /*lista.setAdapter(new ArrayAdapter<String>(
                getActivity().getActionBar().getThemedContext(),
                R.layout.list_item,
                R.id.list_message,
                messages));*/
        lista.setAdapter(new Adapter(
                getActivity().getActionBar().getThemedContext(),
                users,
                messages,
                created));
        return lista;
    }

    public void records(){

        Uri CONTENT_URI = StatusContract.CONTENT_URI;
        String USER = StatusContract.Column.USER;
        String MESSAGE = StatusContract.Column.MESSAGE;
        String CREATED_AT = StatusContract.Column.CREATED_AT;

        ContentResolver contentResolver = getActivity().getActionBar().getThemedContext().getContentResolver();

        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        users = new String[cursor.getCount()];
        messages = new String[cursor.getCount()];
        created = new String[cursor.getCount()];
        int i=0;
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                String user = cursor.getString(cursor.getColumnIndex(USER));
                String message = cursor.getString(cursor.getColumnIndex(MESSAGE));
                String created_at = cursor.getString(cursor.getColumnIndex(CREATED_AT));

                users[i] = user;
                Log.d("USER",user);
                messages[i] = message;
                Log.d("MESSAGE",message);
                created[i] = created_at;
                Log.d("CREATED",created_at);

                i++;
                }
        }else{
            Log.d("ContentProvider", "Cursor:" + cursor.getCount());

        }

    }



}