package com.example.biosec.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.biosec.daos.PasswordsDao
import com.example.biosec.entities.Passwords
import com.example.biosec.utils.subscribeOnBackground

@Database(entities = [Passwords::class], version = 2)
abstract class PasswordsDatabase : RoomDatabase() {

    abstract fun passwordsDao(): PasswordsDao

    companion object {

        var dbInstance: PasswordsDatabase? = null

        @Synchronized
        fun getInstance(context: Context): PasswordsDatabase {

            if (dbInstance == null) {

                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    PasswordsDatabase::class.java,
                    "passwords_db"
                ).fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }

            return dbInstance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(dbInstance!!)
            }
        }

        private fun populateDatabase(db: PasswordsDatabase) {
            val dao = db.passwordsDao()

            subscribeOnBackground {
                dao.insertPassword(
                    Passwords(
                        website = "W3Schools",
                        userName = "KenStarry",
                        emailAddress = "starrycodes@gmail.com",
                        password = "12345",
                        isCertified = false,
                        isLocked = true
                    )
                )

                dao.insertPassword(
                    Passwords(
                        website = "GeeksForGeeks",
                        userName = "Pookie",
                        emailAddress = "starrycodes@gmail.com",
                        password = "12345",
                        isCertified = false,
                        isLocked = false
                    )
                )

                dao.insertPassword(
                    Passwords(
                        website = "Freecodecamp",
                        userName = "Sheilla",
                        emailAddress = "sheillakemboi68@gmail.com",
                        password = "12345",
                        isCertified = false,
                        isLocked = true
                    )
                )
            }
        }
    }
}