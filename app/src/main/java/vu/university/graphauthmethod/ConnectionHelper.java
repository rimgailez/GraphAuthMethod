package vu.university.graphauthmethod;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.sql.*;
import java.time.LocalDate;

public class ConnectionHelper {

    private final DatabaseInfo databaseInfo = new DatabaseInfo();
    private boolean status;
    private Connection connection;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Device getDevice(String deviceId) {
        Device device = new Device();
        Thread thread = new Thread(() -> {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.user, databaseInfo.password);
                String sql = "SELECT device_id, password, registration_date, last_login_date, failed_login_attempts " +
                        "FROM devices WHERE device_id = '" + deviceId + "'";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery();
                if(result.next()){
                    device.deviceId = result.getString("device_id");
                    device.password = result.getString("password");
                    device.registrationDate = result.getDate("registration_date");
                    device.lastLoginDate = result.getDate("last_login_date");
                    device.failedLoginAttempts = result.getInt("failed_login_attempts");
                }
                statement.close();
                connection.close();
                status = true;
            }
            catch (Exception e) {
                status = false;
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        }
        catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
        return device;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean addDevice(String deviceId, String password) {
        String sqlQuery = "INSERT INTO devices (device_id, password, registration_date) VALUES "
                + "('" + deviceId + "','" + password + "','" + LocalDate.now() + "')";
        return executeQuery(sqlQuery);
    }

    public boolean updateLoginDate(String deviceId, LocalDate date) {
        String sqlQuery = "UPDATE devices SET last_login_date = '" + date + "', failed_login_attempts = 0 " +
                "WHERE device_id = '" + deviceId + "'";
        return executeQuery(sqlQuery);
    }

    public boolean updateFailedLoginAttempts(String deviceId, int failedLoginAttempts) {
        String sqlQuery = "UPDATE devices SET failed_login_attempts = '" + failedLoginAttempts +
                "' WHERE device_id = '" + deviceId + "'";
        return executeQuery(sqlQuery);
    }

    public boolean updateDevice(String deviceId, String password, LocalDate registrationDate) {
        String sqlQuery;
        if(password != null){
            sqlQuery = "UPDATE devices SET password = '" + password + "', registration_date = '" + registrationDate +
                    "', last_login_date = null, failed_login_attempts = null WHERE device_id = '" + deviceId + "'";
        } else {
            sqlQuery = "UPDATE devices SET password = null, registration_date = null, " +
                    "last_login_date = null, failed_login_attempts = null WHERE device_id = '" + deviceId + "'";
        }
        return executeQuery(sqlQuery);
    }

    public boolean executeQuery(String sqlQuery) {
        Thread thread = new Thread(() -> {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(databaseInfo.url, databaseInfo.user, databaseInfo.password);
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.executeUpdate();
                statement.close();
                connection.close();
                status = true;
            }
            catch (Exception e) {
                status = false;
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        }
        catch (Exception e) {
            e.printStackTrace();
            status = false;
        }
        return status;
    }
}
