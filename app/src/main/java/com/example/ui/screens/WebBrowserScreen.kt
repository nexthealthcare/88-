package com.example.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.theme.WellnessBlueContainer
import com.example.ui.theme.WellnessBluePrimary
import com.example.ui.theme.WellnessOrangeAccent
import com.example.ui.theme.WellnessRedWarning

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebBrowserScreen(
    initialUrl: String,
    isLargeFontMode: Boolean,
    onUrlChange: (String) -> Unit
) {
    val context = LocalContext.current
    var currentUrlInput by remember { mutableStateOf(initialUrl) }
    var loadedUrl by remember { mutableStateOf(initialUrl) }
    var isLoading by remember { mutableStateOf(false) }
    var hasError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var webViewInstance by remember { mutableStateOf<WebView?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Address Bar & Controls
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = currentUrlInput,
                        onValueChange = { currentUrlInput = it },
                        placeholder = { Text("http:// 또는 https:// 입력") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri,
                            imeAction = ImeAction.Go
                        ),
                        keyboardActions = KeyboardActions(
                            onGo = {
                                var formatted = currentUrlInput.trim()
                                if (!formatted.startsWith("http://") && !formatted.startsWith("https://")) {
                                    formatted = "http://$formatted"
                                }
                                currentUrlInput = formatted
                                loadedUrl = formatted
                                onUrlChange(formatted)
                                webViewInstance?.loadUrl(formatted)
                            }
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("web_url_input"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    IconButton(
                        onClick = {
                            var formatted = currentUrlInput.trim()
                            if (!formatted.startsWith("http://") && !formatted.startsWith("https://")) {
                                formatted = "http://$formatted"
                            }
                            currentUrlInput = formatted
                            loadedUrl = formatted
                            onUrlChange(formatted)
                            webViewInstance?.loadUrl(formatted)
                        },
                        modifier = Modifier
                            .background(WellnessBluePrimary, RoundedCornerShape(12.dp))
                            .size(48.dp)
                            .testTag("web_go_button")
                    ) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "이동", tint = Color.White)
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    IconButton(
                        onClick = {
                            webViewInstance?.reload()
                        },
                        modifier = Modifier.size(44.dp)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "새로고침", tint = WellnessBluePrimary)
                    }

                    IconButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(loadedUrl))
                            try {
                                context.startActivity(intent)
                            } catch (_: Exception) {}
                        },
                        modifier = Modifier.size(44.dp)
                    ) {
                        Icon(Icons.Default.OpenInBrowser, contentDescription = "크롬 브라우저로 열기", tint = WellnessBluePrimary)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Preset Quick Link Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val presets = listOf(
                        "http://88workout.com",
                        "https://88workout.pages.dev",
                        "http://10.0.2.2:8000"
                    )
                    presets.forEach { preset ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (loadedUrl == preset) WellnessBluePrimary else WellnessBlueContainer,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    currentUrlInput = preset
                                    loadedUrl = preset
                                    onUrlChange(preset)
                                    webViewInstance?.loadUrl(preset)
                                }
                        ) {
                            Text(
                                text = preset.removePrefix("http://").removePrefix("https://"),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (loadedUrl == preset) Color.White else WellnessBluePrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = WellnessBluePrimary,
                trackColor = WellnessBlueContainer
            )
        }

        // HTTP Cleartext Guidance Banner
        Surface(
            color = WellnessOrangeAccent.copy(alpha = 0.1f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "🔓", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "HTTP 비보안 프로토콜 허용 활성화 완료 (usesCleartextTraffic=true)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = WellnessOrangeAccent
                )
            }
        }

        // Error Card if site is not yet reachable
        if (hasError) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = WellnessRedWarning)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "웹페이지에 아직 연결되지 않았습니다",
                            fontWeight = FontWeight.Bold,
                            color = WellnessRedWarning,
                            fontSize = 15.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = errorMessage ?: "도메인 DNS가 전파 중이거나 웹서버가 아직 실행되지 않았습니다.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Surface(
                        color = WellnessBlueContainer,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "💡 HTTP로 접속되지 않을 때 해결 방법:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = WellnessBluePrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "1. Cloudflare 대시보드 ➡️ SSL/TLS ➡️ 'Always Use HTTPS'를 [꺼짐(Off)]으로 설정하면 HTTP로도 접속 가능합니다.\n" +
                                      "2. Cloudflare Pages 기본 도메인인 https://88workout.pages.dev 로 먼저 테스트해보세요.\n" +
                                      "3. 도메인 등록기관에서 CNAME 등록 후 전파까지 5~10분이 소요될 수 있습니다.",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            hasError = false
                            webViewInstance?.loadUrl(loadedUrl)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = WellnessBluePrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("다시 시도하기")
                    }
                }
            }
        }

        // Real WebView displaying the site
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    WebView(ctx).apply {
                        webViewInstance = this
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.databaseEnabled = true
                        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                        settings.useWideViewPort = true
                        settings.loadWithOverviewMode = true

                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                super.onPageStarted(view, url, favicon)
                                isLoading = true
                                hasError = false
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                isLoading = false
                            }

                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                super.onReceivedError(view, request, error)
                                if (request?.isForMainFrame == true) {
                                    isLoading = false
                                    hasError = true
                                    errorMessage = error?.description?.toString() ?: "네트워크 연결 오류"
                                }
                            }
                        }

                        loadUrl(loadedUrl)
                    }
                },
                update = { wv ->
                    if (wv.url != loadedUrl && !hasError) {
                        wv.loadUrl(loadedUrl)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
