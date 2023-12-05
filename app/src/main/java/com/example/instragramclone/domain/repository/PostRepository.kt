package com.example.instragramclone.domain.repository

import com.example.instragramclone.domain.model.Post
import com.example.instragramclone.util.Response
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getAllPosts(userid:String): Flow<Response<List<Post>>>

    fun uploadPost(
        postImage:String,
        postDescription:String,
        time: Long,
        userId:String,
        userName:String,
        userImage:String,
    ) : Flow<Response<Boolean>>
}