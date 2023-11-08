package com.example.shimon.quizappintern

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shimon.quizappintern.base.BaseFragment
import com.example.shimon.quizappintern.databinding.FragmentLoginBinding


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.loginBTN.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

        }

        binding.regTV.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

        }
        
    }

}