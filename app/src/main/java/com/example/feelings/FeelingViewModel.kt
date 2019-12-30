package com.example.feelings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FeelingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FeelingRepository

    val allFeeling: LiveData<List<Feeling>>

    init {
        val feelingDao = FeelingDatabase
            .getDatabase(application)
            .feelingDao()

        repository = FeelingRepository(feelingDao)
        allFeeling = repository.allFeelings
    }

    //ViewModel to use co-routine to perform long process
    fun insert(feeling: Feeling) = viewModelScope.launch{
        repository.insert(feeling)
    }
}