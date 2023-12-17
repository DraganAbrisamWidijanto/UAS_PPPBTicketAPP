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

    companion object {
        private var instance: MainActivity? = null
        fun getInstance(): MainActivity {
            return instance!!
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fbDb = FirebaseFirestore.getInstance()
        accountCollection = fbDb.collection("account")
        destinasiCollection = fbDb.collection("destinasi")
        sharedPreferences = SharePreferences.getInstance(this)
        db = RoomDb.getDatabase(this)!!
        dbDao = db.ticketDao()
        favoriteDao = db.favoriteDao()
        executorService = Executors.newSingleThreadExecutor()

        with(binding) {
            btnLogin.setOnClickListener {
                val intent = Intent(this@MainActivity, ContainerLoginActivity::class.java)
                intent.putExtra("number", 0)
                startActivity(intent)
            }
            btnRegister.setOnClickListener {
                val intent = Intent(this@MainActivity, ContainerLoginActivity::class.java)
                intent.putExtra("number", 1)
                startActivity(intent)
            }
        }

        instance = this

        if(sharedPreferences.isLoggedIn()) {
            val intent = Intent(this, MemberActivityContainer::class.java)
            startActivity(intent)
        }
    }

    fun getFavorite(username: String): LiveData<List<Favorite>> {
        return favoriteDao.getAllFavorites(username)
    }
    fun insertFavorite(favorite: Favorite) {
        executorService.execute {
            favoriteDao.insertFavorite(favorite)
        }
    }

    fun deleteFavorite(favorite: Favorite) {
        executorService.execute {
            favoriteDao.deleteFavorite(favorite)
        }
    }

    fun insertTicket(ticket: Ticket) {
        executorService.execute {
            dbDao.insertTicket(ticket)
        }
    }

    fun getTicket(username: String): LiveData<List<Ticket>> {
        return dbDao.getAllTicket(username)
    }

    fun getSharedPreferences(): SharePreferences {
        return sharedPreferences
    }

    fun getDestinasi(): MutableLiveData<List<Destinasi>> {
        return destinasiLiveData
    }
    fun getDestination() {
        destinasiCollection.addSnapshotListener { value, error ->
            if(error != null) {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
            val destinasi = value?.toObjects(Destinasi::class.java)
            if(destinasi != null) {
                    destinasiLiveData.postValue(destinasi)
            }
        }

    }

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

    fun updateDestination(destinasi: Destinasi) {
        destinasiCollection.document(destinasi.id).set(destinasi)
            .addOnSuccessListener {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
    }
    fun deleteDestination(destinasi: Destinasi) {
        destinasiCollection.document(destinasi.id).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
    }
    fun getAdminld(): MutableLiveData<List<Akun>> {
        return accountLiveData
    }

    fun getAdmin() {
        accountCollection.addSnapshotListener { value, error ->
            if(error != null) {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
            val akun = value?.toObjects(Akun::class.java)
            val temp = mutableListOf<Akun>()
            if(akun != null) {
                for(a in akun) {
                    if(a.tipe == "admin") {
                        temp.add(a)
                    }
                }
            }
            if(temp != null) {
                accountLiveData.postValue(temp)
            }
        }
    }

    fun deleteUser(akun: Akun) {
        accountCollection.document(akun.id).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Delete Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show()
            }
    }
    fun updateUser(akun: Akun) {
        accountCollection.document(akun.id).set(akun)
            .addOnSuccessListener {
                Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
            }
    }
    fun registerUser(akun: Akun) {
        // check username later
        accountCollection.add(akun)
            .addOnSuccessListener {documentReference ->
                akun.id = documentReference.id
                documentReference.set(akun)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()

                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
            }
    }

    fun getAkun(username: String, password: String) {
        accountCollection.whereEqualTo("username", username).limit(1).get()
            .addOnSuccessListener { query ->
                if (!query.isEmpty) {
                    val data = query.documents[0].data
                    if (data != null) {
                        val storedPassword = data["password"].toString()
                        if (password == storedPassword) {
                            // Password valid, lanjutkan proses login
                            sharedPreferences.saveUsername(data["username"].toString())
                            sharedPreferences.saveEmail(data["email"].toString())
                            sharedPreferences.savePassword(data["password"].toString())
                            sharedPreferences.saveEmail(data["email"].toString())
                            sharedPreferences.saveNim(data["nim"].toString())
                            if (data["tipe"] == "admin") {
                                sharedPreferences.setAdmin()
                            }

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

    fun login(username: String, password: String) {
        if(username == sharedPreferences.getUsername() && password == sharedPreferences.getPassword()) {
            if(sharedPreferences.isAdmin()){
                sharedPreferences.setLoggedIn(isLoggedIn = true, isAdmin = true)
            } else {
                sharedPreferences.setLoggedIn(isLoggedIn = true, isAdmin = false)
            }
            val intent = Intent(this, MemberActivityContainer::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
        }
    }

}
