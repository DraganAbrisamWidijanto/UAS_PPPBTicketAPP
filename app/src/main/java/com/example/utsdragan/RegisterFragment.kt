package com.example.utsdragan

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import com.example.utsdragan.databinding.FragmentRegisterBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class RegisterFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = MainActivity.getInstance()
        val container = ContainerLoginActivity.getInstance()
        val sharedPreferences = mainActivity.getSharedPreferences()

        with(binding) {

            btnsimpandestinasicrud.setOnClickListener {
                val akun = Akun(
                    username = username.text.toString(),
                    email = email.text.toString(),
                    password = password.text.toString(),
                    nim = nim.text.toString(),
                    tanggal = tanggal.text.toString(),
                )
                mainActivity.registerUser(akun)
                container.goToFragment(0)
            }

            tanggal.setOnClickListener {
                showDatePicker()
            }

        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = Calendar.getInstance()
        selectedDate.set(Calendar.YEAR, year)
        selectedDate.set(Calendar.MONTH, month)
        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        // Logika untuk memeriksa usia pengguna
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val age = currentYear - year

        if (age < 15) {
            // Mengeluarkan toast jika usia pengguna kurang dari 15 tahun
            Toast.makeText(context, "Anda harus berusia 15 tahun untuk mendaftar", Toast.LENGTH_SHORT).show()
        } else {
            // Memperbarui tanggal yang dipilih jika usia pengguna di atas 15 tahun
            updateSelectedDate(selectedDate.time)
        }
    }

    private fun updateSelectedDate(date: Date) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        binding.tanggal.setText(formattedDate)
    }

}