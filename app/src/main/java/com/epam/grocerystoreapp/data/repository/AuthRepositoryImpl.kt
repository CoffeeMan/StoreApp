package com.epam.grocerystoreapp.data.repository

import com.epam.grocerystoreapp.domain.model.User
import com.epam.grocerystoreapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
) : AuthRepository {
    override suspend fun signUp(
        name: String,
        surname: String,
        dateOfBirth: Long,
        email: String,
        password: String,
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()

        firebaseAuth.currentUser?.uid?.let { uid ->
            val user = User(
                id = uid,
                name = name,
                surname = surname,
                dateOfBirth = dateOfBirth,
                email = email,
            )

            firebaseDatabase.getReference(DATABASE_USERS_PATH)
                .child(uid)
                .setValue(user)
                .await()
        }
    }

    override suspend fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun restorePassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    override suspend fun getUser(): User {
        return firebaseAuth.currentUser?.uid?.let { uid ->
            firebaseDatabase.getReference(DATABASE_USERS_PATH)
                .child(uid)
                .get()
                .await()
                .getValue<User>()
        } ?: throw NullPointerException(USER_NPE_MESSAGE)
    }

    override suspend fun getAuthState(): Boolean {
        return firebaseAuth.currentUser?.uid != null
    }

    override suspend fun editUser(name: String, surname: String, dateOfBirth: Long) {
        val user = getUser()
        val editedUser = User(
            id = user.id,
            name = name,
            surname = surname,
            dateOfBirth = dateOfBirth,
            email = user.email
        )

        firebaseAuth.currentUser?.uid?.let { uid ->
            firebaseDatabase.getReference(DATABASE_USERS_PATH)
                .child(uid)
                .setValue(editedUser)
                .await()
        }
    }

    companion object {
        private const val DATABASE_USERS_PATH = "users"
        private const val USER_NPE_MESSAGE = "User is null"
    }

}
