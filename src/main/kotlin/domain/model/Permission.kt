package app.domain.model

import jakarta.persistence.*

@Entity
@Table(name = "permissions")
class Permission(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(optional = false)
    @JoinColumn(name = "resource_id")
    val resource: Resource,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val action: ResourceAction
)
