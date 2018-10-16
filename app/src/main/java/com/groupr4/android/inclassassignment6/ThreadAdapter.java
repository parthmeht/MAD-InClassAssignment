package com.groupr4.android.inclassassignment6;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.IOException;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ThreadAdapter extends BaseAdapter implements ListAdapter {
    private User user;
    private ArrayList<Threads> list;
    private Context context;
    private final OkHttpClient client = new OkHttpClient();
    private String token;

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
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.message_list_view, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        token = preferences.getString("Token", "");
        Threads th = (Threads) getItem(position);
        viewHolder.textView.setText(th.title);
        if (th.user_id.equals(user.userId)){
            viewHolder.imageButton.setVisibility(View.VISIBLE);
        }
        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Threads threads = (Threads) getItem(position);
                Log.d("hello",threads.title);
                Request request = new Request.Builder()
                        .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/thread/delete/"+threads.id)
                        .header("Authorization", "BEARER " + token)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        list.remove(position);
                    }
                });
            }
        });
        return view;
    }

    private class ViewHolder {
        TextView textView;
        ImageButton imageButton;

        public ViewHolder(View view) {
            textView = (TextView)view.findViewById(R.id.textView3);
            imageButton = (ImageButton)view.findViewById(R.id.imageButton);
        }
    }
}

