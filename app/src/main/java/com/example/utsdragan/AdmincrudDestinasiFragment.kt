package com.example.utsdragan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utsdragan.databinding.FragmentAdmincrudDestinasiBinding


class AdmincrudDestinasiFragment : Fragment() {

    private lateinit var binding: FragmentAdmincrudDestinasiBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAdmincrudDestinasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = MainActivity.getInstance()
        mainActivity.getDestination()

        with(binding){
            btnAdd.setOnClickListener {
                findNavController().navigate(R.id.action_CRUDFragment_to_admincrudEditorDestinasiFragment)
            }

            mainActivity.getDestinasi().observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    recyclerView.apply {
                        adapter = DestinasiAdapter(it, true,
                            {
                                val bundle = Bundle()
                                bundle.putString("nama_destinasi", it.nama)
                                bundle.putString("id", it.id)
                                findNavController().navigate(R.id.action_CRUDFragment_to_admincrudEditorDestinasiFragment, bundle)
                            }, {
                                mainActivity.deleteDestination(it)
                                Toast.makeText(requireContext(), "Delete Success", Toast.LENGTH_SHORT).show()
                            })
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
            }
        }
    }

}