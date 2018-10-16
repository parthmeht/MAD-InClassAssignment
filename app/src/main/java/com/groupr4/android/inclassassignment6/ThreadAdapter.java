package com.groupr4.android.inclassassignment6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ThreadAdapter extends ArrayAdapter<Threads> {
    public ThreadAdapter(@NonNull Context context, int resource, @NonNull List<Threads> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return super.getView(position, convertView, parent);
    }
    static class ViewHolder{
        TextView listTitle;
    }
}
