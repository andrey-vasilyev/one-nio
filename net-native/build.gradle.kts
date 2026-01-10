plugins {
    id("one-nio.c-library-conventions")
}

cLib {
    moreCompilerArgs.set(listOf("-ldl"))
}