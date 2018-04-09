package fr.wildcodeschool.roomreservation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class RoomListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        ArrayList<RoomModel> roomModels = loadRoomsFromDB();

        RoomAdapter adapter = new RoomAdapter(this, 0, roomModels);
        ListView lvListRoom = findViewById(R.id.list_room);
        lvListRoom.setAdapter(adapter);
    }

    private ArrayList<RoomModel> loadRoomsFromDB() {
        ArrayList<RoomModel> roomModels = new ArrayList<>();

        // TODO : load rooms from database
        DBHelper mDbHelper = new DBHelper(RoomListActivity.this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DBContract.RoomEntry._ID,
                DBContract.RoomEntry.COLUMN_NAME_ROOM
        };

        Cursor cursor = db.query(
                DBContract.RoomEntry.TABLE_NAME_R,
                projection,
                null, null, null, null, null
        );

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.RoomEntry._ID));
            String room = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.RoomEntry.COLUMN_NAME_ROOM));

            RoomModel roomModel = new RoomModel(id, room);
            roomModels.add(roomModel);
        }
        cursor.close();

        return roomModels;
    }
}
