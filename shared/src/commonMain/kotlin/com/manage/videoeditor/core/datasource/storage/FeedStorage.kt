package com.manage.videoeditor.core.datasource.storage

import com.manage.videoeditor.core.entity.Feed
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class FeedStorage(
    private val settings: Settings,
    private val json: Json
) {
    private companion object {
        private const val KEY_FEED_CACHE = "key_feed_cache"
    }

    private var diskCache: Map<String, Feed>
        get() {
            return settings.getStringOrNull(KEY_FEED_CACHE)?.let { str ->
                json.decodeFromString(ListSerializer(Feed.serializer()), str)
                    .associateBy { it.sourceUrl }
            } ?: mutableMapOf()
        }
        set(value) {
            val list = value.map { it.value }
            settings[KEY_FEED_CACHE] =
                json.encodeToString(ListSerializer(Feed.serializer()), list)
        }
    private val memCache: MutableMap<String, Feed> by lazy { diskCache.toMutableMap() }

    suspend fun saveFeed(feed: Feed) {
        memCache[feed.sourceUrl] = feed
        diskCache = memCache
    }

    suspend fun deleteFeed(url: String) {
        memCache.remove(url)
        diskCache = memCache
    }

    suspend fun getAllFeeds(): List<Feed> = memCache.values.toList()

}