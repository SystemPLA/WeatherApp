package ru.systempla.weatherapp.ui.history;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.systempla.weatherapp.R;

class HistoryEntryAdapter extends RecyclerView.Adapter<HistoryEntryAdapter.ViewHolder> {

    private final List<HistoryEntry> dataSource;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView date;
        private final TextView temperatureValue;
        private final TextView pressureValue;
        private final TextView windSpeedValue;
        private final TextView humidityValue;
//        private final TextView teperatureLabel;
//        private final TextView pressureLabel;
//        private final TextView windSpeedLabel;
//        private final TextView humidityLabel;

        public ViewHolder(View view) {
            super(view);

            date = view.findViewById(R.id.date_tv);
            temperatureValue = view.findViewById(R.id.temperature_value);
            pressureValue = view.findViewById(R.id.pressure_value);
            windSpeedValue = view.findViewById(R.id.wind_value);
            humidityValue = view.findViewById(R.id.humidity_value);
//            teperatureLabel = view.findViewById(R.id.temperature_label);
//            pressureLabel = view.findViewById(R.id.pressure_label);
//            windSpeedLabel = view.findViewById(R.id.wind_label);
//            humidityLabel = view.findViewById(R.id.humidity_label);
        }
    }

    // Передаем в конструктор источник данных
    // В нашем случае это список, но может быть и запрос к БД
    public HistoryEntryAdapter(List<HistoryEntry> dataSource) {
        this.dataSource = dataSource;
    }

    // Создать новый элемент пользовательского интерфейса
    @NonNull
    @Override
    public HistoryEntryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        Log.d("HistoryEntryAdapter", "onCreateViewHolder");
        return viewHolder;
    }

    // Связывание данных (data binding) в пользовательском интерфейсе
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Получить элемент из источника данных (БД, интернет…)
        final int pos = position;
        HistoryEntry item = dataSource.get(position);

        // Вывести на экран, используя ViewHolder
        holder.date.setText(item.getDate());
        holder.temperatureValue.setText(item.getTemperature());
        holder.pressureValue.setText(item.getPressure());
        holder.windSpeedValue.setText(item.getWindSpeed());
        holder.humidityValue.setText(item.getHumidity());
//        holder.teperatureLabel.setText(item.getDescription());
//        holder.pressureLabel.setText(item.getDescription());
//        holder.windSpeedLabel.setText(item.getDescription());
//        holder.humidityLabel.setText(item.getDescription());

        // отрабатывает при необходимости нарисовать карточку
        Log.d("HistoryEntryAdapter", "onBindViewHolder");
    }

    // Вернуть размер данных
    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
