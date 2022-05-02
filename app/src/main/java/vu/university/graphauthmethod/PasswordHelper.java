package vu.university.graphauthmethod;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public class PasswordHelper {

    private final ConnectionHelper connectionHelper = new ConnectionHelper();

    public boolean checkIfPasswordIsValid (Color first, Color second){
        return first.red != second.red || first.green != second.green || first.blue != second.blue;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean checkIfUserCanRegister(String deviceId){
        Device device = connectionHelper.getDevice(deviceId);
        return device == null || device.password == null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean checkIfDeviceExists(String deviceId){
        Device device = connectionHelper.getDevice(deviceId);
        return device.deviceId != null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean register (Color first, Color second, String deviceId) {
        String firstPassword = createPassword(first);
        String secondPassword = createPassword(second);
        String password = firstPassword + "#" + secondPassword;
        if(checkIfDeviceExists(deviceId)){
            return connectionHelper.updateDevice(deviceId, password, LocalDate.now());
        }
        return connectionHelper.addDevice(deviceId, password);
    }

    private String createPassword (Color color){
        int green = (int) ((1 - color.green) * 255);
        int blue = (int) ((1 - color.blue) * 255);
        int red = (int) ((1 - color.red) * 255);
        final String password = green + "*" + blue + "*" + red;
        return password;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean login (Color first, Color second, String deviceId) {
        String firstPassword = createPassword(first);
        String secondPassword = createPassword(second);
        String password = firstPassword + "#" + secondPassword;
        Device device = connectionHelper.getDevice(deviceId);
        if(device.password.equals(password)){
            return connectionHelper.updateLoginDate(deviceId, LocalDate.now());
        } else {
            device.failedLoginAttempts++;
            if(device.failedLoginAttempts == 3){
                connectionHelper.updateDevice(deviceId, null, null);
            } else {
                connectionHelper.updateFailedLoginAttempts(deviceId, device.failedLoginAttempts);
            }
        }
        return false;
    }
}
