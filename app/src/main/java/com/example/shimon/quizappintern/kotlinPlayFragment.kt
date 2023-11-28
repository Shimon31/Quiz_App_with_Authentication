package com.example.shimon.quizappintern

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.shimon.quizappintern.base.BaseFragment
import com.example.shimon.quizappintern.databinding.FragmentKotlinPlayBinding
import java.util.concurrent.TimeUnit

class kotlinPlayFragment :
    BaseFragment<FragmentKotlinPlayBinding>(FragmentKotlinPlayBinding::inflate) {

    var quizList = listOf<Quiz>(

        Quiz(
            "What is Kotlin primarily used for?",
            "Front-end web development",
            "Android app development",
            "Data analysis",
            "Game development",
            "Android app development"
        ),
        Quiz(
            "Which of the following is true about Kotlin?",
            "It's a purely functional programming language",
            "Kotlin is interoperable with Java",
            "It does not support object-oriented programming",
            "Kotlin is developed by Microsoft",
            "Kotlin is interoperable with Java"
        ),
        Quiz(
            "Which keyword in Kotlin is used to declare a variable that can be reassigned?",
            "val",
            "var",
            "const",
            "let",
            "var"
        ),
        Quiz(
            "What is the function used to define an entry point in a Kotlin program?",
            "main()",
            "start()",
            "execute()",
            "entry()",
            "main()"
        ),
        Quiz(
            "In Kotlin, how is a single-line comment denoted?",
            "'",
            "#",
            "/*",
            "//",
            "//"
        ),
        Quiz(
            "What is the Kotlin standard function to perform null-checks and safely access properties?",
            "let()",
            "safeCall( )",
            "verify()",
            "checkNotNull()",
            "let()"
        ),
        Quiz(
            "Which scope function is used in Kotlin to execute a block of code on a non-null object?",
            "with()",
            "apply()",
            "also()",
            "run()",
            "run()"
        ),
        Quiz(
            "What Kotlin feature prevents null pointer exceptions at compile-time?",
            "Safe calls",
            "Non Nullable types",
            "Non-null assertions",
            "Nullable types",
            "Nullable types"
        ),
        Quiz(
            "Which function is used to concatenate strings in Kotlin?",
            "merge()",
            "concat()",
            "plus()",
            "joinToString()",
            "plus()"
        ),
        Quiz(
            "What is the equivalent of a Java switch statement in Kotlin?",
            "when",
            "match",
            "caseOf",
            "switch",
            "when"
        )
    )

    var updateQuestionNo = 1
    var countDownTimer: CountDownTimer? = null
    var miliLestSec = 30000L
    var index = 0
    var hasFinished = false
    var skip = 0
    var correct = 0
    var wrong = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.questionNumberTV.text = "$updateQuestionNo/${quizList.size}"
        startCountDownTimer()
        initQuestion()

        binding.nextQuestionBTN.setOnClickListener {
            showNextQuestion()
        }


    }

    private fun startCountDownTimer() {
        countDownTimer = object : CountDownTimer(miliLestSec, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                miliLestSec = millisUntilFinished
                val second = TimeUnit.MILLISECONDS.toSeconds(miliLestSec).toInt()

                binding.timerTV.text = "Time Left : $second"

            }

            override fun onFinish() {
                showNextQuestion()
            }

        }.start()
    }

    private fun initQuestion() {
        val quiz = quizList[index]

        binding.apply {
            questionTV.text = quiz.question
            option1.text = quiz.option1
            option2.text = quiz.option2
            option3.text = quiz.option3
            option4.text = quiz.option4
        }


    }

    private fun showNextQuestion() {
        checkAnswer()
        binding.apply {
            //question update after clicking next BTN
            if (updateQuestionNo < quizList.size) {
                updateQuestionNo++
                questionNumberTV.text = "$updateQuestionNo/${quizList.size}"
            }
            if (index <= quizList.size - 1) {

                initQuestion()
            }
            //when all question was finished then this function will work
            else {
                hasFinished = true
                Toast.makeText(requireContext(), "goto result", Toast.LENGTH_SHORT).show()
            }
//clear all ans when new question was came
            radioGroup.clearCheck()
        }

    }

    private fun checkAnswer() {

        binding.apply {

            if (radioGroup.checkedRadioButtonId == -1) {
                skip++
            } else {
                val checkButton = view?.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                var checkAnswer = checkButton?.text.toString()

                if (checkAnswer == quizList[index].rightAnswer) {
                    correct++
                    scoreTV.text = "Score : $correct"
                    countDownTimer?.cancel()
                    showAlertDialog("Right Answer")
                } else {
                    wrong++
                    countDownTimer?.cancel()
                    showAlertDialog("Wrong Answer")
                }

            }
            if (index <= quizList.size - 1) {
                index++
            } else {
                showAlertDialog("Finish")
            }


        }
    }

    fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(message)

        builder.setPositiveButton("ok", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (message == "Finish") {
                    countDownTimer?.cancel()


                    val bundle = Bundle()
                    bundle.putString("skip", skip.toString())
                    bundle.putString("right", correct.toString())
                    bundle.putString("wrong", wrong.toString())
                    bundle.putString("numOfQuestion", quizList.size.toString())
                    findNavController().navigate(
                        R.id.action_pythonPlayFragment_to_resultFragment,
                        bundle
                    )

                }

                countDownTimer?.start()
            }

        })

        var alertDialog = builder.create()
        alertDialog.show()


    }

}