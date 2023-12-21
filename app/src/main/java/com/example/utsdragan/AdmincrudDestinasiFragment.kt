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
        // Inflate layout untuk fragmen ini
        binding = FragmentAdmincrudDestinasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mendapatkan instance dari MainActivity
        val mainActivity = MainActivity.getInstance()

        // Memanggil fungsi getDestination() dari MainActivity
        mainActivity.getDestination()

        with(binding) {
            // Menambahkan listener untuk tombol btnAdd
            btnAdd.setOnClickListener {
                // Navigasi ke admincrudEditorDestinasiFragment
                findNavController().navigate(R.id.action_CRUDFragment_to_admincrudEditorDestinasiFragment)
            }

            // Mengamati data dari fungsi getDestinasi() dan melakukan aksi ketika data berubah
            mainActivity.getDestinasi().observe(viewLifecycleOwner) { destinasiList ->
                if (destinasiList.isNotEmpty()) {
                    // Mengatur adapter dan layout manager untuk recyclerView
                    recyclerView.apply {
                        adapter = DestinasiAdapter(destinasiList, true,
                            // Menangani klik item untuk navigasi ke admincrudEditorDestinasiFragment dengan membawa data
                            { destinasi ->
                                val bundle = Bundle()

                                // Menambahkan data "nama_destinasi" ke dalam Bundle dengan nilai dari destinasi.nama
                                bundle.putString("nama_destinasi", destinasi.nama)

                                // Menambahkan data "id" ke dalam Bundle dengan nilai dari destinasi.id
                                bundle.putString("id", destinasi.id)

                                // Melakukan navigasi ke admincrudEditorDestinasiFragment dengan membawa Bundle sebagai parameter
                                findNavController().navigate(R.id.action_CRUDFragment_to_admincrudEditorDestinasiFragment, bundle)
                            },
                            // Menangani klik item untuk menghapus destinasi
                            { destinasi ->
                                mainActivity.deleteDestination(destinasi)
                                Toast.makeText(requireContext(), "Delete Success", Toast.LENGTH_SHORT).show()
                            })

                        // Mengatur layout manager sebagai LinearLayoutManager
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
            }
        }
    }
}
