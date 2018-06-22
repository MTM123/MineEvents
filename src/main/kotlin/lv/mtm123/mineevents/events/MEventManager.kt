package lv.mtm123.mineevents.events

import java.util.concurrent.ThreadLocalRandom

class MEventManager(var chance : Double, private val events : Set<MEvent>){

    fun rollRandomEvent() : MEvent? {

        val p = ThreadLocalRandom.current().nextDouble()

        var cumulativeWeight = 0.0
        for(e in events){
            cumulativeWeight += e.chance
            if(p <= cumulativeWeight){
                return e
            }
        }

        return null
    }

}