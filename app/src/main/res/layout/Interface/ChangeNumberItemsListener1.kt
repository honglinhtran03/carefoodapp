package com.example.projectfood.Interface

import android.widget.Toast
import com.example.projectfood.Interface.ChangeNumberItemsListener
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.graphics.Bitmap.CompressFormat
import android.text.TextUtils
import com.google.gson.Gson
import android.content.SharedPreferences.OnSharedPreferenceChangeListener

interface ChangeNumberItemsListener {
    fun changed()
}