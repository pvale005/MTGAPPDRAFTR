package com.example.pablo.draftr2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.content.ClipboardManager;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class deck_stat extends Activity {
    ArrayList<ListModel> deckList;

    Map<String, Integer> deck = new TreeMap<>();
    Map<Integer, String> deckMap = new TreeMap<>();
    Map<Integer, Integer> manaCostMap = new TreeMap<>();
    Map<String, Integer> manaSymMap = new HashMap<>();
    Map<String, Integer> cardTypeMap = new HashMap<>();
    int red;
    int green;
    int blue;
    int black;
    int white;
    int mountain = 0;
    int forest = 0;
    int island = 0;
    int swamp = 0;
    int plain = 0;
    int lands = 18;
    boolean landSelected = false;
    int maxCMC = -1;
    EditText etxtLands;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_stat);
        Intent i = this.getIntent();
        deckList = i.getParcelableArrayListExtra("deck");
        getStats(deckList);
        genPieChart();
        genCurveChart();
        etxtLands = (EditText) findViewById(R.id.etxtLand);
        etxtLands.setText("" + lands);
        TextView txtTypes = (TextView) findViewById(R.id.textView);
        String totalTypes = "Draft Breakdown\n\n";
        for (String s : cardTypeMap.keySet()) {
            totalTypes += s + ": " + cardTypeMap.get(s) + "\n";
        }
        txtTypes.setText(totalTypes);

    }

    public void genCurveChart() {
        ValueLineChart mCubicValueLineChart = (ValueLineChart) findViewById(R.id.cubiclinechart);
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);

        for (int c = -1; c <= maxCMC + 1; c++) {

            if (manaCostMap.containsKey(c))
                series.addPoint(new ValueLinePoint(c + "", manaCostMap.get(c)));
            else
                series.addPoint(new ValueLinePoint(c + "", 0));
        }

        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
    }

    public void genPieChart() {
        PieChart mPieChart = (PieChart) findViewById(R.id.piechart);
        for (String s : manaSymMap.keySet()) {
            if (s.equals("R")) {
                red = manaSymMap.get(s);
                mPieChart.addPieSlice(new PieModel("Red", red, Color.parseColor("#F9AA8F")));
            }
            if (s.equals("G")) {
                green = manaSymMap.get(s);
                mPieChart.addPieSlice(new PieModel("Green", green, Color.parseColor("#9BD3AE")));
            }
            if (s.equals("U")) {
                blue = manaSymMap.get(s);
                mPieChart.addPieSlice(new PieModel("Blue", blue, Color.parseColor("#AAE0FA")));
            }
            if (s.equals("B")) {
                black = manaSymMap.get(s);
                mPieChart.addPieSlice(new PieModel("Black", black, Color.parseColor("#CBC2BF")));
            }
            if (s.equals("W")) {
                white = manaSymMap.get(s);
                mPieChart.addPieSlice(new PieModel("White", white, Color.parseColor("#FFFBD5")));
            }
        }
        mPieChart.startAnimation();
    }

    public void getStats(ArrayList<ListModel> deck) {
        for (ListModel l : deck) {
            if (l.cmc != null) {//Lands dont have a casting cost and we wouldnt want them in here anyways
                if (!manaCostMap.containsKey(l.cmc))
                    manaCostMap.put(l.cmc, l.quantity);
                else
                    manaCostMap.put(l.cmc, manaCostMap.get(l.cmc) + l.quantity);

                if (l.cmc > maxCMC) {
                    maxCMC = l.cmc;
                }
            }
            for (String s : l.types) {
                if (!cardTypeMap.containsKey(s)) {
                    cardTypeMap.put(s, l.quantity);
                } else
                    cardTypeMap.put(s, cardTypeMap.get(s) + l.quantity);

            }
            if (l.manaCost != null) {//Lands do not have a manacost either.
                String e = l.manaCost;
                String[] res = e.split("\\}");
                for (String s : res) {
                    String c = s.substring(1);
                    if (!c.matches("\\d+")) {
                        if (!manaSymMap.containsKey(c)) {
                            manaSymMap.put(c, l.quantity);
                        } else
                            manaSymMap.put(c, manaSymMap.get(c) + l.quantity);
                        //manaSymMap.put(c, manaSymMap.get(c) * l.quantity);
                    }
                }
            }
        }
    }

    public void landSetter(View v) {
        //TODO Cut lands already selected
        lands = Integer.parseInt(etxtLands.getText().toString());
        Intent intent = new Intent(this, land_select.class);
        intent.putExtra("Red", red);
        intent.putExtra("Green", green);
        intent.putExtra("Blue", blue);
        intent.putExtra("Black", black);
        intent.putExtra("White", white);
        intent.putExtra("Lands", lands);
        startActivityForResult(intent, 33);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (33): {
                if (resultCode == Activity.RESULT_OK) {
                    landSelected = true;
                    mountain = data.getIntExtra("Mountain", 0);
                    island = data.getIntExtra("Island", 0);
                    forest = data.getIntExtra("Forest", 0);
                    swamp = data.getIntExtra("Swamp", 0);
                    plain = data.getIntExtra("Plains", 0);
                }
                break;
            }
        }
    }

    public void clipBoard(View v) {
        if(!landSelected){
            Toast.makeText(getApplicationContext(), "ERROR: Must Select LANDS first!!!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            fillDeckList();
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Deck List", writeList());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Deck List Copied",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public String writeList() {
        String list = "";
        for (String s : deck.keySet()) {
            list += deck.get(s) + " " + s + "\n";
        }
        return list;
    }
    public void toFile(View v){

        //Under Testing
        if(!landSelected){
            Toast.makeText(getApplicationContext(), "ERROR: Must Select LANDS first!!!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            String content = "Tester";
            File file;
            FileOutputStream outputStream;
            try {
                file = new File(Environment.getExternalStorageDirectory(), "MyDecks");
                outputStream = new FileOutputStream(file);
                outputStream.write(content.getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void fillDeckList() {
        //Clear the list to avoid duplicate cases.
        deck.clear();
        //Next we are going to check every land type and add it to the list if it has a quantity greater than zero.
        if (mountain != 0)
            deck.put("Mountain", mountain);
        if (island != 0)
            deck.put("Island", island);
        if (forest != 0)
            deck.put("Forest", forest);
        if (swamp != 0)
            deck.put("Swamp", swamp);
        if (plain != 0)
            deck.put("Plains", plain);
        //Lastly add everything else into our deck list.
        for (ListModel l : deckList) {
            deck.put(l.cname, l.quantity);
        }
    }

    public void makeDeckObject() {
        fillDeckList();

        int j = 0;
        for (String s : deck.keySet()) {
            for (int i = 0; i < deck.get(s); i++) {
                deckMap.put(j, s);
                j++;
            }
        }
    }
    public String genHand(){
        String result = "";
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<deckMap.size() - 1; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i=0; i<7; i++) {
            result += deckMap.get(list.get(i)) + "\n";
        }
        return result;
    }

    public void showAlert(View v) {
        if (!landSelected) {
            Toast.makeText(getApplicationContext(), "ERROR: Must Select LANDS first!!!",
                    Toast.LENGTH_SHORT).show();
        } else {
            makeDeckObject();
            final AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
            myAlert.setMessage(genHand())
                    .setPositiveButton("Redraw", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myAlert.setMessage(genHand());
                            myAlert.show();
                        }
                    })
                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deck_stat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
