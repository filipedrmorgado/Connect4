package pt.isec.a2019137625.Connect4.UI.gui.Resources;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer {
    static MediaPlayer mp;
    private MusicPlayer(){}
    public static void playMusic(String name) {
        String path = Resources.getResourceFilename("Sounds/"+name);
        if (path == null)
            return;
        Media music = new Media(path);
        mp = new MediaPlayer(music);
        mp.setStartTime(Duration.ZERO);
        mp.setStopTime(music.getDuration());
        mp.setAutoPlay(true);
    }
}