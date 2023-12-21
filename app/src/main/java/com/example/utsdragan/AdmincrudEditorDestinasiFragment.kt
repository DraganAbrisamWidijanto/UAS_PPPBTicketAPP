package com.example.utsdragan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.utsdragan.databinding.FragmentAdmincrudEditorDestinasiBinding


class AdmincrudEditorDestinasiFragment : Fragment() {

    private lateinit var binding: FragmentAdmincrudEditorDestinasiBinding
    private var onEdit = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Mengembang layout untuk fragmen ini
        binding = FragmentAdmincrudEditorDestinasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            // Menerima data "nama_destinasi" dari Bundle
            val received = arguments?.getString("nama_destinasi")

            // Jika data diterima tidak kosong, mengatur teks untuk namadestinasiTXT dan mengubah status onEdit menjadi true
            if (!received.isNullOrBlank()) {
                namadestinasiTXT.setText(received)
                onEdit = true
            }

            // Menangani klik tombol untuk kembali ke CRUDFragment
            backtoadmincruddestinasifragment.setOnClickListener {
                findNavController().navigate(R.id.action_admincrudEditorDestinasiFragment_to_CRUDFragment)
            }

            // Menangani klik tombol untuk menyimpan atau memperbarui data destinasi
            btnsimpandestinasicrud.setOnClickListener {
                // Jika dalam mode edit (onEdit == true), memperbarui data destinasi
                if (onEdit) {
                    MainActivity.getInstance().updateDestination(
                        Destinasi(
                            id = arguments?.getString("id")!!,
                            nama = namadestinasiTXT.text.toString()
                        )
                    )
                } else {
                    // Jika dalam mode tambah data, menambahkan data destinasi baru
                    MainActivity.getInstance().postDestination(
                        Destinasi(nama = namadestinasiTXT.text.toString())
                    )
                }

                // Navigasi kembali ke CRUDFragment setelah menyimpan atau memperbarui data
                findNavController().navigate(R.id.action_admincrudEditorDestinasiFragment_to_CRUDFragment)
            }
        }
    }
}
