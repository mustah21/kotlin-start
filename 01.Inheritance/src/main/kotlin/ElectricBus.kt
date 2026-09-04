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

class ElectricBus: ElectricCar() {

    var passenger : Int = 0

    fun loadPassenger() {
        val maxPassengers = 100
        if (this.passenger < maxPassengers) passenger++
    }
    fun unloadPassenger() {
        if (passenger > 0) passenger--
    }

    override fun accelerate() {
        val passengerWeight = 1.0 + passenger * 0.05
        val newSpeed = (this.speed + 1) * 0.8
        val batteryConsumed = ((newSpeed - speed) * 1) * passengerWeight
        if (newSpeed < maxSpeed) {
            if (this.battery > batteryConsumed) {
                this.battery -= batteryConsumed
                this.speed = newSpeed
            }
        }
    }

}


fun main() {
    val car = ElectricCar()

    println("=== ELECTRIC CAR ===")

    // Test charging
    println("Initial battery: ${car.battery}")
    car.chargeBattery()
    println("After charging: ${car.battery}")

    // Test acceleration
    println("\nAccelerating...")
    repeat(5) {
        car.accelerate()
        println("Speed: ${car.speed}, Battery: ${car.battery}")
    }

    // Test that charging doesn't work while moving
    val batteryBefore = car.battery
    car.chargeBattery()
    println("\nTrying to charge while moving:")
    println("Battery before: $batteryBefore")
    println("Battery after: ${car.battery}")

    // Test deceleration and battery recovery
    println("\nDecelerating...")
    repeat(5) {
        car.decelerate()
        println("Speed: ${car.speed}, Battery: ${car.battery}")
    }


    println("\n=== ELECTRIC BUS ===")

    val bus = ElectricBus()

    // Charge bus
    bus.chargeBattery()
    println("Initial bus battery: ${bus.battery}")

    // Test passengers
    println("\nLoading passengers...")
    repeat(10) {
        bus.loadPassenger()
    }
    println("Passengers: ${bus.passenger}")

    // Test acceleration with passengers
    println("\nAccelerating with 10 passengers...")
    repeat(5) {
        bus.accelerate()
        println(
            "Speed: ${bus.speed}, " +
                    "Battery: ${bus.battery}, " +
                    "Passengers: ${bus.passenger}"
        )
    }

    // Test unloading
    println("\nUnloading passengers...")
    repeat(3) {
        bus.unloadPassenger()
    }
    println("Passengers: ${bus.passenger}")

    // Test acceleration with fewer passengers
    println("\nAccelerating with 7 passengers...")
    repeat(5) {
        bus.accelerate()
        println(
            "Speed: ${bus.speed}, " +
                    "Battery: ${bus.battery}, " +
                    "Passengers: ${bus.passenger}"
        )
    }

    // Test passenger limit
    println("\nTesting passenger limit...")
    repeat(200) {
        bus.loadPassenger()
    }
    println("Passengers after trying to load 200: ${bus.passenger}")

    // Test passenger minimum
    println("\nTesting passenger minimum...")
    repeat(200) {
        bus.unloadPassenger()
    }
    println("Passengers after trying to unload 200: ${bus.passenger}")
}