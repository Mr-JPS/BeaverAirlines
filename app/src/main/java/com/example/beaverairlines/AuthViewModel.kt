package com.example.beaverairlines

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.beaverairlines.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

const val TAG = "AuthViewModel"

class AuthViewModel(application: Application) : AndroidViewModel(application) {


    private val auth = FirebaseAuth.getInstance()

    private val db = FirebaseFirestore.getInstance()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

//    fun getCurrentUserID(): String {
//        return auth.currentUser!!.uid
//    }


    var currentUser: FirebaseUser? = null
        get() = auth.currentUser

    fun setUser(user: User) {
        db.collection("user").document(auth.currentUser!!.uid)
            .set(user)
            .addOnFailureListener {
                Log.w(TAG, "Error writing document: $it")
            }


        fun logout() {
            auth.signOut()

        }
    }
}