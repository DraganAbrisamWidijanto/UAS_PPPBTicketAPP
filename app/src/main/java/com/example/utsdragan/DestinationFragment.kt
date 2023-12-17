package com.example.utsdragan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.utsdragan.databinding.FragmentDestinationBinding


class DestinationFragment : Fragment() {

    private lateinit var binding: FragmentDestinationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDestinationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.getInstance().getDestination()
        val list = MainActivity.getInstance().getDestinasi()
        val favlist = MainActivity.getInstance().getFavorite(MainActivity.getInstance().getSharedPreferences().getUsername())
        with(binding) {
            btntodestimasifavoriteditor.setOnClickListener {
                findNavController().navigate(R.id.action_destinationFragment_to_favoritChecklistFragment)
            }

            list.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    RVDestinasiPopuler.apply {
                        adapter = DestinasiAdapter(it)
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
            }

            favlist.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    txtjuduldestfavo.visibility = View.VISIBLE
                    RVDestinasiFavoritUser.apply {
                        visibility = View.VISIBLE
                        adapter = DestinasttUAdapter(it, true) {
                            MainActivity.getInstance().deleteFavorite(it)
                            Toast.makeText(requireContext(), "Delete Success", Toast.LENGTH_SHORT).show()
                        }
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                } else {
                    RVDestinasiFavoritUser.visibility = View.GONE
                    txtjuduldestfavo.visibility = View.GONE
                }
            }
        }
    }


}