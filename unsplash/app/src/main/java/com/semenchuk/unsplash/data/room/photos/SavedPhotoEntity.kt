package com.semenchuk.unsplash.data.room.photos

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.semenchuk.unsplash.data.retrofit.photos.models.photos.Urls
import com.semenchuk.unsplash.data.retrofit.photos.models.photos.User
import com.semenchuk.unsplash.entities.PhotoEntity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "saved_photo")
data class SavedPhotoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    override val id: String,
    @ColumnInfo(name = "background_photo")
    val background_photo: String?,
    @ColumnInfo(name = "author_photo")
    val author_photo: String?,
    @ColumnInfo(name = "author_name")
    val author_name: String?,
    @ColumnInfo(name = "nickname")
    val nickname: String?,
    @ColumnInfo(name = "comments_count")
    val comments_count: String,
    override val altDescription: String?,
    @ColumnInfo(name = "blurHash")
    override val blurHash: String,
    override val color: String?,
    override val createdAt: String?,
    @ColumnInfo(name = "user_description")
    override val description: String?,
    @ColumnInfo(name = "height")
    override val height: Int?,
    @ColumnInfo(name = "likedByUser")
    override var likedByUser: Boolean,
    @ColumnInfo(name = "likes")
    override var likes: Int?,
    override val promotedAt: String?,
    override val updatedAt: String?,
    @Embedded override val urls: Urls?,
    @Embedded override val user: User?,
    @ColumnInfo(name = "width")
    override val width: Int?,
): PhotoEntity, Parcelable