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

class ForecastRVAdapter(presenter: IForecastListPresenter) : RecyclerView.Adapter<ForecastRVAdapter.ViewHolder>() {
    private val presenter: IForecastListPresenter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        presenter.bind(holder)
        RxView.clicks(holder.itemView).map<Any> { o: Any? -> holder }.subscribe(presenter.getClickSubject())
    }

    override fun getItemCount(): Int {
        return presenter.count
    }

    inner class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView), ForecastItemView {
        override var pos = 0

        @BindView(R.id.rv_date_time)
        var rvDateTimeTV: TextView? = null

        @BindView(R.id.rv_description)
        var rvDescriptionTV: TextView? = null

        @BindView(R.id.rv_forecast_temp)
        var rvTemperatureTV: TextView? = null

        @BindView(R.id.rv_forecast_icon)
        var rvIconIV: ImageView? = null

        @BindDrawable(R.drawable.ic_sun)
        var iconSun: Drawable? = null

        @BindDrawable(R.drawable.ic_moon)
        var iconMoon: Drawable? = null

        @BindDrawable(R.drawable.ic_snow)
        var iconSnow: Drawable? = null

        @BindDrawable(R.drawable.ic_fog)
        var iconFog: Drawable? = null

        @BindDrawable(R.drawable.ic_rain)
        var iconRain: Drawable? = null

        @BindDrawable(R.drawable.ic_cloud)
        var iconCloud: Drawable? = null

        @BindDrawable(R.drawable.ic_thunderstorm)
        var iconThunderstorm: Drawable? = null

        @BindDrawable(R.drawable.ic_drizzle)
        var iconDrizzle: Drawable? = null

        override fun setDateTime(dt: Long) {
            val dateFormat = DateFormat.getDateTimeInstance()
            val updateOn = dateFormat.format(Date(dt * 1000))
            rvDateTimeTV!!.text = updateOn
        }

        override fun setTemperature(temp: Float) {
            val currentTextText = String.format(Locale.getDefault(), "%.0f", temp)
            rvTemperatureTV!!.text = currentTextText
        }

        override fun setWeatherDescription(description: String) {
            rvDescriptionTV!!.text = description.toLowerCase()
        }

        override fun setWeatherIcon(actualId: Int, iconCode: String) {
            val id = actualId / 100
            var icon: Drawable? = null
            if (actualId == 800) {
                icon = if (iconCode.contains("d")) {
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
            rvIconIV!!.setImageDrawable(icon)
        }

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    init {
        this.presenter = presenter
    }
}