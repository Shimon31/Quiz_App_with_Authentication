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
import com.example.shimon.quizappintern.databinding.FragmentPythonPlayBinding
import java.util.concurrent.TimeUnit

class PythonPlayFragment : BaseFragment<FragmentPythonPlayBinding>(FragmentPythonPlayBinding::inflate) {
    var quizList = listOf<Quiz>(


        Quiz(
            "What is the output of the following code?\n\nprint(2 + 2 * 3 - 1)",
            "6",
            "7",
            "8",
            "9",
            "7"
        ),
        Quiz(
            "Which of the following is used to create a comment in Python?",
            "%%",
            "//",
            "#",
            "/* */",
            "#"
        ),
        Quiz(
            "Which of the following data types is immutable in Python?",
            "List",
            "Dictionary",
            "Tuple",
            "Set",
            "Tuple"
        ),
        Quiz(
            "What will the 'len()' function return for the list [1, 2, 3, 4, 5]?",
            "4",
            "5",
            "6",
            "Returns an error",
            "5"
        ),
        Quiz(
            "What is the output of the following code?\n\nx = 'Hello'\nprint(x[2:])",
            "Hell",
            "lo",
            "Hel",
            "llo",
            "llo"
        ),
        Quiz(
            "Which keyword is used to define a function in Python?",
            "def",
            "function",
            "define",
            "func",
            "def"
        ),
        Quiz(
            "What method is used to add an element to the end of a list in Python?",
            "append()",
            "add()",
            "insert()",
            "extend()",
            "append()"
        ),
        Quiz(
            "Which of the following statements is used to exit from a loop in Python?",
            "exit()",
            "break",
            "stop()",
            "endloop()",
            "break"
        ),
        Quiz(
            "What does the 'range()' function return?",
            "A list",
            "A tuple",
            "A range object",
            "A dictionary",
            "A range object"
        ),
        Quiz(
            "Which of the following is used to open a file in Python?",
            "open()",
            "read()",
            "file()",
            "openfile()",
            "open()"
        )
    )

    var updateQuestionNo = 1
    var countDownTimer: CountDownTimer? = null
    var miliLestSec = 30000L
    var index = 0
    var hasFinished = false
    var skip = -1
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