package in.co.info.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

/**
 * Created by marauder on 3/17/15.
 */
public class DetailsDatabase {

	public static int version_code = 1;
	public static String db_name = Environment.getExternalStorageDirectory()
			+ "/DetailsDatabase.db";

	// table names to be used
	public static final String details_table = "Details";
	public static final String payments_table = "PaymentsDetails";

	// personal details column keys
	public static final String user_id = "id";
	public static final String username = "username";
	public static final String address = "address";
	public static final String village = "village";
	public static final String city = "city";
	public static final String district = "district";
	public static final String pincode = "pincode";
	public static final String state = "state";
	public static final String mobile = "mobile";
	public static final String landline = "landline";
	public static final String email = "email";

	// order details column keys
	public static final String item_name = "item_name";
	public static final String item_id = "item_id";
	public static final String amount = "amount";
	public static final String order_date = "order_date";
	public static final String balance = "balance";
	public static final String due_date = "due_date";

	// for payment details
	public static final String payment_id = "payment_id";
	public static final String payment = "payment";
	public static final String payment_date = "payment_date";

	public static final String create_details_table = " CREATE TABLE IF NOT EXISTS "
			+ details_table
			+ " ("
			+ user_id
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ username
			+ " TEXT, "
			+ address
			+ " TEXT, "
			+ village
			+ " TEXT, "
			+ city
			+ " TEXT, "
			+ district
			+ " TEXT, "
			+ pincode
			+ " TEXT, "
			+ state
			+ " TEXT, "
			+ mobile
			+ " TEXT, "
			+ landline
			+ " TEXT, "
			+ email
			+ " TEXT, "
			+ item_name
			+ " TEXT, "
			+ item_id
			+ " TEXT, "
			+ amount
			+ " REAL, "
			+ order_date
			+ " TEXT, "
			+ balance
			+ " REAL, "
			+ due_date
			+ " TEXT);";

	public static final String creat_payment_table = " CREATE TABLE IF NOT EXISTS "
			+ payments_table
			+ " ("
			+ payment_id
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ user_id
			+ " INTEGER, "
			+ payment
			+ " REAL, "
			+ payment_date
			+ " TEXT, FOREIGN KEY("
			+ user_id + ") REFERENCES " + details_table + "(" + user_id + "));";

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	public DetailsDatabase(Context context) {
		ourContext = context;
	}

	public DetailsDatabase open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, db_name, null, version_code);
		}

		@Override
		public void onCreate(SQLiteDatabase sqLiteDatabase) {
			sqLiteDatabase.execSQL(create_details_table);
			sqLiteDatabase.execSQL(creat_payment_table);
		}

		@Override
		public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
			Log.w("Database", "Upgrading database from version " + i + " to "
					+ i2 + ", which will destroy all old data");
			sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + details_table);
			sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + payments_table);
			onCreate(sqLiteDatabase);
		}
	}

	public boolean isOpen() {
		Log.i("isOpenDb", ourDatabase.isOpen() + "");
		return ourDatabase.isOpen();
	}

	public void close() {
		if (ourDatabase.isOpen())
			ourHelper.close();
	}

	public boolean isEmpty(String table) {
		if (table.equals("details")) {
			Cursor cursor = ourDatabase.rawQuery("SELECT * FROM "
					+ details_table, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.close();
				Log.i("details empty", "false");
				return false;
			} else {
				Log.i("details empty", "true");
				cursor.close();
				return true;
			}
		} else {
			Cursor cursor = ourDatabase.rawQuery("SELECT * FROM "
					+ payments_table, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.close();
				Log.i("payments empty", "false");
				return false;
			} else {
				Log.i("payments empty", "true");
				cursor.close();
				return true;
			}
		}
	}

	public void addOrder(Order order) {
		ContentValues cv = new ContentValues();
		cv.put(username, order.username);
		cv.put(address, order.address);
		cv.put(village, order.village);
		cv.put(city, order.city);
		cv.put(district, order.district);
		cv.put(pincode, order.pincode);
		cv.put(state, order.state);
		cv.put(mobile, order.mobile);
		cv.put(landline, order.landline);
		cv.put(email, order.email);
		cv.put(item_name, order.item_name);
		cv.put(item_id, order.item_id);
		cv.put(amount, order.amount);
		cv.put(order_date, order.order_date);
		cv.put(balance, order.balance);
		cv.put(due_date, order.due_date);

		ourDatabase.insert(details_table, null, cv);
	}

	public ArrayList<HashMap<String, String>> fetchUserNames() {
		Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + details_table
				+ " ORDER BY " + username + " ASC", null);
		ArrayList<HashMap<String, String>> arr = null;
		if (cursor != null && cursor.getCount() > 0) {
			arr = new ArrayList<HashMap<String, String>>();
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				try {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(user_id, Integer.toString(cursor.getInt(cursor
							.getColumnIndexOrThrow(user_id))));
					map.put(username, cursor.getString(cursor
							.getColumnIndexOrThrow(username)));
					arr.add(map);
					cursor.moveToNext();
				} catch (Exception e) {
					Log.e("fetch error", e.toString());
				}
			}
		}
		return arr;
	}

	public Order showOrder(int uid) {
		Order reqdOrder = null;
		Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + details_table
				+ " WHERE " + user_id + "=" + uid, null);

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			reqdOrder = new Order();
			reqdOrder.setAddress(cursor.getString(cursor
					.getColumnIndex(address)));
			reqdOrder.setUser_id(cursor.getInt(cursor.getColumnIndex(user_id)));
			reqdOrder.setUsername(cursor.getString(cursor
					.getColumnIndex(username)));
			reqdOrder.setVillage(cursor.getString(cursor
					.getColumnIndex(village)));
			reqdOrder.setCity(cursor.getString(cursor.getColumnIndex(city)));
			reqdOrder.setDistrict(cursor.getString(cursor
					.getColumnIndex(district)));
			reqdOrder.setPincode(cursor.getString(cursor
					.getColumnIndex(pincode)));
			reqdOrder.setState(cursor.getString(cursor.getColumnIndex(state)));
			reqdOrder.setItem_name(cursor.getString(cursor
					.getColumnIndex(item_name)));
			reqdOrder.setItem_id(cursor.getString(cursor
					.getColumnIndex(item_id)));
			reqdOrder.setAmount(cursor.getFloat(cursor.getColumnIndex(amount)));
			reqdOrder
					.setBalance(cursor.getFloat(cursor.getColumnIndex(amount)));
			reqdOrder.setOrder_date(cursor.getString(cursor
					.getColumnIndex(order_date)));
			reqdOrder.setDue_date(cursor.getString(cursor
					.getColumnIndex(due_date)));
			reqdOrder
					.setMobile(cursor.getString(cursor.getColumnIndex(mobile)));
			reqdOrder.setLandline(cursor.getString(cursor
					.getColumnIndex(landline)));
			reqdOrder.setEmail(cursor.getString(cursor.getColumnIndex(email)));

		}
		return reqdOrder;
	}
}
