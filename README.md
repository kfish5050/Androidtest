# Androidtest
Test app for school

This app takes questions saved in questionlist.txt, randomizes them, and generates a quiz with an amount of questions based on the number the user inputs
in the main box upon opening the app. questionslist.txt has sensitive format, each question is based on 6 lines, the first being the question, second being correct answer,
3 more lines of false answers, and a newline to separate the question from the others. The final newline is not necessary. This makes changing questions for the app simple.
Questions are compiled at runtime so more questions will slow the app boot time. A known bug is that questions may repeat.
