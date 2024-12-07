package com.skybound.ui.questionnaire

import androidx.lifecycle.ViewModel
import com.skybound.R
import com.skybound.data.questionnaire.Option
import com.skybound.data.questionnaire.Questionnaire

class QuestionnaireViewModel : ViewModel() {
    val answers = mutableMapOf<Int, String>()  // To store answers

    // Save answer for a specific question
    fun saveAnswer(questionIndex: Int, answer: String) {
        answers[questionIndex] = answer
    }

    val questions = listOf(
        Questionnaire(
            id = 1,
            questionText = "Saya suka untuk mencoba hobi atau kegiatan baru dalam hidup saya",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 2,
            questionText = "Saya merasa nyaman ketika berbicara tentang ide baru yang berbeda",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 3,
            questionText = "Saya cenderung menyukai seni, musik, budaya, atau kegiatan kreatif lainnya",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 4,
            questionText = "Saya tidak tertarik dengan topik yang kompleks atau mendalam",
            options = listOf(
                Option("Sangat Setuju", null, 0),
                Option("Setuju", null, 2),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 8),
                Option("Sangat Tidak Setuju", null, 10)
            )
        ),
        Questionnaire(
            id = 5,
            questionText = "Saya lebih suka rutinitas daripada mencoba hal-hal baru",
            options = listOf(
                Option("Sangat Setuju", null, 0),
                Option("Setuju", null, 2),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 8),
                Option("Sangat Tidak Setuju", null, 10)
            )
        ),
        Questionnaire(
            id = 6,
            questionText = "Saya selalu memastikan pekerjaan saya selesai tepat waktu",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 7,
            questionText = "Saya merasa puas ketika pekerjaan saya tersusun dengan rapi dan terorganisir",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 8,
            questionText = "Saya lebih suka menyelesaikan tugas-tugas yang sudah saya rencanakan sebelumnya",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 9,
            questionText = "Saya suka mengabaikan tanggung jawab yang telah diberikan kepada saya",
            options = listOf(
                Option("Sangat Setuju", null, 0),
                Option("Setuju", null, 2),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 8),
                Option("Sangat Tidak Setuju", null, 10)
            )
        ),
        Questionnaire(
            id = 10,
            questionText = "Saya lebih suka melakukan tugas dengan hati-hati dan memperhatikan detail",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 11,
            questionText = "Saya merasa energik dan antusias ketika berada di sekitar banyak orang",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 12,
            questionText = "Saya lebih suka berada di tengah keramaian daripada sendirian",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 13,
            questionText = "Saya merasa nyaman berbicara dengan orang baru di acara sosial",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 14,
            questionText = "Saya tidak suka menjadi pusat perhatian dalam suatu acara atau kegiatan",
            options = listOf(
                Option("Sangat Setuju", null, 0),
                Option("Setuju", null, 2),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 8),
                Option("Sangat Tidak Setuju", null, 10)
            )
        ),
        Questionnaire(
            id = 15,
            questionText = "Saya merasa lebih baik setelah berinteraksi dengan orang lain",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 16,
            questionText = "Saya selalu berusaha untuk membantu orang lain meskipun itu mengorbankan waktu saya",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 17,
            questionText = "Saya tidak memaafkan orang lain ketika mereka melakukan kesalahan",
            options = listOf(
                Option("Sangat Setuju", null, 0),
                Option("Setuju", null, 2),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 8),
                Option("Sangat Tidak Setuju", null, 10)
            )
        ),
        Questionnaire(
            id = 18,
            questionText = "Saya cenderung menghindari konflik dan lebih suka mencari solusi damai",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 19,
            questionText = "Saya percaya bahwa kebanyakan orang pada dasarnya baik",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 20,
            questionText = "Saya lebih suka bekerja dengan orang lain daripada bekerja sendirian",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 21,
            questionText = "Saya mudah merasa cemas ketika menghadapi situasi yang tidak pasti",
            options = listOf(
                Option("Sangat Setuju", null, 0),
                Option("Setuju", null, 2),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 8),
                Option("Sangat Tidak Setuju", null, 10)
            )
        ),
        Questionnaire(
            id = 22,
            questionText = "Saya tidak merasa khawatir tentang masa depan",
            options = listOf(
                Option("Sangat Setuju", null, 10),
                Option("Setuju", null, 8),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 2),
                Option("Sangat Tidak Setuju", null, 0)
            )
        ),
        Questionnaire(
            id = 23,
            questionText = "Saya cenderung mudah merasa tertekan atau cemas dalam situasi yang menantang",
            options = listOf(
                Option("Sangat Setuju", null, 0),
                Option("Setuju", null, 2),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 8),
                Option("Sangat Tidak Setuju", null, 10)
            )
        ),
        Questionnaire(
            id = 24,
            questionText = "Saya merasa gelisah bahkan dalam keadaan yang tenang",
            options = listOf(
                Option("Sangat Setuju", null, 0),
                Option("Setuju", null, 2),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 8),
                Option("Sangat Tidak Setuju", null, 10)
            )
        ),
        Questionnaire(
            id = 25,
            questionText = "Saya sering merasa marah atau frustrasi tanpa alasan yang jelas",
            options = listOf(
                Option("Sangat Setuju", null, 0),
                Option("Setuju", null, 2),
                Option("Netral", null, 5),
                Option("Tidak Setuju", null, 8),
                Option("Sangat Tidak Setuju", null, 10)
            )
        ),
        Questionnaire(
            id = 26,
            questionText = "Berapa total pendapatan dari penjualan Televisi selama bulan April setelah diskon?",
            options = listOf(
                Option("37.500.000", null, 0),
                Option("40.500.000", null, 10),
                Option("40.000.000", null, 2),
                Option("45.000.000", null, 0)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_26_foreground.toString()
        ),
        Questionnaire(
            id = 27,
            questionText = "Berapa pendapatan bersih total dari semua produk selama bulan Juni setelah diskon?",
            options = listOf(
                Option("150.000.000", null, 2),
                Option("158.000.000", null, 0),
                Option("152.000.000", null, 10),
                Option("160.000.000", null, 0)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_26_foreground.toString()
        ),
        Questionnaire(
            id = 28,
            questionText = "Berapa persentase kontribusi penjualan Mesin Cuci terhadap total pendapatan selama 4 bulan?",
            options = listOf(
                Option("35%", null, 10),
                Option("28%", null, 0),
                Option("25%", null, 0),
                Option("30%", null, 0)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_26_foreground.toString()
        ),
        Questionnaire(
            id = 29,
            questionText = "Jika biaya produksi masing-masing produk adalah 60% dari harga satuannya, berapa keuntungan bersih dari penjualan Kulkas selama 4 bulan?",
            options = listOf(
                Option("56.000.000", null, 10),
                Option("60.000.000", null, 0),
                Option("54.000.000", null, 2),
                Option("52.000.000", null, 0)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_26_foreground.toString()
        ),
        Questionnaire(
            id = 30,
            questionText = "Pada bulan apa Toko Elektronik PGS memperoleh keuntungan tertinggi?",
            options = listOf(
                Option("April", null, 10),
                Option("Mei", null, 0),
                Option("Juni", null, 0),
                Option("Juli", null, 0)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_26_foreground.toString()
        ),
        Questionnaire(
            id = 31,
            questionText = "Jika gambar diputar 90° ke arah kanan, bentuk manakah yang beanr?",
            options = listOf(
                Option("A", R.mipmap.jawaban_a_31_foreground.toString(), 0),
                Option("B", R.mipmap.jawaban_b_31_foreground.toString(), 10),
                Option("C", R.mipmap.jawaban_c_31_foreground.toString(), 0),
                Option("D", R.mipmap.jawaban_d_31_foreground.toString(), 0)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_31_foreground.toString()
        ),
        Questionnaire(
            id = 32,
            questionText = "Bentuk apa saja yang dapat melengkapi pola tersebut?",
            options = listOf(
                Option("A", R.mipmap.jawaban_a_32_foreground.toString(), 0),
                Option("B", R.mipmap.jawaban_b_32_foreground.toString(), 0),
                Option("C", R.mipmap.jawaban_c_32_foreground.toString(), 0),
                Option("D", R.mipmap.jawaban_d_32_foreground.toString(), 10)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_32_foreground.toString()
        ),
        Questionnaire(
            id = 33,
            questionText = "Kubus mana yang dapat dibuat berdasarkan pola lipatan kubus di atas?",
            options = listOf(
                Option("A", R.mipmap.jawaban_a_33_foreground.toString(), 0),
                Option("B", R.mipmap.jawaban_b_33_foreground.toString(), 0),
                Option("C", R.mipmap.jawaban_c_33_foreground.toString(), 10),
                Option("D", R.mipmap.jawaban_d_33_foreground.toString(), 0)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_33_foreground.toString()
        ),
        Questionnaire(
            id = 34,
            questionText = "Tentukan pola mana yang sesuai!",
            options = listOf(
                Option("A", null, 0),
                Option("B", null, 0),
                Option("C", null, 0),
                Option("D", null, 10)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_34_foreground.toString()
        ),
        Questionnaire(
            id = 35,
            questionText = "Seekor tikus berlari di dalam labirin. Tikus itu berbelok ke kiri, lalu berbelok ke kanan, lalu berbelok ke kanan lagi. Dimanak letak tikus itu berakhir?",
            options = listOf(
                Option("A", null, 10),
                Option("B", null, 0),
                Option("C", null, 0),
                Option("D", null, 0)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_35_foreground.toString()
        ),
        Questionnaire(
            id = 36,
            questionText = "Sebuah kertas dilobangi seperti pada gambar. Manakah hasil yang tepat?",
            options = listOf(
                Option("A", R.mipmap.jawaban_a_36_foreground.toString(), 0),
                Option("B", R.mipmap.jawaban_b_36_foreground.toString(), 0),
                Option("C", R.mipmap.jawaban_c_36_foreground.toString(), 0),
                Option("D", R.mipmap.jawaban_d_36_foreground.toString(), 10)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_36_foreground.toString()
        ),
        Questionnaire(
            id = 37,
            questionText = "Urutkan dari sudut terkecil",
            options = listOf(
                Option("3-4-2-1", null, 0),
                Option("4-3-1-2", null, 0),
                Option("3-4-1-2", null, 10),
                Option("3-1-4-2", null, 0)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_37_foreground.toString()
        ),
        Questionnaire(
            id = 38,
            questionText = "Tentukan gambar yang tepat untuk tampilan samping",
            options = listOf(
                Option("A", R.mipmap.jawaban_a_38_foreground.toString(), 10),
                Option("B", R.mipmap.jawaban_b_38_foreground.toString(), 0),
                Option("C", R.mipmap.jawaban_c_38_foreground.toString(), 0),
                Option("D", R.mipmap.jawaban_d_38_foreground.toString(), 0)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_38_foreground.toString()
        ),
        Questionnaire(
            id = 39,
            questionText = "Manakah pilihan yang tepat untuk menggambarkan sisi objek tersebut?",
            options = listOf(
                Option("A", null, 0),
                Option("B", null, 0),
                Option("C", null, 0),
                Option("D", null, 10)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_39_foreground.toString()
        ),
        Questionnaire(
            id = 40,
            questionText = "Berapa total keseluruhan jumlah kubus di atas?",
            options = listOf(
                Option("16", null, 10),
                Option("14", null, 0),
                Option("17", null, 0),
                Option("15", null, 0)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_40_foreground.toString()
        ),
        Questionnaire(
            id = 41,
            questionText = "Temukan pola yang sesuai",
            options = listOf(
                Option("A", R.mipmap.jawaban_a_41_foreground.toString(), 0),
                Option("B", R.mipmap.jawaban_b_41_foreground.toString(), 10),
                Option("C", R.mipmap.jawaban_a_41_foreground.toString(), 0),
                Option("D", R.mipmap.jawaban_a_41_foreground.toString(), 0)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_41_foreground.toString()
        ),
        Questionnaire(
            id = 42,
            questionText = "Pilih kode yang sesuai untuk mengisi simpul (node) yang kosong",
            options = listOf(
                Option("P dan T", null, 0),
                Option("P dan R", null, 0),
                Option("Q dan S", null, 10),
                Option("Q dan T", null, 0)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_42_foreground.toString()
        ),
        Questionnaire(
            id = 43,
            questionText = "Temukan pola yang sesuai dan tepat",
            options = listOf(
                Option("A", R.mipmap.jawaban_a_43_foreground.toString(), 0),
                Option("B", R.mipmap.jawaban_b_43_foreground.toString(), 0),
                Option("C", R.mipmap.jawaban_c_43_foreground.toString(), 10),
                Option("D", R.mipmap.jawaban_d_43_foreground.toString(), 0)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_43_foreground.toString()
        ),
        Questionnaire(
            id = 44,
            questionText = "Tentukan kode yang sesuai",
            options = listOf(
                Option("FM", null, 0),
                Option("HK", null, 0),
                Option("GL", null, 0),
                Option("HL", null, 10)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_44_foreground.toString()
        ),
        Questionnaire(
            id = 45,
            questionText = "Tentukan pola yang tepat untuk domino ke-6",
            options = listOf(
                Option("A", null, 0),
                Option("B", null, 10),
                Option("C", null, 0),
                Option("D", null, 0)
            ),
            isImageQuestion = true,
            questionImageUrl = R.mipmap.soal_45_foreground.toString()
        ),
        Questionnaire(
            id = 46,
            questionText = """Pada Kota XYZ, transportasi umum menjadi andalan masyarakat karena dapat menghemat biaya dan ramah lingkungan. Namun, masyarakat menghadapi tantangan berupa jadwal yang tidak teratur, sehingga banyak yang tetap menggunakan kendaraan pribadi untuk keperluan mendesak. 
            
Manakah pernyataan yang benar?""",
            options = listOf(
                Option("Transportasi umum selalu menjadi pilihan utama masyarakat.", null, 2),
                Option("Kendaraan pribadi digunakan untuk alasan praktis.", null, 10),
                Option("Jadwal transportasi umum selalu teratur.", null, 0),
                Option("Kendaraan pribadi lebih hemat biaya dibanding transportasi umum.", null, 0)
            )
        ),
        Questionnaire(
            id = 47,
            questionText = """Semua siswa yang rajin belajar akan lulus ujian. Hadid adalah siswa yang rajin belajar.

Apa yang dapat disimpulkan dari kalimat di atas?""",
            options = listOf(
                Option("Hadid tidak akan lulus ujian.", null, 0),
                Option("Semua siswa akan lulus ujian.", null, 0),
                Option("Hadid pasti lulus ujian.", null, 10),
                Option("Hadid mungkin lulus ujian.", null, 2)
            )
        ),
        Questionnaire(
            id = 48,
            questionText = """Pemerintah mengimbau masyarakat untuk tetap waspada terhadap kemungkinan bencana alam.

Apa sinonim terbaik untuk kata "waspada"?""",
            options = listOf(
                Option("Hati-hati", null, 10),
                Option("Sigap", null, 0),
                Option("Peka", null, 0),
                Option("Siap-siaga", null, 2)
            )
        ),
        Questionnaire(
            id = 49,
            questionText = """Restoran tersebut menawarkan makanan dengan harga yang terjangkau tetapi tetap berkualitas.

Apa arti kata "terjangkau" dalam konteks ini?""",
            options = listOf(
                Option("Dekat secara lokasi.", null, 0),
                Option("Tidak terlalu mahal.", null, 10),
                Option("Dapat diterima oleh semua orang.", null, 0),
                Option("Sangat murah.", null, 2)
            )
        ),
        Questionnaire(
            id = 50,
            questionText = """Petani berhubungan dengan Sawah, seperti Nelayan berhubungan dengan...""",
            options = listOf(
                Option("Laut", null, 10),
                Option("Jala", null, 0),
                Option("Ikan", null, 2),
                Option("Perahu", null, 0)
            )
        )
    )
}
