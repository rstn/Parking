package parking

data class Car(val regNum: String, val color: String) {
    override fun toString(): String {
        return "$regNum $color"
    }
}

class Spot(val number: Int) {
    private val emptyCar = Car("", "")
    private var car = emptyCar

    fun park(car: Car) {
        if (isFree()) {
            this.car = car
        } else {
            throw RuntimeException("Spot already have the car ${this.car}")
        }
    }

    fun free() {
        car = emptyCar
    }

    fun getCar(): Car? {
        return if (isFree()) null else car
    }

    fun isFree(): Boolean {
        return car == emptyCar
    }

    override fun toString(): String {
        return "$number $car"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Spot

        if (number != other.number) return false

        return true
    }

    override fun hashCode(): Int {
        return number
    }
}
