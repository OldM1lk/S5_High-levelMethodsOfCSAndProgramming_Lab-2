package data.source

import domain.model.User
import util.hashPassword

val users = listOf(
    User(
        "alice",
        "salt1",
        hashPassword("qwerty", "salt1")
    ),
    User(
        "bob",
        "salt2",
        hashPassword("12345", "salt2")
    )
)