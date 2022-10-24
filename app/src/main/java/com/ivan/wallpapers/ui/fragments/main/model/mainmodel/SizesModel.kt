package com.ivan.wallpapers.ui.fragments.main.model.mainmodel

import androidx.room.*
import com.ivan.wallpapers.dry.DateConverter
import java.time.OffsetDateTime
import java.util.Date

@Entity
data class SizesModel(
   @ColumnInfo val type:String,
   @ColumnInfo val url:String,
   @ColumnInfo val id:String,
   @ColumnInfo val dateAdded:Date?,
   @PrimaryKey(autoGenerate = true) val autoId:Int)