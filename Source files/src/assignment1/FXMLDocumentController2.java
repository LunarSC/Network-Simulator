package assignment1;

import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.zip.CRC32;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.layout.HBox;
import javafx.scene.shape.LineTo;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;

/**
 *
 * @author Thomas Pedraza I have abided by the UNCG academic integrity policy.
 */
public class FXMLDocumentController2 implements Initializable {

    private SequentialTransition st1;
    private SequentialTransition st2;
    private PathTransition pt11;
    private PathTransition pt12;
    private PathTransition pt13;
    private PathTransition pt21;
    private PathTransition pt22;
    private PathTransition pt23;
    private AnimationTimer timer;
    private String inputText;
    private int timeCounter = 0;
    private double pixelChopLength;
    private char[] bitsArray;
    private byte[] bytes;
    private String binaryInputText;
    private String BITDLLayer;
    private String BITPhLayer;
    private String BITNLayer;
    private ParallelTransition paraT;
    private StringBuilder binary;
    private CubicCurve cc00;
    private CubicCurve cc01;
    private CubicCurve cc10;
    private CubicCurve cc11;
    private boolean hasSentConf;

    //Injecting FXML objects into Controller
    @FXML private Line connectingLine;
    @FXML private Line branchLine;
    @FXML private Circle travelingCircle;
    @FXML private Circle travelingCircle2;
    @FXML private TextField AL1;
    @FXML private TextField PL1;
    @FXML private TextField SL1;
    @FXML private TextField TL1;
    @FXML private TextField NL1;
    @FXML private TextField DL1;
    @FXML private TextField PhL1;
    @FXML private TextField inputBox;
    @FXML private TextField outputBox;
    @FXML private ImageView rightComp;
    @FXML private ImageView midComp;
    @FXML private ImageView rightRouter;
    @FXML private TextField AL2;
    @FXML private TextField PL2;
    @FXML private TextField SL2;
    @FXML private TextField TL2;
    @FXML private TextField NL2;
    @FXML private TextField DL2;
    @FXML private TextField PhL2;
    @FXML private TextField AL3;
    @FXML private TextField PL3;
    @FXML private TextField SL3;
    @FXML private TextField TL3;
    @FXML private TextField NL3;
    @FXML private TextField DL3;
    @FXML private TextField PhL3;
    @FXML private TextField outputBox2;
    @FXML private TextArea txtArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtArea.setEditable(false);
        outputBox.setEditable(false);
        outputBox2.setEditable(false);
        hasSentConf = false;
        txtArea.setText("Click the circle at the Physical layer for a pop up window to show the digital signals.\n"
        + "Analog signals are shown in the chart at the bottom right when you click on physical layer.\n"
        + "Binary representations are shown in this window.");
    }

    //Start translation of the circle after submit button is hit.
    @FXML public void submitButtonClicked() {
        if (inputBox.getText().equals("")) {
            return;
        }
        inputText = inputBox.getText();
        binaryInputText = toBinary();
        beginNetwork();
        beginDL();
        beginPhL();
        //moving the first circle
        Path path1 = new Path();
        Path path2 = new Path();
        Path path3 = new Path();

        path1.getElements().add(new MoveTo(50, 50));
        path1.getElements().add(new VLineTo(connectingLine.getTranslateY() + 40));

        path2.getElements().add(new MoveTo(50, connectingLine.getTranslateY() + 40));
        path2.getElements().add(new HLineTo(rightComp.getTranslateX() + 100));

        path3.getElements().add(new MoveTo(rightComp.getTranslateX() + 100, rightComp.getTranslateY() + 80));
        path3.getElements().add(new VLineTo(50));

        pt11 = new PathTransition();
        pt11.setDuration(Duration.millis(5000));
        pt11.setPath(path1);

        pt12 = new PathTransition();
        pt12.setDuration(Duration.millis(4000));
        pt12.setPath(path2);

        pt13 = new PathTransition();
        pt13.setDuration(Duration.millis(5000));
        pt13.setPath(path3);

        pt11.setNode(travelingCircle);
        pt11.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt11.setCycleCount(1);

        pt12.setNode(travelingCircle);
        pt12.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt12.setCycleCount(1);

        pt13.setNode(travelingCircle);
        pt13.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt13.setCycleCount(1);

        st1 = new SequentialTransition();
        st1.getChildren().addAll(pt11, pt12, pt13);

        //Second circle stuff
        Path path21 = new Path();
        Path path22 = new Path();
        Path path23 = new Path();

        path21.getElements().add(new MoveTo(15, 50));
        path21.getElements().add(new VLineTo(connectingLine.getTranslateY() + 40));

        path22.getElements().add(new MoveTo(20, connectingLine.getTranslateY() + 40));
        path22.getElements().add(new HLineTo(branchLine.getTranslateX() + 40));
        path22.getElements().add(new LineTo(branchLine.getTranslateX() + branchLine.getEndX(), (connectingLine.getTranslateY() + 40) + branchLine.getEndY()));
        path22.getElements().add(new HLineTo(midComp.getTranslateX() + 100));

        path23.getElements().add(new MoveTo(midComp.getTranslateX() + 100, midComp.getTranslateY() + 80));
        path23.getElements().add(new VLineTo(outputBox2.getTranslateY()));

        pt21 = new PathTransition();
        pt21.setDuration(Duration.millis(5000));
        pt21.setPath(path21);

        pt22 = new PathTransition();
        pt22.setDuration(Duration.millis(3000));
        pt22.setPath(path22);

        pt23 = new PathTransition();
        pt23.setDuration(Duration.millis(5500));
        pt23.setPath(path23);

        pt21.setNode(travelingCircle2);
        pt21.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt21.setCycleCount(1);

        pt22.setNode(travelingCircle2);
        pt22.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt22.setCycleCount(1);

        pt23.setNode(travelingCircle2);
        pt23.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt23.setCycleCount(1);

        st2 = new SequentialTransition();
        st2.getChildren().addAll(pt21, pt22, pt23);

        //This timer's main purpose is to change the textfields with conditions based on the circles
        //current translate properties relative to each textbox.
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                startAni();
                ++timeCounter;
            }
        };

        paraT = new ParallelTransition();
        paraT.getChildren().addAll(st1, st2);
        paraT.play();
        timer.start();
    }

    @FXML public void circleClicked() {
        if (((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                >= AL1.getTranslateY())
                && ((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                <= (AL1.getTranslateY() + AL1.getPrefHeight()))) {
            txtArea.setText(binaryInputText);
        }
        if (((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                >= PL1.getTranslateY())
                && ((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                <= (PL1.getTranslateY() + PL1.getPrefHeight()))) {
            txtArea.setText(binaryInputText);
        }
        if (((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                >= SL1.getTranslateY())
                && ((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                <= (SL1.getTranslateY() + SL1.getPrefHeight()))) {
            txtArea.setText(binaryInputText);
        }
        if (((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                >= TL1.getTranslateY())
                && ((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                <= (TL1.getTranslateY() + TL1.getPrefHeight()))) {
            txtArea.setText(binaryInputText);
        }
        //Network Layer
        if (((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                >= NL1.getTranslateY())
                && ((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                <= (NL1.getTranslateY() + NL1.getPrefHeight()))) {
            txtArea.setText(BITNLayer);
        }
        //Data Link Layer
        if (((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                >= DL1.getTranslateY())
                && ((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                <= (DL1.getTranslateY() + DL1.getPrefHeight()))) {
            txtArea.setText(BITDLLayer);
        }
        //Physical Layer
        if (((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                >= PhL1.getTranslateY())
                && ((travelingCircle.getTranslateY()
                + travelingCircle.getRadius())
                <= (PhL1.getTranslateY() + PhL1.getPrefHeight()))) {
            txtArea.setText(BITPhLayer);
            digitalWindow();
            analogLines();
        }
    }

    @FXML public void circle2Clicked() {
        if (((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                >= AL1.getTranslateY())
                && ((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                <= (AL1.getTranslateY() + AL1.getPrefHeight()))) {
            txtArea.setText(binaryInputText);
        }
        if (((travelingCircle2.getTranslateY()
                + travelingCircle.getRadius())
                >= PL1.getTranslateY())
                && ((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                <= (PL1.getTranslateY() + PL1.getPrefHeight()))) {
            txtArea.setText(binaryInputText);
        }
        if (((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                >= SL1.getTranslateY())
                && ((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                <= (SL1.getTranslateY() + SL1.getPrefHeight()))) {
            txtArea.setText(binaryInputText);
        }
        if (((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                >= TL1.getTranslateY())
                && ((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                <= (TL1.getTranslateY() + TL1.getPrefHeight()))) {
            txtArea.setText(binaryInputText);
        }
        //Network Layer
        if (((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                >= NL1.getTranslateY())
                && ((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                <= (NL1.getTranslateY() + NL1.getPrefHeight()))) {
            txtArea.setText(BITNLayer);
        }
        //Data Link Layer
        if (((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                >= DL1.getTranslateY())
                && ((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                <= (DL1.getTranslateY() + DL1.getPrefHeight()))) {
            txtArea.setText(BITDLLayer);
        }
        //Physical Layer
        if (((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                >= PhL1.getTranslateY())
                && ((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                <= (PhL1.getTranslateY() + PhL1.getPrefHeight()))) {
            txtArea.setText(binaryInputText);
            digitalWindow();
            analogLines();
        }
        //Going up
                if (((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                >= AL3.getTranslateY())
                && ((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                <= (AL3.getTranslateY() + AL3.getPrefHeight()))
                && (travelingCircle2.getTranslateX() + travelingCircle.getRadius())
                >=  AL3.getTranslateX()) {
            txtArea.setText(binaryInputText);
        }
        if (((travelingCircle2.getTranslateY()
                + travelingCircle.getRadius())
                >= PL3.getTranslateY())
                && ((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                <= (PL3.getTranslateY() + PL3.getPrefHeight()))
                && (travelingCircle2.getTranslateX() + travelingCircle.getRadius())
                >=  PL3.getTranslateX()) {
            txtArea.setText(binaryInputText);
        }
        if (((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                >= SL3.getTranslateY())
                && ((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                <= (SL3.getTranslateY() + SL3.getPrefHeight()))
                && (travelingCircle2.getTranslateX() + travelingCircle.getRadius())
                >=  SL3.getTranslateX()) {
            txtArea.setText(binaryInputText);
        }
        if (((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                >= TL3.getTranslateY())
                && ((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                <= (TL3.getTranslateY() + TL3.getPrefHeight()))
                && (travelingCircle2.getTranslateX() + travelingCircle.getRadius())
                >=  TL3.getTranslateX()) {
            txtArea.setText(binaryInputText);
        }
        //Network Layer
        if (((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                >= NL3.getTranslateY())
                && ((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                <= (NL3.getTranslateY() + NL3.getPrefHeight()))
                && (travelingCircle2.getTranslateX() + travelingCircle.getRadius())
                >=  NL3.getTranslateX()) {
            txtArea.setText(BITNLayer);
        }
        //Data Link Layer
        if (((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                >= DL3.getTranslateY())
                && ((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                <= (DL3.getTranslateY() + DL3.getPrefHeight()))
                && (travelingCircle2.getTranslateX() + travelingCircle.getRadius())
                >=  DL3.getTranslateX()) {
            txtArea.setText(BITDLLayer);
        }
        //Physical Layer
        if (((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                >= PhL3.getTranslateY())
                && ((travelingCircle2.getTranslateY()
                + travelingCircle2.getRadius())
                <= (PhL3.getTranslateY() + PhL3.getPrefHeight()))
                && (travelingCircle2.getTranslateX() + travelingCircle.getRadius())
                >=  PhL3.getTranslateX()) {
            txtArea.setText(binaryInputText);
            digitalWindow();
            analogLines();
        }
    }
    //Pausing and playing the animation
    @FXML public void pauseButtonClicked() {
        if (paraT.getStatus() == Animation.Status.PAUSED) {
            paraT.play();
            timer.start();
        } else {
            paraT.pause();
            timer.stop();
        }
    }

    public void startAni() {
        //Going down, handles text box color change to signify data exchange
        if (((travelingCircle.getTranslateY() + travelingCircle.getRadius()) >= inputBox.getTranslateY())) {
            inputBox.setStyle("-fx-background-color: green; -fx-border-color: black");
            if(hasSentConf == true && ((travelingCircle.getTranslateX() + travelingCircle.getRadius()) <= inputBox.getTranslateX())){
                inputBox.setText("Message recieved!");
            }
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
            outputBox.setText(inputText);
            if((hasSentConf == false)){
                sendConf();
            }
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
        if (((travelingCircle.getTranslateY() - travelingCircle2.getRadius()) <= NL2.getTranslateY()) && (travelingCircle.getTranslateX() >= NL2.getTranslateX())) {
            NL2.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if (((travelingCircle.getTranslateY() - travelingCircle2.getRadius()) <= DL2.getTranslateY()) && (travelingCircle.getTranslateX() >= DL2.getTranslateX())) {
            DL2.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if (((travelingCircle.getTranslateY() - travelingCircle2.getRadius()) <= PhL2.getTranslateY()) && (travelingCircle.getTranslateX() >= PhL2.getTranslateX())) {
            PhL2.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        //2nd Circle
        if (((travelingCircle2.getTranslateY() - travelingCircle2.getRadius()) <= outputBox2.getTranslateY()) && (travelingCircle2.getTranslateX() >= outputBox2.getTranslateX())) {
            outputBox2.setStyle("-fx-background-color: green; -fx-border-color: black");
            outputBox2.setText(inputText);
        }
        if (((travelingCircle2.getTranslateY() - travelingCircle2.getRadius()) <= AL3.getTranslateY()) && (travelingCircle2.getTranslateX() >= AL3.getTranslateX())) {
            AL3.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if (((travelingCircle2.getTranslateY() - travelingCircle2.getRadius()) <= PL3.getTranslateY()) && (travelingCircle2.getTranslateX() >= PL3.getTranslateX())) {
            PL3.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if (((travelingCircle2.getTranslateY() - travelingCircle2.getRadius()) <= SL3.getTranslateY()) && (travelingCircle2.getTranslateX() >= SL3.getTranslateX())) {
            SL3.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if (((travelingCircle2.getTranslateY() - travelingCircle2.getRadius()) <= TL3.getTranslateY()) && (travelingCircle2.getTranslateX() >= TL3.getTranslateX())) {
            TL3.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if (((travelingCircle2.getTranslateY() - travelingCircle2.getRadius()) <= NL3.getTranslateY()) && (travelingCircle2.getTranslateX() >= NL3.getTranslateX())) {
            NL3.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if (((travelingCircle2.getTranslateY() - travelingCircle2.getRadius()) <= DL3.getTranslateY()) && (travelingCircle2.getTranslateX() >= DL3.getTranslateX())) {
            DL3.setStyle("-fx-background-color: green; -fx-border-color: black");
        }
        if (((travelingCircle2.getTranslateY() - travelingCircle2.getRadius()) <= PhL3.getTranslateY()) && (travelingCircle2.getTranslateX() >= PhL3.getTranslateX())) {
            PhL3.setStyle("-fx-background-color: green; -fx-border-color: black");
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

    public String toBinary() {
        String str = inputText;
        bytes = str.getBytes();
        binary = new StringBuilder();
        int counter = 0;
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
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
        bitsArray = BITPhLayer.toCharArray();
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

    public void analogLines(){
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

        ancP.getChildren().addAll(label1, label2, label3, drawAnalogLines());

        scp.setContent(ancP);

        Scene scene = new Scene(scp);
        stage.setScene(scene);
        stage.setTitle("Analog Signals");
        stage.show();
    }

    public void beginNetwork() {
        BITNLayer = "01001011010101010010111011100011";
        BITNLayer += "10110100101010101101000101110010";
        BITNLayer += binaryInputText;
    }

    private void beginDL() {
        StringBuilder CRC = new StringBuilder();
        CRC32 crc = new CRC32();
        crc.update(bytes);
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                CRC.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        BITDLLayer = (BITNLayer + CRC);
        System.out.println(crc);
    }

    private void beginPhL() {
        BITPhLayer = "10101010";
        for (int i = 0; i < 5; i++) {
            BITPhLayer += "10101010";
        }
        BITPhLayer += ("10101011" + BITDLLayer);
    }

    private HBox drawAnalogLines() {
        CubicCurve currCurve;
        ArrayList<CubicCurve> test = new ArrayList<>();
        HBox returnBox = new HBox();
        returnBox.setPrefSize(550, 400);
        returnBox.setTranslateX(50);
        for(char i : bitsArray){
            currCurve = new CubicCurve();
            currCurve.setFill(Color.TRANSPARENT);
            currCurve.setStroke(Color.BLACK);

            if(i == '0'){
                currCurve.setStartX(-30);
                currCurve.setEndX(30);
                currCurve.setStartY(0);
                currCurve.setEndY(0);
                currCurve.setControlX1(0);
                currCurve.setControlX2(0);
                currCurve.setControlY1(20);
                currCurve.setControlY2(20);
                currCurve.setTranslateY((returnBox.getPrefHeight() / 2 ) + 8);
            } else {
                currCurve.setStartX(-30);
                currCurve.setEndX(30);
                currCurve.setStartY(0);
                currCurve.setEndY(0);
                currCurve.setControlX1(0);
                currCurve.setControlX2(0);
                currCurve.setControlY1(-20);
                currCurve.setControlY2(-20);
                currCurve.setTranslateY((returnBox.getPrefHeight() / 2 ) - 8);
            }
            test.add(currCurve);
        }
        returnBox.getChildren().addAll(test);
        return returnBox;
    }

    private void sendConf() {
        Path path = new Path();
        path.getElements().add(new MoveTo(rightComp.getTranslateX() + 100, 50));
        path.getElements().add(new VLineTo(connectingLine.getTranslateY() + 40));
        path.getElements().add(new HLineTo(connectingLine.getTranslateX() - 55));
        path.getElements().add(new VLineTo(inputBox.getTranslateY()));

        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.millis(8000));
        pt.setPath(path);
        pt.setNode(travelingCircle);
        pt.setCycleCount(1);
        if(paraT.getStatus() == Animation.Status.RUNNING){
            paraT.stop();
            pt.play();
        }
        hasSentConf = true;
    }
}
