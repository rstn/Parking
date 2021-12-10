package parking

fun String.isPositiveNumber(): Boolean {
    val strNum = this.trim()
    if (strNum.isEmpty() || strNum.first() == '0') return false
    return strNum.all { it.isDigit() }
}

