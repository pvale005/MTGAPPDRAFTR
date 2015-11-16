package com.example.pablo.draftr2;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pablo on 10/17/2015.
 */
public class CustomAdaper extends ArrayAdapter<ListModel> {

    CustomAdaper(Context context, ArrayList<ListModel> cards) {
        super(context, R.layout.custom_row, cards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Prepare for rendering
        LayoutInflater custInflater = LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder")
        View customView = custInflater.inflate(R.layout.custom_row, parent, false);
        ListModel lm = getItem(position);
        TextView txtName = (TextView) customView.findViewById(R.id.txtName);
        TextView txtQuantity = (TextView) customView.findViewById(R.id.txtQuantity);
        txtName.setText(lm.cname);
        txtQuantity.setText(lm.quantity.toString());

        if(lm.color.equals("White"))
            customView.setBackgroundResource(R.color.plains);
        if(lm.color.equals("Red"))
            customView.setBackgroundResource(R.color.mountain);
        if(lm.color.equals("Blue"))
            customView.setBackgroundResource(R.color.island);
        if(lm.color.equals("Black"))
            customView.setBackgroundResource(R.color.swamp);
        if(lm.color.equals("Green"))
            customView.setBackgroundResource(R.color.forest);
        if(lm.color.equals("Gold"))
            customView.setBackgroundResource(R.color.gold);
        if(lm.color.equals("Colorless"))
            customView.setBackgroundResource(R.color.colorless);

        return customView;
    }
}

