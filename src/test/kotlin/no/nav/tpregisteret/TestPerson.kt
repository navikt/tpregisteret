package no.nav.tpregisteret

class TestPerson(val fnr: String, vararg val tpForhold: TestTpOrdning) {
    class TestTpOrdning(val tssId: String, val tpId: String, val dunno: String, val bogus: String)
    companion object {
        val YTELSE_DTO_FOR_TEST_PERSON3_AND_TP_ORDNING_1 = """[{"id":1,"fnr":"00000000003","datoFom":"2001-01-01","datoTom":null},{"id":2,"fnr":"00000000003","datoFom":"2001-01-01","datoTom":null}]"""


        private val TP_ORDNING_1 = TestTpOrdning("11111111111", "1111", "000000000", "TP1")
        private val TP_ORDNING_2 = TestTpOrdning("22222222222", "2222", "000000000", "TP2")
        private val TP_ORDNING_3 = TestTpOrdning("33333333333", "3333", "000000000", "TP3")

        val testPerson1 = TestPerson("00000000001")
        val testPerson2 = TestPerson("00000000002", TP_ORDNING_1)
        val testPerson3 = TestPerson("00000000003", TP_ORDNING_1, TP_ORDNING_2)
        val testPerson4 = TestPerson("00000000004", TP_ORDNING_1)
        val testPerson5 = TestPerson("00000000005", TP_ORDNING_1)
        val testPerson6 = TestPerson("00000000006", TP_ORDNING_3)
        val testPerson7 = TestPerson("00000000007", TP_ORDNING_2)
    }
}
