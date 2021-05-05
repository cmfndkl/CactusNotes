package com.example.cactusnotes.notelistui

data class Notes(var title: String, var note: String)

val noteList = listOf(
    Notes("Remember", "Doing is important!"),
    Notes("Shopping List", "Carrots, Bananas, Apples, Milk"),
    Notes("Learn Kotlin", "Extensions functions are of a great help when creating a clean.."),
    Notes("Fake Notes", "Lorem ipsum"),
    Notes("Fake Notes", "Cactus App"),
    Notes("Learn Kotlin", "Extensions functions are of a great help when creating a clean.."),
    Notes("Learn Kotlin", "Extensions functions are of a great help when creating a clean.."),
    Notes("Learn Kotlin", "Extensions functions are of a great help when creating a clean.."),
    Notes(
        "Lorem Ipsum",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
                "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate " +
                "velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt " +
                "in culpa qui officia deserunt mollit anim id est laborum."
    ),
    Notes(
        "Lorem Ipsum", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    Notes(
        "Lorem Ipsum", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Arcu non odio " +
                "euismod lacinia."
    )
)
