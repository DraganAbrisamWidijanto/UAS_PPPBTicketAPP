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

        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnPesanTiket.setOnClickListener {
                val navController = findNavController()
                navController.navigate(R.id.action_dashboardFragment2_to_inputRencanaFragment)
            }

            showdatepickerpls.setOnClickListener {
                showDatePicker()
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePicker(requireContext())
        datePicker.init(year, month, day, DatePicker.OnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            onDateChanged(year, monthOfYear, dayOfMonth)
        })

        // Create a custom dialog to host the DatePicker
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(datePicker)
            .setPositiveButton("OK") { _, _ ->
                // Optional: Handle "OK" button click if needed
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun onDateChanged(year: Int, month: Int, day: Int) {
        val selectedDate = Calendar.getInstance()
        selectedDate.set(Calendar.YEAR, year)
        selectedDate.set(Calendar.MONTH, month)
        selectedDate.set(Calendar.DAY_OF_MONTH, day)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(selectedDate.time)

        // Show a Toast with the selected date
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