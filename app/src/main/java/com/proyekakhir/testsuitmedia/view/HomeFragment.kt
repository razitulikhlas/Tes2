package com.proyekakhir.testsuitmedia.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.proyekakhir.testsuitmedia.databinding.FragmentHomeBinding
import com.proyekakhir.testsuitmedia.utils.showToastShort
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        with(binding) {
            btnNext.setOnClickListener {
                if (edName.text.isNotEmpty()) {
                    val directions =
                        HomeFragmentDirections.actionHomeFragmentToMenuFragment(edName.text.toString())
                    findNavController().navigate(directions)
                } else {
                    showToastShort(requireActivity(), "please insert name")
                }
            }

            btnCheck.setOnClickListener {
                if (edPolindrome.text.isNotEmpty()) {
                    val checkPalindrome = viewModel.isPalindromeString(edPolindrome.text.toString())
                    if (checkPalindrome) {
                        showDialog("isPalindrome", SweetAlertDialog.SUCCESS_TYPE)
                    } else {
                        showDialog("not palindrome", SweetAlertDialog.ERROR_TYPE)
                    }
                    Log.e("TAG", "onViewCreated:result $checkPalindrome")
                } else {
                    showToastShort(requireActivity(), "Please insert palindrome check")
                }
            }
        }
    }

    private fun showDialog(message: String, type: Int) {
        SweetAlertDialog(requireActivity(), type)
            .setTitleText("Result palindrome check")
            .setContentText(message)
            .setCancelClickListener {
                it.cancel()
            }
            .show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}