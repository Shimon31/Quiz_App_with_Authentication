package com.example.shimon.quizappintern

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shimon.quizappintern.base.BaseFragment
import com.example.shimon.quizappintern.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val auth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.logoutBTN.setOnClickListener {
            auth.signOut().apply {
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
        }

        binding.kotlin.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_kotlinPlayFragment)

        }
        binding.java.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_javaPlayFragment)

        }
        binding.flutter.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_flutterPlayFragment)

        }
        binding.python.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_pythonPlayFragment)

        }

    }

}