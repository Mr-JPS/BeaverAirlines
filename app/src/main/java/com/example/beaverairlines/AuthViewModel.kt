package com.example.beaverairlines

import android.app.Application
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.beaverairlines.auth.SigninLoginFragment
import com.example.beaverairlines.auth.SigninLoginFragmentDirections
import com.example.beaverairlines.data.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.security.AccessController.getContext

const val TAG = "AuthViewModel"

class AuthViewModel(application: Application) : AndroidViewModel(application) {


    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

//    private val _currentUser = MutableLiveData<FirebaseUser?>(auth.currentUser)
//    val currentUser: LiveData<FirebaseUser?>
//        get() = _currentUser

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
        }


        fun updateUser(user: User) {
            db.collection("user").document(auth.currentUser!!.uid)
                .set(user)
                .addOnFailureListener {
                    Log.w(TAG, "Error writing document: $it")
                }
        }

    fun userIdGenerator(): String {
        val nbr1 = (0..9).random()
        val nbr2 = (0..9).random()
        val nbr3 = (0..9).random()
        val nbr4 = (0..9).random()
        val nbr5 = (0..9).random()
        val nbr6 = (0..9).random()
        val nbr7 = (0..9).random()
        val nbr8 = (0..9).random()

        return "BA22-$nbr1$nbr2$nbr3$nbr4$nbr5$nbr6$nbr7$nbr8"
    }

    fun mileHighGenerator(): String {
        val nbr1 = (0..9).random()
        val nbr2 = (0..9).random()
        val nbr3 = (0..9).random()
        val nbr4 = (0..9).random()
        val nbr5 = (0..9).random()
        val nbr6 = (0..9).random()
        val nbr7 = (0..9).random()
        val nbr8 = (0..9).random()

        return "BMH$nbr1$nbr2$nbr3$nbr4$nbr5$nbr6$nbr7$nbr8-Y22"

    }



    fun getUserName() {
            db.collection("user").document(auth.currentUser!!.uid)
                .get()
                .addOnSuccessListener {
                    val userName = it.getString("fullName")
                }
        }


        fun getCurrentUserDetails() {
            db.collection("user").document(auth.currentUser!!.uid)
                .get()
                .addOnSuccessListener {
                    val userName = it.getString("fullname")
                }
        }

        fun logout() {
            auth.signOut()

        }
    }


/*
    fun registerUser(name: String, email: String, password: String) {

        if(validateRegistration(name, email, password)){
            //Toast.makeText(getContext(), "Please wait...", Toast.LENGTH_SHORT).show()
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                      val firebaseUser: FirebaseUser = task.result!!.user!!
//                    val registeredEmail = firebaseUser.email!!
                    val newUser = User(
                        fullName =  name,
                        email = email
                    )
                   setUser(newUser)
                    loginUser(email,password)
//                  viewModel.currentUser = firebaseAuth.currentUser

//                    Toast.makeText(getContext(), "$name, you have successfully registered Your email $registeredEmail",
//                        Toast.LENGTH_LONG).show()
//                    findNavController().navigate(R.id.action_signinFragment_to_homeFragment)
//

                } else {
                    //Toast.makeText(getContext(), task.exception!!.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }


    private fun validateRegistration(name: String, email: String, password: String) : Boolean {

        return when {
            TextUtils.isEmpty(name) ->{
                //showErrorSnackBar("Please enter your name")
                //Toast.makeText(getContext(), "Please enter your name", Toast.LENGTH_SHORT).show()
                false
            } TextUtils.isEmpty(email) ->{
                //showErrorSnackBar("Please enter your email address")
                //Toast.makeText(getContext(), "Please enter your email address", Toast.LENGTH_SHORT).show()
                false
            } TextUtils.isEmpty(password) ->{
                //showErrorSnackBar("Please enter a password")
                //Toast.makeText(getContext(), "Please enter a password", Toast.LENGTH_SHORT).show()
                false
            } else -> {
                true
            }
        }
    }

     fun loginUser(email: String, password: String){

        if(validateLogin(email, password)){
            //Toast.makeText(getContext(), "Please wait...", Toast.LENGTH_SHORT).show()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        _currentUser.value = auth.currentUser
                        //viewModel.currentUser = auth.currentUser
                        //val currentUser = auth.currentUser
                        //Toast.makeText(getContext(), "You have successfully logged in!",
                         //   Toast.LENGTH_LONG).show()
                       // findNavController(SigninLoginFragment()).navigate(R.id.NavControllerFragment)
                       //navigateTo(SigninLoginFragmentDirections.actionSigninFragmentToNavControllerFragment())
                    } else {
                        //Toast.makeText(getContext(), "Login failed.",
                            //Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Login failed: ${it.exception?.message}")

                    }
                }
                .addOnFailureListener {
                    Log.e("firebase login", it.message!! )
                }
        }
    }


    private fun validateLogin(email: String, password: String) : Boolean {

        return when {
            TextUtils.isEmpty(email) ->{
                //showErrorSnackBar("Please enter your email address")
                //Toast.makeText(getContext(), "Please enter your email address", Toast.LENGTH_SHORT).show()
                false
            } TextUtils.isEmpty(password) ->{
                //showErrorSnackBar("Please enter your password")
                //Toast.makeText(getContext(), "Please enter a password", Toast.LENGTH_SHORT).show()
                false
            } else -> {
                true
            }
        }
    }


//    fun getCurrentUserID() : String{
//        return FirebaseAuth.getInstance().currentUser!!.uid
//    }

/*
    fun showErrorSnackBar(message :String){
        val snackbar = Snackbar.make(requireActivity().findViewById(android.R.id.content), message,
            Snackbar.LENGTH_LONG)

        val snackBarView = snackbar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red_700))

        snackbar.show()
    }
}
*/

 */

