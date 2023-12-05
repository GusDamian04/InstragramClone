package com.example.instragramclone.di

import com.example.instragramclone.data.AuthenticationRepositoryImpl
import com.example.instragramclone.data.PostRepositoryImpl
import com.example.instragramclone.data.UserRepositoryImpl
import com.example.instragramclone.domain.repository.AuthenticationRepository
import com.example.instragramclone.domain.repository.PostRepository
import com.example.instragramclone.domain.repository.UserRepository
import com.example.instragramclone.domain.use_cases.AuthenticationUserCases.AuthenticationUseCases
import com.example.instragramclone.domain.use_cases.AuthenticationUserCases.FirebaseAuthState
import com.example.instragramclone.domain.use_cases.AuthenticationUserCases.FirebaseSignIn
import com.example.instragramclone.domain.use_cases.AuthenticationUserCases.FirebaseSignOut
import com.example.instragramclone.domain.use_cases.AuthenticationUserCases.FirebaseSignUp
import com.example.instragramclone.domain.use_cases.AuthenticationUserCases.IsUserAuthenticated
import com.example.instragramclone.domain.use_cases.UserUseCases.GetUserDetails
import com.example.instragramclone.domain.use_cases.UserUseCases.PostsUseCases.GetAllPosts
import com.example.instragramclone.domain.use_cases.UserUseCases.PostsUseCases.PostsUseCases
import com.example.instragramclone.domain.use_cases.UserUseCases.PostsUseCases.UploadPost
import com.example.instragramclone.domain.use_cases.UserUseCases.SetUserDetails
import com.example.instragramclone.domain.use_cases.UserUseCases.UserUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InstagramModule {

    @Singleton
    @Provides
    fun provideFirebaseAuthentication():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }
    @Singleton
    @Provides
    fun provideFirebaseFirestore():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage():FirebaseStorage{
        return FirebaseStorage.getInstance()
    }
    @Singleton
    @Provides
    fun provideAuthenticationRepository(auth: FirebaseAuth, firestore: FirebaseFirestore): AuthenticationRepository{
        return AuthenticationRepositoryImpl(auth = auth, firestore = firestore)
    }
    @Singleton
    @Provides
    fun provideAuthUseCases(repository: AuthenticationRepository)= AuthenticationUseCases(
        isUserAuthenticated = IsUserAuthenticated(repository = repository),
        firebaseAuthState = FirebaseAuthState(repository = repository),
        firebaseSignOut = FirebaseSignOut(repository = repository),
        firebaseSignIn = FirebaseSignIn(repository = repository),
        firebaseSignUp = FirebaseSignUp(repository = repository)
    )

    @Singleton
    @Provides
    fun provideUserRepository(firebaseFirestore: FirebaseFirestore): UserRepository {
        return UserRepositoryImpl(firebaseFirestore=firebaseFirestore)
    }

    @Singleton
    @Provides
    fun provideUserUseCases(repository: UserRepository)=UserUseCases(
        getUserDetails = GetUserDetails(repository=repository),
        setUserDetails = SetUserDetails(repository=repository)
    )

    @Singleton
    @Provides
    fun providePostRepository(firebaseFirestore: FirebaseFirestore):PostRepository{
        return PostRepositoryImpl(firebaseFirestore = firebaseFirestore)
    }

    fun providePostsUseCases(repository: PostRepository) = PostsUseCases(
        getAllPosts = GetAllPosts(repository=repository),
        uploadPost = UploadPost(repository=repository)
    )
}