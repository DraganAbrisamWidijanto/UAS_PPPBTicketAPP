package com.example.utsdragan

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.utsdragan.databinding.ActivityMainBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    // Deklarasi variabel-variabel
    private lateinit var binding: ActivityMainBinding
    private lateinit var fbDb: FirebaseFirestore
    private lateinit var accountCollection: CollectionReference
    private lateinit var destinasiCollection: CollectionReference
    private lateinit var sharedPreferences: SharePreferences
    private lateinit var executorService: ExecutorService
    private lateinit var db: RoomDb
    private lateinit var dbDao: TicketDao
    private lateinit var favoriteDao: FavoriteDao
    private val destinasiLiveData: MutableLiveData<List<Destinasi>> by lazy {
        MutableLiveData<List<Destinasi>>()
    }
    private val accountLiveData: MutableLiveData<List<Akun>> by lazy {
        MutableLiveData<List<Akun>>()
    }

    // Companion object untuk menyediakan instance tunggal dari MainActivity
    companion object {
        private var instance: MainActivity? = null
        fun getInstance(): MainActivity {
            return instance!!
        }
    }

    // Metode onCreate yang dipanggil saat aktivitas dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi Firebase Firestore dan komponen-komponen lainnya
        fbDb = FirebaseFirestore.getInstance()
        accountCollection = fbDb.collection("account")
        destinasiCollection = fbDb.collection("destinasi")
        sharedPreferences = SharePreferences.getInstance(this)
        db = RoomDb.getDatabase(this)!!
        dbDao = db.ticketDao()
        favoriteDao = db.favoriteDao()
        executorService = Executors.newSingleThreadExecutor()

        // Menangani klik tombol
        with(binding) {
            // Menangani klik tombol login
            btnLogin.setOnClickListener {
                val intent = Intent(this@MainActivity, ContainerLoginActivity::class.java)
                intent.putExtra("number", 0)
                startActivity(intent)
            }
            // Menangani klik tombol register
            btnRegister.setOnClickListener {
                val intent = Intent(this@MainActivity, ContainerLoginActivity::class.java)
                intent.putExtra("number", 1)
                startActivity(intent)
            }
        }

        instance = this

        // Cek apakah pengguna sudah login sebelumnya
        if(sharedPreferences.isLoggedIn()) {
            val intent = Intent(this, MemberActivityContainer::class.java)
            startActivity(intent)
        }
    }

    // Metode untuk mendapatkan daftar favorit berdasarkan username
    fun getFavorite(username: String): LiveData<List<Favorite>> {
        return favoriteDao.getAllFavorites(username)
    }

    // Metode untuk menyisipkan data favorit ke dalam database lokal
    fun insertFavorite(favorite: Favorite) {
        executorService.execute {
            favoriteDao.insertFavorite(favorite)
        }
    }

    // Metode untuk menghapus data favorit dari database lokal
    fun deleteFavorite(favorite: Favorite) {
        executorService.execute {
            favoriteDao.deleteFavorite(favorite)
        }
    }

    // Metode untuk menyisipkan data tiket ke dalam database lokal
    fun insertTicket(ticket: Ticket) {
        executorService.execute {
            dbDao.insertTicket(ticket)
        }
    }

    // Metode untuk mendapatkan daftar tiket berdasarkan username
    fun getTicket(username: String): LiveData<List<Ticket>> {
        return dbDao.getAllTicket(username)
    }

    // Metode untuk mendapatkan instance dari SharePreferences
    fun getSharedPreferences(): SharePreferences {
        return sharedPreferences
    }

    // Metode untuk mendapatkan LiveData daftar destinasi
    fun getDestinasi(): MutableLiveData<List<Destinasi>> {
        return destinasiLiveData
    }

    // Metode untuk mendapatkan data destinasi dari Firebase Firestore
    fun getDestination() {
        destinasiCollection.addSnapshotListener { value, error ->
            if(error != null) {
                // Tampilkan pesan gagal jika terjadi kesalahan
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
            val destinasi = value?.toObjects(Destinasi::class.java)
            if(destinasi != null) {
                // Update LiveData dengan data destinasi
                    destinasiLiveData.postValue(destinasi)
            }
        }
    }

    // Metode untuk menyisipkan data destinasi ke dalam Firebase Firestore
    fun postDestination(destinasi: Destinasi) {
        destinasiCollection.add(destinasi)
            .addOnSuccessListener {
                destinasi.id = it.id
                it.set(destinasi)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
    }

    // Metode untuk mengupdate data destinasi di Firebase Firestore
    fun updateDestination(destinasi: Destinasi) {
        destinasiCollection.document(destinasi.id).set(destinasi)
            .addOnSuccessListener {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
    }

    // Metode untuk menghapus data destinasi dari Firebase Firestore
    fun deleteDestination(destinasi: Destinasi) {
        destinasiCollection.document(destinasi.id).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
    }

    // Metode untuk mendapatkan LiveData daftar admin
    fun getAdminld(): MutableLiveData<List<Akun>> {
        return accountLiveData
    }

    // Metode untuk mendapatkan data admin dari Firebase Firestore
    fun getAdmin() {
        accountCollection.addSnapshotListener { value, error ->
            if(error != null) {
                // Tampilkan pesan gagal jika terjadi kesalahan
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
            val akun = value?.toObjects(Akun::class.java)
            val temp = mutableListOf<Akun>()
            if(akun != null) {
                // Iterasi setiap data Akun
                for(a in akun) {
                    if(a.tipe == "admin") {
                        // Jika tipe Akun adalah "admin", tambahkan ke dalam list sementara (temp)
                        temp.add(a)
                    }
                }
            }
            if(temp != null) {
                // Update LiveData dengan list Akun yang memiliki tipe "admin"
                accountLiveData.postValue(temp)
            }
        }
    }

    // Metode untuk menghapus data pengguna dari Firebase Firestore
    fun deleteUser(akun: Akun) {
        accountCollection.document(akun.id).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Delete Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show()
            }
    }

    // Metode untuk mengupdate data pengguna di Firebase Firestore
    fun updateUser(akun: Akun) {
        accountCollection.document(akun.id).set(akun)
            .addOnSuccessListener {
                Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
            }
    }

    // Metode untuk menyisipkan data pengguna ke dalam Firebase Firestore
    fun registerUser(akun: Akun) {
        // check username later
        accountCollection.add(akun)
            .addOnSuccessListener {documentReference ->
                // Jika data berhasil disisipkan, dapatkan ID dokumen baru
                akun.id = documentReference.id
                // Set data Akun ke dokumen yang baru dibuat
                documentReference.set(akun)
                    .addOnSuccessListener {
                        // Tampilkan pesan sukses jika penyisipan berhasil
                        Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()

                    }
                    .addOnFailureListener {
                        // Tampilkan pesan gagal jika penyisipan data gagal
                        Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                // Tampilkan pesan gagal jika penyisipan data gagal
                Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
            }
    }

    // Metode untuk mendapatkan data akun dari Firebase Firestore berdasarkan username dan password
    fun getAkun(username: String, password: String) {
        accountCollection.whereEqualTo("username", username).limit(1).get()
            .addOnSuccessListener { query ->
                if (!query.isEmpty) {
                    val data = query.documents[0].data
                    if (data != null) {
                        val storedPassword = data["password"].toString()
                        // Membandingkan password yang dimasukkan dengan password yang disimpan
                        if (password == storedPassword) {
                            // Password valid, lanjutkan proses login dan
                            // Menyimpan data pengguna ke dalam Shared Preferences
                            sharedPreferences.saveUsername(data["username"].toString())
                            sharedPreferences.saveEmail(data["email"].toString())
                            sharedPreferences.savePassword(data["password"].toString())
                            sharedPreferences.saveEmail(data["email"].toString())
                            sharedPreferences.saveNim(data["nim"].toString())
                            // Jika akun adalah admin, set flag admin di SharedPreferences
                            if (data["tipe"] == "admin") {
                                sharedPreferences.setAdmin()
                            }
                            // Lanjutkan proses login
                            login(sharedPreferences.getUsername(), sharedPreferences.getPassword())
                        } else {
                            // Password tidak valid, tampilkan pesan toast
                            Toast.makeText(this, "Password salah", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Tampilkan pesan toast bahwa data akun tidak valid
                        Toast.makeText(this, "Data akun tidak valid", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Tampilkan pesan toast bahwa data akun tidak ditemukan
                    Toast.makeText(this, "Data akun tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    // Metode untuk proses login
    fun login(username: String, password: String) {
        // Cek apakah pengguna adalah admin atau bukan dan atur flag di SharedPreferences
        if(username == sharedPreferences.getUsername() && password == sharedPreferences.getPassword()) {
            if(sharedPreferences.isAdmin()){
                sharedPreferences.setLoggedIn(isLoggedIn = true, isAdmin = true)
            } else {
                sharedPreferences.setLoggedIn(isLoggedIn = true, isAdmin = false)
            }
            // Pindah ke aktivitas MemberActivityContainer setelah login berhasil
            val intent = Intent(this, MemberActivityContainer::class.java)
            startActivity(intent)
        } else {
            // Tampilkan pesan toast jika login gagal
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
        }
    }

}
