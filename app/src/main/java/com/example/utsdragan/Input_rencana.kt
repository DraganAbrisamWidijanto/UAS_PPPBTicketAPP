package com.example.utsdragan

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity

class Input_rencana : AppCompatActivity() {

    // Fungsi ini dipanggil saat aktivitas (halaman) dibuat.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_rencana)

        // Inisialisasi view yang digunakan
        val backButton = findViewById<ImageView>(R.id.backButton)

        // Menambahkan onClickListener untuk tombol logout
        backButton.setOnClickListener{
            // Kode untuk logout
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        // Inisialisasi elemen-elemen antarmuka pengguna
        val asalSpinner: Spinner = findViewById(R.id.spinnerAsal)
        val tujuanSpinner: Spinner = findViewById(R.id.spinnerTujuan)
        val kelasSpinner: Spinner = findViewById(R.id.spinnerKelas)
        val namaSpinner: Spinner = findViewById(R.id.spinnerNama)
        val hargaTextView: TextView = findViewById(R.id.harga)
        val toggleButtons = arrayOf(
            findViewById<ToggleButton>(R.id.toggleButton1),
            findViewById<ToggleButton>(R.id.toggleButton2),
            findViewById<ToggleButton>(R.id.toggleButton3),
            findViewById<ToggleButton>(R.id.toggleButton4),
            findViewById<ToggleButton>(R.id.toggleButton5),
            findViewById<ToggleButton>(R.id.toggleButton6),
            findViewById<ToggleButton>(R.id.toggleButton7)
        )

        // Inisialisasi Spinner untuk Asal, Tujuan, dan Kelas
        val asalArray = arrayOf("Gambir-Jakarta","Tugu-Yogyakarta", "Surabaya Kota-Surabaya")
        val tujuanArray = arrayOf("Gambir-Jakarta","Tugu-Yogyakarta", "Surabaya Kota-Surabaya")
        val namaArray = arrayOf("KA Taksaka", "KA Argo Lawu", "KA Argo Dwipangga", "KA Argo Bromo Anggrek")
        val kelasArray = arrayOf("Ekonomi", "Eksekutif")

        // Adapter untuk Spinner
        val asalAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, asalArray)
        val tujuanAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tujuanArray)
        val kelasAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, kelasArray)
        val namaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, namaArray)

        // Mengatur adapter untuk Spinner
        asalSpinner.adapter = asalAdapter
        tujuanSpinner.adapter = tujuanAdapter
        kelasSpinner.adapter = kelasAdapter
        namaSpinner.adapter = namaAdapter

        // Harga default
        var harga = 0

        // Menghitung harga berdasarkan pilihan Asal dan Tujuan
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
            hargaTextView.text = "Harga: Rp $harga"

            // Menampilkan pemberitahuan jika paket telah ditambahkan
            if (harga > 0) {
                Toast.makeText(this, "Pilihan telah ditambahkan!", Toast.LENGTH_SHORT).show()
            }
        }

        // Menambahkan Listener untuk Spinner
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

        // Menambahkan Listener untuk ToggleButtons
        for (button in toggleButtons) {
            button.setOnCheckedChangeListener { _, _ -> calculateHarga() }
        }

        // Menghitung harga awal saat aplikasi dibuka
        calculateHarga()

        // Menambahkan tombol "Dashboard" untuk pindah ke DashboardActivity
        val dashboardButton: Button = findViewById(R.id.btnsimpan)
        dashboardButton.setOnClickListener {
            // Mendapatkan nilai dari DatePicker
            val datePicker = findViewById<DatePicker>(R.id.datePickerr)
            val year = datePicker.year
            val month = datePicker.month
            val dayOfMonth = datePicker.dayOfMonth

            // Buat Intent untuk berpindah ke DashboardActivity
            val intent = Intent(this, Dashboard::class.java)

            // Kirim data tambahan dalam intent
            intent.putExtra("tanggal", "$year-${month + 1}-$dayOfMonth")
            intent.putExtra("asal", asalSpinner.selectedItem.toString())
            intent.putExtra("tujuan", tujuanSpinner.selectedItem.toString())
            intent.putExtra("Nama", namaSpinner.selectedItem.toString())

            // Memeriksa toggle yang dipilih
            val selectedPaket = StringBuilder()
            for (button in toggleButtons) {
                if (button.isChecked) {
                    selectedPaket.append(button.text).append(", ") // Menambahkan paket yang dipilih
                }
            }

            // Hapus koma ekstra di akhir
            if (selectedPaket.isNotEmpty()) {
                selectedPaket.deleteCharAt(selectedPaket.length - 2)
            }

            // Menyertakan data paket tambahan dalam intent
            intent.putExtra("paket", selectedPaket.toString())
            intent.putExtra("harga", harga)

            // Memulai aktivitas DashboardActivity
            startActivity(intent)
        }
    }
}
