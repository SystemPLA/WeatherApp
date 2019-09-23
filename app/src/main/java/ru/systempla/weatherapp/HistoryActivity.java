package ru.systempla.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_history_activity);

        RecyclerView recyclerView = findViewById(R.id.rv_history);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DataSourceBuilder builder = new DataSourceBuilder(getResources());
        final List<HistoryEntry> dataSource = builder.build();
        final HistoryEntryAdapter adapter = new HistoryEntryAdapter(dataSource);
        recyclerView.setAdapter(adapter);

        Button add = findViewById(R.id.add_to_rv_bt);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //тестовые данные для новых элементов списка
                dataSource.add(0, new HistoryEntry("Аппокалипсис сегодня",
                        "+100","1 мм. рт. ст","100 м/с", "200%"));
                adapter.notifyDataSetChanged();
            }
        });
    }
}
