package ru.systempla.weatherapp.ui.history;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.systempla.weatherapp.R;
import ru.systempla.weatherapp.database.DatabaseHelper;
import ru.systempla.weatherapp.database.WeatherHistoryTable;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button clearBt;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_history_activity);

        initViews();
        initDB();

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DataSourceBuilder builder = new DataSourceBuilder(WeatherHistoryTable.getAllNotes(database));
        final List<HistoryEntry> dataSource = builder.build();
        final HistoryEntryAdapter adapter = new HistoryEntryAdapter(dataSource);
        recyclerView.setAdapter(adapter);

        clearBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherHistoryTable.deleteAll(database);
                dataSource.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initDB() {
        database = new DatabaseHelper(getApplicationContext()).getWritableDatabase();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.rv_history);
        clearBt = findViewById(R.id.clear_rv);
    }
}
