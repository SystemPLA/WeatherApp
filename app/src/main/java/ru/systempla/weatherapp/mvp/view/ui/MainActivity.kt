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
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    @BindView(R.id.main_drawer_layout)
    lateinit var drawer: DrawerLayout
    @BindView(R.id.nav_view)
    lateinit var navigationView: NavigationView

    private val navigator: Navigator = SupportAppNavigator(this, R.id.content)

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        val presenter = MainPresenter()
        App.instance.appComponent.inject(presenter)
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this);
        initSideMenu()
        if (savedInstanceState == null) {
            router.replaceScreen(WeatherDataScreen())
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
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
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun checkGeolocationPermission() {
        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            getPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    override fun openNavDrawer() {
        drawer.openDrawer(GravityCompat.START)
    }

    private fun initSideMenu() {
        val toggle = ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val id = menuItem.itemId
        when (menuItem.itemId) {
            R.id.nav_weather_data -> presenter.navigateToWeatherData()
            R.id.nav_forecast -> presenter.navigateToForecast()
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    private fun checkPermission(vararg permissions: String): Boolean {
        var flag = true
        for (permission in permissions) {
            flag = flag and (ActivityCompat.checkSelfPermission(App.instance, permission) == PackageManager.PERMISSION_GRANTED)
        }
        return flag
    }


    private fun getPermission(vararg permissions: String) {
        ActivityCompat.requestPermissions(this, permissions, 100)
    }
}