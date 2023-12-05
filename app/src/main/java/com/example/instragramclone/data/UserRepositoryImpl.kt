package com.example.instragramclone.data

import com.example.instragramclone.domain.model.User
import com.example.instragramclone.domain.repository.UserRepository
import com.example.instragramclone.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) :UserRepository{
    private var operationSuccesful= false
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getUserDetails(userid: String): Flow<Response<User>> = callbackFlow {
        Response.Loading
        val snapShotListener = firebaseFirestore.collection("users")
            .document(userid)
            .addSnapshotListener{snapshot, error ->
                val response = if(snapshot!=null){
                    val userInfo = snapshot.toObject(User::class.java)
                    Response.Success<User>(userInfo!!)
                }
                else{
                    Response.Error(error?.message?:error.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose{
            snapShotListener.remove()
        }
    }

    override fun setUserDetails(
        userid: String,
        name: String,
        userName: String,
        bio: String,
        websiteUrl: String
    ): Flow<Response<Boolean>> = flow {
        operationSuccesful = false
        try{
            val userObj = mutableMapOf<String,String>()
            userObj["name"] = name
            userObj["username"]= userName
            userObj["bio"]=bio
            userObj["websiteUrl"] = websiteUrl
            firebaseFirestore.collection("users").document(userid).update(userObj as Map<String, Any>)
                .addOnSuccessListener {

                }.await()
            if(operationSuccesful){
                emit(Response.Success(operationSuccesful))
            }
            else {
                emit(Response.Error("Edit Does Not Succed"))
            }
        }
        catch (e:Exception){
            Response.Error(e.localizedMessage?:"An Unexpected Error")
        }
    }
}