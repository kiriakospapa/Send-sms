package com.example.sendsms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {

    ArrayList<Contact> contacts = new ArrayList<>();
    Context context;

    public ContactAdapter(ArrayList<Contact> arrayList, Context context) {
        this.contacts = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list, parent, false);

            Contact contact = (Contact) getItem(position);

            TextView name = (TextView) convertView.findViewById(R.id.Name);

            TextView number = (TextView) convertView.findViewById(R.id.number);

            name.setText(contact.getName());

            number.setText(contact.getPhone());


        }*/


        // Get the data item for this position
         Contact  contact =(Contact) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row

            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(context);

            convertView = inflater.inflate(R.layout.list, parent, false);

            viewHolder.name = (TextView) convertView.findViewById(R.id.Name);

            viewHolder.number = (TextView) convertView.findViewById(R.id.number);
            // Cache the viewHolder object inside the fresh view

            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag

            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name.setText(contact.getName());

        viewHolder.number.setText(contact.getPhone());
        // Return the completed view to render on screen
        return convertView;
    }

    static class ViewHolder {

        TextView name;

        TextView number;
    }

}