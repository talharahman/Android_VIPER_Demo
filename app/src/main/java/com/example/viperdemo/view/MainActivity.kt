package com.example.viperdemo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.viperdemo.R
import com.example.viperdemo.contracts.MainContract
import com.example.viperdemo.databinding.ActivityMainBinding
import com.example.viperdemo.entity.Article
import com.example.viperdemo.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), MainContract.View {

    private var presenter: MainContract.Presenter? = null
    private lateinit var binding: ActivityMainBinding
    private val recyclerView: RecyclerView by lazy { binding.newsFeed }
 //   private val progressBar: ProgressBar by lazy { binding.newsLoading }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        presenter = MainPresenter(this, applicationContext)
        recyclerView.adapter = ArticlesAdapter()
    }

    override fun onResume() {
        super.onResume()
        presenter?.onViewCreated()
    }

    override fun showLoading() {
        recyclerView.isEnabled = false
     //   progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        recyclerView.isEnabled = true
     //   progressBar.visibility = View.GONE
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