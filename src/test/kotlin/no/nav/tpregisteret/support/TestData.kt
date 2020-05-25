package no.nav.tpregisteret.support

import java.time.LocalDate

object TestData{
    private val testDate = LocalDate.of(2001,1,1)
    private val testDate2 = LocalDate.of(1900,1,1)

    class TestYtelse(val id: Long, val person: TestPerson, val type: String, val datoFom: LocalDate, val datoTom: LocalDate?, val medlemskapDatoFom: LocalDate?, val tpOrdning: TestTpOrdning){
        companion object {
            val map = mutableMapOf<Pair<TestPerson, TestTpOrdning>, MutableList<TestYtelse>>()
            fun getJson(person: TestPerson, tpOrdning: TestTpOrdning)
                    = map[person to tpOrdning]?.joinToString(",","[","]", transform = TestYtelse::json) ?: "[]"
        }

        init {
            map.getOrPut(person to tpOrdning) { mutableListOf() } += this
        }
        val json = """{"id":$id,"fnr":"${person.fnr}","type":"$type","datoFom":"$datoFom","datoTom":${datoTom?.let{"\"$it\""}},"medlemskapDatoFom":${medlemskapDatoFom?.let{"\"$it\""}}}"""
    }
    class TestOrganisation(val orgNr: String, vararg val tpOrdninger: TestTpOrdning){
        val json = tpOrdninger.joinToString("\",\"", "[\"", "\"]", transform = TestTpOrdning::navn)
    }
    class TestPerson(val fnr: String, vararg val tpForhold: TestTpOrdning){
        val json = tpForhold.joinToString(",","[","]", transform = TestTpOrdning::json)
    }
    class TestTpOrdning(val tssId: String, val tpId: String, val orgNr: String, val navn: String) {
        val json = """{"tssId":"$tssId","tpId":"$tpId","orgNr":"$orgNr","navn":"$navn"}"""
    }


    val TP_ORDNING_1 = TestTpOrdning("11111111111", "1111", "000000000", "TP1")
    val TP_ORDNING_2 = TestTpOrdning("22222222222", "2222", "000000000", "TP2")
    val TP_ORDNING_3 = TestTpOrdning("33333333333", "3333", "000000000", "TP3")
    val TP_ORDNING_4 = TestTpOrdning("44444444444", "4444", "111111111", "TP4")
    val VAULT_TP_ORDNING_1 = TestTpOrdning("55555555555", "3333", "111111111", "VTP1")
    val VAULT_TP_ORDNING_2 = TestTpOrdning("66666666666", "1111", "222222222", "VTP2")

    val PERSON_1 = TestPerson("00000000001")
    val PERSON_2 = TestPerson("00000000002", TP_ORDNING_1)
    val PERSON_3 = TestPerson("00000000003", TP_ORDNING_1, TP_ORDNING_2)
    val PERSON_4 = TestPerson("00000000004", TP_ORDNING_1)
    val PERSON_5 = TestPerson("00000000005", TP_ORDNING_1)
    val PERSON_6 = TestPerson("00000000006", TP_ORDNING_3)
    val PERSON_7 = TestPerson("00000000007", TP_ORDNING_2)

    val ORG_1 = TestOrganisation("000000000", TP_ORDNING_1, TP_ORDNING_2, TP_ORDNING_3)
    val ORG_2 = TestOrganisation("111111111", TP_ORDNING_4)

    val YTELSE_1 = TestYtelse(1, PERSON_3, "AFP", testDate,null, testDate2, TP_ORDNING_1)
    val YTELSE_2 = TestYtelse(2, PERSON_3, "UFORE", testDate,null, testDate2, TP_ORDNING_1)
    val YTELSE_3 = TestYtelse(3, PERSON_2, "BARN", testDate, null, testDate2, TP_ORDNING_1)
}
