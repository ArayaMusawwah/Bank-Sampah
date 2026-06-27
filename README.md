<div align="center">
  <br>
  <h1>🌿 Bank Sampah</h1>
  <p><strong>Aplikasi Pencatatan Setoran Bank Sampah Berbasis Android</strong></p>
  <p>
    Aplikasi untuk mencatat dan mengelola setoran sampah nasabah bank sampah
    <br>
    dengan fitur CRUD, pencarian, pengurutan, dan dashboard statistik.
  </p>
  <br>
  <p>
    <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
    <img src="https://img.shields.io/badge/Min%20SDK-29-4CAF50?style=for-the-badge&logo=android&logoColor=white" alt="Min SDK 29">
    <img src="https://img.shields.io/badge/Target%20SDK-36-1B5E20?style=for-the-badge&logo=android&logoColor=white" alt="Target SDK 36">
    <img src="https://img.shields.io/badge/Material%20Design%203-757575?style=for-the-badge&logo=materialdesign&logoColor=white" alt="Material Design 3">
    <img src="https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white" alt="SQLite">
  </p>
  <br>
</div>

<p align="center">
  <a href="#fitur">Fitur</a> •
  <a href="#galeri">Galeri</a> •
  <a href="#tech-stack">Tech Stack</a> •
  <a href="#cara-menjalankan">Cara Menjalankan</a> •
  <a href="#struktur-proyek">Struktur</a> •
  <a href="#author">Author</a>
</p>

<br>

<p align="center">
  <img src="screenshots/hero-banner.jpg" alt="Hero Banner" width="100%" style="max-width: 808px; border-radius: 16px;">
</p>

<br>

---

## ✨ Fitur Aplikasi

<table>
<tr>
  <td width="50%" valign="top">
    <h3>🔐 Login & Logout</h3>
    <p>Autentikasi sederhana dengan session yang disimpan di SharedPreferences. Logout dengan konfirmasi dialog.</p>
  </td>
  <td width="50%" valign="top">
    <h3>📊 Dashboard Statistik</h3>
    <p>Ringkasan total anggota, total setoran, total berat, total saldo, dan 5 setoran terbaru. Dilengkapi diagram batang breakdown per jenis sampah.</p>
  </td>
</tr>
<tr>
  <td width="50%" valign="top">
    <h3>📋 CRUD Setoran</h3>
    <p>Tambah, lihat, edit, dan hapus catatan setoran sampah. Dilengkapi autocomplete nama anggota dan spinner jenis sampah.</p>
  </td>
  <td width="50%" valign="top">
    <h3>👥 CRUD Anggota</h3>
    <p>Kelola data anggota bank sampah dengan field nama, alamat, email, telepon, dan tanggal daftar otomatis.</p>
  </td>
</tr>
<tr>
  <td width="50%" valign="top">
    <h3>🔍 Pencarian Real-time</h3>
    <p>Cari setoran atau anggota berdasarkan nama secara instan dengan <code>TextWatcher</code>.</p>
  </td>
  <td width="50%" valign="top">
    <h3>⬆⬇ Sorting Multi-kriteria</h3>
    <p>Urutkan setoran berdasarkan nama, berat, saldo, atau terbaru — ascending maupun descending.</p>
  </td>
</tr>
<tr>
  <td width="50%" valign="top">
    <h3>🌙 Dark Mode</h3>
    <p>Dukungan tema gelap penuh untuk kenyamanan mata saat penggunaan di malam hari.</p>
  </td>
  <td width="50%" valign="top">
    <h3>🎨 Material Design 3</h3>
    <p>Tampilan modern dengan tema hijau hutan, coklat tanah, dan krem — Material Design 3 <em>(Material You)</em>.</p>
  </td>
</tr>
</table>

---

## 🖼️ Galeri Screenshot

<p align="center">
  <em>Klik thumbnail untuk melihat gambar ukuran penuh</em>
</p>

<table>
<tr>
  <td align="center">
    <a href="screenshots/login.jpg">
      <img src="screenshots/thumb/login.jpg" alt="Halaman Login" width="200">
    </a>
    <br>
    <sub>Halaman Login</sub>
  </td>
  <td align="center">
    <a href="screenshots/dashboard.jpg">
      <img src="screenshots/thumb/dashboard.jpg" alt="Dashboard" width="200">
    </a>
    <br>
    <sub>Dashboard Statistik</sub>
  </td>
</tr>
<tr>
  <td align="center">
    <a href="screenshots/menu-daftar-setoran.jpg">
      <img src="screenshots/thumb/menu-daftar-setoran.jpg" alt="Daftar Setoran" width="200">
    </a>
    <br>
    <sub>Daftar Setoran</sub>
  </td>
  <td align="center">
    <a href="screenshots/menu-daftar-anggota.jpg">
      <img src="screenshots/thumb/menu-daftar-anggota.jpg" alt="Daftar Anggota" width="200">
    </a>
    <br>
    <sub>Daftar Anggota</sub>
  </td>
</tr>
<tr>
  <td align="center">
    <a href="screenshots/tambah-setoran.jpg">
      <img src="screenshots/thumb/tambah-setoran.jpg" alt="Tambah Setoran" width="200">
    </a>
    <br>
    <sub>Tambah Setoran</sub>
  </td>
  <td align="center">
    <a href="screenshots/edit-setoran.jpg">
      <img src="screenshots/thumb/edit-setoran.jpg" alt="Edit Setoran" width="200">
    </a>
    <br>
    <sub>Edit Setoran</sub>
  </td>
</tr>
<tr>
  <td align="center">
    <a href="screenshots/tambah-anggota.jpg">
      <img src="screenshots/thumb/tambah-anggota.jpg" alt="Tambah Anggota" width="200">
    </a>
    <br>
    <sub>Tambah Anggota</sub>
  </td>
  <td align="center">
    <a href="screenshots/dialog-logout.jpg">
      <img src="screenshots/thumb/dialog-logout.jpg" alt="Dialog Logout" width="200">
    </a>
    <br>
    <sub>Dialog Logout</sub>
  </td>
</tr>
</table>

---

## 🛠️ Tech Stack

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android">
  <img src="https://img.shields.io/badge/Material%20Design%203-757575?style=for-the-badge&logo=materialdesign&logoColor=white" alt="Material Design 3">
  <img src="https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white" alt="SQLite">
  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle">
</p>

| Komponen | Detail |
|----------|--------|
| **Bahasa** | Java |
| **Min SDK** | 29 (Android 10) |
| **Target SDK** | 36 |
| **UI Framework** | Material Design 3 (Material Components for Android) |
| **Database** | SQLite via `SQLiteOpenHelper` |
| **Build System** | Gradle 9.4.1 + AGP 9.2.1 |
| **Architecture** | Single-module, Fragment + BottomNavigationView |
| **State Login** | SharedPreferences |

---

## 🚀 Cara Menjalankan

1. **Clone repositori**
   ```bash
   git clone https://github.com/ArayaMusawwah/Bank-Sampah.git
   ```

2. **Buka di Android Studio**
   - File → Open → Pilih folder `Bank-Sampah`

3. **Sync Gradle**
   - Tunggu Gradle Sync selesai mendownload dependencies

4. **Run aplikasi**
   - Pilih device/emulator (min SDK 29)
   - Klik tombol **Run ▶️**

5. **Login**
   - Username: `admin`
   - Password: `12345`

> **Catatan:** Aplikasi menggunakan SQLite lokal — data akan terisi otomatis dengan sample data saat pertama kali dijalankan.

---

## 📁 Struktur Proyek

```
BankSampah/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/mogador/banksampah/
│   │       │   ├── LoginActivity.java          # Halaman login
│   │       │   ├── MainActivity.java           # Host fragment (bottom nav)
│   │       │   ├── DashboardFragment.java      # Dashboard statistik
│   │       │   ├── SetoranFragment.java        # Daftar setoran
│   │       │   ├── AnggotaFragment.java        # Daftar anggota
│   │       │   ├── AddEditSetoranActivity.java # Form tambah/edit setoran
│   │       │   ├── AddEditAnggotaActivity.java # Form tambah/edit anggota
│   │       │   ├── DatabaseHelper.java         # SQLite CRUD
│   │       │   ├── Setoran.java                # Model setoran
│   │       │   ├── Anggota.java                # Model anggota
│   │       │   ├── SetoranAdapter.java         # Adapter RecyclerView setoran
│   │       │   └── AnggotaAdapter.java         # Adapter RecyclerView anggota
│   │       ├── res/
│   │       │   ├── drawable/                   # Shape & ripple drawables
│   │       │   ├── layout/                     # Layout XML
│   │       │   ├── menu/                       # Menu XML
│   │       │   └── values/                     # Tema, warna, string
│   │       └── AndroidManifest.xml
│   └── build.gradle.kts
├── screenshots/                                # Screenshot aplikasi
├── build.gradle.kts                            # Root build
├── settings.gradle.kts
└── gradle/libs.versions.toml                   # Version catalog
```

---

## 👨‍💻 Author

<div align="center">
  <p>
    <strong>Muhammad Iqbal Ramadhan</strong>
    <br>
    NIM: 231011400285
  </p>
  <p>
    <a href="https://github.com/ArayaMusawwah">
      <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub">
    </a>
    <a href="https://github.com/ArayaMusawwah/Bank-Sampah">
      <img src="https://img.shields.io/badge/Repositori-1B5E20?style=for-the-badge&logo=git&logoColor=white" alt="Repositori">
    </a>
  </p>
</div>

---

<div align="center">
  <sub>Dibuat dengan ❤️ untuk tugas akhir mata kuliah Pemrograman Mobile</sub>
  <br>
  <sub>© 2026 Muhammad Iqbal Ramadhan</sub>
</div>
