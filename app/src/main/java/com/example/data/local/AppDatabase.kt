package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [RatingEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ratingDao(): RatingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sarpanch_report_card.db"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Seed initial authentic civic reviews so Results Dashboard is immediately engaging
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        seedInitialReviews(database.ratingDao())
                    }
                }
            }

            private suspend fun seedInitialReviews(dao: RatingDao) {
                val sampleReviews = listOf(
                    RatingEntity(
                        villageName = "रामपुर",
                        panchayatName = "ग्राम पंचायत रामपुर",
                        sarpanchName = "श्री रामेश्वर यादव",
                        roadRating = 8,
                        drainageRating = 7,
                        electricityRating = 9,
                        cleanlinessRating = 6,
                        waterRating = 8,
                        educationRating = 8,
                        healthRating = 7,
                        streetLightRating = 8,
                        govSchemesRating = 9,
                        overallDevelopmentRating = 8,
                        overallScore = 7.8,
                        comment = "सड़क और बिजली की स्थिति में बहुत सुधार हुआ है, लेकिन गांव की नालियों की नियमित सफाई पर और ध्यान देने की जरूरत है।",
                        timestamp = System.currentTimeMillis() - 86400000L * 3
                    ),
                    RatingEntity(
                        villageName = "किशनपुर",
                        panchayatName = "ग्राम पंचायत किशनपुर",
                        sarpanchName = "श्रीमती सुनीता देवी",
                        roadRating = 9,
                        drainageRating = 8,
                        electricityRating = 8,
                        cleanlinessRating = 8,
                        waterRating = 9,
                        educationRating = 9,
                        healthRating = 8,
                        streetLightRating = 9,
                        govSchemesRating = 9,
                        overallDevelopmentRating = 9,
                        overallScore = 8.6,
                        comment = "सरपंच साहिबा ने प्राथमिक विद्यालय और नल-जल योजना में बहुत सराहनीय कार्य किया है। सरकारी योजनाओं का लाभ पात्र लोगों तक पहुंचा।",
                        timestamp = System.currentTimeMillis() - 86400000L * 2
                    ),
                    RatingEntity(
                        villageName = "चंदनपुर",
                        panchayatName = "ग्राम पंचायत चंदनपुर",
                        sarpanchName = "श्री मुकेश वर्मा",
                        roadRating = 6,
                        drainageRating = 5,
                        electricityRating = 7,
                        cleanlinessRating = 5,
                        waterRating = 6,
                        educationRating = 6,
                        healthRating = 5,
                        streetLightRating = 6,
                        govSchemesRating = 7,
                        overallDevelopmentRating = 6,
                        overallScore = 5.9,
                        comment = "गांव में स्ट्रीट लाइटें तो लगीं पर कई महीनों से खराब हैं। स्वास्थ्य उप-केंद्र में डॉक्टर व दवाइयों की कमी दूर की जानी चाहिए।",
                        timestamp = System.currentTimeMillis() - 86400000L * 1
                    ),
                    RatingEntity(
                        villageName = "हरिपुर",
                        panchayatName = "ग्राम पंचायत हरिपुर",
                        sarpanchName = "श्री जगमोहन सिंह",
                        roadRating = 7,
                        drainageRating = 6,
                        electricityRating = 8,
                        cleanlinessRating = 7,
                        waterRating = 7,
                        educationRating = 8,
                        healthRating = 7,
                        streetLightRating = 7,
                        govSchemesRating = 8,
                        overallDevelopmentRating = 7,
                        overallScore = 7.2,
                        comment = "पंचायत भवन का निर्माण और खेल मैदान का काम अच्छा रहा। जल निकासी की व्यवस्था को और बेहतर करना चाहिए।",
                        timestamp = System.currentTimeMillis() - 3600000L * 12
                    )
                )
                dao.insertAll(sampleReviews)
            }
        }
    }
}
