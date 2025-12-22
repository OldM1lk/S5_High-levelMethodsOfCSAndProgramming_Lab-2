package app.domain.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, length = 50)
    val login: String,

    @Column(nullable = false, length = 32)
    val salt: String,

    @Column(name = "password_hash", nullable = false, length = 255)
    val passwordHash: String
)
