package app.trikode.simampoeh.domain.model.upload_berkas

import retrofit2.http.Part

data class UploadedBerkas (
    val key: String,
    val berkas: Part
)