package com.example.utsdragan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utsdragan.databinding.FragmentAdmincrudPlusadminBinding


class AdmincrudPlusadminFragment : Fragment() {

    private lateinit var binding: FragmentAdmincrudPlusadminBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAdmincrudPlusadminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = MainActivity.getInstance()
        mainActivity.getAdmin()
        with(binding) {
            btnAdd.setOnClickListener {
                findNavController().navigate(R.id.action_CRUDFragment_to_admincrudEditorPlusadminFragment)
            }
            mainActivity.getAdminld().observe(viewLifecycleOwner) {
                if(it.isNotEmpty()) {
                    recyclerView.apply {
                        adapter = AkunAdapter(it, {
                            val bundle = Bundle()
                            bundle.putString("id", it.id)
                            bundle.putString("username", it.username)
                            bundle.putString("email", it.email)
                            bundle.putString("nim", it.nim)
                            bundle.putString("tanggal", it.tanggal)
                            bundle.putString("password", it.password)
                            findNavController().navigate(R.id.action_CRUDFragment_to_admincrudEditorPlusadminFragment, bundle)
                        }, {
                            Toast.makeText(requireContext(), "Delete ${it.username}", Toast.LENGTH_SHORT).show()
                            mainActivity.deleteUser(it)
                        })
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                }
            }
        }
    }

}