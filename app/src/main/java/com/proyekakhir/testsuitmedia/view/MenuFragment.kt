package com.proyekakhir.testsuitmedia.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.proyekakhir.testsuitmedia.databinding.FragmentMenuBinding
import com.proyekakhir.testsuitmedia.view.event.EventActivity
import com.proyekakhir.testsuitmedia.view.guest.GuestActivity

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        val name = MenuFragmentArgs.fromBundle(requireArguments()).name
        with(binding){
            tvName.text = name
            btnChooseEvent.setOnClickListener {
                startActivity(
                    Intent(
                        requireActivity(),
                        EventActivity::class.java
                    )
                )
            }
            btnChooseGuest.setOnClickListener {
                startActivity(
                    Intent(
                        requireActivity(),
                        GuestActivity::class.java
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}