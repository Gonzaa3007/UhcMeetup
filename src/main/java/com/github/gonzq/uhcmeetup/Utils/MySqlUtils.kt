package com.github.gonzq.uhcmeetup.Utils

import com.github.gonzq.uhcmeetup.Managers.StatsManager
import com.github.gonzq.uhcmeetup.Players.GamePlayer
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.util.UUID

class MySqlUtils(private val host: String, private val port: String, private val database: String,
                 private val user: String, private val password: String) {
    private var url: String = "jdbc:mysql://$host:$port/$database?autoReconnect=true&useUnicode=yes"
    private lateinit var connection: Connection

    fun connect(): Boolean {
        return try {
            connection = DriverManager.getConnection(url,user,password)
            if (!connection.isClosed)
                println("Successfully connected to $host:$port/$database")
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    fun disconnect() {
        try {
            connection.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun insertPlayer(uid: UUID) {
        val stats = StatsManager.getInstance()
        val kills = stats.kills.getOrPut(uid){0}
        val deaths = stats.deaths.getOrPut(uid){0}
        val wins = stats.wins.getOrPut(uid){0}
        val gapps = stats.gapps.getOrPut(uid){0}
        val played = stats.played.getOrPut(uid){0}
        val shoots = stats.shoots.getOrPut(uid){0}
        var statement: Statement? = null
        try {
            statement = connection.createStatement()
            statement.execute("INSERT INTO UhcStats (UUID, Kills, Deaths, Wins, Gapps, Played) VALUES ('$uid', " +
                    "'$kills', '$deaths', '$wins', '$gapps', '$shoots', '$played')")
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            if (statement != null) {
                try {
                    statement.close()
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun updatePlayer(uid: UUID) {
        val stats = StatsManager.getInstance()
        val kills = stats.kills.getOrPut(uid){0}
        val deaths = stats.deaths.getOrPut(uid){0}
        val wins = stats.wins.getOrPut(uid){0}
        val gapps = stats.gapps.getOrPut(uid){0}
        val played = stats.played.getOrPut(uid){0}
        val shoots = stats.shoots.getOrPut(uid){0}
        var statement: Statement? = null
        try {
            statement = connection.createStatement()
            statement.execute("UPDATE UhcStats SET Kills = '$kills', Deaths = '$deaths', Wins = '$wins', " +
                    "Gapps = '$gapps', BowShoots = '$shoots', Played = '$played' WHERE UUID = '$uid'")
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            if (statement != null) {
                try {
                    statement.close()
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun createTable() {
        var statement: Statement? = null
        try {
            statement = connection.createStatement()
            statement.execute("CREATE TABLE IF NOT EXISTS UhcStats (UUID VARCHAR(100), Kills Integer, " +
                    "Deaths Integer, Wins Integer, Gapps Integer, BowShoots Integer, Played Integer)")
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            if (statement != null) {
                try {
                    statement.close()
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun playerIsInTable(uid: UUID): Boolean {
        var statement: Statement? = null
        try {
            statement = connection.createStatement()
            val rs = statement.executeQuery("SELECT * FROM UhcStats WHERE UUID = '$uid'")
            return rs.next()
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        } finally {
            if (statement != null) {
                try {
                    statement.close()
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
            }
        }
    }
}