package ru.systempla.weatherapp.mvp.view.ui.fragment

import android.Manifest
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import butterknife.*
import com.tbruyelle.rxpermissions3.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

    @InjectPresenter
    lateinit var presenter: WeatherDataPresenter

    @BindView(R.id.loading)
    lateinit var loadingRelativeLayout: RelativeLayout

    @BindView(R.id.optionHitBoxExtender)
    lateinit var popupMenuButton: View

    @BindView(R.id.n_city_label)
    lateinit var cityLabel: TextView

    @BindView(R.id.n_humidity_value)
    lateinit var humidityValue: TextView

    @BindView(R.id.n_pressure_value)
    lateinit var pressureValue: TextView

    @BindView(R.id.n_uv_value)
    lateinit var uvValue: TextView

    @BindView(R.id.n_weather_desc_value)
    lateinit var weatherDescValue: TextView

    @BindView(R.id.n_weather_image)
    lateinit var weatherImage: ImageView

    @BindView(R.id.n_wind_speed_value)
    lateinit var windSpeedValue: TextView

    @BindView(R.id.n_temperature_value)
    lateinit var temperatureValue: TextView

    @BindView(R.id.drawer_button)
    lateinit var drawerButton: ImageView

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

    @OnClick(R.id.optionHitBoxExtender)
    fun showMenu(v: View?) {
        showPopupMenu(v!!)
    }

    @OnClick(R.id.drawer_button)
    fun showDrawer() {
        drawer.openDrawer(GravityCompat.START)
    }

    @ProvidePresenter
    fun providePresenter(): WeatherDataPresenter {
        val presenter = WeatherDataPresenter(AndroidSchedulers.mainThread(), Schedulers.io())
        App.instance.appComponent.inject(presenter)
        return presenter
    }

    private lateinit var rxPermissions: RxPermissions
    private lateinit var unbinder: Unbinder
    private lateinit var drawer: DrawerLayout


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
                    presenter.setPermissionsSetting(1)
                    presenter.setCitySetting("gps")
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
        App.instance.appComponent.inject(this)
        unbinder = ButterKnife.bind(this, view)
        rxPermissions = RxPermissions(this)
        drawer = requireActivity().findViewById(R.id.main_drawer_layout)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
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
            presenter.setCitySetting(input.text.toString())
            presenter.loadAccordingToSettings()
        }
        builder.show()
    }

    override fun checkForGPSUpdate() {
        rxPermissions.requestEachCombined(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe { permission ->
                    when {
                        permission.granted -> presenter.loadGPSData()
                        permission.shouldShowRequestPermissionRationale ->
                            showMessage("Location permission refused temporally")
                        else -> {
                            presenter.setPermissionsSetting(0)
                            showMessage("Location permission refused permanently")
                        }
                    }
                }
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