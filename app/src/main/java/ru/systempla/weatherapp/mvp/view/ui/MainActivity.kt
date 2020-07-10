package ru.systempla.weatherapp.mvp.view.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.navigation.NavigationView
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.systempla.weatherapp.R
import ru.systempla.weatherapp.mvp.App
import ru.systempla.weatherapp.mvp.presenter.MainPresenter
import ru.systempla.weatherapp.mvp.view.MainView
import ru.systempla.weatherapp.navigation.Screens.WeatherDataScreen
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView, NavigationView.OnNavigationItemSelectedListener {
    @InjectPresenter
    var presenter: MainPresenter? = null

    @Inject
    var router: Router? = null

    @Inject
    var navigatorHolder: NavigatorHolder? = null

    @BindView(R.id.main_drawer_layout)
    var drawer: DrawerLayout? = null

    @BindView(R.id.nav_view)
    var navigationView: NavigationView? = null
    var navigator: Navigator = SupportAppNavigator(this, R.id.content)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getInstance().getAppComponent().inject(this)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        initSideMenu()
        if (savedInstanceState == null) {
            router!!.replaceScreen(WeatherDataScreen())
        }
    }

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        val presenter = MainPresenter()
        App.getInstance().getAppComponent().inject(presenter)
        return presenter
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder!!.setNavigator(navigator)
    }

    override fun onResume() {
        super.onResume()
        presenter.startGPSUpdate()
    }

    override fun onStop() {
        presenter.stopGPSUpdate()
        super.onStop()
    }

    override fun onPause() {
        navigatorHolder!!.removeNavigator()
        super.onPause()
    }

    fun checkGeolocationPermission() {
        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            getPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    fun openNavDrawer() {
        drawer!!.openDrawer(GravityCompat.START)
    }

    private fun initSideMenu() {
        val toggle = ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer!!.addDrawerListener(toggle)
        toggle.syncState()
        navigationView!!.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val id = menuItem.itemId
        if (id == R.id.nav_weather_data) {
            presenter.navigateToWeatherData()
        } else if (id == R.id.nav_forecast) {
            presenter.navigateToForecast()
        }
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }

    fun checkPermission(permission: String?): Boolean {
        ActivityCompat.checkSelfPermission(App.getInstance(), permission!!)
        return ActivityCompat.checkSelfPermission(App.getInstance(), permission) == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermission(vararg permissions: String?): Boolean {
        var flag = true
        for (permission in permissions) {
            flag = flag and (ActivityCompat.checkSelfPermission(App.getInstance(), permission!!) == PackageManager.PERMISSION_GRANTED)
        }
        return flag
    }

    fun getPermission(permission: String) {
        ActivityCompat.requestPermissions(this, arrayOf(permission), 100)
    }

    fun getPermission(vararg permissions: String?) {
        ActivityCompat.requestPermissions(this, permissions, 100)
    }
}