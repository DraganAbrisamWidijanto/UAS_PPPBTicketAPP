package com.example.utsdragan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utsdragan.databinding.FragmentTicketBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class TicketFragment : Fragment() {

    private lateinit var binding: FragmentTicketBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = MainActivity.getInstance()
        // Mendapatkan data tiket berdasarkan nama pengguna dari database
        val data = mainActivity.getTicket(mainActivity.getSharedPreferences().getUsername())

        with(binding) {
            // Mengamati perubahan data tiket
            data.observe(viewLifecycleOwner) {
                val pass = mutableListOf<Ticket>()
                if(it.isNotEmpty()) {
                    // Memfilter tiket yang masih aktif berdasarkan tanggal dan waktu
                    for(i in it) {
                        if (isDateBefore(i.tanggal) && hasTimePassed(i.jam)) {
                            pass.add(i)
                        }
                    }
                }

                if(pass.isEmpty()) {
                    // Menampilkan elemen UI jika tidak ada tiket aktif
                    imageNoActiveTicket.visibility = View.VISIBLE
                    profileFragmentName.visibility = View.VISIBLE
                    textNoActiveTicketdesc.visibility = View.VISIBLE
                    btnPesanTiket2.visibility = View.VISIBLE
                    RVTiketAktif.visibility = View.GONE
                } else {
                    // Menampilkan daftar tiket aktif
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
            // Menavigasi ke InputRencanaFragment ketika tombol "Pesan Tiket" diklik
            btnPesanTiket2.setOnClickListener {
                findNavController().navigate(R.id.action_ticketFragment2_to_inputRencanaFragment)
            }
            // Menavigasi ke RiwayatPesananFragment ketika tombol "Tiket Tidak Aktif" diklik
            btnTiketTidakAktif.setOnClickListener {
                findNavController().navigate(R.id.action_ticketFragment2_to_riwayatPesananFragment)
            }
        }
    }

    // Metode untuk memeriksa apakah tanggal tiket masih sebelum tanggal saat ini
    fun isDateBefore(dateString: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            // Parse string tanggal
            val inputDate = dateFormat.parse(dateString)

            // Dapatkan tanggal saat ini
            val currentDate = Date()

            // Bandingkan tanggal input dengan tanggal saat ini
            return inputDate?.after(currentDate) == true
        } catch (e: Exception) {
            // Tangani kesalahan parsing
            e.printStackTrace()
            return false
        }
    }
    // Metode untuk memeriksa apakah waktu tiket sudah berlalu
    fun hasTimePassed(inputTime: String): Boolean {
        // Tentukan format waktu
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        // Dapatkan waktu saat ini
        val currentTime = Calendar.getInstance().time

        try {
            // Parse string waktu
            val inputDate = timeFormat.parse(inputTime)

            // Bandingkan waktu saat ini dengan waktu input
            Log.d("time", inputDate.toString())
            return inputDate?.before(currentTime) == true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Kembalikan false jika ada kesalahan atau parsing gagal
        return false
    }
}