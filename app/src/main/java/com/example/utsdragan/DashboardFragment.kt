package com.example.utsdragan

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.utsdragan.databinding.FragmentDashboardBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Mengembang tata letak untuk fragment ini menggunakan View Binding
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            // Menambahkan listener untuk tombol btnPesanTiket
            btnPesanTiket.setOnClickListener {
                // Mendapatkan NavController dan navigasi ke InputRencanaFragment
                val navController = findNavController()
                navController.navigate(R.id.action_dashboardFragment2_to_inputRencanaFragment)
            }
            // Menambahkan listener untuk tombol showdatepickerpls
            showdatepickerpls.setOnClickListener {
                // Memanggil fungsi showDatePicker() untuk menampilkan dialog pemilih tanggal
                showDatePicker()
            }
        }
    }

    // Fungsi untuk menampilkan dialog pemilih tanggal
    private fun showDatePicker() {
        // Mendapatkan tanggal saat ini
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Membuat instance DatePicker dengan tanggal saat ini
        val datePicker = DatePicker(requireContext())
        datePicker.init(year, month, day, DatePicker.OnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            // Menangani perubahan tanggal dengan memanggil onDateChanged()
            onDateChanged(year, monthOfYear, dayOfMonth)
        })

        // Membuat dialog kustom untuk menampilkan DatePicker
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(datePicker)
            .setPositiveButton("OK") { _, _ ->
                // Opsional: Menangani klik tombol "OK" jika diperlukan
            }
            .setNegativeButton("Cancel", null)
            .create()
        // Menampilkan dialog
        dialog.show()
    }

    // Fungsi yang dipanggil saat tanggal berubah pada DatePicker
    private fun onDateChanged(year: Int, month: Int, day: Int) {
        // Membuat instance Calendar dengan tanggal yang dipilih
        val selectedDate = Calendar.getInstance()
        selectedDate.set(Calendar.YEAR, year)
        selectedDate.set(Calendar.MONTH, month)
        selectedDate.set(Calendar.DAY_OF_MONTH, day)

        // Format tanggal yang dipilih
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(selectedDate.time)

        // Menampilkan Toast dengan tanggal yang dipilih ada rentang tanggal yang sudah dipesan atau tidak
        val dates = MainActivity.getInstance().getTicket(MainActivity.getInstance().getSharedPreferences().getUsername())
        dates.observe(viewLifecycleOwner) {
            if (it != null) {
                for (date in it) {
                    Log.d("TAG", "onDateChanged: ${date.tanggal}, $formattedDate")
                    if (date.tanggal == formattedDate) {
                        Toast.makeText(requireContext(), "Anda punya tiket pada hari itu", Toast.LENGTH_SHORT)
                            .show()
                        break
                    }
                }
            }
        }
    }

}