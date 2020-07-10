package ru.systempla.weatherapp.mvp.view.ui.fragment

import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import butterknife.BindDrawable
import butterknife.BindString
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_weather_data.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.systempla.weatherapp.R
import ru.systempla.weatherapp.mvp.App
import ru.systempla.weatherapp.mvp.presenter.WeatherDataPresenter
import ru.systempla.weatherapp.mvp.view.WeatherDataView
import java.util.*

class WeatherDataFragment : MvpAppCompatFragment(), WeatherDataView {

    companion object {
        fun newInstance(): WeatherDataFragment = WeatherDataFragment()
    }

    private lateinit var drawer: DrawerLayout
    private lateinit var loadingRelativeLayout: RelativeLayout
    private lateinit var popupMenuButton: View
    private lateinit var cityLabel: TextView
    private lateinit var humidityValue: TextView
    private lateinit var pressureValue: TextView
    private lateinit var uvValue: TextView
    private lateinit var weatherDescValue: TextView
    private lateinit var weatherImage: ImageView
    private lateinit var windSpeedValue: TextView
    private lateinit var temperatureValue: TextView
    private lateinit var drawerButton: ImageView

    @InjectPresenter
    lateinit var presenter: WeatherDataPresenter

    @BindString(R.string.language)
    lateinit var language: String

    @BindString(R.string.wind_speed)
    lateinit var speedUnit: String

    @BindString(R.string.pressure_unit)
    lateinit var pressureUnit: String

    @BindDrawable(R.drawable.ic_sun)
    lateinit var iconSun: Drawable

    @BindDrawable(R.drawable.ic_moon)
    lateinit var iconMoon: Drawable

    @BindDrawable(R.drawable.ic_snow)
    lateinit var iconSnow: Drawable

    @BindDrawable(R.drawable.ic_fog)
    lateinit var iconFog: Drawable

    @BindDrawable(R.drawable.ic_rain)
    lateinit var iconRain: Drawable

    @BindDrawable(R.drawable.ic_cloud)
    lateinit var iconCloud: Drawable

    @BindDrawable(R.drawable.ic_thunderstorm)
    lateinit var iconThunderstorm: Drawable

    @BindDrawable(R.drawable.ic_drizzle)
    lateinit var iconDrizzle: Drawable

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.location_change_menu)
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.change_city -> {
                    showInputDialog()
                    return@setOnMenuItemClickListener true
                }
                R.id.change_to_gps -> {
                    presenter.setSetting("gps")
                    presenter.loadAccordingToSettings()
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
        popupMenu.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun setup() {
        optionHitBoxExtender.setOnClickListener {view -> showPopupMenu(view)}
        drawer_button.setOnClickListener {drawer.openDrawer(GravityCompat.START)}

        loadingRelativeLayout = loading
        popupMenuButton = optionHitBoxExtender
        cityLabel = n_city_label
        humidityValue = n_humidity_value
        pressureValue = n_pressure_value
        uvValue = n_uv_value
        weatherDescValue = n_weather_desc_value
        weatherImage = n_weather_image
        windSpeedValue = n_wind_speed_value
        temperatureValue = n_temperature_value
        drawerButton = drawer_button
        drawer = main_drawer_layout
    }

    override fun onResume() {
        super.onResume()
        presenter.setLanguage(language)
        presenter.checkSettings()
        presenter.loadAccordingToSettings()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_weather_data, container, false)
        App.instance.appComponent.inject(this)
        setup()
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.location_change_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun showInputDialog() {
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle(R.string.change_city)
        val input = EditText(this.context)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton("OK") { _: DialogInterface?, _: Int ->
            presenter.setSetting(input.text.toString())
            presenter.loadAccordingToSettings()
        }
        builder.show()
    }

    @ProvidePresenter
    fun providePresenter(): WeatherDataPresenter {
        val presenter = WeatherDataPresenter(AndroidSchedulers.mainThread(), Schedulers.io())
        App.instance.appComponent.inject(presenter)
        return presenter
    }

    override fun showMessage(text: String?) {
        Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
    }

    override fun setCityName(name: String) {
        cityLabel.text = name
    }

    override fun setWeatherDescription(description: String) {
        weatherDescValue.text = description.toLowerCase(Locale.ROOT)
    }

    override fun setUVIndex(uvIndex: Float) {
        val currentTextText = String.format(Locale.getDefault(), "%.2f", uvIndex)
        uvValue.text = currentTextText
    }

    override fun setPressure(pressure: Float) {
        if (language == "en") {
            pressureValue.text = String.format("%s $pressureUnit", pressure)
        }
        if (language == "ru") {
            val newPressure = pressure * 0.75006375541921
            pressureValue.text = String.format("%.0f $pressureUnit", newPressure)
        }
    }

    override fun setHumidity(humidity: Float) {
        humidityValue.text = String.format("%s %%", humidity)
    }

    override fun setWindSpeed(speed: Double) {
        windSpeedValue.text = String.format("%s $speedUnit", speed)
    }

    override fun setWeatherIcon(actualId: Int, sunrise: Long, sunset: Long) {
        val id = actualId / 100
        var icon: Drawable? = null
        if (actualId == 800) {
            val currentTime = Date().time
            icon = if (currentTime in sunrise until sunset) {
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
        weatherImage.setImageDrawable(icon)
    }

    override fun setCurrentTemperature(temp: Float) {
        val currentTextText = String.format(Locale.getDefault(), "%.0f", temp)
        temperatureValue.text = currentTextText
    }

    override fun showLoading() {
        loadingRelativeLayout.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingRelativeLayout.visibility = View.GONE
    }
}