package com.example.instragramclone.domain.use_cases.UserUseCases

import com.example.instragramclone.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetails @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userid:String) =
        repository.getUserDetails(userid=userid)
}