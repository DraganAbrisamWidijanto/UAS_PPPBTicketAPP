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

    // Menginisialisasi variabel untuk data binding
    private lateinit var binding: FragmentInputRencanaBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menginflate tata letak untuk fragmen ini menggunakan data binding
        binding = FragmentInputRencanaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Mendapatkan instance dari MainActivity
        val mainActivity = MainActivity.getInstance()

        // Array yang berisi pilihan untuk spinner
        val asalArray = arrayOf("Gambir-Jakarta","Tugu-Yogyakarta", "Surabaya Kota-Surabaya")
        val tujuanArray = arrayOf("Gambir-Jakarta","Tugu-Yogyakarta", "Surabaya Kota-Surabaya")
        val namaArray = arrayOf(
            "KA Argo Bromo Anggrek", "KA Argo Dwipangga", "KA Argo Lawu", "KA Argo Muria", "KA Argo Parahyangan", "KA Taksaka")
        val kelasArray = arrayOf("Ekonomi", "Eksekutif")
        val fran = mutableListOf<String>()

        // Adapter untuk spinner
        val asalAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, asalArray)
        val tujuanAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tujuanArray)
        val kelasAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, kelasArray)
        val namaAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, namaArray)

        // Harga tiket yang akan dihitung
        var harga = 0

        // Spinner untuk memilih asal, tujuan, kelas, dan nama kereta
        val asalSpinner = binding.spinnerAsal
        val tujuanSpinner = binding.spinnerTujuan
        val kelasSpinner = binding.spinnerKelas
        val namaSpinner = binding.spinnerNama
        val hargaTextView = binding.harga

        // Array dari tombol toggle
        val toggleButtons = arrayOf(
            binding.toggleButton1,
            binding.toggleButton2,
            binding.toggleButton3,
            binding.toggleButton4,
            binding.toggleButton5,
            binding.toggleButton6,
            binding.toggleButton7
        )

        // Menetapkan adapter ke spinner
        asalSpinner.adapter = asalAdapter
        tujuanSpinner.adapter = tujuanAdapter
        kelasSpinner.adapter = kelasAdapter
        namaSpinner.adapter = namaAdapter

        // Fungsi untuk menghitung harga tiket
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

        // Listener untuk spinner asal
        asalSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Memanggil metode calculateHarga() saat item dipilih pada spinner asal
                calculateHarga()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Listener untuk spinner tujuan
        tujuanSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                calculateHarga()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Listener untuk spinner kelas
        kelasSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                calculateHarga()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Listener untuk toggle buttons
        for (button in toggleButtons) {
            button.setOnCheckedChangeListener { _, _ ->
                // Memanggil metode calculateHarga() saat status toggle berubah
                calculateHarga()
                // Menambahkan nama paket tambahan ke dalam list jika belum ada
                if (!fran.contains(button.text.toString())) {
                    fran.add(button.text.toString())
                }
            }
        }

        // Menampilkan date picker saat tombol di klik
        binding.frick.setOnClickListener{
            showDatePicker()
        }

        // Menampilkan time picker saat tombol di klik
        binding.brick.setOnClickListener {
            showTimePicker()
        }

        calculateHarga()

        // Tombol untuk menyimpan tiket dan kembali ke halaman dashboard
        val dashboardButton = binding.btnsimpan
        dashboardButton.setOnClickListener {
            // Membuat objek Ticket dari data yang dipilih
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
            // Menyimpan tiket ke database
            mainActivity.insertTicket(ticket)
            // Menampilkan pemberitahuan bahwa tiket telah ditambahkan
            notifyTicket()
            // Kembali ke halaman dashboard
            val navController = findNavController()
            navController.navigate(R.id.action_inputRencanaFragment_to_dashboardFragment)
        }

        // Tombol untuk kembali ke halaman dashboard tanpa menyimpan tiket
        binding.backtohomefragmen.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_inputRencanaFragment_to_dashboardFragment)
        }
    }

    // Metode untuk menampilkan pemberitahuan notifikasi
    private fun notifyTicket() {
        // ID unik untuk notifikasi
        val NOTIFICATION_ID = 1 // Unique notification ID

        // Membuat intent untuk disertakan dalam notifikasi
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            0
        }

        // Membuat intent untuk disertakan dalam notifikasi
        val intent = Intent(requireContext(), NotifReceiver::class.java).putExtra("message", "Ticket Added")
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, flag)

        // Membuat objek notifikasi
        val builder = NotificationCompat.Builder(requireContext(), "1")
            .setSmallIcon(R.drawable.notif1jamkeberangkatan)
            .setContentTitle("Ticket Added")
            .setContentText("Anda telah menambahkan tiket, silahkan cek di halaman tiket")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) // Menentukan intent yang akan dijalankan saat notifikasi diklik
            .setAutoCancel(true) // Mengatur notifikasi agar otomatis hilang saat diklik

        // Membuat channel notifikasi (untuk Android versi Oreo ke atas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Membuat objek channel notifikasi dengan ID "1"
            val channel = NotificationChannel("1", "1", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "1"
            val notificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel) // Menambahkan channel notifikasi ke NotificationManager
        }

        // Menampilkan notifikasi
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    // Metode untuk menampilkan date picker
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000 // Set a minimum date if needed
        datePickerDialog.show()
    }

    // Metode untuk menangani hasil pemilihan tanggal dari date picker
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = Calendar.getInstance()
        selectedDate.set(Calendar.YEAR, year)
        selectedDate.set(Calendar.MONTH, month)
        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        // Memperbarui tampilan tanggal yang dipilih
        updateSelectedDate(selectedDate.time)
    }

    // Metode untuk memperbarui tampilan tanggal yang dipilih
    private fun updateSelectedDate(date: Date) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        binding.tanggal.text = formattedDate
    }

    // Metode untuk menampilkan time picker
    private fun showTimePicker() {
        // Membuat instance dari Calendar untuk mendapatkan waktu saat ini
        val calendar = Calendar.getInstance()
        // Mendapatkan nilai jam dan menit dari waktu saat ini
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Membuat dialog time picker
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            this, // OnTimeSetListener akan dipanggil ketika waktu diatur
            hour,
            minute,
            true // Format waktu 24 jam (true) atau 12 jam (false)
        )
        // Menampilkan dialog time picker
        timePickerDialog.show()
    }

    // Metode yang dipanggil ketika waktu telah diatur pada time picker
    override fun onTimeSet(view: android.widget.TimePicker?, hourOfDay: Int, minute: Int) {
        // Membuat instance dari Calendar untuk menyimpan waktu yang dipilih
        val selectedTime = Calendar.getInstance()
        // Mengatur jam dan menit pada instance Calendar
        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        selectedTime.set(Calendar.MINUTE, minute)

        // Memperbarui tampilan waktu yang dipilih
        updateSelectedTime(selectedTime.time)
    }

    // Metode untuk memperbarui tampilan waktu yang dipilih pada TextView
    private fun updateSelectedTime(time: Date) {
        // Membuat objek SimpleDateFormat untuk memformat waktu
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        // Memformat waktu yang dipilih
        val formattedTime = timeFormat.format(time)
        // Mengatur teks pada TextView jam
        binding.jam.text = formattedTime
    }
}