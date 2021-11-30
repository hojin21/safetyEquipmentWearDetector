package org.tensorflow.lite.examples.detection.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Record(
    val saveTime: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}