package com.example.gamequiz

// Topics.kt
enum class Topics(val questions: List<Question>, val imageResourceId: Int) {
    MATHEMATICS(
        listOf(
            Question("2 + 2 = ?", "4", listOf("3", "5", "6")),
            Question("Square root of 9", "3", listOf("4", "2", "5")),
            Question("Solve for X: X + 1 = 3", "2", listOf("1", "4", "5")),
            Question("20 / 4", "5", listOf("4", "6", "8")),
            Question("7 x 8", "56", listOf("42", "49", "64"))
        ), R.drawable.mathematics_image
    ),
    GREEK_MYTHOLOGY(
        listOf(
            Question("Who's the god of thunder?", "Zeus", listOf("Poseidon", "Hades", "Apollo")),
            Question("Who's the goddess of beauty?", "Aphrodite", listOf("Hera", "Athena", "Artemis")),
            Question("What was Odysseus' Island?", "Ithaca", listOf("Crete", "Sicily", "Troy")),
            Question("Greek Hero Weak in the heel?", "Achilles", listOf("Hercules", "Perseus", "Theseus")),
            Question("What animal did the Achaeans use to trick the Trojans?", "Horse", listOf("Dog", "Cat", "Cow"))
        ), R.drawable.greek_mythology_image
    ),
    HISTORY(
        listOf(
            Question("Mexican independence year", "1810", listOf("1821", "1910", "1800")),
            Question("Grito de Dolores year", "1810", listOf("1821", "1910", "1800")),
            Question("Year that WWII ended", "1945", listOf("1939", "1941", "1943")),
            Question("Which country participated in the Cake Wars?", "Mexico", listOf("France", "USA", "Germany")),
            Question(
                "Famous French figure which threatened Europe",
                "Napoleon",
                listOf("Louis XIV", "Joan of Arc", "Marie Antoinette")
            )
        ), R.drawable.history_image
    ),
    GEOGRAPHY(
        listOf(
            Question("Which country is the Amazon forest in?", "Brazil", listOf("Peru", "Colombia", "Venezuela")),
            Question("Which is the highest mountain?", "Mount Everest", listOf("K2", "Kangchenjunga", "Lhotse")),
            Question("Which state doesn't exist in Mexico?", "Tlaxcala", listOf("Yucatan", "Quintana Roo", "Sonora")),
            Question("Which country does the Nile River exist in?", "Egypt", listOf("Sudan", "Ethiopia", "Uganda")),
            Question("Which state is the Popocatepetl in?", "Puebla", listOf("Mexico City", "Morelos", "Tlaxcala"))
        ), R.drawable.geography_image
    ),
    SPORTS(
        listOf(

            Question("¿Quién ganó la Copa Mundial de la FIFA 2018?", "Francia", listOf("Argentina", "Brasil", "España")),
            Question("¿Cuál es el club más exitoso en la historia de la Liga de Campeones de la UEFA?", "Real Madrid", listOf("Manchester United", "Bayern Munich", "FC Barcelona")),
            Question("¿Quién ha ganado el mayor número de Balones de Oro?", "Lionel Messi", listOf("Cristiona Ronaldo", "Neymar", "Zinedine Zidane")),
            Question("¿Cuál es el torneo de tenis más prestigioso en el mundo?", "Wimbledon", listOf("Abierto Francia", "Abierto Australia", "Abierto USA")),
            Question("¿Cuál es el deporte nacional de Canadá?", "Hockey", listOf("Beisbol", "Futbol", "Baloncesto"))
        ), R.drawable.deporte
    ),
}
