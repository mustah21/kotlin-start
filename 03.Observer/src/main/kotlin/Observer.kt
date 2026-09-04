data class WeatherData(
    val temperature: Float,
    val humidity: Float,
    val pressure: Float
)

interface Observer {
    fun update(weatherData: WeatherData)
}
interface Subject {
    fun register(observer: Observer)
    fun removeObserver(observer: Observer)
    fun notifyObservers(weatherData: WeatherData)
}

class WeatherStation: Subject {

    private val observers = mutableListOf<Observer>()
    private var currentData: WeatherData? = null

    override fun register(observer: Observer) {
        observers.add(observer)
    }
    override fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifyObservers(weatherData: WeatherData) {
        for (observer in observers) {
            observer.update(weatherData)
        }
    }

    // This method is called whenever new weather data is available.
    fun measurementsChanged(newData: WeatherData) {
        this.currentData = newData
        println("WeatherStation: Got new data -> $currentData")
        notifyObservers(newData)
    }

}


class CurrentConditionsDisplay: Observer {

    fun display(weatherData: WeatherData) {
        println("Temp: ${weatherData.temperature}")
        println("Humidity: ${weatherData.humidity}")
        println("Pressure: ${weatherData.pressure}")
    }

    override fun update(weatherData: WeatherData) {
        display(weatherData)
    }
}

class StatisticsDisplay: Observer {

    private val temperatures = mutableListOf<Float>()

    fun display() {
       val average = temperatures.average()
       println("StatisticsDisplay: $average")
    }

    override fun update(weatherData: WeatherData) {
        temperatures.add(weatherData.temperature)
        display()
    }

}

fun main() {
    val weatherStation = WeatherStation()

    // 2. Create the display devices (the observers).
    val currentDisplay = CurrentConditionsDisplay()
    val statsDisplay = StatisticsDisplay()

    // 3. Register the observers with the weather station.
    weatherStation.register(currentDisplay)
    weatherStation.register(statsDisplay)

    // Simulate new weather measurements.
    println("--- Simulating new measurement ---")
    weatherStation.measurementsChanged(
        WeatherData(
            25.0f, 65f,
            1012f
        )
    )

    println("\n--- Simulating another measurement ---")
    weatherStation.measurementsChanged(
        WeatherData(
            27.5f, 70f,
            1011f
        )
    )

    // 4. Unregister one of the observers.
    weatherStation.removeObserver(statsDisplay)
    println("\n--- Simulating a final measurement ---")
    weatherStation.measurementsChanged(
        WeatherData(
            26.0f, 90f,
            1013f
        )
    )

}