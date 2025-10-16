package data.source

import domain.model.Permission
import domain.model.ResourceAction

val permissions = listOf(
    Permission(
        "alice",
        "B",
        setOf(ResourceAction.READ, ResourceAction.WRITE, ResourceAction.EXECUTE)
    ),
    Permission(
        "bob",
        "X",
        setOf(ResourceAction.READ)
    )
)