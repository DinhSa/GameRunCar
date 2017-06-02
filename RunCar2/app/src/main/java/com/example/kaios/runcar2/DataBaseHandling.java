package com.example.kaios.runcar2;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

//import com.example.kaios.foody.QuanAn;

/**
 * Created by kaios on 4/10/2017.
 */

public class DataBaseHandling extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Ratings.sqlite";
    private static final String DB_PATH_SUFFIX = "/databases/";
    static Context ctx;
    public DataBaseHandling(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    // ---------------------------------------------------------------
    //copy DB from assets
    public void CopyDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = getDatabasePath();

        // if the path doesn't exist first, create it
        File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    // ---------------------------------------------------------------
    //get path
    private static String getDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }

    // ---------------------------------------------------------------
    //Open DB
    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }


    // ---------------------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    // ---------------------------------------------------------------
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    // ---------------------------------------------------------------
    // Get dữ điểm Max
    public Model_Diem GetMax() {
        Model_Diem data = null;
        String sql= "SELECT * FROM tbDiem WHERE Diem=(SELECT MAX(Diem) FROM tbDiem)";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {

                String ten=c.getString(0);
                int Diem = c.getInt(1);
                data=new Model_Diem(ten,Diem);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return data;
    }

    // ---------------------------------------------------------------
    // Insert Diểm
    public void insert (int Diem){
        String sql ="INSERT INTO tbDiem(Name, Diem) VALUES('You',"+Diem+")";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(sql);
        db.close();
    }

    // ---------------------------------------------------------------
    //lấy 5 điểm cao nhất
    public ArrayList<Model_Diem> Top5(){
        ArrayList<Model_Diem> ListTop5=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql="select * from tbDiem order by Diem DESC";
        Cursor c = db.rawQuery(sql, null);
        int i=0;
        if (c.moveToFirst()) {
            do {
                i++;
                ListTop5.add(new Model_Diem(c.getString(0),c.getInt(1)));
            } while (c.moveToNext()&& i<5);
        }
        db.close();
        c.close();
        return ListTop5;
    }

    // ---------------------------------------------------------------
    //xóa dữ liệu bảng
    public void DeleteData(){
        String sql="DELETE FROM tbDiem";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(sql);
        db.close();
    }
}
