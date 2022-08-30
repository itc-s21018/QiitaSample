package jp.ac.it_college.std.s21018.qiitasample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wikiclient.ui.ArticleListAdapter
import com.example.wikiclient.ui.MainViewModel
import com.example.wikiclient.ui.model.Article
import jp.ac.it_college.std.s21018.qiitasample.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val articleListAdapter = ArticleListAdapter()

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initArticleList()
        observeArticles()
        initButton()

        viewModel.fetchArticles("Android", 20)
    }

    private fun initButton() {
        binding.button.setOnClickListener {
            // 検索ボタンクリック時の処理
            val formText = binding.form.text.toString()
            if (formText.isNotEmpty()) {
                viewModel.fetchArticles(formText, 20)
            }
        }
    }

    private fun initArticleList() {
        binding.art.apply {
            adapter = articleListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun updateArticleList() {

        val articles: List<Article> = (0..10).map { number ->
            Article(number, "記事$number", "https://s3-ap-northeast-1.amazonaws.com/qiita-image-store/0/155135/0c2db45b0bd4b1aa023f5a7da835b76c2d191bd4/x_large.png?1585895165")
        }

        articleListAdapter.submitList(articles)
    }

    private fun observeArticles() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articles.collect { articles ->

                    updateArticleList(articles)
                }
            }
        }
    }
    private fun updateArticleList(articles: List<Article>) {
        articleListAdapter.submitList(articles)
    }
}