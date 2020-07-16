package ru.systempla.weatherapp.mvp.view.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.navigation.NavigationView
import com.tbruyelle.rxpermissions3.RxPermissions
import dagger.Provides
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.systempla.weatherapp.R
import ru.systempla.weatherapp.mvp.App
import ru.systempla.weatherapp.mvp.model.final_groups.FinalGroups.Messages.GEOLOCATION_REQUEST_FR
import ru.systempla.weatherapp.mvp.model.final_groups.FinalGroups.Messages.GEOLOCATION_REQUEST_UPDATE
import ru.systempla.weatherapp.mvp.model.final_groups.FinalGroups.Messages.GEOLOCATION_REQUEST_WD
import ru.systempla.weatherapp.mvp.presenter.MainPresenter
import ru.systempla.weatherapp.mvp.view.MainView
import ru.systempla.weatherapp.navigation.Screens.WeatherDataScreen
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Singleton


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

    private val rxPermissions: RxPermissions = RxPermissions(this)

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
        if (savedInstanceState == null) router.replaceScreen(WeatherDataScreen())
    }

    private fun initSideMenu() {
        val toggle = ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onResume() {
        super.onResume()
        presenter.requestGPSUpdate()
    }

    override fun onStop() {
        presenter.stopGPSUpdate()
        super.onStop()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun openNavDrawer() {
        drawer.openDrawer(GravityCompat.START)
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

    override fun checkForGPSUpdate() {
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe { granted ->
                    if (granted) {
                        presenter.startGPSUpdate()
                    } else {
                        Toast.makeText(this, "Location permission refused", Toast.LENGTH_SHORT).show()
                    }
                }
    }

}
