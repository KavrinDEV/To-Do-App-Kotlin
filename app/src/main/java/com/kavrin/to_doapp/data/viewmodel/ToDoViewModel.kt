package com.kavrin.to_doapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kavrin.to_doapp.data.ToDoDatabase
import com.kavrin.to_doapp.data.models.ToDoData
import com.kavrin.to_doapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * From this VieModel we are gonna access all our queries from our DAO
 *
 * this class extend AndroidViewModel that contains Application Context
 */
class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    // accessing userDao from UserDatabase
    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    // Passing userDao to Repository
    private val repository: ToDoRepository = ToDoRepository(toDoDao)

    // Read all data from repository
    val getAllData: LiveData<List<ToDoData>> = repository.getAllData

    // Sorted by High Priority
    val sortByHighPriority: LiveData<List<ToDoData>> = repository.sortByHighPriority

    // Sorted by Low Priority
    val sortByLowPriority: LiveData<List<ToDoData>> = repository.sortByLowPriority

    // Using Coroutine to Insert new data to database
    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }

    // Using Coroutine to Update the data on database
    fun updateData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDoData)
        }
    }

    // Using Coroutine to Delete the data from database
    fun deleteData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(toDoData)
        }
    }

    // Using Coroutine to Delete All the data from database
    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }

    /**
     * Search the db
     */
    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>> {
        return repository.searchDatabase(searchQuery)
    }

}

/**
 * [ViewModel] is part of android architecture components
 *
 * The ViewModel's role is to provide data to the UI and
 * survive configuration changes.
 * A ViewModel acts as a communication center between the repository and the UI.
 *
 */