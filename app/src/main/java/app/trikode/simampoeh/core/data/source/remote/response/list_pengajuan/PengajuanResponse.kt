package app.trikode.simampoeh.core.data.source.remote.response.list_pengajuan


data class PengajuanResponse(
    var uid: String,
    var kode: String,
    var jenis: String,
    var waktuInput: String,
    var namaStatus: String,
    var idStatus: String,
    var namaPelayanan: String
)