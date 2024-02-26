package com.manage.videoeditor.core

class Settings(val defaultFeedUrls: Set<String>) {
    fun isDefault(feedUrl: String) = defaultFeedUrls.contains(feedUrl)
}