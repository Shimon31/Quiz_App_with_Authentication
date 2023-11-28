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
import com.example.shimon.quizappintern.databinding.FragmentFlutterPlayBinding
import java.util.concurrent.TimeUnit


class FlutterPlayFragment :
    BaseFragment<FragmentFlutterPlayBinding>(FragmentFlutterPlayBinding::inflate) {
    var quizList = listOf<Quiz>(

        Quiz(
            "What is Flutter?",
            "A programming language",
            "A mobile development framework",
            "An operating system",
            "A design tool",
            "A mobile development framework"
        ),
        Quiz(
            "Which programming language is primarily used for Flutter app development?",
            "Java",
            "Python",
            "Dart",
            "Swift",
            "Dart"
        ),
        Quiz(
            "What is the widget in Flutter?",
            "A piece of code that defines the structure of an app",
            "A small application",
            "A platform-specific component",
            "A programming language",
            "A piece of code that defines the structure of an app"
        ),
        Quiz(
            "What does MaterialApp represent in Flutter?",
            "An iOS application",
            "A material design application",
            "A web application",
            "An Android application",
            "A material design application"
        ),
        Quiz(
            "Which command is used to create a new Flutter project from the command line?",
            "flutter start",
            "flutter create",
            "flutter new",
            "flutter project",
            "flutter create"
        ),
        Quiz(
            "What is the purpose of setState() in Flutter?",
            "To initialize the state of the app",
            "To rebuild the widget tree",
            "To define the layout of the app",
            "To create animations",
            "To rebuild the widget tree"
        ),
        Quiz(
            "Which class is used as the base class for widgets in Flutter?",
            "WidgetBase",
            "BaseWidget",
            "StatefulWidget",
            "StatelessWidget",
            "Widget"
        ),
        Quiz(
            "What is the purpose of a Scaffold widget in Flutter?",
            "To create animations",
            "To manage app navigation",
            "To implement the app's basic layout structure",
            "To handle user gestures",
            "To implement the app's basic layout structure"
        ),
        Quiz(
            "What is the purpose of the 'hot reload' feature in Flutter?",
            "To create a new project",
            "To quickly rebuild the app's UI without restarting the app",
            "To optimize app performance",
            "To generate app icons",
            "To quickly rebuild the app's UI without restarting the app"
        ),
        Quiz(
            "What command is used to run a Flutter app on an Android emulator or device?",
            "flutter start",
            "flutter run-android",
            "flutter launch",
            "flutter install",
            "flutter run-android"
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