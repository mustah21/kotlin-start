/**
Create a class ElectricCar that behaves like ElectricCar class in lecture1 slides with the exception
that deceleration from speed s1 to s2 (where s1 > s2) increases battery level with 20% of the
amount that is consumed when accelerating from s2 to s1.
Use the ElectricCar class logic for determining consumption during acceleration.
Also, make charging impossible if the car moves.

Create also a class ElectricBus that behaves like ElectricCar but has an additional feature that
allows for loading and unloading passengers.
Make the consumption during acceleration depend on the amount of passengers. Use inheritance.

Write in main function code that exercises your classes enough to convince yourself
(and anyone reading the code) that the classes work as intended.
 **/
import kotlin.math.*

// battery, speed
open class ElectricCar(val maxSpeed: Double = 150.0, val fullBattery: Double = 100.0) {

    var speed: Double = 0.0
    var battery: Double = 0.0

    fun chargeBattery() {
        if (speed == 0.0) {
            battery = fullBattery
        }
    }

    open fun accelerate() {
        val newSpeed = (this.speed + 1) * 0.8
        val batteryConsumed = (newSpeed - speed) * 1

        if (newSpeed < maxSpeed) {
            if (this.battery > batteryConsumed) {
                this.battery -= batteryConsumed
                this.speed = newSpeed
            }
        }
    }

    open fun decelerate() {
        val newSpeed = max(speed - 1.0, 0.0)
        val batteryRecovered = ((speed - newSpeed) * 1) * 0.2
        this.battery += batteryRecovered
        this.speed = newSpeed
    }

}

fun main() {
    val c1 = ElectricCar()

    println("Initial state:")
    println("Speed: ${c1.speed}")
    println("Battery: ${c1.battery}")

    println("\nCharging...")
    c1.chargeBattery()
    println("Speed: ${c1.speed}")
    println("Battery: ${c1.battery}")

    println("\nAccelerating...")
    for (i in 1..20) {
        c1.accelerate()
        println("Speed: ${c1.speed}, Battery: ${c1.battery}")
    }

    println("\nDecelerating...")
    while (c1.speed > 0) {
        c1.decelerate()
        println("Speed: ${c1.speed}, Battery: ${c1.battery}")
    }

    println("\nFinal state:")
    println("Speed: ${c1.speed}")
    println("Battery: ${c1.battery}")
}