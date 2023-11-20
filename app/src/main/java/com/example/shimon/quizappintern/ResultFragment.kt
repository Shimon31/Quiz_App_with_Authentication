package com.example.shimon.quizappintern

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shimon.quizappintern.base.BaseFragment
import com.example.shimon.quizappintern.databinding.FragmentResultBinding


class ResultFragment : BaseFragment<FragmentResultBinding>(FragmentResultBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val skip = requireArguments().getString("skip", "0")
        val corerct = requireArguments().getString("right", "0")
        val wrong = requireArguments().getString("wrong", "0")
        val numOfQuestion = requireArguments().getString("numOfQuestion", "0")



        binding.showResult.text = "Number Of Question :$numOfQuestion\n" +
                "Skip Question: $skip\n" +
                "Correct Answer: $corerct\n" +
                "Wrong Answer: $wrong\n"

        binding.goToHomePage.setOnClickListener {

            findNavController().navigate(R.id.action_resultFragment_to_homeFragment)
        }


    }

}