package app.trikode.simampoeh.domain.model.layanan

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AktaLahir(
    val nama: String,
    val jenisKelamin: Int,
    val tempatDilahirkan: Int,
    val tempatLahir: String,
    val tglLahir: String,
    val jamLahir: String?,
    val jenisKelahiran: String?,
    val kelahiranKe: String?,
    val beratBayi: String?,
    val panjangBayi: String?,
    val penolongKelahiran: Int?,
    val noHp: String?,
    val namaAyah: String,
    val namaIbu: String,
    val namaSaksi1: String?,
    val namaSaksi2: String,
    val kirimPos: Int
): Parcelable
