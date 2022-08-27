package com.android.developer.sequis_test.core.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.android.developer.sequis_test.core.data.local.entities.PictureEntity
import com.google.gson.reflect.TypeToken
import com.android.developer.sequis_test.core.domain.util.JsonParser


@ProvidedTypeConverter
class PictureConverter(
    private val parser: JsonParser
) {
    @TypeConverter
    fun fromJson(json: String): List<PictureEntity.Comment> {
        return parser.fromJson<ArrayList<PictureEntity.Comment>>(
            json,
            object : TypeToken<ArrayList<PictureEntity.Comment>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toEntity(entity: List<PictureEntity.Comment>): String {
        return parser.toJson(
            entity,
            object : TypeToken<ArrayList<PictureEntity.Comment>>() {}.type
        ) ?: "[]"
    }
}