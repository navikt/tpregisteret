package no.nav.tpregisteret.organisation

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.stream.Stream

class TestData : ArgumentsProvider {

    companion object {
        const val tpNrA = "1111"
        const val tpNrB = "4444"
        const val orgNrA = "000000000"
        const val orgNrB = "111111111"
        const val tssIdA = "11111111111"
        const val validVaultMappedTpNrA = "1111"
        const val validVaultMappedTpNrB = "3333"
        const val validVaultMappedOrgNrA = "222222222"
        const val validVaultMappedOrgNrB = "111111111"
        const val invalidTpNr = "1234"
        const val invalidOrgNr = "123456789"
        const val invalidTssId = "11111111112"
        private val ownTpNr = status().isOk
        private val doesntOwnTpNr = status().isNotFound

    }

    @Override
    override fun provideArguments(extensionContext: ExtensionContext) = Stream.of(
            Arguments.of(orgNrA, tpNrA, ownTpNr),
            Arguments.of(orgNrB, tpNrB, ownTpNr),
            Arguments.of(orgNrB, tpNrA, doesntOwnTpNr),
            Arguments.of(orgNrA, tpNrB, doesntOwnTpNr),
            Arguments.of(orgNrA, invalidTpNr, doesntOwnTpNr),
            Arguments.of(invalidOrgNr, tpNrB, doesntOwnTpNr),
            Arguments.of(validVaultMappedOrgNrA, validVaultMappedTpNrA, ownTpNr),
            Arguments.of(validVaultMappedOrgNrB, validVaultMappedTpNrB, ownTpNr),
            Arguments.of(validVaultMappedOrgNrA, validVaultMappedTpNrB, doesntOwnTpNr),
            Arguments.of(validVaultMappedOrgNrB, validVaultMappedTpNrA, doesntOwnTpNr),
            Arguments.of(validVaultMappedOrgNrA, invalidTpNr, doesntOwnTpNr),
            Arguments.of(invalidOrgNr, validVaultMappedTpNrA, doesntOwnTpNr))
}