package com.example.pablo.draftr2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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
        //Get a refrence to the text controls in the
        TextView txtName = (TextView) customView.findViewById(R.id.txtName);
        TextView txtQuantity = (TextView) customView.findViewById(R.id.txtQuantity);
        //Set the text controls in the custom view adapter.
        txtName.setText(lm.cname);
        txtQuantity.setText(lm.quantity.toString());

        //This switch statement handles the actual coloring of the rows by changing the background.
        switch (lm.color){
            case "White":
                customView.setBackgroundResource(R.color.plains);
                break;
            case "Red":
                customView.setBackgroundResource(R.color.mountain);
                break;
            case "Blue":
                customView.setBackgroundResource(R.color.island);
                break;
            case "Black":
                customView.setBackgroundResource(R.color.swamp);
                break;
            case "Green":
                customView.setBackgroundResource(R.color.forest);
                break;
            case "Gold":
                customView.setBackgroundResource(R.color.gold);
                break;
            case "Colorless":
                customView.setBackgroundResource(R.color.colorless);
                break;
        }
        /*
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
        */
        return customView;
    }
}

