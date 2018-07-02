package lv.mtm123.mineevents.events

import java.util.concurrent.ThreadLocalRandom

class MEventManager(var chance : Double, private val events : Set<MEvent>){

    init {
        val sum = events.stream().mapToDouble { e -> e.weight}.sum()
        events.forEach { e -> e.weight /= sum }
    }

    fun rollRandomEvent() : MEvent? {

        val p = ThreadLocalRandom.current().nextDouble()

        var cumulativeWeight = 0.0
        for(e in events){
            cumulativeWeight += e.weight
            if(p <= cumulativeWeight){
                println("yep")
                return e
            }
        }

        return null
    }

}