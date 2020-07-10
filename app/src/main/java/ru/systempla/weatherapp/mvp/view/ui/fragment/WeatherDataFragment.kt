package ru.systempla.weatherapp.mvp.view.ui.fragment

import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import butterknife.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.systempla.weatherapp.mvp.App
import ru.systempla.weatherapp.mvp.presenter.WeatherDataPresenter
import ru.systempla.weatherapp.mvp.view.WeatherDataView
import java.util.*

class WeatherDataFragment : MvpAppCompatFragment(), WeatherDataView {
    private var unbinder: Unbinder? = null
    private var drawer: DrawerLayout? = null

    @InjectPresenter
    var presenter: WeatherDataPresenter? = null

    @BindString(R.string.language)
    var language: String? = null

    @BindString(R.string.wind_speed)
    var speedUnit: String? = null

    @BindString(R.string.pressure_unit)
    var pressureUnit: String? = null

    @BindView(R.id.loading)
    var loadingRelativeLayout: RelativeLayout? = null

    @BindView(R.id.optionHitBoxExtender)
    var popupMenuButton: View? = null

    @BindView(R.id.n_city_label)
    var cityLabel: TextView? = null

    @BindView(R.id.n_humidity_value)
    var humidityValue: TextView? = null

    @BindView(R.id.n_pressure_value)
    var pressureValue: TextView? = null

    @BindView(R.id.n_uv_value)
    var uvValue: TextView? = null

    @BindView(R.id.n_weather_desc_value)
    var weatherDescValue: TextView? = null

    @BindView(R.id.n_weather_image)
    var weatherImage: ImageView? = null

    @BindView(R.id.n_wind_speed_value)
    var windSpeedValue: TextView? = null

    @BindView(R.id.n_temperature_value)
    var temperatureValue: TextView? = null

    @BindView(R.id.drawer_button)
    var drawerButton: ImageView? = null

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

    @OnClick(R.id.optionHitBoxExtender)
    fun showMenu(v: View) {
        showPopupMenu(v)
    }

    @OnClick(R.id.drawer_button)
    fun showDrawer() {
        drawer!!.openDrawer(GravityCompat.START)
    }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(this.context!!, v)
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

    override fun onResume() {
        super.onResume()
        presenter.setLanguage(language)
        presenter.checkSettings()
        presenter.loadAccordingToSettings()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_weather_data, container, false)
        unbinder = ButterKnife.bind(this, view)
        App.getInstance().getAppComponent().inject(this)
        drawer = activity!!.findViewById(R.id.main_drawer_layout)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.location_change_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun showInputDialog() {
        val builder = AlertDialog.Builder(this.context!!)
        builder.setTitle(R.string.change_city)
        val input = EditText(this.context)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->
            presenter.setSetting(input.text.toString())
            presenter.loadAccordingToSettings()
        }
        builder.show()
    }

    @ProvidePresenter
    fun providePresenter(): WeatherDataPresenter {
        val presenter = WeatherDataPresenter(AndroidSchedulers.mainThread(), Schedulers.io())
        App.getInstance().getAppComponent().inject(presenter)
        return presenter
    }

    fun showMessage(text: String?) {
        Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
    }

    fun setCityName(name: String?) {
        cityLabel!!.text = name
    }

    fun setWeatherDescription(description: String) {
        weatherDescValue!!.text = description.toLowerCase()
    }

    fun setUVIndex(uvIndex: Float) {
        val currentTextText = String.format(Locale.getDefault(), "%.2f", uvIndex)
        uvValue!!.text = currentTextText
    }

    fun setPressure(pressure: Float) {
        if (language == "en") {
            pressureValue!!.text = String.format("%s $pressureUnit", pressure)
        }
        if (language == "ru") {
            val newPressure = pressure * 0.75006375541921
            pressureValue!!.text = String.format("%.0f $pressureUnit", newPressure)
        }
    }

    fun setHumidity(humidity: Float) {
        humidityValue!!.text = String.format("%s %%", humidity)
    }

    fun setWindSpeed(speed: Double) {
        windSpeedValue!!.text = String.format("%s $speedUnit", speed)
    }

    fun setWeatherIcon(actualId: Int, sunrise: Long, sunset: Long) {
        val id = actualId / 100
        var icon: Drawable? = null
        if (actualId == 800) {
            val currentTime = Date().time
            icon = if (currentTime >= sunrise && currentTime < sunset) {
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
        weatherImage!!.setImageDrawable(icon)
    }

    fun setCurrentTemperature(temp: Float) {
        val currentTextText = String.format(Locale.getDefault(), "%.0f", temp)
        temperatureValue!!.text = currentTextText
    }

    fun showLoading() {
        loadingRelativeLayout!!.visibility = View.VISIBLE
    }

    fun hideLoading() {
        loadingRelativeLayout!!.visibility = View.GONE
    }

    companion object {
        fun newInstance(): WeatherDataFragment {
            return WeatherDataFragment()
        }
    }
}