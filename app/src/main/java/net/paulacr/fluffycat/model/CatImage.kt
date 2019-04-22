package net.paulacr.fluffycat.model

data class CatImage(val id: String, val url: String, val breeds: List<Breed>, val categories: List<Category>)

data class Breed(val id: String, val name: String, val temperament: String)

data class Category(val id: String, val name: String)