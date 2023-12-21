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


// Kelas DestinationFragment merupakan bagian dari Fragmen yang menangani tampilan destinasi dalam aplikasi
class DestinationFragment : Fragment() {

    // Variabel untuk menyimpan referensi ke tata letak (layout) FragmentDestinationBinding
    private lateinit var binding: FragmentDestinationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Membuat instance dari ViewDataBinding berdasarkan layout XML FragmentDestinationBinding
        binding = FragmentDestinationBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Metode onViewCreated dipanggil setelah tampilan dibuat dan digunakan untuk melakukan konfigurasi tambahan
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Memanggil fungsi getDestination dari MainActivity untuk mendapatkan destinasi
        MainActivity.getInstance().getDestination()
        // Mendapatkan daftar destinasi dan daftar favorit dari MainActivity
        val list = MainActivity.getInstance().getDestinasi()
        val favlist = MainActivity.getInstance().getFavorite(MainActivity.getInstance().getSharedPreferences().getUsername())
        // Menangani elemen UI menggunakan tata letak (binding)
        with(binding) {
            // Menangani aksi ketika tombol "btntodestimasifavoriteditor" diklik
            btntodestimasifavoriteditor.setOnClickListener {
                // Navigasi ke FavoritChecklistFragment menggunakan NavController
                findNavController().navigate(R.id.action_destinationFragment_to_favoritChecklistFragment)
            }

            // Mengamati perubahan pada daftar destinasi
            list.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    // Menetapkan adapter DestinasiAdapter pada RecyclerView RVDestinasiPopuler
                    RVDestinasiPopuler.apply {
                        adapter = DestinasiAdapter(it)
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
            }

            // Mengamati perubahan pada daftar favorit pengguna
            favlist.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    // Menampilkan judul dan RecyclerView destinasi favorit pengguna
                    txtjuduldestfavo.visibility = View.VISIBLE
                    RVDestinasiFavoritUser.apply {
                        visibility = View.VISIBLE
                        // Menetapkan adapter DestinasttUAdapter pada RecyclerView RVDestinasiFavoritUser
                        adapter = DestinasttUAdapter(it, true) {
                            // Menangani penghapusan item favorit
                            MainActivity.getInstance().deleteFavorite(it)
                            Toast.makeText(requireContext(), "Destinasi berhasil dihapus", Toast.LENGTH_SHORT).show()
                        }
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                } else {
                    // Menyembunyikan RecyclerView destinasi favorit pengguna dan judul
                    RVDestinasiFavoritUser.visibility = View.GONE
                    txtjuduldestfavo.visibility = View.GONE
                }
            }
        }
    }
}