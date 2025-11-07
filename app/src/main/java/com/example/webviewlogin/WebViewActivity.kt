package com.example.webviewlogin

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.webviewlogin.databinding.ActivityWebviewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding
    private var username: String? = null
    private var token: String? = null

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_TOKEN = "extra_token"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 取得傳入的使用者資訊
        username = intent.getStringExtra(EXTRA_USERNAME)
        token = intent.getStringExtra(EXTRA_TOKEN)

        setupViews()
        setupWebView()
    }

    private fun setupViews() {
        binding.userInfoTextView.text = "使用者: $username"

        binding.loadButton.setOnClickListener {
            val url = binding.urlEditText.text.toString()
            if (url.isNotBlank()) {
                loadUrl(url)
            } else {
                Toast.makeText(this, "請輸入網址", Toast.LENGTH_SHORT).show()
            }
        }

        binding.logoutButton.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.builtInZoomControls = true
            settings.displayZoomControls = false

            // WebViewClient 用於處理頁面載入
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.webProgressBar.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.webProgressBar.visibility = View.GONE
                    binding.urlEditText.setText(url)
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    return false
                }
            }

            // WebChromeClient 用於處理進度條
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    binding.webProgressBar.progress = newProgress
                }
            }
        }

        // 載入預設網址
        val defaultUrl = binding.urlEditText.text.toString()
        if (defaultUrl.isNotBlank()) {
            loadUrl(defaultUrl)
        }
    }

    private fun loadUrl(url: String) {
        var urlToLoad = url
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            urlToLoad = "https://$url"
        }
        binding.webView.loadUrl(urlToLoad)
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
