# Hadyan Fachri
# 2306245030
# Advanced Programming A

# Tutorial 3
*1. Explain what principles you apply to your project!*

Dalam tutorial ini, saya menerapkan beberapa prinsip SOLID untuk meningkatkan kualitas dan maintainability kode. Prinsip utama yang saya terapkan adalah:
- Single Responsibility Principle (SRP)
   - Prinsip ini menekankan bahwa setiap kelas harus memiliki satu tanggung jawab saja. Saya menerapkan prinsip ini dengan memisahkan CarController dari ProductController sehingga masing-masing controller hanya bertanggung jawab untuk mengelola satu jenis entitas saja.


- Open/Closed Principle (OCP)
   - Kode yang telah ada tidak perlu dimodifikasi untuk penambahan fitur baru, namun tetap terbuka untuk ekstensi. Pendekatan ini telah diterapkan melalui penggunaan interface untuk service dan repository.

*2. Explain the advantages of applying SOLID principles to your project with examples.*
- Keuntungan Single Responsibility Principle:
  - Kode Lebih Mudah Dipahami: Dengan memisahkan CarController dari ProductController, setiap controller menjadi lebih fokus dan mudah dipahami.
  - Contoh: Sebelumnya CarController mewarisi ProductController yang menciptakan ketergantungan yang tidak perlu dan membingungkan. Sekarang, setiap controller hanya menangani satu jenis entitas, sehingga perubahan pada fitur product tidak akan mempengaruhi fitur car.


- Keuntungan Open/Closed Principle:
  - Kemudahan Pengembangan: Ketika perlu menambahkan fitur baru, kita tidak perlu mengubah kode yang sudah ada.
  - Contoh: Jika ingin menambahkan entitas baru seperti Order, kita cukup membuat class baru yang mengimplementasikan interface yang ada tanpa mengubah kode di service atau repository yang sudah berjalan.



*3. Explain the disadvantages of not applying SOLID principles to your project with examples.*
- Kerugian Tidak Menerapkan Single Responsibility Principle:
  - Kode Sulit Dipelihara: Jika controller menangani banyak tugas, perubahan pada satu fitur dapat memengaruhi fitur lain secara tidak terduga.
  - Contoh: Dalam kode sebelumnya, CarController mewarisi ProductController, sehingga perubahan pada logika ProductController akan memengaruhi CarController. Hal ini dapat menyebabkan bug yang sulit dilacak.


- Kerugian Tidak Menerapkan Open/Closed Principle:
  - Kode Menjadi Fragile: Setiap kali ada fitur baru, kita perlu mengubah kode yang sudah ada, meningkatkan risiko bug.
  - Contoh: Jika controller tidak menggunakan abstraksi service, penambahan fitur baru akan memerlukan modifikasi pada controller yang sudah ada, yang berisiko merusak fungsionalitas yang telah berjalan dengan baik.
  - Dengan pemisahan controller untuk Car dan Product, proyek ini telah melangkah ke arah penerapan prinsip SOLID yang lebih baik, terutama dalam aspek Single Responsibility Principle, yang merupakan langkah kritis dalam perbaikan arsitektur aplikasi

# Tutorial 2
*1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.*

Masalah Kualitas Kode yang Ditemukan dan Diperbaiki:
1. Perbaikan Penanganan Nilai Null
    - Masalah: Kode memiliki pemeriksaan null yang tidak konsisten di seluruh repository dan service
    - Strategi: Menambahkan pemeriksaan null yang komprehensif di ProductRepository dan cakupan pengujian yang tepat:


2. Duplikasi Kode dalam Controller
   - Masalah: String URL redirect yang berulang di ProductController
   - Strategi: Membuat konstanta untuk menghindari duplikasi seperti
   ``private static final String REDIRECT_PRODUCT_LIST = "redirect:/product/list";``


3. Kurangnya Pengujian Layer Service
   - Masalah: Tidak ada cakupan pengujian untuk ProductServiceImpl
   - Strategi: Membuat suite pengujian komprehensif yang mencakup semua metode service dan kasus-kasus khusus


4. Penanganan Error yang Tidak Konsisten
    - Masalah: Penanganan kasus not-found yang tidak konsisten di controller
    - Strategi: Standarisasi untuk mengembalikan redirect ke halaman list untuk konsistensi contohnya ``return REDIRECT_PRODUCT_LIST;``

*2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!*

Berdasarkan workflows yang sudah saya implementasikan, menurut saya telah memenuhi definisi Continuous Integration (CI) dan juga COntinuous Deployment (CD). Mengapa?
1. Continuous Integration (CI)
   - Workflow ci.yml sudah memenuhi prinsip CI dengan menjalankan pengujian otomatis (unit test) setiap kali ada perubahan kode (push atau pull request). Ini memastikan bahwa kode yang saya diintegrasikan ke dalam repositori selalu dalam keadaan stabil dan bebas dari bug.
   - Workflow pmd.yml dan scorecard.yml menambahkan lapisan validasi tambahan dengan melakukan analisis kode (PMD) dan memantau keamanan rantai pasok (Scorecard). Ini memperkuat proses CI dengan memastikan kode tidak hanya berfungsi dengan baik, tetapi juga aman dan berkualitas tinggi.


2. Continuous Deployment (CD) dengan Koyeb
   - Koyeb dapat diintegrasikan dengan GitHub untuk secara otomatis melakukan deployment setiap kali ada perubahan kode yang lolos dari tahap CI.
   - Namun, dalam workflows yang diberikan, tidak terlihat integrasi langsung dengan Koyeb. Untuk memenuhi prinsip CD sepenuhnya, perlu menambahkan langkah deployment ke Koyeb dalam workflows GitHub Actions. Misalnya, setelah semua pengujian dan analisis selesai, saya menambahkan langkah untuk mengirim kode ke Koyeb untuk deployment otomatis.


3. Fokus pada Delivery
   - Saat ini, workflows seperti ci, pmd dan scorecard berfokus pada validasi kode (pengujian, analisis kode, dan keamanan), tetapi belum secara eksplisit menunjukkan langkah-langkah deployment ke Koyeb. Untuk mencapai CD yang lengkap, perlu memastikan bahwa setelah kode lolos semua validasi, kode tersebut langsung dideploy ke lingkungan produksi atau staging melalui Koyeb.

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