package com.appstacks.indiannaukribazaar.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.appstacks.indiannaukribazaar.room.table.NewsEntity;
import com.appstacks.indiannaukribazaar.room.table.NotificationEntity;

import java.util.List;

@Dao
public interface DAO {

    /* table video transaction ------------------------------------------------------------------ */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(NewsEntity news);

    @Query("DELETE FROM news WHERE id = :id")
    void deleteNews(long id);

    @Query("DELETE FROM news")
    void deleteAllNews();

    @Query("SELECT * FROM news ORDER BY saved_date DESC LIMIT :limit OFFSET :offset")
    List<NewsEntity> getAllNewsByPage(int limit, int offset);

    @Query("SELECT COUNT(id) FROM news")
    Integer getNewsCount();

    @Query("SELECT * FROM news WHERE id = :id LIMIT 1")
    NewsEntity getNews(long id);

    /* table notification transaction ----------------------------------------------------------- */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotification(NotificationEntity notification);

    @Query("DELETE FROM notification WHERE id = :id")
    void deleteNotification(long id);

    @Query("DELETE FROM notification")
    void deleteAllNotification();

    @Query("SELECT * FROM notification ORDER BY created_at DESC LIMIT :limit OFFSET :offset")
    List<NotificationEntity> getNotificationByPage(int limit, int offset);

    @Query("SELECT * FROM notification WHERE id = :id LIMIT 1")
    NotificationEntity getNotification(long id);

    @Query("SELECT COUNT(id) FROM notification WHERE read = 0")
    Integer getNotificationUnreadCount();

    @Query("SELECT COUNT(id) FROM notification")
    Integer getNotificationCount();
}
