package co.edu.udea.cmovil.gr7.yamba;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 18/10/15.
 */
public class Adapter extends ArrayAdapter {
    public String[] users;
    public String[] messages;
    public String[] created;
    public Adapter(Context context,String[] A, String[] B, String[] C) {
        super(context, 0, A);
        users = A;
        messages = B;
        created = C;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView list_user = (TextView) convertView.findViewById(R.id.list_user);
        TextView list_message = (TextView) convertView.findViewById(R.id.list_message);
        TextView list_created_at = (TextView) convertView.findViewById(R.id.list_created_at);
        // Populate the data into the template view using the data object
        list_user.setText(users[position]);
        list_message.setText(messages[position]);
        list_created_at.setText(created[position]);
        // Return the completed view to render on screen
        return convertView;
    }
}
