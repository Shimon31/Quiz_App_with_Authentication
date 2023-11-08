package com.example.shimon.quizappintern

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shimon.quizappintern.base.BaseFragment
import com.example.shimon.quizappintern.databinding.FragmentRegisterBinding


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpBTN.setOnClickListener {

            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

        }

        binding.loginTV.setOnClickListener {

            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

        }

    }

}