package com.example.viperdemo.view

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.viperdemo.BaseApplication
import com.example.viperdemo.R
import com.example.viperdemo.contracts.ReaderContract
import com.example.viperdemo.databinding.ActivityReaderBinding
import com.example.viperdemo.entity.Article
import com.example.viperdemo.presenter.ReaderPresenter
import org.jetbrains.anko.toast
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Back


class ReaderActivity : AppCompatActivity(), ReaderContract.View {

    private lateinit var binding: ActivityReaderBinding
    private var presenter: ReaderContract.Presenter? = null
    private val articleView: WebView by lazy { binding.articleWebPage }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_reader)
        presenter = ReaderPresenter(this)
    }

    companion object {
        const val TAG = "ReaderActivity"
    }

    private val navigator: Navigator? by lazy {
        Navigator { command ->
            if (command is Back) { finish() }
        }
    }

    override fun onPause() {
        super.onPause()
        BaseApplication.INSTANCE.baseRouter.navigatorHolder.removeNavigator()
    }

    override fun onResume() {
        super.onResume()
        supportActionBar.let {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val argument = intent
            .getParcelableExtra<Article>("data")
        argument?.let { presenter?.onViewCreated(it) }

        BaseApplication.INSTANCE.baseRouter.navigatorHolder.setNavigator(navigator)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                presenter?.onBackPressed()
                true
            }
            else -> false
        }
    }

    override fun loadArticle(url: String) {
        articleView.loadUrl(url)
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