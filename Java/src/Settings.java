/**
 * Created by Sawyer on 3/13/2016.
 */
public class Settings {
    private String settingsFilePath = "settings.json";
    private int volume, winX, winY;

    public Settings() {

    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setWinX(int winX) {
        this.winX = winX;
    }

    public void setWinY(int winY) {
        this.winY = winY;
    }

    public int getVolume() {
        return volume;
    }

    public int getWinX() {
        return winX;
    }

    public int getWinY() {
        return winY;
    }

    private void writeSettings() {

    }

    private void readSettings() {

    }
}
