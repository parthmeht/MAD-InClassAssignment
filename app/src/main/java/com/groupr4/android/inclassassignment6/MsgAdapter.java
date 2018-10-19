package com.groupr4.android.inclassassignment6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class  MsgAdapter extends ArrayAdapter<Msg> {

    public MsgAdapter(@NonNull Context context, int resource, @NonNull List<Msg> objects) {
        super(context, resource, objects);
    }



    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public Msg getItem(int position) {
        return super.getItem(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        Msg current_msg=getItem(position);
       if(current_msg!=null) {

           ViewHolder viewHolder;
           if (convertView == null) {

               convertView = LayoutInflater.from(getContext()).inflate(R.layout.add_message_layout, parent, false);
               viewHolder = new ViewHolder();
               viewHolder.UserName = (TextView) convertView.findViewById(R.id.txtUserName);
               viewHolder.Messages = (TextView) convertView.findViewById(R.id.txteachMessgae);
               viewHolder.MsgTime = (TextView) convertView.findViewById(R.id.txtMsgTime);
               convertView.setTag(viewHolder);
           } else {
               viewHolder = (ViewHolder) convertView.getTag();
           }

           viewHolder.UserName.setText(current_msg.user_fname.trim() + " " + current_msg.user_lname.trim());
           if (current_msg.msgContent != null) {
               viewHolder.Messages.setText(current_msg.msgContent.trim());
           }
           return convertView;
       }
        return super.getView(position, convertView, parent);
    }


    private static class ViewHolder
    {
        TextView Messages;
        TextView UserName;
        TextView MsgTime;

    }
}
