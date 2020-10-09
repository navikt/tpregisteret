package no.nav.tpregisteret.support

import com.nimbusds.jwt.JWTClaimsSet
import no.nav.security.token.support.test.JwtTokenGenerator
import no.nav.tpregisteret.SCOPE_KEY
import no.nav.tpregisteret.TPREGISTERET_SCOPE
import java.time.Instant
import java.util.*

abstract class Tokenizer {
    private val maskinportenClaims: JWTClaimsSet
        get() = JWTClaimsSet.Builder()
                .issuer("maskinporten")
                .expirationTime(Date.from(Instant.now().plusSeconds(360)))
                .claim(SCOPE_KEY, TPREGISTERET_SCOPE).build()

    val maskinportenToken: String
        get() = JwtTokenGenerator.createSignedJWT(maskinportenClaims).serialize()
}