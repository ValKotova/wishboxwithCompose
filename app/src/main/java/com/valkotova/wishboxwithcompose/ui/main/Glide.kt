package com.valkotova.wishboxwithcompose.ui.main

import android.content.Context
import android.net.Uri
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.valkotova.wishboxwithcompose.BuildConfig
import com.valkotova.wishboxwithcompose.domain.model.common.ImageInfo

@GlideModule
class GlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.setIsActiveResourceRetentionAllowed(true)
    }
}

fun getGlideUrl(key: String, version: String) : String{
    return "${BuildConfig.SERVER_URL}static/${key}/${version}"
}

fun ImageInfo.getSmallUri() : Uri {
    return Uri.parse(getGlideUrl(this.key, this.versions.small))
}

fun ImageInfo.getBigUri() : Uri {
    return Uri.parse(getGlideUrl(this.key, this.versions.big))
}