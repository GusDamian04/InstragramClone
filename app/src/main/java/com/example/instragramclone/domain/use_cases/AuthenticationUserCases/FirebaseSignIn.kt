package com.example.instragramclone.domain.use_cases.AuthenticationUserCases

import com.example.instragramclone.domain.repository.AuthenticationRepository
import javax.inject.Inject

class FirebaseSignIn @Inject constructor(
    private val repository: AuthenticationRepository
){
    operator fun invoke(email:String,password:String) = repository.firebaseSignIn(email, password)
}