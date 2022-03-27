package com.proyekakhir.testsuitmedia.view.event

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.proyekakhir.core.adapter.EventAdapter
import com.proyekakhir.core.utils.Data
import com.proyekakhir.testsuitmedia.R
import com.proyekakhir.testsuitmedia.databinding.FragmentHomeEventBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeEventFragment : Fragment() {

    private var _binding: FragmentHomeEventBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EventViewModel by viewModel()
    private var eventAdapter: EventAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeEventBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        eventAdapter = EventAdapter()
        eventAdapter?.setData(Data.generateDataDummy())

        viewModel.event.observe(viewLifecycleOwner) { list ->
            eventAdapter?.setData(list)
            with(binding) {
                rcv.layoutManager = LinearLayoutManager(requireActivity())
                rcv.adapter = eventAdapter
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_list).isVisible = false
    }


}