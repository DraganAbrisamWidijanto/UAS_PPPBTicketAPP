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

// Kelas FavoritChecklistFragment merupakan bagian dari Fragmen yang menangani tampilan daftar destinasi favorit pengguna
class FavoritChecklistFragment : Fragment() {
    // Variabel untuk menyimpan referensi ke tata letak (layout) FragmentFavoritChecklistBinding
    private lateinit var binding: FragmentFavoritChecklistBinding
    // Daftar sementara destinasi favorit yang akan disimpan atau dihapus
    private var lists = mutableListOf<Favorite>()

    // Metode onCreateView dipanggil saat fragment pertama kali dibuat untuk menetapkan tata letak tampilan
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Membuat instance dari ViewDataBinding berdasarkan layout XML FragmentFavoritChecklistBinding
        binding = FragmentFavoritChecklistBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Metode onViewCreated dipanggil setelah tampilan dibuat dan digunakan untuk melakukan konfigurasi tambahan
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Memanggil fungsi getDestination dari MainActivity untuk mendapatkan destinasi
        MainActivity.getInstance().getDestination()
        // Mendapatkan nama pengguna dari SharedPreferences di MainActivity
        val username = MainActivity.getInstance().getSharedPreferences().getUsername()
        // Mendapatkan daftar destinasi dari MainActivity
        val list = MainActivity.getInstance().getDestinasi()
        // Log informasi terkait daftar destinasi
        Log.d("FavoritChecklist", "onViewCreated1 :  $list")
        // Menangani elemen UI menggunakan tata letak (binding)
        with(binding) {
            // Menangani aksi ketika tombol "backtofragmentdestination" diklik
            backtofragmentdestination.setOnClickListener {
                // Navigasi ke DestinationFragment menggunakan NavController
                findNavController().navigate(R.id.action_favoritChecklistFragment_to_destinationFragment)
            }

            // Mengamati perubahan pada daftar destinasi
            list.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    // Membuat daftar sementara destinasi favorit yang sesuai dengan format Favorite
                    val temp = mutableListOf<Favorite>()
                    for(i in it) {
                        temp.add(Favorite(id = i.id, nama = i.nama, username = username))
                    }
                    // Menetapkan adapter DestinasttUAdapter pada RV pilih destinasi favorit
                    uichansensei.apply {

                        adapter = DestinasttUAdapter(temp) {
                            // Menangani penambahan atau penghapusan item favorit dalam daftar sementara
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

            // Menangani aksi ketika tombol "btnsimpandestinasicrud" diklik
            btnsimpandestinasicrud.setOnClickListener {
                Log.d("FavoritChecklist", "onViewCreated: $lists")
                // Memeriksa apakah daftar sementara tidak kosong
                if(lists.isNotEmpty()) {
                    for(i in lists) {
                        // Menangani penambahan atau penghapusan item favorit di MainActivity
                        if(i.isChecked) {
                            MainActivity.getInstance().insertFavorite(i)
                        } else {
                            MainActivity.getInstance().deleteFavorite(i)
                        }
                    }
                    // Navigasi kembali ke DestinationFragment setelah menyimpan perubahan favorit
                    findNavController().navigate(R.id.action_favoritChecklistFragment_to_destinationFragment)
                } else {
                    // Menampilkan pesan Toast jika daftar sementara kosong
                    Toast.makeText(requireContext(), "Silahkan pilih destinasi terlebih dahulu", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}