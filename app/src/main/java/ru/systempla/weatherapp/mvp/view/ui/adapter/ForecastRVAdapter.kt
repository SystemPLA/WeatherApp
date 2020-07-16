package ru.systempla.weatherapp.mvp.view.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindDrawable
import butterknife.BindView
import butterknife.ButterKnife
import com.jakewharton.rxbinding2.view.RxView
import ru.systempla.weatherapp.R
import ru.systempla.weatherapp.mvp.presenter.IForecastListPresenter
import ru.systempla.weatherapp.mvp.view.list.ForecastItemView
import java.text.DateFormat
import java.util.*

class ForecastRVAdapter(private val presenter: IForecastListPresenter) : RecyclerView.Adapter<ForecastRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        presenter.bind(holder)
        RxView.clicks(holder.itemView).map{ holder }.subscribe(presenter.clickSubject)
    }

    override fun getItemCount(): Int {
        return presenter.count
    }

    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView), ForecastItemView {
        override var pos = 0

        @BindView(R.id.rv_date_time)
        lateinit var rvDateTimeTV: TextView

        @BindView(R.id.rv_description)
        lateinit var  rvDescriptionTV: TextView

        @BindView(R.id.rv_forecast_temp)
        lateinit var  rvTemperatureTV: TextView

        @BindView(R.id.rv_forecast_icon)
        lateinit var  rvIconIV: ImageView

        @BindDrawable(R.drawable.ic_sun)
        lateinit var  iconSun: Drawable

        @BindDrawable(R.drawable.ic_moon)
        lateinit var  iconMoon: Drawable

        @BindDrawable(R.drawable.ic_snow)
        lateinit var  iconSnow: Drawable

        @BindDrawable(R.drawable.ic_fog)
        lateinit var  iconFog: Drawable

        @BindDrawable(R.drawable.ic_rain)
        lateinit var  iconRain: Drawable

        @BindDrawable(R.drawable.ic_cloud)
        lateinit var  iconCloud: Drawable

        @BindDrawable(R.drawable.ic_thunderstorm)
        lateinit var  iconThunderstorm: Drawable

        @BindDrawable(R.drawable.ic_drizzle)
        lateinit var  iconDrizzle: Drawable

        override fun setDateTime(dt: Long) {
            val dateFormat = DateFormat.getDateTimeInstance()
            val updateOn = dateFormat.format(Date(dt * 1000))
            rvDateTimeTV.text = updateOn
        }

        override fun setTemperature(temp: Float) {
            val currentTextText = String.format(Locale.getDefault(), "%.0f", temp)
            rvTemperatureTV.text = currentTextText
        }

        override fun setWeatherDescription(description: String) {
            rvDescriptionTV.text = description.toLowerCase(Locale.ROOT)
        }

        override fun setWeatherIcon(actualId: Int, code: String) {
            val id = actualId / 100
            lateinit var icon: Drawable
            if (actualId == 800) {
                icon = if (code.contains("d")) {
                    iconSun
                } else {
                    iconMoon
                }
            } else {
                when (id) {
                    2 -> {
                        icon = iconThunderstorm
                    }
                    3 -> {
                        icon = iconDrizzle
                    }
                    5 -> {
                        icon = iconRain
                    }
                    6 -> {
                        icon = iconSnow
                    }
                    7 -> {
                        icon = iconFog
                    }
                    8 -> {
                        icon = iconCloud
                    }
                }
            }
            rvIconIV.setImageDrawable(icon)
        }

        init {
            ButterKnife.bind(this, itemView)
        }
    }

}