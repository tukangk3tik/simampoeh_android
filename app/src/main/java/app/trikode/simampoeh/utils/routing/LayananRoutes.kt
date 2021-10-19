package app.trikode.simampoeh.utils.routing

import app.trikode.simampoeh.ui.akta_kematian.tambah.TambahAktaKematianActivity
import app.trikode.simampoeh.ui.akta_lahir.tambah.TambahAktaLahirActivity
import app.trikode.simampoeh.ui.akta_pengakuan_anak.tambah.TambahAktaPengakuanAnakActivity
import app.trikode.simampoeh.ui.akta_pengangkatan_anak.tambah.TambahAktaPengangkatanAnakActivity
import app.trikode.simampoeh.ui.akta_pengesahan_anak.tambah.TambahAktaPengesahanAnakActivity
import app.trikode.simampoeh.ui.akta_perkawinan.tambah.TambahAktaPerkawinanActivity
import app.trikode.simampoeh.ui.kia.tambah.TambahKiaActivity
import app.trikode.simampoeh.ui.kk.tambah.TambahKkActivity
import app.trikode.simampoeh.ui.ktp.tambah.TambahKtpActivity
import app.trikode.simampoeh.ui.menu_lainnya.MenuLainnyaActivity
import app.trikode.simampoeh.ui.surat_pindah.tambah.TambahSuratPindahActivity

class LayananRoutes {

    private var classRoute: Class<*>? = null

    companion object {
        @Volatile
        private var INSTANCE: LayananRoutes? = null

        fun getInstance(): LayananRoutes = INSTANCE ?: synchronized(this) {
            val instance = LayananRoutes()
            INSTANCE = instance
            instance
        }
    }

    fun setDestination(destination: String) {
        when(destination) {
            "kelahiran" -> classRoute = TambahAktaLahirActivity::class.java
            "pengakuan_anak" -> classRoute = TambahAktaPengakuanAnakActivity::class.java
            "pengesahan_anak" -> classRoute = TambahAktaPengesahanAnakActivity::class.java
            "pengangkatan_anak" -> classRoute = TambahAktaPengangkatanAnakActivity::class.java
            "perkawinan" -> classRoute = TambahAktaPerkawinanActivity::class.java
            "kematian" -> classRoute = TambahAktaKematianActivity::class.java
            "ktp" -> classRoute = TambahKtpActivity::class.java
            "kk" -> classRoute = TambahKkActivity::class.java
            "kartu_identitas_anak" -> classRoute = TambahKiaActivity::class.java
            "surat_pindah" -> classRoute = TambahSuratPindahActivity::class.java
            "menu_lainnya" -> classRoute = MenuLainnyaActivity::class.java
            else -> {
                classRoute = null
                println("No route available!")
            }
        }
    }

    fun getRoute(): Class<*>? {
        return classRoute
    }
}