package com.example.utsdragan

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.navigation.fragment.findNavController
import com.example.utsdragan.databinding.FragmentAdmincrudEditorPlusadminBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AdmincrudEditorPlusadminFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentAdmincrudEditorPlusadminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Mengembang layout untuk fragmen ini
        binding = FragmentAdmincrudEditorPlusadminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mendapatkan instance dari MainActivity
        val mainActivity = MainActivity.getInstance()

        with(binding) {
            // Menerima data "id" dari Bundle
            val received = arguments?.getString("id")

            // Jika data diterima tidak kosong, mengisi teks input dengan data yang diterima
            if (!received.isNullOrBlank()) {
                usernameadmin.setText(arguments?.getString("username"))
                emailadmin.setText(arguments?.getString("email"))
                nimAdmin.setText(arguments?.getString("nim"))
                tanggal.setText(arguments?.getString("tanggal"))
                passwordAmind.setText(arguments?.getString("password"))
            }

            // Menangani klik tombol untuk menyimpan atau memperbarui data admin
            btnsimpandestinasicrud.setOnClickListener {
                if (!received.isNullOrBlank()) {
                    // Jika dalam mode edit, memperbarui data admin
                    mainActivity.updateUser(
                        Akun(
                            id = received,
                            username = usernameadmin.text.toString(),
                            email = emailadmin.text.toString(),
                            nim = nimAdmin.text.toString(),
                            tanggal = tanggal.text.toString(),
                            password = passwordAmind.text.toString(),
                            tipe = "admin"
                        )
                    )
                } else {
                    // Jika dalam mode tambah data, menambahkan data admin baru
                    mainActivity.registerUser(
                        Akun(
                            username = usernameadmin.text.toString(),
                            email = emailadmin.text.toString(),
                            nim = nimAdmin.text.toString(),
                            tanggal = tanggal.text.toString(),
                            password = passwordAmind.text.toString(),
                            tipe = "admin"
                        )
                    )
                }

                // Navigasi kembali ke CRUDFragment setelah menyimpan atau memperbarui data
                findNavController().navigate(R.id.action_admincrudEditorPlusadminFragment_to_CRUDFragment)
            }

            // Menangani klik tombol untuk kembali ke CRUDFragment
            backtoadmincruddestinasifragment.setOnClickListener {
                findNavController().navigate(R.id.action_admincrudEditorPlusadminFragment_to_CRUDFragment)
            }

            // Menangani klik input tanggal untuk menampilkan date picker
            tanggal.setOnClickListener {
                showDatePicker()
            }
        }
    }

    // Menampilkan date picker dialog
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
        datePickerDialog.show()
    }

    // Callback ketika tanggal dipilih pada date picker
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = Calendar.getInstance()
        selectedDate.set(Calendar.YEAR, year)
        selectedDate.set(Calendar.MONTH, month)
        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateSelectedDate(selectedDate.time)
    }

    // Memperbarui teks input tanggal dengan tanggal yang dipilih
    private fun updateSelectedDate(date: Date) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        binding.tanggal.setText(formattedDate)
    }
}