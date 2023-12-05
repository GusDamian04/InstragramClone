package com.example.instragramclone.domain.use_cases.UserUseCases.PostsUseCases

import com.example.instragramclone.domain.repository.PostRepository
import javax.inject.Inject

class UploadPost @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(userid:String, userName:String,userImage:String,
                        postsImage:String, postsDescription:String, time:Long
    )=repository.uploadPost(
        userId=userid,
        userImage = userImage,
        userName = userName,
        postDescription = postsDescription,
        postImage = postsImage,
        time = time,

    )
}