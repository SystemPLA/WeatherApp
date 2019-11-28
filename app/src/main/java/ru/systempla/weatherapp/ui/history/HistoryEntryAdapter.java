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

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView date;
        private final TextView cityCountry;
        private final TextView temperatureValue;
        private final TextView pressureValue;
        private final TextView windSpeedValue;
        private final TextView humidityValue;

        ViewHolder(View view) {
            super(view);

            date = view.findViewById(R.id.date_tv);
            cityCountry = view.findViewById((R.id.city_country));
            temperatureValue = view.findViewById(R.id.temperature_value);
            pressureValue = view.findViewById(R.id.pressure_value);
            windSpeedValue = view.findViewById(R.id.wind_value);
            humidityValue = view.findViewById(R.id.humidity_value);
        }
    }

    HistoryEntryAdapter(List<HistoryEntry> dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public HistoryEntryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        Log.d("HistoryEntryAdapter", "onCreateViewHolder");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        HistoryEntry item = dataSource.get(position);

        holder.date.setText(item.getDate());
        holder.cityCountry.setText(item.getCityCountry());
        holder.temperatureValue.setText(item.getTemperature());
        holder.pressureValue.setText(item.getPressure());
        holder.windSpeedValue.setText(item.getWindSpeed());
        holder.humidityValue.setText(item.getHumidity());

        Log.d("HistoryEntryAdapter", "onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
