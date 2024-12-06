package com.skybound.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.skybound.data.local.entity.RoadmapEntity

@Dao
interface RoadmapDao {
    @Insert
    suspend fun insertRoadmap(roadmap: RoadmapEntity)

    @Query("SELECT * FROM roadmap_table WHERE userId = :userId")
    suspend fun getRoadmapsByUserId(userId: String): List<RoadmapEntity>
}
