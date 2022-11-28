package com.example.sem08.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sem08.model.Lugar

@Dao
interface LugarDao {
    @Query("SELECT * FROM lugar")
    fun obtenerLugares() : LiveData<List<Lugar>>

    //insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun agregarLugar(lugar: Lugar)

    //update
    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun modificarLugar(lugar: Lugar)

    //delete
    @Delete
    suspend fun eliminarLugar(lugar: Lugar)
}