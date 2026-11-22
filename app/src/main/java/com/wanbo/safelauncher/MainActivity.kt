package com.wanbo.safelauncher

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : Activity() {
    private val bg = Color.rgb(13, 19, 33)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(42, 32, 42, 24); setBackgroundColor(bg) }
        val title = TextView(this).apply { text = "WANBO  •  我的應用程式"; textSize = 26f; setTextColor(Color.WHITE); typeface = Typeface.DEFAULT_BOLD }
        root.addView(title)
        val date = TextView(this).apply { text = SimpleDateFormat("yyyy/MM/dd  HH:mm", Locale.TAIWAN).format(Date()); textSize = 16f; setTextColor(0xffa9bad0.toInt()); setPadding(0, 8, 0, 22) }
        root.addView(date)
        val scroll = ScrollView(this)
        val items = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
        val apps = packageManager.queryIntentActivities(intent, 0).filter { it.activityInfo.packageName != packageName }.distinctBy { it.activityInfo.packageName }.sortedBy { it.loadLabel(packageManager).toString().lowercase() }
        if (apps.isEmpty()) {
            items.addView(TextView(this).apply { text = "未找到 TV 應用程式。請按返回鍵離開。"; textSize = 20f; setTextColor(Color.WHITE) })
        }
        apps.forEach { app ->
            val name = app.loadLabel(packageManager).toString()
            val button = TextView(this).apply {
                text = name; textSize = 22f; setTextColor(Color.WHITE); gravity = Gravity.CENTER_VERTICAL
                setPadding(22, 12, 22, 12); isFocusable = true; isFocusableInTouchMode = false
                background = GradientDrawable().apply { setColor(0xff24324b.toInt()); cornerRadius = 14f }
                setOnFocusChangeListener { _, focused ->
                    background = GradientDrawable().apply { setColor(if (focused) 0xff3769b8.toInt() else 0xff24324b.toInt()); cornerRadius = 14f }
                }
                setOnClickListener {
                    try { startActivity(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER).setClassName(app.activityInfo.packageName, app.activityInfo.name).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) }
                    catch (_: Exception) { android.widget.Toast.makeText(this@MainActivity, "無法開啟 $name", android.widget.Toast.LENGTH_SHORT).show() }
                }
            }
            items.addView(button, LinearLayout.LayoutParams(-1, 64).apply { bottomMargin = 10 })
        }
        scroll.addView(items); root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        root.addView(TextView(this).apply { text = "測試版 • 不取代原廠首頁 • 按返回鍵離開"; textSize = 14f; setTextColor(0xffa9bad0.toInt()) })
        setContentView(root)
    }
}
