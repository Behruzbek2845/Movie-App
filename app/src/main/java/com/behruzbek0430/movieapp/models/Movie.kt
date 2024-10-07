package com.behruzbek0430.movieapp.models

class Movie {
    var id: String? = null
    var name1: String? = null
    var image1: String? = null
    var date: String? = null
    var description: String? = null
    var language: String? = null
    var national: String? = null
    var silka: String? = null


    constructor()

    constructor(image1: String?) {
        this.image1 = image1
    }

    constructor(id: String?, name1: String?, image1: String?) {
        this.id = id
        this.name1 = name1
        this.image1 = image1
    }

    constructor(
        id: String?,
        name1: String?,
        image1: String?,
        date: String?,
        description: String?,
        language: String?,
        national: String?,
        silka: String?
    ) {
        this.id = id
        this.name1 = name1
        this.image1 = image1
        this.date = date
        this.description = description
        this.language = language
        this.national = national
        this.silka = silka
    }




}
