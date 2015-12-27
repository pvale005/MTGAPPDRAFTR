package com.example.pablo.draftr2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class card_list extends Activity {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<ListModel> listObject = new ArrayList<ListModel>();
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<ListModel> adapter;
    private ListView mListView;
    private List<Card> allCards;
    public int cards = 0;
    //Variables for the Drawer
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    public byte[] loadJSON() {
        try {
            InputStream is = getAssets().open("AllSets.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return buffer;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    private void addDrawerItems() {
        String[] osArray = { "Android", "iOS", "Windows", "OS X", "Linux"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();


        if (mListView == null) {
            //Sets the current listView to the listView control we are using.
            mListView = (ListView) findViewById(R.id.lv_cardCatalog);
        }
        try {
            allCards = new LoadCards().getCards(loadJSON());
        } catch (IOException e) {
            e.printStackTrace();
        }
        addItems();
        adapter = new CustomAdaper(this,listObject);
        setListAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                updateTxtNumber(cards += 1);
                adapter.getItem(position).quantity += 1;
                //oast.makeText(card_list.this, cdude, Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getItem(position).quantity == 0)
                    return false;
                else {
                    updateTxtNumber(cards -= 1);
                    adapter.getItem(position).quantity -= 1;
                    //oast.makeText(card_list.this, cdude, Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    return true;
                }
            }
        });
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(card_list.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_list, menu);
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
    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION

    public void addItems() {
        int i = 0;
        while (i < allCards.size()) {
            Card card = allCards.get(i);
            ListModel lm = new ListModel
                    (
                    card.getText(),
                    card.getName(),
                    0,
                    card.getCardColor(card.getColors()),
                    card.getTypes(),
                    card.getManaCost(),
                    card.getCmc()
                    );
            //listItems.add(""+allCards.get(i).getName());
            listObject.add(lm);
            i++;
        }

    }
    public void confirm(View v){
        ArrayList<ListModel> deck =new ArrayList<ListModel>();
        Intent intent = new Intent(this, deck_stat.class);
        for(ListModel l: listObject){
            if(l.quantity > 0)
                deck.add(l);
        }
        intent.putParcelableArrayListExtra("deck", deck);
        startActivity(intent);
    }

    public void reset(View v){
        for(int i=0;i < mListView.getCount(); i++){
            adapter.getItem(i).quantity = 0;
            adapter.notifyDataSetChanged();
            updateTxtNumber(cards =0);
            //finish();
            //startActivity(getIntent());
        }
    }


    protected ListView getListView() {
        if (mListView == null) {
            mListView = (ListView) findViewById(R.id.lv_cardCatalog);
        }
        return mListView;
    }
  /*  private CardListParcel buildParcel(){
        CardListParcel clParcel = null;
        for(ListModel l : listObject)
        {
            if(l.quantity > 0)
                loParcel.add(l);
        }
        clParcel.setListObject(loParcel);
        return clParcel;
        //clParcel.setCards(cards);
    }
    */
    protected void setListAdapter(ListAdapter adapter) {
        getListView().setAdapter(adapter);
    }
    public void updateTxtNumber(int toThis) {

        TextView textView = (TextView) findViewById(R.id.txtNumber);
        if(toThis < 10)
            //Attempt at keeping alignment the same for single digit counts. I'm sure there is a smarter way to do this.
            textView.setText("Cards: \t" + "0"+toThis);
        else
            textView.setText("Cards: \t" + toThis);

        return;
    }
    /*
    protected ListAdapter getListAdapter() {
        ListAdapter adapter = getListView().getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            return ((HeaderViewListAdapter)adapter).getWrappedAdapter();
        } else {
            return adapter;
        }
    }
    */
}
