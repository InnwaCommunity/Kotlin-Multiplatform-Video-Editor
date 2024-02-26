package com.manage.videoeditor.core.datasource.network

import com.manage.videoeditor.core.entity.Feed

interface FeedParser {
    suspend fun parse(sourceUrl: String, xml: String, isDefault: Boolean): Feed
}