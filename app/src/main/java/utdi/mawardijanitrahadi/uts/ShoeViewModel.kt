package utdi.mawardijanitrahadi.uts

// Import library untuk implementasi ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Kelas ViewModel untuk menangani data sepatu
class ShoeViewModel : ViewModel() {

    // LiveData untuk daftar sepatu
    private val _shoes = MutableLiveData<List<Shoe>>()
    val shoes: LiveData<List<Shoe>> get() = _shoes

    // Inisialisasi ViewModel, mengisi LiveData dengan data sepatu awal
    init {

        _shoes.value = listOf(
            Shoe("Running Shoes", "Nike", R.drawable.nike_shoe),
            Shoe("Sneakers", "Adidas", R.drawable.adidas_shoe),
            Shoe("Casual Shoes", "Puma", R.drawable.puma_shoe),
            Shoe("Boot Shoes", "Harley Davidson", R.drawable.davidson_boot_shoe),
            Shoe("Mules Shoes", "Dior", R.drawable.dior_mules_shoe),
            // Dapat menambahkan data sepatu lain jika diperlukan
        )
    }

    // Fungsi untuk mencari sepatu berdasarkan query
    fun searchShoes(query: String) {
        // Jika query kosong, tampilkan semua sepatu
        _shoes.value = if (query.isEmpty()) {
            _shoes.value
        } else {
            // Filter sepatu berdasarkan query
            _shoes.value?.filter { shoe ->
                shoe.name.contains(query, ignoreCase = true) ||
                        shoe.brand.contains(query, ignoreCase = true)
            }
        }
    }
}
