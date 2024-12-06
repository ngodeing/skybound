package com.skybound.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roadmap_table")
data class RoadmapEntity(
    @PrimaryKey val id: String,
    val roadmapName: String,
    val addedAt: String,
    val userId: String
)