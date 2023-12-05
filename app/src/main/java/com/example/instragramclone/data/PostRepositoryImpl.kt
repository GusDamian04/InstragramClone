package com.example.instragramclone.data

import com.example.instragramclone.domain.model.Post
import com.example.instragramclone.domain.repository.PostRepository
import com.example.instragramclone.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
):PostRepository{
    private var operationSuccessful=false
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllPosts(userid: String):
            Flow<Response<List<Post>>> = callbackFlow{
        Response.Loading
        val snapshotListener = firebaseFirestore.collection("posts")
            .whereNotEqualTo("userId",userid)
            .addSnapshotListener{snapshot, e->
                val response = if(snapshot!=null){
                    val postsList = snapshot.toObjects(Post::class.java)
                    Response.Success<List<Post>>(postsList)
                }
                else {
                    Response.Error(e?.message?:e.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose{
            snapshotListener.remove()
        }
    }

    override fun uploadPost(
        postImage: String,
        postDescription: String,
        time: Long,
        userId: String,
        userName: String,
        userImage: String
    ): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        try{
            val postsId = firebaseFirestore.collection("posts").document().id
            val post = Post(
                postImage = postImage,
                postDescription = postDescription,
                postId = postsId,
                time = time,
                userName = userName,
                userImage = userImage,
                userId = userId
            )
            firebaseFirestore.collection("posts").document(postsId).set(post)
                .addOnSuccessListener {
                    operationSuccessful = true
                }.await()
            if(operationSuccessful){
                emit(Response.Success(operationSuccessful))
            }
            else{
                emit(Response.Error("Post Upload Unsuccessful"))
            }
        }
        catch(e:Exception){
            emit(Response.Error(e.localizedMessage?:"An Unexpected Error"))
        }
    }
}