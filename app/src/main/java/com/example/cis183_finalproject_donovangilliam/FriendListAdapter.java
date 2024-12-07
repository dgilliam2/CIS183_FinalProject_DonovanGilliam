package com.example.cis183_finalproject_donovangilliam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

        dbhelper = new DatabaseHelper(context);
        Friend friend = friend_list.get(i);

        ArrayList<String> comm_methods_list = dbhelper.getCommMethodNamesByID(friend.getFriendID());
        ArrayList<String> interests_list = dbhelper.getInterestNamesByID(friend.getFriendID());

        // concat comm methods into a string
        String commMethods = String.join(", ", comm_methods_list);
        String interestsString = String.join(", ", interests_list);


        TextView name = view.findViewById(R.id.tv_v_cc_f_name);
        TextView email = view.findViewById(R.id.tv_v_cc_f_email);
        TextView age = view.findViewById(R.id.tv_v_cc_f_age);
        TextView birthday = view.findViewById(R.id.tv_v_cc_f_bday);
        TextView phone = view.findViewById(R.id.tv_v_cc_f_phonenum);
        TextView closeness = view.findViewById(R.id.tv_v_cc_f_closeness);
        TextView interests = view.findViewById(R.id.tv_v_cc_f_interests);
        TextView comms = view.findViewById(R.id.tv_v_cc_f_comms);

        ImageView male = view.findViewById(R.id.iv_v_cc_male);
        ImageView female = view.findViewById(R.id.iv_v_cc_female);
        ImageView other = view.findViewById(R.id.iv_v_cc_other);
        ImageView mark = view.findViewById(R.id.iv_v_cc_mark);


        male.setVisibility(View.INVISIBLE);
        female.setVisibility(View.INVISIBLE);
        other.setVisibility(View.INVISIBLE);
        mark.setVisibility(View.INVISIBLE);

        name.setText("Name: "+ friend.getFname() + " " + friend.getLname());
        email.setText("Email: " + friend.getEmail());
        if (friend.getGender() == 0)
        {
            male.setVisibility(View.VISIBLE);
        }
        else if (friend.getGender() == 1)
        {
            female.setVisibility(View.VISIBLE);
        }
        else
        {
            other.setVisibility(View.VISIBLE);
        }
        age.setText("Age: " + friend.getAge());
        birthday.setText("DOB: " + friend.getBirthday());
        phone.setText("Phone #: " + friend.getPhoneNum());
        closeness.setText("Closeness: " + friend.closenessString(friend.getClosenessLevel()));
        if (!interests_list.isEmpty())
        {
            interests.setText("Interests: " + interestsString);
        }
        else
        {
            interests.setText("Interests: None");

        }
        if (!comm_methods_list.isEmpty()) {
            comms.setText("Communication: " + commMethods);
        }
        else
        {
            comms.setText("Communication: None");
        }

        if (friend.isMarked() == 1)
        {
            mark.setVisibility(View.VISIBLE);
        }
        else
        {
            mark.setVisibility(View.INVISIBLE);
        }
        return view;
    }
}