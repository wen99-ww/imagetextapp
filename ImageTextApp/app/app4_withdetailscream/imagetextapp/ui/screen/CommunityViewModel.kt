package com.example.imagetextapp.ui.screen

// viewmodel/CommunityViewModel.kt
//package com.example.imagetextapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.imagetextapp.model.Post
import com.example.imagetextapp.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class CommunityViewModel(
    private val repository: PostRepository
) : ViewModel() {

    val posts: Flow<PagingData<Post>> = repository.getPosts()
        .cachedIn(viewModelScope)  // 关键：避免重复加载
}

class CommunityViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommunityViewModel::class.java)) {
            return CommunityViewModel(PostRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}