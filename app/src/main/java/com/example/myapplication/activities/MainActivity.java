package com.example.myapplication.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import com.example.myapplication.activities.ItemDetailActivity;
import com.example.myapplication.models.Item;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ItemAdapter;
import com.example.myapplication.database.DBHelper;
import com.example.myapplication.models.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemAdapter itemAdapter;
    ArrayList<Item> itemList;
    FloatingActionButton fabAddItem;
    DBHelper dbHelper;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fabAddItem = findViewById(R.id.fabAddItem);

        SharedPreferences prefs = getSharedPreferences("MyDataAppPrefs", MODE_PRIVATE);
        userId = prefs.getInt("user_id", -1);

        dbHelper = new DBHelper(this);
        itemList = new ArrayList<>();
        loadItemsFromDB();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(this, itemList, userId);
        recyclerView.setAdapter(itemAdapter);

        fabAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ItemDetailActivity.class);
            intent.putExtra("mode", "add");
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItemsFromDB();
        itemAdapter.updateData(itemList);
    }

    private void loadItemsFromDB() {
        itemList.clear();
        Cursor cursor = dbHelper.getItemsForUser(userId);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                itemList.add(new Item(id, title, desc, userId));
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
