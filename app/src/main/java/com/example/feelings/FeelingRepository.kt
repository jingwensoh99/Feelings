package com.example.feelings

import androidx.lifecycle.LiveData

class FeelingRepository(private val feelingDAO: FeelingDAO) {
    // Room execute all queries on a separate thread.

    val allFeelings: LiveData<List<Feeling>> = feelingDAO.getFeelings()

    suspend fun insert (feeling: Feeling) {
        feelingDAO.insertFeeling(feeling)
    }
}