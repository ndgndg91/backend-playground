package com.ndgndg91.distributedlock.domain.order.entity

import com.ndgndg91.distributedlock.domain.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "`order`")
class Order(
    @Column(nullable = false) var title: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(mappedBy = "order", cascade = [CascadeType.PERSIST])
    var items: MutableList<OrderLineItem> = mutableListOf()
}