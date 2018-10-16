package com.groupr4.android.inclassassignment6;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ThreadAdapter extends BaseAdapter implements ListAdapter {
    private User user;
    private ArrayList<Threads> list;
    private Context context;

    public ThreadAdapter(User user, ArrayList<Threads> list, Context context) {
        this.user = user;
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.message_list_view, null);
        }
        Threads th = (Threads) getItem(position);
        TextView textView = view.findViewById(R.id.textView3);
        ImageButton imageButton = view.findViewById(R.id.imageButton);
        textView.setText(th.title);
        if (th.user_id.equals(user.userId)){
            imageButton.setVisibility(View.VISIBLE);
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Threads threads = (Threads) getItem(position);
                Log.d("hello",threads.title);
            }
        });
        return view;
    }
}