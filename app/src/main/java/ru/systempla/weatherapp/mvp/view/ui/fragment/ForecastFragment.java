package ru.systempla.weatherapp.mvp.view.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.systempla.weatherapp.R;
import ru.systempla.weatherapp.mvp.App;
import ru.systempla.weatherapp.mvp.presenter.ForecastPresenter;
import ru.systempla.weatherapp.mvp.presenter.WeatherDataPresenter;
import ru.systempla.weatherapp.mvp.view.ForecastView;
import ru.systempla.weatherapp.mvp.view.ui.adapter.ForecastRVAdapter;

public class ForecastFragment extends MvpAppCompatFragment implements ForecastView {

    @InjectPresenter
    ForecastPresenter presenter;

    @BindView(R.id.rl_loading)
    RelativeLayout loadingRelativeLayout;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @BindView(R.id.n_city_label)
    TextView cityText;

    @BindView(R.id.drawer_button)
    ImageView drawerButton;

    @BindView(R.id.n_popup_menu)
    ImageView popupMenuIcon;

    @OnClick(R.id.n_popup_menu)
    public void showMenu(View v){
        showPopupMenu(v);
    }

    @OnClick(R.id.drawer_button)
    public void showDrawer(){drawer.openDrawer(GravityCompat.START);}

    ForecastRVAdapter adapter;
    Unbinder unbinder;
    DrawerLayout drawer;

    @ProvidePresenter
    public ForecastPresenter providePresenter() {
        ForecastPresenter presenter = new ForecastPresenter(AndroidSchedulers.mainThread(), Schedulers.io());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static ForecastFragment newInstance(){
        return new ForecastFragment();
    }

    private void showPopupMenu(View v){
        PopupMenu popupMenu = new PopupMenu(this.getContext(), v);
        popupMenu.inflate(R.menu.location_change_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.change_city:
                    showInputDialog();
                    return true;
                case R.id.change_to_gps:
                    presenter.setSetting("gps");
                    presenter.loadAccordingToSettings();
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(R.string.change_city);

        final EditText input = new EditText(this.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.setSetting(input.getText().toString());
                presenter.loadAccordingToSettings();
            }
        });
        builder.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forcast, container, false);
        unbinder = ButterKnife.bind(this, view);
        App.getInstance().getAppComponent().inject(this);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.main_drawer_layout);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.location_change_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.checkSettings();
        presenter.loadAccordingToSettings();
    }

    @Override
    public void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ForecastRVAdapter(presenter.getForecastListPresenter());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showLoading() {
        loadingRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingRelativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(this.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setCity(String city){
        cityText.setText(city);
    }
}
