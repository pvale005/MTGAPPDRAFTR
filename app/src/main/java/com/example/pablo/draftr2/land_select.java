package com.example.pablo.draftr2;

import android.app.Activity;
import android.content.Intent;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

public class land_select extends Activity {
    double red;
    double green;
    double blue;
    double black;
    double white;
    double total;
    int mountain;
    int forest;
    int island;
    int swamp;
    int plain;
    int lands;
    EditText txtR;
    EditText txtG;
    EditText txtB;
    EditText txtU;
    EditText txtW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_select);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));
        unpack();

        total = red + green + blue + black + white;
        //This is how lands are calculated but it seems that are some rounding errors that are occuring sometimes
        //The logic need to be ironed out
        mountain = (int) Math.round((red / total) * lands);
        forest = (int) Math.round((green / total) * lands);
        island = (int) Math.round((blue / total) * lands);
        swamp = (int) Math.round((black / total) * lands);
        plain = (int) Math.round((white / total) * lands);

        txtR = (EditText) findViewById(R.id.txtR);
        txtG = (EditText) findViewById(R.id.txtG);
        txtB = (EditText) findViewById(R.id.txtB);
        txtU = (EditText) findViewById(R.id.txtU);
        txtW = (EditText) findViewById(R.id.txtW);

        updateTxtFields();
    }

    public void end(View v) {
        finish();
    }

    public void unpack() {
        Intent i = this.getIntent();
        red = i.getIntExtra("Red", 0);
        green = i.getIntExtra("Green", 0);
        blue = i.getIntExtra("Blue", 0);
        black = i.getIntExtra("Black", 0);
        white = i.getIntExtra("White", 0);
        lands = i.getIntExtra("Lands", 0);
    }

    public void buttonHandler(View v) {
        switch (v.getId()) {  // <= this is it
            case R.id.P1:
                if (plain != 0)
                    plain -= 1;
                break;
            case R.id.P2:
                plain += 1;
                break;
            case R.id.I1:
                if (island != 0)
                    island -= 1;
                break;
            case R.id.I2:
                island += 1;
                break;
            case R.id.S1:
                if (swamp != 0)
                    swamp -= 1;
                break;
            case R.id.S2:
                swamp += 1;
                break;
            case R.id.F1:
                if (forest != 0)
                    forest -= 1;
                break;
            case R.id.F2:
                forest += 1;
                break;
            case R.id.M1:
                if (mountain != 0)
                    mountain -= 1;
                break;
            case R.id.M2:
                mountain += 1;
                break;
        }
        updateTxtFields();
    }

    public void updateTxtFields() {
        txtR.setText("" + mountain);
        txtG.setText("" + forest);
        txtU.setText("" + island);
        txtB.setText("" + swamp);
        txtW.setText("" + plain);
    }

    public void ok(View v) {
        Intent output = new Intent();
        mountain = Integer.parseInt(txtR.getText().toString());
        forest = Integer.parseInt(txtG.getText().toString());
        island = Integer.parseInt(txtU.getText().toString());
        swamp = Integer.parseInt(txtB.getText().toString());
        plain = Integer.parseInt(txtW.getText().toString());

        output.putExtra("Mountain", mountain);
        output.putExtra("Forest", forest);
        output.putExtra("Island", island);
        output.putExtra("Swamp", swamp);
        output.putExtra("Plains", plain);
        setResult(deck_stat.RESULT_OK, output);
        finish();
    }

}
