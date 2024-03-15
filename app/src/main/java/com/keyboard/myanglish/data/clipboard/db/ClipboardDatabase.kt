package com.keyboard.myanglish.data.clipboard.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [ClipboardEntry::class],
    exportSchema = true
)
abstract class ClipboardDatabase : RoomDatabase() {
    abstract fun clipboardDao(): ClipboardDao
}