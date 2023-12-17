package com.example.drinkapp.screen.client.ggmap

import android.content.pm.PackageManager
import android.net.Uri
import android.view.KeyEvent
import android.webkit.*
import androidx.core.app.ActivityCompat
import com.example.drinkapp.databinding.ClientActivityGgmapBinding
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseActivity


class GgMapActivity :
    BaseActivity<ClientActivityGgmapBinding>(ClientActivityGgmapBinding::inflate) {
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private var url_address: String = Constant.KEY_CONSTANT_KEY_QUERY_GG_MAP
    private var uri_address: String = Constant.KEY_CONSTANT_KEY_CHECK_GG_MAP

    override fun initView() {
        val AddressName = intent.getSerializableExtra(Constant.KEY_CONSTANT_KEY_ADDRESS_NAME) as String
        if(AddressName!=null){
            url_address = url_address + AddressName
            uri_address = uri_address + AddressName
        }

        binding.apply {
            val webSettings: WebSettings = webview.settings
            // Kích hoạt JavaScript
            webSettings.javaScriptEnabled = true

            // Cho phép zoom
            webSettings.setSupportZoom(true)
            webSettings.builtInZoomControls = true

            // Thiết lập mã hóa văn bản mặc định
            webSettings.defaultTextEncodingName = "UTF-8"

            // ngăn chặn cửa sổ pop-up và cửa sổ mới mở trong WebView
            webSettings.setSupportMultipleWindows(false)

            // Kích hoạt caching
            webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

            // Thêm quyền truy cập vị trí
            webSettings.setGeolocationEnabled(true)

            webview.webViewClient = MyWebViewClient()
            webview.webChromeClient = MyWebChromeClient()
        }
    }

    override fun initData() {
        binding.apply {
            // Check quyền truy cập vị trí
            if (ActivityCompat.checkSelfPermission(
                    this@GgMapActivity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Nếu không có quyền, yêu cầu quyền
                ActivityCompat.requestPermissions(
                    this@GgMapActivity,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            } else {
                // Nếu đã có quyền, load trang web
                webview.loadUrl(url_address)
            }
        }
    }

    override fun handleEvent() {
        // TODO("Not yet implemented")
    }

    private class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val url = request?.url.toString()
            if (isLocationUrl(url)) {
                // Xử lý điểm địa lý tại đây nếu cần thiết
                handleLocationUrl(url)
                return true
            } else {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        private fun isLocationUrl(url: String): Boolean {
            // Kiểm tra xem URL có phải là URL của điểm địa lý không
            return Uri.parse(url).host == Constant.KEY_CONSTANT_KEY_CHECK_GG_MAP
        }

        private fun handleLocationUrl(url: String) {
            // Thực hiện xử lý cho điểm địa lý tại đây
            // Ví dụ: Hiển thị thông báo, gửi dữ liệu địa lý cho một service, v.v.
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            // Thực hiện xử lý khi trang web đã tải xong
            // Ví dụ: ẩn hiển thị loading, thực hiện các tương tác khác
        }
    }

    private class MyWebChromeClient : WebChromeClient() {
        override fun onGeolocationPermissionsShowPrompt(
            origin: String?,
            callback: GeolocationPermissions.Callback?
        ) {
            // Xử lý quyền truy cập vị trí
            callback?.invoke(origin, true, false)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webview.canGoBack()) {
            binding.webview.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Quyền truy cập vị trí đã được cấp, load trang web
                    binding.webview.loadUrl(url_address)
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}