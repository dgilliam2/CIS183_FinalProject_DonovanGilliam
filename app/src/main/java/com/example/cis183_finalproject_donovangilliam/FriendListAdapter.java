package com.example.cis183_finalproject_donovangilliam;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendListAdapter extends BaseAdapter
{
    Context context;
    ArrayList<Friend> friend_list;
    DatabaseHelper dbhelper;
    public FriendListAdapter(Context c, ArrayList<Friend> fl)
    {
        context = c;
        friend_list = fl;
        dbhelper = new DatabaseHelper(context);
    }

    @Override
    public int getCount() {
        return friend_list.size();
    }

    @Override
    public Object getItem(int i) {
        return friend_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if (view == null)
        {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService((MainActivity.LAYOUT_INFLATER_SERVICE));
            view = mInflater.inflate(R.layout.custom_cell_friend_list, null);
        }

        TextView name = view.findViewById(R.id.tv_v_cc_f_name);
        TextView email = view.findViewById(R.id.tv_v_cc_f_email);
        TextView age = view.findViewById(R.id.tv_v_cc_f_age);
        TextView birthday = view.findViewById(R.id.tv_v_cc_f_bday);
        TextView phone = view.findViewById(R.id.tv_v_cc_f_phonenum);
        TextView closeness = view.findViewById(R.id.tv_v_cc_f_closeness);
        TextView comms = view.findViewById(R.id.tv_v_cc_f_comms);

        Friend friend = friend_list.get(i);
        ArrayList<String> commMethodsList = dbhelper.getCommMethodsByID(friend.getFriendID());
        String commMethods = String.join(", ", commMethodsList);

        name.setText("Name: "+ friend.getFname() + " " + friend.getLname());
        email.setText("Email: " + friend.getEmail());
        age.setText("Age: " + friend.getAge());
        birthday.setText("DOB: " + friend.getBirthday());
        phone.setText("Phone #: " + friend.getPhoneNum());
        closeness.setText("Closeness: " + friend.closenessString(friend.getClosenessLevel()));
        comms.setText("Communication: " + commMethods);
        return view;
    }
}