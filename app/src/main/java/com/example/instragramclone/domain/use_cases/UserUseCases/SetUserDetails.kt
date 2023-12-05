package com.example.instragramclone.domain.use_cases.UserUseCases

import android.provider.ContactsContract.CommonDataKinds.Website
import com.example.instragramclone.domain.repository.UserRepository
import javax.inject.Inject

class SetUserDetails @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(
        userid:String,
        name:String,
        userName:String,
        bio:String,
        websiteUrl: String
    )=
        repository.setUserDetails(
            userid = userid,
            name=name,
            userName=userName,
            bio=bio,
            websiteUrl=websiteUrl
        )
}