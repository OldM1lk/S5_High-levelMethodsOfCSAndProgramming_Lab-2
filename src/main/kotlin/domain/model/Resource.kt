package app.domain.model

import jakarta.persistence.*

@Entity
@Table(name = "resources")
class Resource(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, length = 50)
    val name: String,

    @Column(name = "max_volume", nullable = false)
    val maxVolume: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val parent: Resource? = null,
)