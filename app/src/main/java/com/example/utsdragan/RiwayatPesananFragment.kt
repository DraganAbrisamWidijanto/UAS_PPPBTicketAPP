package com.example.utsdragan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utsdragan.databinding.FragmentRiwayatPesananBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Fragment untuk menampilkan riwayat pesanan pengguna.
 */

class RiwayatPesananFragment : Fragment() {

    private lateinit var binding: FragmentRiwayatPesananBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentRiwayatPesananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Mendapatkan instance dari MainActivity dan daftar tiket pengguna
        val mainActivity = MainActivity.getInstance()
        val list = mainActivity.getTicket(mainActivity.getSharedPreferences().getUsername())
        val pass = mutableListOf<Ticket>()

        // Mengamati perubahan pada daftar tiket
        list.observe(viewLifecycleOwner) {
            for(i in it) {
                // Menyaring tiket yang tanggalnya sudah berlalu
                if(isDatePassed(i.tanggal.toString())) {
                    pass.add(i)
                }
            }

            with(binding) {
                if(pass.isEmpty()) {
                    // Menampilkan elemen UI yang sesuai jika tidak ada tiket yang aktif
                    imageNoActiveTicket.visibility = View.VISIBLE
                    profileFragmentName.visibility = View.VISIBLE
                    textNoActiveTicketdesc.visibility = View.VISIBLE
                    RVTiketAktif.visibility = View.GONE
                } else {
                    // Menampilkan daftar tiket yang aktif
                    imageNoActiveTicket.visibility = View.GONE
                    profileFragmentName.visibility = View.GONE
                    textNoActiveTicketdesc.visibility = View.GONE
                    RVTiketAktif.visibility = View.VISIBLE
                    RVTiketAktif.apply {
                        adapter = TicketAdapter(pass)
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    }
                }
            }
        }
        with(binding) {
            // Konfigurasi aksi klik untuk kembali ke halaman tiket
            backtoticketfragmen.setOnClickListener {
                findNavController().navigate(R.id.action_riwayatPesananFragment_to_ticketFragment2)
            }
        }
    }

    /**
     * Memeriksa apakah tanggal tiket sudah berlalu atau belum.
     *
     * @param dateString Tanggal tiket dalam format "dd/MM/yyyy".
     * @return `true` jika tanggal tiket sudah berlalu, `false` jika tidak.
     */
    fun isDatePassed(dateString: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            // Mengurai string tanggal input
            val inputDate = dateFormat.parse(dateString)

            // Mendapatkan tanggal saat ini
            val currentDate = Date()

            // Membandingkan tanggal input dengan tanggal saat ini
            return inputDate?.before(currentDate) == true
        } catch (e: Exception) {
            // Menangani kesalahan parsing, misalnya jika format tanggal input tidak valid
            e.printStackTrace()
            return false
        }
    }
}