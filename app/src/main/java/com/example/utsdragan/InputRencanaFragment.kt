package com.example.utsdragan

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.utsdragan.databinding.FragmentInputRencanaBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class InputRencanaFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: FragmentInputRencanaBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInputRencanaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = MainActivity.getInstance()

        val asalArray = arrayOf("Gambir-Jakarta","Tugu-Yogyakarta", "Surabaya Kota-Surabaya")
        val tujuanArray = arrayOf("Gambir-Jakarta","Tugu-Yogyakarta", "Surabaya Kota-Surabaya")
        val namaArray = arrayOf(
            "KA Argo Bromo Anggrek", "KA Argo Dwipangga", "KA Argo Lawu", "KA Argo Muria", "KA Argo Parahyangan", "KA Taksaka")
        val kelasArray = arrayOf("Ekonomi", "Eksekutif")
        val fran = mutableListOf<String>()

        val asalAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, asalArray)
        val tujuanAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tujuanArray)
        val kelasAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, kelasArray)
        val namaAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, namaArray)

        var harga = 0

        val asalSpinner = binding.spinnerAsal
        val tujuanSpinner = binding.spinnerTujuan
        val kelasSpinner = binding.spinnerKelas
        val namaSpinner = binding.spinnerNama
        val hargaTextView = binding.harga

        val toggleButtons = arrayOf(
            binding.toggleButton1,
            binding.toggleButton2,
            binding.toggleButton3,
            binding.toggleButton4,
            binding.toggleButton5,
            binding.toggleButton6,
            binding.toggleButton7
        )

        asalSpinner.adapter = asalAdapter
        tujuanSpinner.adapter = tujuanAdapter
        kelasSpinner.adapter = kelasAdapter
        namaSpinner.adapter = namaAdapter

        fun calculateHarga() {
            // Mendapatkan nilai dari Spinner dan ToggleButtons
            val selectedAsal = asalSpinner.selectedItem.toString()
            val selectedTujuan = tujuanSpinner.selectedItem.toString()
            val selectedKelas = kelasSpinner.selectedItem.toString()

            // Logika perhitungan harga
            harga = when {
                // Kasus 1
                (selectedAsal == "Gambir-Jakarta" && selectedTujuan == "Tugu-Yogyakarta") ||
                        (selectedAsal == "Tugu-Yogyakarta" && selectedTujuan == "Gambir-Jakarta") -> {
                    if (selectedKelas == "Ekonomi") 200000 else 300000
                }
                // Kasus 2
                (selectedAsal == "Tugu-Yogyakarta" && selectedTujuan == "Surabaya Kota-Surabaya") ||
                        (selectedAsal == "Surabaya Kota-Surabaya" && selectedTujuan == "Tugu-Yogyakarta") -> {
                    if (selectedKelas == "Ekonomi") 200000 else 300000
                }
                // Kasus 3
                (selectedAsal == "Gambir-Jakarta" && selectedTujuan == "Surabaya Kota-Surabaya") ||
                        (selectedAsal == "Surabaya Kota-Surabaya" && selectedTujuan == "Gambir-Jakarta") -> {
                    if (selectedKelas == "Ekonomi") 300000 else 500000
                }
                else -> 0
            }

            // Menghitung harga paket tambahan
            for (button in toggleButtons) {
                if (button.isChecked) {
                    harga += 20000
                }
            }

            // Menampilkan harga pada TextView
            hargaTextView.text = "Rp$harga"

            // Menampilkan pemberitahuan jika paket telah ditambahkan
            if (harga > 0) {
                Toast.makeText(requireContext(), "Pilihan telah ditambahkan!", Toast.LENGTH_SHORT).show()
            }
        }

        asalSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                calculateHarga()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        tujuanSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                calculateHarga()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        kelasSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                calculateHarga()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        for (button in toggleButtons) {
            button.setOnCheckedChangeListener { _, _ ->
                calculateHarga()
                if (!fran.contains(button.text.toString())) {
                    fran.add(button.text.toString())
                }
            }
        }

        binding.frick.setOnClickListener{
            showDatePicker()
        }

        binding.brick.setOnClickListener {
            showTimePicker()
        }

        calculateHarga()

        val dashboardButton = binding.btnsimpan
        dashboardButton.setOnClickListener {
            val ticket = Ticket(
                username = mainActivity.getSharedPreferences().getUsername(),
                nama = binding.spinnerNama.selectedItem.toString(),
                tanggal = binding.tanggal.text.toString(),
                jam = binding.jam.text.toString(),
                asal = binding.spinnerAsal.selectedItem.toString(),
                tujuan = binding.spinnerTujuan.selectedItem.toString(),
                harga = harga,
                pesananTambahan = fran
            )
            mainActivity.insertTicket(ticket)
            notifyTicket()
            val navController = findNavController()
            navController.navigate(R.id.action_inputRencanaFragment_to_dashboardFragment)
        }

        binding.backtohomefragmen.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_inputRencanaFragment_to_dashboardFragment)
        }
    }


    private fun notifyTicket() {
        val NOTIFICATION_ID = 1 // Unique notification ID

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            0
        }

        val intent = Intent(requireContext(), NotifReceiver::class.java).putExtra("message", "Ticket Added")
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, flag)
        val builder = NotificationCompat.Builder(requireContext(), "1")
            .setSmallIcon(R.drawable.notif1jamkeberangkatan)
            .setContentTitle("Ticket Added")
            .setContentText("Anda telah menambahkan tiket, silahkan cek di halaman tiket")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("1", "1", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "1"
            val notificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000 // Set a minimum date if needed
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = Calendar.getInstance()
        selectedDate.set(Calendar.YEAR, year)
        selectedDate.set(Calendar.MONTH, month)
        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        updateSelectedDate(selectedDate.time)
    }

    private fun updateSelectedDate(date: Date) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        binding.tanggal.text = formattedDate
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            this,
            hour,
            minute,
            true // Set to true for 24-hour format, false for 12-hour format
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: android.widget.TimePicker?, hourOfDay: Int, minute: Int) {
        val selectedTime = Calendar.getInstance()
        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        selectedTime.set(Calendar.MINUTE, minute)

        updateSelectedTime(selectedTime.time)
    }

    private fun updateSelectedTime(time: Date) {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedTime = timeFormat.format(time)
        binding.jam.text = formattedTime
    }

}