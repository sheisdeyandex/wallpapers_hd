package com.wallpapers.hd.ui.fragments.main.model.mainmodel

import androidx.room.*
import java.util.Date

@Entity
data class SizesModel(
   @ColumnInfo val type:String,
   @ColumnInfo val url:String,
   @ColumnInfo val id:String,
   @ColumnInfo val dateAdded:Date?,
   @PrimaryKey(autoGenerate = true) val autoId:Int)