package com.capotasto.helpmemorizationapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static SQLiteDatabase db;
    private static final String TABLE_NAME = "vocabularies";

    float historicX = Float.NaN;
    float historicY = Float.NaN;
    static final int DELTA = 50;

    enum Direction {LEFT, RIGHT;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        //make Database
        DatabaseHandler databaseHandler = new DatabaseHandler(getBaseContext());
        db = databaseHandler.getWritableDatabase();

        //Insert TestData
//        Vocabulary insertWord1 = new Vocabulary(1, "dog", "animal", "Dog is dog.", "dog");
//        Vocabulary insertWord2 = new Vocabulary(2, "cat", "animal", "Cat is cat.", "cat");
//        Vocabulary insertWord3 = new Vocabulary(3, "fish", "fish", "Fish is fish.", "fish");
//        Vocabulary insertWord4 = new Vocabulary(4, "rice", "food", "Rice is food.", "rice");
//        Vocabulary insertWord5 = new Vocabulary(5, "monkey", "animal", "Monkey is monkey.", "monkey");
//
//        databaseHandler.addWord(insertWord1);
//        databaseHandler.addWord(insertWord2);
//        databaseHandler.addWord(insertWord3);
//        databaseHandler.addWord(insertWord4);
//        databaseHandler.addWord(insertWord5);

        //Read Database
        List<Vocabulary> vocabularyList = databaseHandler.getAllWords();

        final ListView listView = (ListView) findViewById(R.id.main_list);

        ArrayList<String> list = new ArrayList<>();

        for (Vocabulary vocabulary : vocabularyList) {
            list.add(vocabulary.getWord());
        }

        WordsListAdapter adapter = new WordsListAdapter(this, R.layout.words_list_adaptar,list);
        listView.setAdapter(adapter);
        adapter.setListView(listView);

        //Floating Button
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.btn_add_word);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
