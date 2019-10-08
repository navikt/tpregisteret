package no.nav.tpregisteret

import no.nav.tpregisteret.tpordning.TpOrdning

class TestPerson(val fnr : String, vararg val tpForhold : TpOrdning) {
    companion object {
        private val TP_ORDNING_1 = TpOrdning("11111111111", "1111", "000000000", "TP1")
        private val TP_ORDNING_2 = TpOrdning("22222222222", "2222", "000000000", "TP2")
        private val TP_ORDNING_3 = TpOrdning("33333333333", "3333", "000000000", "TP3")

        val testPerson1 = TestPerson("00000000001")
        val testPerson2 = TestPerson("00000000002", TP_ORDNING_1)
        val testPerson3 = TestPerson("00000000003", TP_ORDNING_1, TP_ORDNING_2)
        val testPerson4 = TestPerson("00000000004", TP_ORDNING_1)
        val testPerson5 = TestPerson("00000000005", TP_ORDNING_1)
        val testPerson6 = TestPerson("00000000006", TP_ORDNING_3)
        val testPerson7 = TestPerson("00000000007", TP_ORDNING_2)
    }
}
