package app.trikode.simampoeh.core.data.source.remote.response

data class TujuanKirim(
    override var kirim: String? = null,
    override var idProvinsi: String? = null,
    override var idKabupaten: String? = null,
    override var idKecamatan: String? = null,
    override var idKelurahan: String? = null,
    override var alamat: String? = null,
    override var kodePos: String? = null
): ITujuanKirim
