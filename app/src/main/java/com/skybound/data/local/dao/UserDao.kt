package com.skybound.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.skybound.data.local.entity.RoadmapEntity
import com.skybound.data.local.entity.UserEntity
import com.skybound.data.remote.response.UserWithRoadmaps

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: String): UserEntity?

    @Query("DELETE FROM user_table WHERE id = :userId")
    suspend fun deleteUserById(userId: String)

    @Transaction
    @Query("SELECT * FROM user_table WHERE id = :userId")
    suspend fun getUserWithRoadmaps(userId: String): UserWithRoadmaps

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoadmaps(roadmaps: List<RoadmapEntity>)

    @Delete
    suspend fun deleteUser(user: UserEntity)
}