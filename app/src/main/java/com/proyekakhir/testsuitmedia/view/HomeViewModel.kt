package com.proyekakhir.testsuitmedia.view

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel(){

    fun isPalindromeString(inputStr: String): Boolean {
        val sb = StringBuilder(inputStr)

        //reverse the string
        val reverseStr = sb.reverse().toString()

        //compare reversed string with input string
        return inputStr.equals(reverseStr, ignoreCase = true)
    }


}