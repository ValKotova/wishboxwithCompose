package com.valkotova.wishboxwithcompose.domain.model.common

import com.valkotova.wishboxwithcompose.data.model.common.LinkInfoData

data class LinkInfo(
    val name: String,
    val link: String
)

fun LinkInfoData.to_LinkInfo() = LinkInfo(
    name = this.name,
    link = this.link
)