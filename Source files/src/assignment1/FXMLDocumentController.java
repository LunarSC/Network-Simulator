package assignment1;

import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Thomas Pedraza I have abided by the UNCG academic integrity policy.
 */
public class FXMLDocumentController implements Initializable {

    private SequentialTransition st;
    private PathTransition pt;
    private PathTransition pt2;
    private PathTransition pt3;
    private AnimationTimer timer;
    private String inputText;
    private int timeCounter = 0;
    private double pixelChopLength;
    private char[] bitsArray;
    private byte[] bytes;
    private String binaryInputText;

    //Injecting FXML objects into Controller
    @FXML
    private Line connectingLine;
    @FXML
    private Circle travelingCircle;
    @FXML
    private TextField AL1;
    @FXML
    private TextField PL1;
    @FXML
    private TextField SL1;
    @FXML
    private TextField TL1;
    @FXML
    private TextField NL1;
    @FXML
    private TextField DL1;
    @FXML
    private TextField PhL1;
    @FXML
    private TextField inputBox;
    @FXML
    private TextField outputBox;
    @FXML
    private ImageView rightComp;
    @FXML
    private TextField AL2;
    @FXML
    private TextField PL2;
    @FXML
    private TextField SL2;
    @FXML
    private TextField TL2;
    @FXML
    private TextField NL2;
    @FXML
    private TextField DL2;
    @FXML
    private TextField PhL2;
    @FXML
    private TextArea txtArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtArea.setText("Pause the animation at any layer and click the circle to see how data is represented in that layer."
                + "\nOnly the Physical Layer and Data Link Layer have been implemented."
                + "\nData Link Layer has arbitrary Dest and Source addresses, 11111111... and 00000000..."
                + "\nas well as arbitrary CRC and Type values. These will be implemented in Assignment 2!"
                + "\nYou can scroll through the text and the lines at the bottom when they're populated with"
                + " text and lines."
                + "\nEnjoy the program :)");
    }

    //Start translation of the circle after submit button is hit.
    @FXML
    public void submitButtonClicked() {
        if (inputBox.getText().equals("")) {
            return;
        }
        inputText = inputBox.getText();
        binaryInputText = toBinary();
        //moving the circle
        Path path1 = new Path();
        Path path2 = new Path();
        Path path3 = new Path();

        path1.getElements().add(new MoveTo(50, 50));
        path1.getElements().add(new VLineTo(connectingLine.getTranslateY() + 40));

        path2.getElements().add(new MoveTo(50, connectingLine.getTranslateY() + 40));
        path2.getElements().add(new HLineTo(rightComp.getTranslateX() + 100));

        path3.getElements().add(new MoveTo(rightComp.getTranslateX() + 100, rightComp.getTranslateY() + 80));
        path3.getElements().add(new VLineTo(50));

        pt = new PathTransition();
        pt.setDuration(Duration.millis(5000));
        pt.setPath(path1);

        pt2 = new PathTransition();
        pt2.setDuration(Duration.millis(4000));
        pt2.setPath(path2);

        pt3 = new PathTransition();
        pt3.setDuration(Duration.millis(5000));
        pt3.setPath(path3);

        pt.setNode(travelingCircle);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.setCycleCount(1);

        pt2.setNode(travelingCircle);
        pt2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt2.setCycleCount(1);

        pt3.setNode(travelingCircle);
        pt3.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt3.setCycleCount(1);

        st = new SequentialTransition();
        st.getChildren().addAll(pt, pt2, pt3);

        //This timer's main purpose is to change the textfields with conditions based on the circles
        //current translate properties relative to each textbox.
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                startAni();
                ++timeCounter;
            }
        };

        st.play();
        timer.start();
    }

    @FXML
    public void circleClicked() {
        if (((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                >= AL1.getTranslateY())
                && ((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                <= (AL1.getTranslateY() + AL1.getPrefHeight()))) {

        }
        if (((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                >= PL1.getTranslateY())
                && ((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                <= (PL1.getTranslateY() + PL1.getPrefHeight()))) {

        }
        if (((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                >= SL1.getTranslateY())
                && ((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                <= (SL1.getTranslateY() + SL1.getPrefHeight()))) {

        }
        if (((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                >= TL1.getTranslateY())
                && ((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                <= (TL1.getTranslateY() + TL1.getPrefHeight()))) {

        }
        if (((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                >= NL1.getTranslateY())
                && ((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                <= (NL1.getTranslateY() + NL1.getPrefHeight()))) {

        }
        //Data Link Layer
        if (((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                >= DL1.getTranslateY())
                && ((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                <= (DL1.getTranslateY() + DL1.getPrefHeight()))) {
            txtArea.setText(binaryInputText);
        }
        //Physical Layer
        if (((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                >= PhL1.getTranslateY())
                && ((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                <= (PhL1.getTranslateY() + PhL1.getPrefHeight()))) {
            digitalWindow();
        }
    }

//Pausing and playing the animation
    @FXML
    public void pauseButtonClicked() {
        if (st.getStatus() == Animation.Status.PAUSED) {
            st.play();
            timer.start();
        } else {
            st.pause();
            timer.stop();
        }
    }

    public void digitalWindow() {
            Stage stage = new Stage();
            ScrollPane scp = new ScrollPane();
            scp.setPrefSize(600, 400);

            AnchorPane ancP = new AnchorPane();
            ancP.setPrefSize(600, 400);

            Label label1 = new Label("+1");
            Label label2 = new Label("0");
            Label label3 = new Label("-1");

            label1.setTranslateY(150);
            label2.setTranslateY(200);
            label3.setTranslateY(250);


            ancP.getChildren().addAll(label1, label2, label3, drawLines());

            scp.setContent(ancP);

            Scene scene = new Scene(scp);
            stage.setScene(scene);
            stage.setTitle("Digital Signals");
            stage.show();
        }

    public void startAni() {
        //Going down, handles text box color change to signify data exchange
        if (((travelingCircle.getTranslateY() + travelingCircle.getRadius()) >= inputBox.getTranslateY())) {
            inputBox.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if ((travelingCircle.getTranslateY() + travelingCircle.getRadius()) >= AL1.getTranslateY()) {
            AL1.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if ((travelingCircle.getTranslateY() + travelingCircle.getRadius()) >= PL1.getTranslateY()) {
            PL1.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if ((travelingCircle.getTranslateY() + travelingCircle.getRadius()) >= SL1.getTranslateY()) {
            SL1.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if ((travelingCircle.getTranslateY() + travelingCircle.getRadius()) >= TL1.getTranslateY()) {
            TL1.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if ((travelingCircle.getTranslateY() + travelingCircle.getRadius()) >= NL1.getTranslateY()) {
            NL1.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if ((travelingCircle.getTranslateY() + travelingCircle.getRadius()) >= DL1.getTranslateY()) {
            DL1.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if ((travelingCircle.getTranslateY() + travelingCircle.getRadius()) >= PhL1.getTranslateY()) {
            PhL1.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        //Going up
        if (((travelingCircle.getTranslateY() - travelingCircle.getRadius()) <= outputBox.getTranslateY()) && (travelingCircle.getTranslateX() >= outputBox.getTranslateX())) {
            outputBox.setStyle("-fx-background-color: green; -fx-border-color: black");
            outputBox.setText(inputBox.getText());
        }
        if (((travelingCircle.getTranslateY() - travelingCircle.getRadius()) <= AL2.getTranslateY()) && (travelingCircle.getTranslateX() >= AL2.getTranslateX())) {
            AL2.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if (((travelingCircle.getTranslateY() - travelingCircle.getRadius()) <= PL2.getTranslateY()) && (travelingCircle.getTranslateX() >= PL2.getTranslateX())) {
            PL2.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if (((travelingCircle.getTranslateY() - travelingCircle.getRadius()) <= SL2.getTranslateY()) && (travelingCircle.getTranslateX() >= SL2.getTranslateX())) {
            SL2.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if (((travelingCircle.getTranslateY() - travelingCircle.getRadius()) <= TL2.getTranslateY()) && (travelingCircle.getTranslateX() >= TL2.getTranslateX())) {
            TL2.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if (((travelingCircle.getTranslateY() - travelingCircle.getRadius()) <= NL2.getTranslateY()) && (travelingCircle.getTranslateX() >= NL2.getTranslateX())) {
            NL2.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if (((travelingCircle.getTranslateY() - travelingCircle.getRadius()) <= DL2.getTranslateY()) && (travelingCircle.getTranslateX() >= DL2.getTranslateX())) {
            DL2.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if (((travelingCircle.getTranslateY() - travelingCircle.getRadius()) <= PhL2.getTranslateY()) && (travelingCircle.getTranslateX() >= PhL2.getTranslateX())) {
            PhL2.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
    }

    public String toBinary() {
        String str = inputText;
        bytes = str.getBytes();
        StringBuilder binary = new StringBuilder();
        //Preamble
        for (int i = 0; i < 7; ++i) {
            binary.append("10101010");
        }
        //SFD
        binary.append("10101011");
        //Dest
        for (int i = 0; i < 6; ++i) {
            binary.append("11111111");
        }
        //Source
        for (int i = 0; i < 6; i++) {
            binary.append("00000000");
        }
        //Type
        for (int i = 0; i < 2; i++) {
            binary.append("00000000");
        }
        int counter = 0;
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        //CRC
        for (int i = 0; i < 4; i++) {
            binary.append("01001101");
        }
        return ("" + binary);
    }

   public HBox drawLines() {
        HBox oneTime = new HBox();
        oneTime.setPrefSize(550, 400);
        oneTime.setTranslateX(50);
        if (oneTime.getChildren().isEmpty() == false) {
            oneTime.getChildren().clear();
        }
        bitsArray = new char[inputText.length()];
        bitsArray = binaryInputText.toCharArray();
        ArrayList<Line> lineList = new ArrayList<>();
        Line currLine;
        Line vertLine;
        double transYif0 = 250;
        double transYif1 = 150;

        for (char i : bitsArray) {
            //line inits
            currLine = new Line();
            vertLine = new Line();
            currLine.setStartX(0);
            currLine.setEndX(30);
            vertLine.setStartY(0);
            vertLine.setEndY(transYif0 - transYif1);

            //for first line
            if (lineList.isEmpty()) {
                if (i == '0') {
                    currLine.setTranslateY(transYif0);
                } else if (i == '1') {
                    currLine.setTranslateY(transYif1);
                }
                currLine.setTranslateX(0);
                lineList.add(currLine);
                continue;
            }

            //vertLine properties
            vertLine.setTranslateX(lineList.get(lineList.size() - 1).getTranslateX());
            vertLine.setTranslateY(transYif1);

            //currline translateX properties
            if (i == '0') {
                currLine.setTranslateY(transYif0);
                //add vert line if last line's Y != this line's Y
                if (lineList.get(lineList.size() - 1).getTranslateY() == transYif1) {
                    oneTime.getChildren().add(vertLine);
                }

            } else {
                currLine.setTranslateY(transYif1);
                //add vert line if last line's Y != this line's Y
                if (lineList.get(lineList.size() - 1).getTranslateY() == transYif0) {
                    oneTime.getChildren().add(vertLine);
                }
            }

            lineList.add(currLine);
            oneTime.getChildren().add(currLine);
        }
        return oneTime;
    }

}
