package m.tech.basemvvm.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "post")
data class Post(
    @SerializedName("userId")
    @ColumnInfo
    val userId: Int,

    @SerializedName("id")
    @ColumnInfo
    @PrimaryKey
    val id: Int,

    @SerializedName("title")
    @ColumnInfo()
    val tile: String,

    @SerializedName("body")
    @ColumnInfo()
    val body: String
)