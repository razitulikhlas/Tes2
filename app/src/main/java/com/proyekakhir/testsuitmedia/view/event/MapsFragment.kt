package com.proyekakhir.testsuitmedia.view.event

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.proyekakhir.core.adapter.EventMapsAdapter
import com.proyekakhir.testsuitmedia.R
import com.proyekakhir.testsuitmedia.databinding.FragmentMapsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapsFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private var adapter: EventMapsAdapter? = null
    private val viewModel: EventViewModel by viewModel()
    private lateinit var mapsView: GoogleMap
    private lateinit var marker: Marker
//    private val callback = OnMapReadyCallback { googleMap ->
//        /**
//         * Manipulates the map once available.
//         * This callback is triggered when the map is ready to be used.
//         * This is where we can add markers or lines, add listeners or move the camera.
//         * In this case, we just add a marker near Sydney, Australia.
//         * If Google Play services is not installed on the device, the user will be prompted to
//         * install it inside the SupportMapFragment. This method will only be triggered once the
//         * user has installed Google Play services and returned to the app.
//         */
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        adapter = EventMapsAdapter()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        with(binding) {
            viewPager.clipToPadding = false
            viewPager.clipChildren = false
            viewPager.offscreenPageLimit = 3
            viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(40))
            compositePageTransformer.addTransformer { page, position ->
                val r = 1 - Math.abs(position)
                page.scaleY = (0.85 + r * 0.15).toFloat()
            }
            viewPager.setPageTransformer(compositePageTransformer)


        }
        viewModel.event.observe(viewLifecycleOwner) {
            Log.e("TAG", "onViewCreated: $it")
            adapter?.setData(it)
            binding.viewPager.adapter = adapter
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun getMarker(position: Int) {
        mapsView.clear()
        viewModel.event.observe(viewLifecycleOwner) {
            for (i in it.indices) {
                val markerOptions = MarkerOptions()
                markerOptions.position(LatLng(it[i].latitude, it[i].longitude))
                    .title(it[i].title)
                if (i == position) {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(com.proyekakhir.testsuitmedia.R.mipmap.ic_marker_selected))
                    mapsView.moveCamera(
                        CameraUpdateFactory.newLatLng(
                            LatLng(
                                it[i].latitude,
                                it[i].longitude
                            )
                        )
                    )
                    mapsView.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                it[i].latitude,
                                it[i].longitude
                            ), 16f
                        )
                    )
                } else {
                    markerOptions
                        .icon(BitmapDescriptorFactory.fromResource(com.proyekakhir.testsuitmedia.R.mipmap.ic_marker_unselected))
                }
                marker = mapsView.addMarker(markerOptions)!!
            }
            mapsView.uiSettings.setAllGesturesEnabled(true)
            mapsView.uiSettings.isZoomGesturesEnabled = true
            mapsView.uiSettings.isCompassEnabled = true
            mapsView.uiSettings.isMyLocationButtonEnabled = true
            mapsView.uiSettings.isMapToolbarEnabled = true

        }

    }

    override fun onMapReady(mapsView: GoogleMap) {
        this.mapsView = mapsView
        getMarker(0)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                getMarker(position)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_maps).isVisible = false
    }
}