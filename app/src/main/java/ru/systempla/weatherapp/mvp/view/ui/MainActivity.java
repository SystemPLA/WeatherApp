package ru.systempla.weatherapp.mvp.view.ui;

import android.os.Bundle;

import javax.inject.Inject;

import butterknife.ButterKnife;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.systempla.weatherapp.R;
import ru.systempla.weatherapp.mvp.App;
import ru.systempla.weatherapp.mvp.presenter.MainPresenter;
import ru.systempla.weatherapp.mvp.view.MainView;
import ru.systempla.weatherapp.navigation.Screens;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @Inject
    Router router;

    @InjectPresenter
    MainPresenter presenter;

    @Inject
    NavigatorHolder navigatorHolder;

    Navigator navigator = new SupportAppNavigator(this, R.id.content);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getAppComponent().inject(this);
        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);

        if(savedInstanceState == null){
            router.replaceScreen(new Screens.WeatherDataScreen());
        }
    }

    @ProvidePresenter
    public MainPresenter providePresenter(){
        MainPresenter presenter = new MainPresenter();
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.startGPSUpdate();
    }

    @Override
    protected void onStop() {
        presenter.stopGPSUpdate();
        super.onStop();
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }


}