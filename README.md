# Tutorial 1
## Reflection 1
*You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code. If you nd any mistake in your source code, please explain how to improve your code.*

Setelah membaca module dan melakukan tutorial 1, saya mendapatkan beberapa hal yang perlu diperhatikan ketika menerapkan pinsip - prinsip *Clean Code* dan juga praktik *Secure Coding*. Berikut beberapa poin yang saya dapatkan:
1. ***Clean Coding Principles***
   1. **Meaningful Names**: Pastikan nama variabel, metode, dan kelas memiliki makna yang jelas. Misalnya, jika kode memiliki variabel yang menyimpan usia, beri nama `age` bukan `a`. Penamaan yang ambigu dapat memunculkan kebingungan disaat kita sendiri maupun orang lain ketika *debugging*.
   2. **Functions Should Be Small**: Fungsi sebaiknya melakukan satu hal saja dan melakukannya dengan baik. Jika fungsi terlalu panjang, pertimbangkan untuk memecahnya menjadi fungsi-fungsi kecil. Sama seperti sebelumnya, fungsi yang panjang akan mengakibatkan seseorang sulit dalam merubah kode jika terjadi perubahan di masa yang akan datang.
   3. **Comments**: Hindari komentar yang berlebihan. Kode yang baik seharusnya bisa menjelaskan dirinya sendiri. Jika kita merasa perlu menulis komentar, mungkin kode tersebut perlu diperbaiki. Prinsip ini juga bisa diterapkan saat ingin meng-*commit* kode ke dalam *repository*. Tidak terlalu panjang dan deskriptif tetapi masih *on point* dan dapat dipahami.
   4. **Error Handling**: Pastikan kode menangani error dengan baik, misalnya menggunakan exception daripada return code. Jangan biarkan error tidak tertangani dan biasanya akan berakibat di lain waktu dan lain hal.
   5. **Objects and Data Structures**: Gunakan objek untuk menyembunyikan data dan mengekspos perilaku. Hindari mengekspos data secara langsung melalui getter dan setter.


2. ***Secure Coding Practices***
   1. **Input Validation**: Selalu validasi input dari pengguna untuk mencegah serangan seperti SQL Injection atau XSS (Cross-Site Scripting).
   2. **Authentication and Authorization**: Pastikan hanya pengguna yang terautentikasi dan memiliki hak akses yang sesuai yang dapat mengakses fitur tertentu.
   3. **Error Handling**: Jangan menampilkan pesan error yang terlalu detail kepada pengguna, karena bisa memberikan informasi sensitif kepada penyerang.

## Reflection 2
*Suppose that after writing the CreateProductFunctionalTest.java along with the
corresponding test case, you were asked to create another functional test suite that
veries the number of items in the product list. You decided to create a new Java class
similar to the prior functional test suites with the same setup procedures and instance
variables.
What do you think about the cleanliness of the code of the new functional test suite? Will
the new code reduce the code quality? Identify the potential clean code issues, explain
the reasons, and suggest possible improvements to make the code cleaner!*

***Unit Test***

Setelah menulis unit test, saya merasa lebih percaya diri bahwa kode yang saya buat berfungsi dengan benar dalam berbagai skenario. Namun, muncul pertanyaan: berapa banyak unit test yang harus dibuat untuk sebuah kelas?
Jawabannya tergantung pada kompleksitas kelas dan jumlah metode atau fungsionalitas yang ada. Idealnya, setiap metode harus memiliki setidaknya satu unit test yang mencakup berbagai kasus input, termasuk kasus batas (boundary conditions) dan skenario error.

Untuk memastikan bahwa unit test yang dibuat sudah cukup, kita bisa menggunakan metrik code coverage. Code coverage membantu mengukur persentase kode yang dijalankan selama pengujian. Namun, perlu diingat bahwa 100% code coverage tidak menjamin kode bebas dari bug. Code coverage hanya menunjukkan bahwa semua baris kode telah dijalankan, tetapi tidak menjamin kebenaran logika atau ketiadaan kesalahan dalam kode.

***Functional Test***

Misalnya, setelah menulis CreateProductFunctionalTest.java, saya diminta membuat functional test baru untuk memverifikasi jumlah item dalam daftar produk. Saya memutuskan membuat kelas Java baru dengan struktur yang mirip dengan functional test sebelumnya, termasuk setup dan instance variable yang sama.

- Masalah Potensial:
  - Duplikasi Kode: Jika setup dan instance variable diulang di setiap test suite, ini bisa menyebabkan duplikasi kode dan mengurangi kualitas kode.
  - Kesulitan Maintenance: Jika ada perubahan di setup atau instance variable, saya harus mengubahnya di banyak tempat, yang bisa memakan waktu dan rentan terhadap kesalahan.


- Saran Perbaikan:
  - Gunakan Inheritance atau Composition: Buat kelas dasar (base class) yang berisi setup dan instance variable yang umum. Kelas test suite baru bisa mewarisi atau menggunakan kelas dasar tersebut.
  - Gunakan Dependency Injection: Jika menggunakan framework seperti Spring, manfaatkan dependency injection untuk menyediakan instance variable yang sama di semua test suite.
  - Gunakan Utility Class: Buat utility class yang berisi metode setup yang bisa digunakan di semua test suite.
  - Dengan cara ini, kode akan lebih bersih, mudah dipelihara, dan mengurangi duplikasi. Selain itu, kualitas kode akan tetap terjaga meskipun jumlah test suite bertambah.