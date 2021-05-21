package example

class Scorecard private constructor(private val frameScores : List<FrameScore>) {

    companion object {
        fun of(vararg frames: Frame) : Scorecard {
            val frameScores = frames.map{ frame, nextFrame, secondNextFrame -> FrameScore.from(frame, nextFrame, secondNextFrame) }
            return Scorecard(frameScores)
        }

        fun <R> Array<out Frame>.map(transform: (frame: Frame, nextFrame: Frame?, secondNextFrame: Frame?) -> R) : List<R> {
            return this.mapIndexed { index, frame ->
                transform(frame, this.getOrNull(index + 1), this.getOrNull(index + 2))
            }
        }
    }

    fun points() : Int {
        return frameScores.take(10).sumBy { frameScore -> frameScore.points() }
    }
}

sealed class FrameScore {
    companion object {
        fun from(frame : Frame, nextFrame: Frame?, secondNextFrame: Frame?) : FrameScore {
            return when (frame) {
                is Frame.WithOneRoll -> if (frame.isStrike()) {
                    FrameScore.strike(nextFrame, secondNextFrame)
                } else {
                    FrameScore.Pending
                }
                is Frame.WithTwoRolls -> if (frame.isSpare()) {
                    FrameScore.spare(nextFrame)
                } else {
                    FrameScore.Scored(frame.knockedPins())
                }
            }
        }

        private fun spare(nextFrame: Frame?) : FrameScore {
            return if (nextFrame != null) {
                FrameScore.Scored(10 + nextFrame.knockedPinsOnFirstRoll())
            } else {
                FrameScore.Pending
            }
        }

        private fun strike(nextFrame: Frame?, secondNextFrame: Frame?) : FrameScore {
            return when(nextFrame) {
                null -> Pending
                is Frame.WithOneRoll -> if (nextFrame.isStrike() && secondNextFrame != null) {
                    FrameScore.Scored(10 + 10 + secondNextFrame.knockedPinsOnFirstRoll())
                } else {
                    FrameScore.Pending
                }
                is Frame.WithTwoRolls -> FrameScore.Scored(10 + nextFrame.knockedPins())
            }
        }
    }

    object Pending : FrameScore() {
        override fun points(): Int = 0
    }

    data class Scored(private val points: Int) : FrameScore() {
        override fun points(): Int {
            return this.points
        }
    }

    abstract fun points() : Int
}
