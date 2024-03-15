package com.keyboard.myanglish.data.clipboard.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ClipboardDao {
    @Query("SELECT COUTN")

    @Query("SELECT * FROM ${ClipboardEntry.TABLE_NAME} WHERE pinned=0 AND deleted=0")
    suspend fun getAllUnpinned(): List<ClipboardEntry>

    @Query("UPDATE ${ClipboardEntry.TABLE_NAME} SET DELETED = 1 WHERE timestamp<:timestamp AND pinned = 0 AND deleted = 0")
    suspend fun markUnpinnedAsDeletedEarlierThan(timestamp: Long)
}