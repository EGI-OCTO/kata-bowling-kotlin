package example

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class MyTests : StringSpec({
    "[] = 0 points" {
        Scorecard.of().points() shouldBe 0
    }
    "[[1,-]] = 0 point (en attente du deuxième lancé)" {
        Scorecard.of(
            Frame.WithOneRoll(Roll(knockedPins = 1))
        ).points() shouldBe 0
    }
    "[[1,0]] = 1 points" {
        Scorecard.of(
            Frame.WithTwoRolls(Roll(knockedPins = 1), Roll(knockedPins = 0))
        ).points() shouldBe 1
    }
    "[[1,8]] = 9 points" {
        Scorecard.of(
            Frame.WithTwoRolls(Roll(knockedPins = 1), Roll(knockedPins = 8))
        ).points() shouldBe 9
    }
    "[[1,8], [1,0]] = 10 points" {
        Scorecard.of(
            Frame.WithTwoRolls(Roll(knockedPins = 1), Roll(knockedPins = 8)),
            Frame.WithTwoRolls(Roll(knockedPins = 1), Roll(knockedPins = 0))
        ).points() shouldBe 10
    }
    "[[5,5](spare)] = 0 points (en attente du prochain lancé)" {
        Scorecard.of(
            Frame.WithTwoRolls(Roll(knockedPins = 5), Roll(knockedPins = 5))
        ).points() shouldBe 0
    }
    "[[5,5](spare), [1,-]] = 11 points (en attente du prochain lancé)" {
        Scorecard.of(
            Frame.WithTwoRolls(Roll(knockedPins = 5), Roll(knockedPins = 5)),
            Frame.WithOneRoll(Roll(knockedPins = 1))
        ).points() shouldBe 11
    }
    "[[5,5](spare), [1,1]] = 13 points : (10 + 1) + (1 + 1)" {
        Scorecard.of(
            Frame.WithTwoRolls(Roll(knockedPins = 5), Roll(knockedPins = 5)),
            Frame.WithTwoRolls(Roll(knockedPins = 1), Roll(knockedPins = 1))
        ).points() shouldBe 13
    }
    "[[5,5](spare), [5,5](spare), [1,1]] = 28 points : (10 + 5) + (10 + 1) + (1 + 1)" {
        Scorecard.of(
            Frame.WithTwoRolls(Roll(knockedPins = 5), Roll(knockedPins = 5)),
            Frame.WithTwoRolls(Roll(knockedPins = 5), Roll(knockedPins = 5)),
            Frame.WithTwoRolls(Roll(knockedPins = 1), Roll(knockedPins = 1))
        ).points() shouldBe 28
    }
    "[[10](strike)] = 0 points (en attente des deux prochains lancés)" {
        Scorecard.of(
            Frame.WithOneRoll(Roll(knockedPins = 10))
        ).points() shouldBe 0
    }
    "[[10](strike), [1,-]] = 0 points (en attente du prochain lancé)" {
        Scorecard.of(
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithOneRoll(Roll(knockedPins = 1))
        ).points() shouldBe 0
    }
    "[[10](strike), [1,1]] = 14 points : (10 + 1 + 1) + (1 + 1)" {
        Scorecard.of(
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithTwoRolls(Roll(knockedPins = 1), Roll(knockedPins = 1))
        ).points() shouldBe 14
    }
    "[[10](strike), [10](strike)] = 0 points (en attente du prochain lancé)" {
        Scorecard.of(
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithOneRoll(Roll(knockedPins = 10))
        ).points() shouldBe 0
    }
    "[[10](strike), [10](strike), [1,-]] = 21 points : (10 + 10 + 1) (en attente du prochain lancé)" {
        Scorecard.of(
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithOneRoll(Roll(knockedPins = 1))
        ).points() shouldBe 21
    }
    "[[10](strike), [10](strike), [1,1]] = (10 + 10 + 1) + (10 + 1 + 1) + (1 +1) (en attente du prochain lancé)" {
        Scorecard.of(
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithTwoRolls(Roll(knockedPins = 1), Roll(knockedPins = 1))
        ).points() shouldBe 35
    }
    "aucune quille et spare à la 10ème frame [[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[5,5],[1,-]] : 11 points" {
        Scorecard.of(
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 5), Roll(knockedPins = 5)),
            Frame.WithOneRoll(Roll(knockedPins = 1))
        ).points() shouldBe 11
    }
    "aucune quilles et strike à la 10ème frame [[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[10],[1,1]] : 12 points" {
        Scorecard.of(
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithTwoRolls(Roll(knockedPins = 0), Roll(knockedPins = 0)),
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithTwoRolls(Roll(knockedPins = 1), Roll(knockedPins = 1))
        ).points() shouldBe 12
    }
    "que des strikes [[10],[10],[10],[10],[10],[10],[10],[10],[10],[10],[10,10]] : 300 points" {
        Scorecard.of(
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithOneRoll(Roll(knockedPins = 10)),
            Frame.WithTwoRolls(Roll(knockedPins = 10), Roll(knockedPins = 10))
        ).points() shouldBe 300
    }
})
