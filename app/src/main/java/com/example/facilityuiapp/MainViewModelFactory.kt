package com.example.facilityuiapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.facilityuiapp.repo.Repository

class MainViewModelFactory(private val repo: Repository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }
}