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

        // Mendapatkan instance dari MainActivity
        val mainActivity = MainActivity.getInstance()

        // Memanggil fungsi getAdmin() dari MainActivity
        mainActivity.getAdmin()
        with(binding) {
            // Menambahkan listener untuk tombol btnAdd
            btnAdd.setOnClickListener {
                // Navigasi ke admincrudEditorPlusadminFragment
                findNavController().navigate(R.id.action_CRUDFragment_to_admincrudEditorPlusadminFragment)
            }
            mainActivity.getAdminld().observe(viewLifecycleOwner) {
                if(it.isNotEmpty()) {
                    // Mengatur adapter dan layout manager untuk recyclerView
                    recyclerView.apply {
                        adapter = AkunAdapter(it,
                            // Menangani klik item untuk navigasi ke admincrudEditorPlusadminFragment dengan membawa data
                            {
                            val bundle = Bundle()
                            bundle.putString("id", it.id)
                            bundle.putString("username", it.username)
                            bundle.putString("email", it.email)
                            bundle.putString("nim", it.nim)
                            bundle.putString("tanggal", it.tanggal)
                            bundle.putString("password", it.password)
                            findNavController().navigate(R.id.action_CRUDFragment_to_admincrudEditorPlusadminFragment, bundle)
                        },
                            // Menangani klik item untuk menghapus admin
                            {
                            Toast.makeText(requireContext(), "Delete ${it.username}", Toast.LENGTH_SHORT).show()
                            mainActivity.deleteUser(it)
                        })

                        // Mengatur layout manager sebagai LinearLayoutManager
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                }
            }
        }
    }
}