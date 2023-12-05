package com.example.instragramclone.domain.repository

import com.example.instragramclone.domain.model.User
import com.example.instragramclone.util.Response
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserDetails(userid:String): Flow<Response<User>>
    fun setUserDetails(
        userid:String,
        name:String,
        userName:String,
        bio:String,
        websiteUrl:String
    ):Flow<Response<Boolean>>
}