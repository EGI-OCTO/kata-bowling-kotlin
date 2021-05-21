package example

sealed class Frame {
    data class WithOneRoll(private val roll: Roll) : Frame() {
        fun isStrike() : Boolean {
            return roll.knockedPins == 10
        }
        override fun knockedPinsOnFirstRoll(): Int {
            return roll.knockedPins
        }
    }

    data class WithTwoRolls(private val firstRoll: Roll, private val secondRoll: Roll) : Frame() {
        fun knockedPins(): Int {
            return firstRoll.knockedPins + secondRoll.knockedPins
        }
        fun isSpare() : Boolean {
            return knockedPins() == 10;
        }

        override fun knockedPinsOnFirstRoll(): Int {
            return firstRoll.knockedPins
        }
    }

    abstract fun knockedPinsOnFirstRoll() : Int
}
