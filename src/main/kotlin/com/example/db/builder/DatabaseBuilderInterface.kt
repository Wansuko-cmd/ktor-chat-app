package com.example.db.builder

import org.jetbrains.exposed.sql.Database

interface DatabaseBuilderInterface {

    val database: Database
}
