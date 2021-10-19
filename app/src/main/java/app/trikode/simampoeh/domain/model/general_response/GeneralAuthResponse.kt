package app.trikode.simampoeh.domain.model.general_response

interface GeneralAuthResponse {
    var token: String?
    var response_result: Int
    var response_message: String?
}