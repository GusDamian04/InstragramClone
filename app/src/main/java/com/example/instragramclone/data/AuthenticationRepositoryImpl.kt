package com.example.instragramclone.data

import com.example.instragramclone.domain.model.User
import com.example.instragramclone.domain.repository.AuthenticationRepository
import com.example.instragramclone.util.Constants
import com.example.instragramclone.util.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthenticationRepository{
    var operationSiccessful: Boolean = false
    override fun isUserAuthenticatedInFirebase(): Boolean {
        return auth.currentUser!=null
    }

    @ExperimentalCoroutinesApi
    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend( auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose{
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun firebaseSignIn(email: String, password: String): Flow<Response<Boolean>> = flow{
        operationSiccessful = false
        try {
            emit(Response.Loading)
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                operationSiccessful = true
            }.await()
            emit(Response.Success(operationSiccessful))
        }
        catch (e: Exception){
            emit(Response.Error(e.localizedMessage?: "An Unexpected Error"))
        }
    }

    override fun firebaseSignOut(): Flow<Response<Boolean>> = flow{
        try{
            emit(Response.Loading)
            auth.signOut()
            emit(Response.Success(true))
        }
        catch (e: Exception){
            emit(Response.Error(e.localizedMessage?: "An Unexpected Error"))
        }
    }

    override fun firebaseSignUp(
        email: String,
        password: String,
        userName: String
    ): Flow<Response<Boolean>> = flow{
        operationSiccessful = false
        try{
            emit(Response.Loading)
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                operationSiccessful = true
            }.await()
            if(operationSiccessful){
                val userid = auth.currentUser?.uid!!
                val obj = User(userName = userName, userid = userid, password = password, email = email)
                firestore.collection(Constants.COLLECTION_NAME_USERS).document(userid).set(obj).addOnSuccessListener {

                }.await()
                emit(Response.Success(operationSiccessful))
            }
            else {
                Response.Success(operationSiccessful)
            }
        }
        catch (e: Exception){
            emit(Response.Error(e.localizedMessage?: "An Unexpected Error"))
        }
    }
}