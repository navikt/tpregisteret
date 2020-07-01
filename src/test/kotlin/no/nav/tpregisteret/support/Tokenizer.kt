package no.nav.tpregisteret.support

import no.nav.security.token.support.test.JwtTokenGenerator

interface Tokenizer {
    val token: String
        get() = JwtTokenGenerator.signedJWTAsString(null)

    val bearer: String
        get() = "Bearer $token"

    val auth: String
        get() = "Authorization"
}