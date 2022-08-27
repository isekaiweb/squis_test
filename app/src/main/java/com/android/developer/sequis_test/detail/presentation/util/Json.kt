package com.android.developer.sequis_test.detail.presentation.util

import android.content.Context
import androidx.annotation.RawRes
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader


fun Context.listJson(@RawRes jsonRaw: Int): List<String> {
    val list = mutableListOf<String>()
    val jsonArray = loadJsonArray(jsonRaw)

    jsonArray.let {
        for (i in 0 until it.length()) {
            list.add(it.getString(i))
        }
    }

    return list
}


private fun Context.loadJsonArray(@RawRes jsonRaw: Int): JSONArray {
    val builder = StringBuilder()
    val `in` = resources.openRawResource(jsonRaw)


    val reader = BufferedReader(InputStreamReader(`in`))
    var line: String?
    while (reader.readLine().also { line = it } != null) {
        builder.append(line)
    }
    return JSONArray(builder.toString())
}
