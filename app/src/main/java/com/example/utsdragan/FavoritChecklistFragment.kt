package com.example.utsdragan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.utsdragan.databinding.FragmentFavoritChecklistBinding

class FavoritChecklistFragment : Fragment() {

    private lateinit var binding: FragmentFavoritChecklistBinding
    private var lists = mutableListOf<Favorite>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritChecklistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.getInstance().getDestination()
        val username = MainActivity.getInstance().getSharedPreferences().getUsername()
        val list = MainActivity.getInstance().getDestinasi()
        Log.d("FavoritChecklist", "onViewCreated1 :  $list")
        with(binding) {
            backtofragmentdestination.setOnClickListener {
                findNavController().navigate(R.id.action_favoritChecklistFragment_to_destinationFragment)
            }

            list.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    val temp = mutableListOf<Favorite>()
                    for(i in it) {
                        temp.add(Favorite(id = i.id, nama = i.nama, username = username))
                    }
                    uichansensei.apply {

                        adapter = DestinasttUAdapter(temp) {
                            if(!it.isChecked) {
                                lists.remove(it)
                            } else {
                                lists.add(it)
                            }
                            Log.d("FavoritChecklist", "onViewCreated: $lists")
                        }
                        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
                    }
                }
            }

            btnsimpandestinasicrud.setOnClickListener {
                Log.d("FavoritChecklist", "onViewCreated: $lists")
                if(lists.isNotEmpty()) {
                    for(i in lists) {
                        if(i.isChecked) {
                            MainActivity.getInstance().insertFavorite(i)
                        } else {
                            MainActivity.getInstance().deleteFavorite(i)
                        }
                    }
                    findNavController().navigate(R.id.action_favoritChecklistFragment_to_destinationFragment)
                } else {
                    Toast.makeText(requireContext(), "Silahkan pilih destinasi terlebih dahulu", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}