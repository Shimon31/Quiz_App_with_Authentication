package com.example.shimon.quizappintern

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shimon.quizappintern.base.BaseFragment
import com.example.shimon.quizappintern.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.kotlin.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_kotlinQuizFragment)

        }
        binding.java.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_javaQuizFragment)

        }
        binding.flutter.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_flutterQuizFragment)

        }
        binding.python.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_pythonQuizFragment)

        }

    }

}