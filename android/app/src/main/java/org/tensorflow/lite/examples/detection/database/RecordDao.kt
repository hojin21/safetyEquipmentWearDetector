package org.tensorflow.lite.examples.detection.database

import androidx.room.*

@Dao
interface RecordDao {
    @Insert
    fun insert(record: Record)

    @Delete
    fun delete(record: Record)

    // DB 읽어오기
    @Query("SELECT * FROM Record")
    fun getAll(): List<Record>
}