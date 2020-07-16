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
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
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

    private val permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    )

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
        Dexter.withActivity(this)
                .withPermissions(permissions)
                .withListener(object: MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if (report?.deniedPermissionResponses.isNullOrEmpty()) presenter.startGPSUpdate()
                        else Toast.makeText(applicationContext, "Location permission refused", Toast.LENGTH_SHORT).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                        val permissionsToRational : ArrayList<String> = ArrayList()
                        permissions?.forEach { permissionRequest -> permissionsToRational.add(permissionRequest.name) }
                        showPermissionDialog(permissionsToRational.toArray(), "location", GEOLOCATION_REQUEST_UPDATE)
                    }
                })
                .check()
    }



    //    Code from tutorial (should be reformed to meet weatherapp needs)
    private fun checkForPermission(permission: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED
                -> Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()
                shouldShowRequestPermissionRationale(permission) -> showPermissionDialog(permission, name, requestCode)
                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        fun innerCheck(name: String): Boolean {
            return if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "$name permission refused", Toast.LENGTH_SHORT).show()
                false
            } else {
                Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()
                true
            }
        }

        when (requestCode) {
            GEOLOCATION_REQUEST_UPDATE -> if (innerCheck("location")) presenter.startGPSUpdate()
            GEOLOCATION_REQUEST_WD -> if (innerCheck("location")) presenter.startGPSUpdate()
            GEOLOCATION_REQUEST_FR -> if (innerCheck("location")) presenter.startGPSUpdate()
        }
    }
    private fun showPermissionDialog(permission: Array<(out)String>!, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(this@MainActivity, permission, requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

//    End of the tutorial code block

}
