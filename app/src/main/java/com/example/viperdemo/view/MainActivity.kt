package com.example.viperdemo.view

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.viperdemo.BaseApplication
import com.example.viperdemo.R
import com.example.viperdemo.contracts.MainContract
import com.example.viperdemo.databinding.ActivityMainBinding
import com.example.viperdemo.entity.Article
import com.example.viperdemo.presenter.MainPresenter
import org.jetbrains.anko.toast
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Forward

class MainActivity : AppCompatActivity(), MainContract.View {

    private var presenter: MainContract.Presenter? = null
    private lateinit var binding: ActivityMainBinding
    private val recyclerView: RecyclerView by lazy { binding.newsFeed }
    private val progressBar: ProgressBar by lazy { binding.newsLoading }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        presenter = MainPresenter(this, applicationContext)
        recyclerView.adapter = ArticlesAdapter(ArticlesAdapter.OnClickListener {
            presenter?.articleSelected(it)
        })
    }

    private val navigator: Navigator? by lazy {
        Navigator { command ->
            if (command is Forward) {
                val article = (command.transitionData as Article)
                when (command.screenKey) {
                    ReaderActivity.TAG -> startActivity(
                        Intent(this@MainActivity, ReaderActivity::class.java)
                            .putExtra("data", article as Parcelable))
                    else -> Log.e("Router", "Unknown screen: " + command.screenKey)
                }
            }
        }
    }

    override fun showLoading() {
        recyclerView.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        recyclerView.isEnabled = true
        progressBar.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        BaseApplication.INSTANCE.baseRouter.navigatorHolder.removeNavigator()
    }

    override fun onResume() {
        super.onResume()
        presenter?.onViewCreated()
        BaseApplication.INSTANCE.baseRouter.navigatorHolder.setNavigator(navigator)
    }

    override fun showArticles(data: List<Article>) {
        (recyclerView.adapter as ArticlesAdapter).submitList(data)
    }

    override fun showError(msg: String) {
        toast(msg)
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        presenter = null
        super.onDestroy()
    }

}