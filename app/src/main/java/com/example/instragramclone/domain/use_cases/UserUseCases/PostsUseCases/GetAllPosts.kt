package com.example.instragramclone.domain.use_cases.UserUseCases.PostsUseCases

import com.example.instragramclone.domain.repository.PostRepository
import javax.inject.Inject

class GetAllPosts @Inject constructor(
    private val repository: PostRepository
){
    operator fun invoke(userid:String)=repository.getAllPosts(userid=userid)
}