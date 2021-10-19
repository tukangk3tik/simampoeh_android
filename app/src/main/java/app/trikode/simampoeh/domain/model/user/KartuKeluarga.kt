package app.trikode.simampoeh.domain.model.user

import app.trikode.simampoeh.core.data.source.remote.response.kk.AnggotaKeluargaResponse

data class KartuKeluarga(
    var noKk: String,
    var alamat: String,
    var anggota: ArrayList<AnggotaKeluargaResponse>
)
