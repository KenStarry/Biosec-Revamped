package com.example.biosec.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.biosec.R
import com.example.biosec.daos.ArchivesDao
import com.example.biosec.daos.PasswordsDao
import com.example.biosec.entities.Archives
import com.example.biosec.entities.Passwords
import com.example.biosec.utils.subscribeOnBackground

@Database(entities = [Passwords::class, Archives::class], version = 4)
abstract class PasswordsDatabase : RoomDatabase() {

    abstract fun passwordsDao(): PasswordsDao
    abstract fun archivesDao(): ArchivesDao

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

        //  Populate the database onCreate
        private fun populateDatabase(db: PasswordsDatabase) {
            val dao = db.passwordsDao()
            val archivesDao = db.archivesDao()

            subscribeOnBackground {
                dao.insertPassword(
                    Passwords(
                        website = "W3Schools",
                        userName = "KenStarry",
                        emailAddress = "starrycodes@gmail.com",
                        password = "12345",
                        passStrengthIcon = R.drawable.ic_weak_pass,
                        isLocked = true,
                        passIcon = R.drawable.ic_circle,
                        passColor = R.color.blue_light
                    )
                )

                dao.insertPassword(
                    Passwords(
                        website = "GeeksForGeeks",
                        userName = "Pookie",
                        emailAddress = "starrycodes@gmail.com",
                        password = "12345",
                        passStrengthIcon = R.drawable.ic_weak_pass,
                        isLocked = false,
                        passIcon = R.drawable.ic_circle,
                        passColor = R.color.blue
                    )
                )

                dao.insertPassword(
                    Passwords(
                        website = "Freecodecamp",
                        userName = "Sheilla",
                        emailAddress = "sheillakemboi68@gmail.com",
                        password = "12345",
                        passStrengthIcon = R.drawable.ic_strong_pass,
                        isLocked = true,
                        passIcon = R.drawable.ic_circle,
                        passColor = R.color.blue_light
                    )
                )

                archivesDao.insert(
                    Archives(
                        website = "Freecodecamp",
                        userName = "Sheilla",
                        emailAddress = "sheillakemboi68@gmail.com",
                        password = "12345",
                        passStrengthIcon = R.drawable.ic_strong_pass,
                        isLocked = true,
                        passIcon = R.drawable.ic_circle,
                        passColor = R.color.blue_light
                    )
                )
            }
        }
    }
}