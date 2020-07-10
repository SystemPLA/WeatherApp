package ru.systempla.weatherapp.mvp.view.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.systempla.weatherapp.mvp.App
import ru.systempla.weatherapp.mvp.presenter.ForecastPresenter
import ru.systempla.weatherapp.mvp.view.ForecastView
import ru.systempla.weatherapp.mvp.view.ui.adapter.ForecastRVAdapter

class ForecastFragment : MvpAppCompatFragment(), ForecastView {
    @InjectPresenter
    var presenter: ForecastPresenter? = null

    @BindString(R.string.language)
    var language: String? = null

    @BindView(R.id.rl_loading)
    var loadingRelativeLayout: RelativeLayout? = null

    @BindView(R.id.rv)
    var recyclerView: RecyclerView? = null

    @BindView(R.id.n_city_label)
    var cityText: TextView? = null

    @BindView(R.id.drawer_button)
    var drawerButton: ImageView? = null

    @BindView(R.id.optionHitBoxExtender2)
    var popupMenuIcon: View? = null

    @OnClick(R.id.optionHitBoxExtender2)
    fun showMenu(v: View) {
        showPopupMenu(v)
    }

    @OnClick(R.id.drawer_button)
    fun showDrawer() {
        drawer!!.openDrawer(GravityCompat.START)
    }

    private var adapter: ForecastRVAdapter? = null
    private var unbinder: Unbinder? = null
    private var drawer: DrawerLayout? = null

    @ProvidePresenter
    fun providePresenter(): ForecastPresenter {
        val presenter = ForecastPresenter(AndroidSchedulers.mainThread(), Schedulers.io())
        App.getInstance().getAppComponent().inject(presenter)
        return presenter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_forcast, container, false)
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

    override fun onResume() {
        super.onResume()
        presenter.setLanguage(language)
        presenter.checkSettings()
        presenter.loadAccordingToSettings()
    }

    fun init() {
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        adapter = ForecastRVAdapter(presenter.getForecastListPresenter())
        recyclerView!!.adapter = adapter
    }

    fun showLoading() {
        loadingRelativeLayout!!.visibility = View.VISIBLE
    }

    fun hideLoading() {
        loadingRelativeLayout!!.visibility = View.GONE
    }

    fun showMessage(text: String?) {
        Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
    }

    fun updateList() {
        adapter.notifyDataSetChanged()
    }

    fun setCity(city: String?) {
        cityText!!.text = city
    }

    companion object {
        fun newInstance(): ForecastFragment {
            return ForecastFragment()
        }
    }
}