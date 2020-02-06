package ru.systempla.weatherapp.mvp.view.ui.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding2.view.RxView;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.systempla.weatherapp.R;
import ru.systempla.weatherapp.mvp.presenter.IForecastListPresenter;
import ru.systempla.weatherapp.mvp.view.list.ForecastItemView;

public class ForecastRVAdapter extends RecyclerView.Adapter<ForecastRVAdapter.ViewHolder> {
    private IForecastListPresenter presenter;

    public ForecastRVAdapter(IForecastListPresenter presenter) {
        this.presenter = presenter;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.pos = position;
        presenter.bind(holder);
        RxView.clicks(holder.itemView).map(o -> holder).subscribe(presenter.getClickSubject());
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ForecastItemView {

        int pos = 0;

        @BindView(R.id.rv_date_time)
        TextView rvDateTimeTV;

        @BindView(R.id.rv_description)
        TextView rvDescriptionTV;

        @BindView(R.id.rv_forecast_temp)
        TextView rvTemperatureTV;

        @BindView(R.id.rv_forecast_icon)
        ImageView rvIconIV;

        @BindDrawable(R.drawable.ic_sun)
        Drawable iconSun;

        @BindDrawable(R.drawable.ic_moon)
        Drawable iconMoon;

        @BindDrawable(R.drawable.ic_snow)
        Drawable iconSnow;

        @BindDrawable(R.drawable.ic_fog)
        Drawable iconFog;

        @BindDrawable(R.drawable.ic_rain)
        Drawable iconRain;

        @BindDrawable(R.drawable.ic_cloud)
        Drawable iconCloud;

        @BindDrawable(R.drawable.ic_thunderstorm)
        Drawable iconThunderstorm;

        @BindDrawable(R.drawable.ic_drizzle)
        Drawable iconDrizzle;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public int getPos() {
            return pos;
        }

        @Override
        public void setDateTime(long dt) {
            DateFormat dateFormat = DateFormat.getDateTimeInstance();
            String updateOn = dateFormat.format(new Date(dt * 1000));
            rvDateTimeTV.setText(updateOn);
        }

        @Override
        public void setTemperature(float temp) {
            String currentTextText = String.format(Locale.getDefault(), "%.0f", temp);
            rvTemperatureTV.setText(currentTextText);
        }

        @Override
        public void setWeatherDescription(String description) {
            rvDescriptionTV.setText(description.toLowerCase());
        }


        @Override
        public void setWeatherIcon(int actualId, String iconCode) {
            int id = actualId / 100;
            Drawable icon = null;

            if (actualId == 800) {
                if (iconCode.contains("d")) {
                    icon = iconSun;
                } else {
                    icon = iconMoon;
                }
            } else {
                switch (id) {
                    case 2: {
                        icon = iconThunderstorm;
                        break;
                    }
                    case 3: {
                        icon = iconDrizzle;
                        break;
                    }
                    case 5: {
                        icon = iconRain;
                        break;
                    }
                    case 6: {
                        icon = iconSnow;
                        break;
                    }
                    case 7: {
                        icon = iconFog;
                        break;
                    }
                    case 8: {
                        icon = iconCloud;
                        break;
                    }
                }
            }
            rvIconIV.setImageDrawable(icon);
        }
    }
}
