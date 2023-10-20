// Mendefinisikan package untuk kode Compose UI
package utdi.mawardijanitrahadi.uts

// Mengimpor library dan komponen Compose yang diperlukan
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Mendefinisikan data class untuk merepresentasikan sepatu
data class Shoe(val name: String, val brand: String, val imageResId: Int)

// Fungsi komposabel untuk menampilkan daftar sepatu menggunakan LazyColumn
@Composable
fun ShoeList(shoes: List<Shoe>) {
    LazyColumn {
        items(shoes) { shoe ->
            ShoeCard(shoe = shoe)
        }
    }
}

// Fungsi komposabel untuk menampilkan Card untuk sepatu
@Composable
fun ShoeCard(shoe: Shoe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = Color.White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Menampilkan gambar sepatu
            Image(
                painter = painterResource(id = shoe.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .background(color = Color.Gray)
            )
            // Menampilkan nama dan merek sepatu dalam Column
            Column {
                Text(
                    text = shoe.name,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = shoe.brand,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

// Fungsi komposabel untuk searchBar menggunakan OutlinedTextField
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    // State untuk menyimpan query saat ini pada searchBar
    var query by remember { mutableStateOf("") }

    // OutlinedTextField untuk input pengguna dengan ikon pencarian dan pengaturan
    OutlinedTextField(
        value = query,
        onValueChange = {
            query = it
            // Panggil kembali fungsi yang diberikan saat melakukan pencarian
            onSearch(it)
        },
        label = { Text(text = "Search") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        trailingIcon = {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings Icon")
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

// Fungsi komposabel untuk MainActivity
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivity() {
    // Daftar sepatu yang akan ditampilkan
    val shoes = remember {
        listOf(
            Shoe("Running Shoes", "Nike", R.drawable.nike_shoe),
            Shoe("Sneakers", "Adidas", R.drawable.adidas_shoe),
            Shoe("Casual Shoes", "Puma", R.drawable.puma_shoe),
            Shoe("Boot Shoes", "Harley Davidson", R.drawable.davidson_boot_shoe),
            Shoe("Mules Shoes", "Dior", R.drawable.dior_mules_shoe),
            // Dapat menambahkan data sepatu lebih banyak jika diperlukan
        )
    }

    // State untuk menyimpan daftar sepatu yang sudah difilter berdasarkan query pencarian
    var filteredShoes by remember { mutableStateOf(shoes) }

    // State untuk menyimpan query pencarian saat ini
    var searchQuery by remember { mutableStateOf("") }

    // Column untuk menyusun komponen UI secara vertikal
    Column {
        // TopAppBar dengan judul dan ikon pencarian
        TopAppBar(
            title = { Text(text = "Mawardi Shoes Store") },
            actions = {
                IconButton(onClick = { /* Menangani klik ikon pencarian */ }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            }
        )
        // Komposabel SearchBar dengan callback untuk memperbarui daftar sepatu yang sudah difilter
        SearchBar(onSearch = {
            searchQuery = it
            // Perbarui daftar sepatu yang sudah difilter berdasarkan query pencarian
            filteredShoes = if (it.isEmpty()) {
                shoes
            } else {
                shoes.filter { shoe ->
                    shoe.name.contains(it, ignoreCase = true) ||
                            shoe.brand.contains(it, ignoreCase = true)
                }
            }
        })
        // Menampilkan daftar sepatu menggunakan komposabel ShoeList
        ShoeList(shoes = filteredShoes)
    }
}

// Komposabel preview untuk MainActivity
@Preview(showBackground = true)
@Composable
fun PreviewMainActivity() {
    // Preview MainActivity
    MainActivity()
}
