package edu.montclair.mobilecomputing.mymac.assignment_1.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserInfoDatabase extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private Context context;
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "USERINFO";

    // Orders table name
    private static final String TABLE_USER = "user";

    private static final String AUTO_ID = "AUTOID";
    private static final String USERNAME = "username";
    private static final String NAME = "name";
    private static final String MAJOR = "major";
    private static final String EMAIL = "email";
    private static final String DOB = "dob";
    private static final String PASSWORD = "password";

    //    ID INTEGER PRIMARY KEY AUTOINCREMENT
    public UserInfoDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        onCreate(this.getReadableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_OrderS_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_USER + " (" + USERNAME + " TEXT," + NAME + " TEXT,"
                + MAJOR + " TEXT," + EMAIL + " TEXT," + DOB + " TEXT," + PASSWORD + " TEXT," + AUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT);";

//        String CREATE_OrderS_TABLE = "CREATE TABLE IF NOT EXISTS "
//                + TABLE_CHATFRIENDS + " (" + ID + " TEXT," + SEND_USER_ID + " TEXT,"
//                + FROM_USER_ID + " TEXT," + CHAT_TEXT + " TEXT," + DATE + " TEXT," + TIME + " TEXT," + status + " TEXT);";
        db.execSQL(CREATE_OrderS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
    }


    public boolean addNewUer(UserContainer userContainer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, userContainer.getUsername());
        values.put(NAME, userContainer.getName());
        values.put(MAJOR, userContainer.getMajor());
        values.put(EMAIL, userContainer.getEmail());
        values.put(DOB, userContainer.getDob());
        values.put(PASSWORD, userContainer.getPassword());
        String countQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + EMAIL + "='" + userContainer.getEmail() + "'";
        SQLiteDatabase dbr = this.getReadableDatabase();
        Cursor cursor = dbr.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            db.insert(TABLE_USER, null, values);
            db.close();
            return true;
        } else {
            return false;
        }

    }

    public String getForgotPAssword(String email, String username) {
        String countQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + EMAIL + "=" + email + " AND " + USERNAME + "=" + username;
        String passwordfound = "";
        SQLiteDatabase dbr = this.getReadableDatabase();
        Cursor cursor = dbr.rawQuery(countQuery, null);
        if (cursor.moveToFirst()) {
            passwordfound = cursor.getString(5);
        } else {
            passwordfound = "";
        }
        return passwordfound;
    }

    public UserContainer checkUerExist(String username, String password) {
        String countQuery = "SELECT * FROM " + TABLE_USER + " WHERE (" + USERNAME + "='" + username + "' AND " + PASSWORD + "='" + password + "')";
        UserContainer userContainer = new UserContainer();
        SQLiteDatabase dbr = this.getReadableDatabase();
        Cursor cursor = dbr.rawQuery(countQuery, null);
        if (cursor.moveToFirst()) {
            userContainer.setUsername(cursor.getString(0));
            userContainer.setName(cursor.getString(1));
            userContainer.setMajor(cursor.getString(2));
            userContainer.setEmail(cursor.getString(3));
            userContainer.setDob(cursor.getString(4));
            userContainer.setPassword(cursor.getString(5));
            userContainer.setUser_id(String.valueOf(cursor.getInt(6)));
            return userContainer;
        }
        return userContainer;
    }

//    public void addAllChatFriends(ArrayList<ChatContainer> chatList) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        if (chatList.size() > 0) {
//            for (int i = 0; i < chatList.size(); i++) {
//                ChatContainer order = chatList.get(i);
//                values.put(ID, order.getId());
//                values.put(SEND_USER_ID, order.getSend_user_id());
//                values.put(FROM_USER_ID, order.getFrom_user_id());
//                values.put(CHAT_TEXT, order.getChat_text());
//                values.put(DATE, order.getDate());
//                values.put(TIME, order.getTime());
//                values.put(DATETIME, order.getDate_time());
//                values.put(status, order.getStatus());
//                db.insert(TABLE_CHATFRIENDS, null, values);
//            }
//        }
//        db.close();
//    }

    public void updateUserWithoutPassword(UserContainer userContainer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, userContainer.getUsername());
        values.put(NAME, userContainer.getName());
        values.put(MAJOR, userContainer.getMajor());
        values.put(EMAIL, userContainer.getEmail());
        int result = db.update(TABLE_USER, values, AUTO_ID + "=+" + userContainer.getUser_id(), null);
        if (result <= 0) {
//            db.insert(TABLE_CHATFRIENDS, null, values);
        }
        db.close();
    }

    public void updateUserWithPassword(UserContainer userContainer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, userContainer.getUsername());
        values.put(NAME, userContainer.getName());
        values.put(MAJOR, userContainer.getMajor());
        values.put(EMAIL, userContainer.getEmail());
        values.put(DOB, userContainer.getDob());
        values.put(PASSWORD, userContainer.getPassword());
        int result = db.update(TABLE_USER, values, AUTO_ID + "=+" + userContainer.getUser_id(), null);
        if (result <= 0) {
//            db.insert(TABLE_CHATFRIENDS, null, values);
        }
        db.close();
    }

//    public void updateSendChatFriends(String auto_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(status, "1");
//        int result = db.update(TABLE_CHATFRIENDS, values, AUTO_ID + "=+" + auto_id, null);
//        if (result <= 0) {
//            db.insert(TABLE_CHATFRIENDS, null, values);
//        }
//        db.close();
//    }

//    public void updateReceiverFriends(ChatContainer order) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(ID, order.getId());
//        values.put(SEND_USER_ID, order.getSend_user_id());
//        values.put(FROM_USER_ID, order.getFrom_user_id());
//        values.put(CHAT_TEXT, order.getChat_text());
//        values.put(DATE, order.getDate());
//        values.put(TIME, order.getTime());
//        values.put(status, order.getStatus());
//        values.put(DATETIME, order.getDate_time());
//        int result = db.update(TABLE_CHATFRIENDS, values, ID + "=+'" + order.getId() + "'", null);
//        if (result <= 0) {
//            db.insert(TABLE_CHATFRIENDS, null, values);
//        }
//        db.close();
//    }
//
//
//    public void deleteAllChatFriends(String sent_user_id, String from_user_id) {
//        String deleteQuery = "DELETE FROM " + TABLE_CHATFRIENDS + " WHERE (" + SEND_USER_ID + "=" + sent_user_id + " AND " + FROM_USER_ID
//                + "=" + from_user_id + ") OR (" + SEND_USER_ID + "=" + from_user_id + " AND " + FROM_USER_ID + "="
//                + sent_user_id + ")";
//        SQLiteDatabase db = this.getReadableDatabase();
////        db.rawQuery(countQuery, null);
//        db.execSQL(deleteQuery);
//        db.close();
//    }
//
//    public void deleteRecords(String chat_id) {
//        String countQuery = "DELETE FROM " + TABLE_CHATFRIENDS + " WHERE " + ID + "=+" + chat_id;
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(countQuery);
//        db.close();
//    }
//
//    public int getChatFriendCount(String sent_user_id, String from_user_id) {
//        String countQuery = "SELECT * FROM " + TABLE_CHATFRIENDS + " WHERE (" + SEND_USER_ID + " = " + sent_user_id + " AND " + FROM_USER_ID
//                + " = " + from_user_id + ") OR (" + SEND_USER_ID + " = " + from_user_id + " AND " + FROM_USER_ID + " = "
//                + sent_user_id + ");";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        return cursor.getCount();
//    }
//
//    public ArrayList<ChatContainer> getUserChat(String sent_user_id, String from_user_id, int page) {
//        ArrayList<ChatContainer> chatList = new ArrayList<>();
////        ArrayList<ChatContainer> chatFailedMessage = new ArrayList<>();
//        String chatData = "SELECT * FROM " + TABLE_CHATFRIENDS + " WHERE (" + SEND_USER_ID + " = " + sent_user_id + " AND " + FROM_USER_ID
//                + " = " + from_user_id + ") OR (" + SEND_USER_ID + " = " + from_user_id + " AND " + FROM_USER_ID + " = "
//                + sent_user_id + ") ORDER BY " + AUTO_ID + " DESC LIMIT " + page + "," + "15";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(chatData, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                ChatContainer chatContainer = new ChatContainer();
//                chatContainer.setId(cursor.getString(0));
//                chatContainer.setSend_user_id(cursor.getString(1));
//                chatContainer.setFrom_user_id(cursor.getString(2));
//                chatContainer.setChat_text(cursor.getString(3));
//                chatContainer.setDate(cursor.getString(4));
//                chatContainer.setTime(cursor.getString(5));
//                chatContainer.setStatus(cursor.getString(6));
//                chatContainer.setAuto_id(cursor.getInt(7));
//                chatContainer.setDate_time(cursor.getString(8));
////                if (cursor.getString(6).equals("")) {
////                    chatFailedMessage.add(0, chatContainer);
////                }
//                chatList.add(chatContainer);
//            } while (cursor.moveToNext());
//
//        }
//        db.close();
////        if (chatFailedMessage.size() > 0) {
////            ((ChatActivity) context).setFailedMessage(chatFailedMessage);
////        }
//        return chatList;
//    }
//
//    public int getLastChatId(String sent_user_id, String from_user_id) {
//        String chat_id = "";
//        String chatData = "SELECT * FROM " + TABLE_CHATFRIENDS + " WHERE (" + SEND_USER_ID + " = " + sent_user_id + " AND " + FROM_USER_ID
//                + " = " + from_user_id + ") OR (" + SEND_USER_ID + " = " + from_user_id + " AND " + FROM_USER_ID + " = "
//                + sent_user_id + ") ORDER BY " + ID + " DESC LIMIT 1";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(chatData, null);
//
//        if (cursor.moveToFirst()) {
//            chat_id = cursor.getString(0);
//        }
//        db.close();
//        if (chat_id.equals("")) {
//            return 0;
//        } else {
//            return Integer.parseInt(chat_id);
//        }
//
//    }
//
//    public ArrayList<ChatContainer> getUserFailedMessage(String sent_user_id, String from_user_id, int page) {
//        ArrayList<ChatContainer> chatFailedMessage = new ArrayList<>();
//        String chatData = "SELECT * FROM " + TABLE_CHATFRIENDS + " WHERE (" + SEND_USER_ID + " = " + sent_user_id + " AND " + FROM_USER_ID
//                + " = " + from_user_id + ") OR (" + SEND_USER_ID + " = " + from_user_id + " AND " + FROM_USER_ID + " = "
//                + sent_user_id + ") ORDER BY " + AUTO_ID + " DESC LIMIT " + page + "," + "15";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(chatData, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                ChatContainer chatContainer = new ChatContainer();
//                chatContainer.setId(cursor.getString(0));
//                chatContainer.setSend_user_id(cursor.getString(1));
//                chatContainer.setFrom_user_id(cursor.getString(2));
//                chatContainer.setChat_text(cursor.getString(3));
//                chatContainer.setDate(cursor.getString(4));
//                chatContainer.setTime(cursor.getString(5));
//                chatContainer.setStatus(cursor.getString(6));
//                chatContainer.setAuto_id(cursor.getInt(7));
//                chatContainer.setDate_time(cursor.getString(8));
//                if (cursor.getString(6).equals("")) {
//                    chatFailedMessage.add(0, chatContainer);
//                }
//            } while (cursor.moveToNext());
//
//        }
//        db.close();
//        return chatFailedMessage;
//    }
//
//    public int getLastContainer(String sent_user_id, String from_user_id) {
//
//        String chatData = "SELECT * FROM " + TABLE_CHATFRIENDS + " WHERE (" + SEND_USER_ID + " = " + sent_user_id + " AND " + FROM_USER_ID
//                + " = " + from_user_id + ") OR (" + SEND_USER_ID + " = " + from_user_id + " AND " + FROM_USER_ID + " = "
//                + sent_user_id + ") ORDER BY " + AUTO_ID + " DESC LIMIT 1";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(chatData, null);
//        cursor.moveToFirst();
//        return cursor.getInt(7);
//    }
//
//    public void updateAllRecords(String from_user_id, String sent_user_id) {
//        String deleteQuery = "UPDATE " + TABLE_CHATFRIENDS + " SET " + status + "=1 WHERE (" + SEND_USER_ID + "=" + sent_user_id + " AND " + FROM_USER_ID
//                + "=" + from_user_id + ") OR (" + SEND_USER_ID + "=" + from_user_id + " AND " + FROM_USER_ID + "="
//                + sent_user_id + ")";
//        SQLiteDatabase db = this.getReadableDatabase();
////        db.rawQuery(countQuery, null);
//        db.execSQL(deleteQuery);
//        db.close();
//    }
//
//
//    public void deleteAllChatData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("delete from " + TABLE_CHATFRIENDS);
//        db.close();
//    }


}