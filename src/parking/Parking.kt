package parking

import java.lang.IllegalArgumentException
import java.lang.RuntimeException

class Parking(private var maxSpots: Int = 0) {
    private val spots: MutableMap<Int, Spot> = mutableMapOf()

    init {
        if (maxSpots > 0) changeAmountOfSpots(maxSpots)
    }

    fun existSpots(): Boolean {
        return maxSpots > 0
    }

    fun changeAmountOfSpots(newAmountOfSpot: Int) {
        if (newAmountOfSpot < 1) throw IllegalArgumentException()
        spots.clear()
        maxSpots = newAmountOfSpot
        for (index in 1..maxSpots) {
            spots[index] = Spot(index)
        }
    }

    fun getAllNotEmptySpots(): List<Spot> {
        return spots.filter { !it.value.isFree() }.values.toList()
    }

    fun getSpotsByColor(color: String): List<Spot> {
        val _color = color.lowercase()
        return spots.filter { it.value.getCar()?.color?.lowercase() == _color }.values.toList()
    }

    fun getSpotByRegNum(regNum: String): Spot? {
        val spot = spots.filter { it.value.getCar()?.regNum == regNum }.values
        return if (spot.isNotEmpty()) spot.first() else null
    }

    fun park(car: Car): Int {
        val spotWithIndex = findFreeSpot()
        spotWithIndex.second.park(car)
        return spotWithIndex.first
    }

    fun leave(spotNum: Int): Boolean {
        return getSpotByNum(spotNum)?.free() != null
    }

    fun getSpotByNum(spotNum: Int): Spot? {
        return spots[spotNum]
    }

    fun isParkFuel(): Boolean {
        return spots.all { !it.value.isFree() }
    }

    private fun findFreeSpot(): Pair<Int, Spot> {
        for (entry in spots.entries) {
            if (entry.value.isFree()) {
                return Pair(entry.key, entry.value)
            }
        }
        throw RuntimeException("Cannot find free spot")
    }
}