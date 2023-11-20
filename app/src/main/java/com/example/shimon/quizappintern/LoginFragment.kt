package com.example.shimon.quizappintern

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shimon.quizappintern.base.BaseFragment
import com.example.shimon.quizappintern.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    lateinit var firebaseUser: FirebaseUser
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseAuth.getInstance().currentUser?.let {

            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

        }


        binding.loginBTN.setOnClickListener {

            var email = binding.emailET.text.toString().trim()
            var password = binding.passET.text.toString().trim()
            
            if (isEmailValid(email)&&isPasswordValid(password)){

                loginUser(email,password)
            }else
            {
                Toast.makeText(requireContext(), "Invalid Email and Password", Toast.LENGTH_SHORT).show()
            }



        }

        binding.regTV.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

        }
        
    }

    private fun loginUser(email: String, password: String) {

        val auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task->
            if (task.isSuccessful){
                val user = auth.currentUser
                Toast.makeText(requireContext(), "Login SuccessFully\n ${user?.email}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
            else{
                Toast.makeText(requireContext(),"Login Failed ${task.exception?.message}",Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
//        val passwordRegex =
//            Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
        return password.length >= 6
    }

}