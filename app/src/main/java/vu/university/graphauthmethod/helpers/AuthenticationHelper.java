package vu.university.graphauthmethod.helpers;

import android.os.Build;
import androidx.annotation.RequiresApi;
import vu.university.graphauthmethod.models.Color;
import vu.university.graphauthmethod.models.Device;

import java.time.LocalDate;

public class AuthenticationHelper {

    private final ConnectionHelper connectionHelper = new ConnectionHelper();

    public boolean checkIfPasswordIsValid (Color first, Color second){
        return first.getRed() != second.getRed() || first.getGreen() != second.getGreen() || first.getBlue() != second.getBlue();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean checkIfUserCanRegister(String deviceId){
        Device device = connectionHelper.getDevice(deviceId);
        return device == null || device.getPassword() == null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean checkIfDeviceExists(String deviceId){
        Device device = connectionHelper.getDevice(deviceId);
        return device.getDeviceId() != null;
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
        int green = (int) ((1 - color.getGreen()) * 255);
        int blue = (int) ((1 - color.getBlue()) * 255);
        int red = (int) ((1 - color.getRed()) * 255);
        final String password = green + "*" + blue + "*" + red;
        return password;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean login (Color first, Color second, String deviceId) {
        String firstPassword = createPassword(first);
        String secondPassword = createPassword(second);
        String password = firstPassword + "#" + secondPassword;
        Device device = connectionHelper.getDevice(deviceId);
        if(device.getPassword().equals(password)){
            return connectionHelper.updateLoginDate(deviceId, LocalDate.now());
        } else {
            device.setFailedLoginAttempts(device.getFailedLoginAttempts()+1);
            if(device.getFailedLoginAttempts() == 3){
                connectionHelper.updateDevice(deviceId, null, null);
            } else {
                connectionHelper.updateFailedLoginAttempts(deviceId, device.getFailedLoginAttempts());
            }
        }
        return false;
    }
}
