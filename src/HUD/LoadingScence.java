package HUD;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadingScence {
    private AnchorPane anchorPane;
    private Stage loadingStage;
    private Scene scene;
    private Canvas canvas;
    private Stage nextStage;

    public LoadingScence(Stage nextStage)
    {

        this.nextStage=nextStage;
        init();
        draw();

    }
    public void init()
    {
        loadingStage=new Stage();
        anchorPane=new AnchorPane();
        anchorPane.setPrefSize(1000,700);
        canvas=new Canvas(1000,700);
        anchorPane.getChildren().add(canvas);
        scene=new Scene(anchorPane,1000,700);
        loadingStage.setScene(scene);

    }
    public void draw() {
        Image background, loadbar, loading, LoadingBar;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        try {
            background = new Image(new FileInputStream("src/HUD/loading Bar/cauvang.png"));
            LoadingBar = new Image(new FileInputStream("src/HUD/loading Bar/Loading Bar.png"), 776, 140, false, true);
            loadbar = new Image(new FileInputStream("src/HUD/loading Bar/load bar.png"), 532, 55, false, false);
            loading = new Image(new FileInputStream("src/HUD/loading Bar/loading.png"));

            gc.drawImage(background, 0, 0, 1000, 700);
            Timeline timeline = new Timeline();
            final long timeStart = System.currentTimeMillis();
            KeyFrame kf = new KeyFrame(
                    Duration.seconds(0.017), actionEvent -> {
                double t = (System.currentTimeMillis() - timeStart) / 1000.0;
                gc.drawImage(LoadingBar, 128, 232, 776, 140);
                gc.drawImage(loadbar, 236, 307, 540, 55);
                gc.drawImage(loading, 236, 307, t * 176, 55);
            }
            );
            timeline.getKeyFrames().add(kf);
            timeline.setCycleCount(60*3);
            timeline.play();
            timeline.setOnFinished(actionEvent -> {
                loadingStage.hide();
                nextStage.show();
            });
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }
    public Stage getLoadingStage()
    {
        return loadingStage;
    }
}
