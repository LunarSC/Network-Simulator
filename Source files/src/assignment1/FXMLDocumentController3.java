package assignment1;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.fxml.FXML;
import java.util.ResourceBundle;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;

public class FXMLDocumentController3 implements Initializable {
     //Injecting FXML objects into Controller
    @FXML private Line l0;
    @FXML private Line l1;
    @FXML private Line l3;
    @FXML private Line l8;
    @FXML private Line l9;
    @FXML private ImageView r0;
    @FXML private ImageView r1;
    @FXML private ImageView r2;
    @FXML private ImageView r3;
    @FXML private ImageView r4;
    @FXML private ImageView r5;
    @FXML private ImageView r6;
    @FXML private ImageView r7;
    @FXML private Line l2Line;
    @FXML private Line l1Line;
    @FXML private Line C2R2;
    @FXML private Line r2C2;
    @FXML private Text t0;
    @FXML private Text t1;
    @FXML private Text t2;
    @FXML private Text t3;
    @FXML private Text t4;
    @FXML private Text t5;
    @FXML private Text t6;
    @FXML private Text t7;
    @FXML private Text t8;
    @FXML private Text t9;
    @FXML private Text t10;
    @FXML private Text t01;
    @FXML private Text t11;
    @FXML private Text t21;
    @FXML private Text t31;
    @FXML private Text t41;
    @FXML private Text t51;
    @FXML private Text t61;
    @FXML private Text t71;
    @FXML private Text t81;
    @FXML private Text t91;
    @FXML private Text t101;
    @FXML private Circle travelingCircle;
    @FXML private Rectangle AL1;
    @FXML private Rectangle PL1;
    @FXML private Rectangle SL1;
    @FXML private Rectangle TL1;
    @FXML private Rectangle NL1;
    @FXML private Rectangle DL1;
    @FXML private Rectangle PhL1;
    @FXML private TextField inputBox;
    @FXML private TextField outputBox;
    @FXML private Rectangle AL2;
    @FXML private Rectangle PL2;
    @FXML private Rectangle SL2;
    @FXML private Rectangle TL2;
    @FXML private Rectangle NL2;
    @FXML private Rectangle DL2;
    @FXML private Rectangle PhL2;
    @FXML private TextArea txtArea;
    @FXML private GridPane mainGrid;
    @FXML private Circle circle2;
    @FXML private Text routerInfo;
    @FXML private ScrollPane paneForLines;
    @FXML private AnchorPane ancP;

    private SequentialTransition sT;
    private SequentialTransition sT2;
    private SequentialTransition arqST;
    private AnimationTimer timer;
    private String inputText = "";
    private char[] bitsArray;
    private byte[] bytes;
    private String binaryInputText = "";
    private String BITTLayer = "";
    private String BITDLLayer = "";
    private String BITPhLayer = "";
    private String BITNLayer = "";
    private StringBuilder binary;
    private int[] randomNums;
    private int[] randomNums2;
    private final int r0IP = 0x18A7A3D6;
    private final int r0MAC = 0x38A76A9D;
    private final int r5IP = 0x18A7A3AA;
    private final int r5MAC = 0x18A78D6A;
    private final int sourceAndDestPort = 0x1A0B;
    private boolean fragNeeded = false;
    private boolean arqChecked = false;
    private char lastNode = '0';
    private boolean circle1Finished = false;
    private boolean circle2Finished = false;
    private ArrayList<Shape> layers;
    private Circle ackCircle;
    private URL url;
    private ResourceBundle rb;
    private boolean anLinesPres = false;
    private boolean digLinesPres = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.url = url;
        this.rb = rb;
        layers = new ArrayList<>();
        layers.add(AL1);
        layers.add(PL1);
        layers.add(SL1);
        layers.add(TL1);
        layers.add(NL1);
        layers.add(DL1);
        layers.add(PhL1);

        layers.add(AL2);
        layers.add(PL2);
        layers.add(SL2);
        layers.add(TL2);
        layers.add(NL2);
        layers.add(DL2);
        layers.add(PhL2);
        layers.forEach((static_bloc) -> {
            static_bloc.setFill(Paint.valueOf("cc1212"));
        });

        setRouterInfo();

        randomNums = new int[11];
        randomNums2 = new int[11];
        Random rand = new Random(System.nanoTime());
        for(int i = 0; i < 11; ++i){
            randomNums[i] = rand.nextInt(7) + 1;
            randomNums2[i] = rand.nextInt(7) + 1;
        }
        placeText(randomNums, randomNums2);
        txtArea.setEditable(false);
        outputBox.setEditable(false);
    }

    //Start translation of the circle after submit button is hit.
    @FXML public void submitButtonClicked() {
        if (inputBox.getText().equals("")) {
            return;
        }
        //turn input into binary
        binaryBuilding();
        //start circle animations
        moveCircle();
        moveCircle2();
    }

    //Moves the red circle
    @FXML public void moveCircle(){
        //Circle's path from input box to the sender's router.
        sT = new SequentialTransition();
        //initial path from Application layer to r0.
        Path path = new Path();
        path.getElements().add(new MoveTo(inputBox.getTranslateX() - travelingCircle.getRadius(), inputBox.getTranslateY()));
        path.getElements().add(new VLineTo(PhL1.getTranslateY()));
        path.getElements().add(new HLineTo(l1Line.getTranslateX() - travelingCircle.getRadius()));
        path.getElements().add(new VLineTo(C2R2.getTranslateY() - travelingCircle.getRadius()));
        path.getElements().add(new HLineTo(l0.getTranslateX() - travelingCircle.getRadius()));
        sT.getChildren().add(new PathTransition(Duration.seconds(4), path));

        //Add to graph for dijkstra's algorithm
        List<Character> pathing = makeGraph(randomNums).getShortestPath('0', '5');
        Collections.reverse(pathing);

        sT.getChildren().addAll(dijkstraPath(pathing, travelingCircle, randomNums, false));

        //Moving circle from destination router to output box.
        Path fPath = new Path();
        fPath.getElements().add(new MoveTo(r5.getTranslateX() + 25, r5.getTranslateY() + 25));
        fPath.getElements().add(new HLineTo(l2Line.getTranslateX() - travelingCircle.getRadius()));
        fPath.getElements().add(new VLineTo(PhL2.getHeight() + PhL2.getTranslateY() - travelingCircle.getRadius()));
        fPath.getElements().add(new HLineTo(PhL2.getTranslateX() + PhL2.getWidth() - travelingCircle.getRadius()));
        fPath.getElements().add(new VLineTo(outputBox.getTranslateY()));
        PathTransition pT1 = new PathTransition(Duration.seconds(4), fPath);
        sT.getChildren().add(pT1);
        sT.setNode(travelingCircle);

        //This timer's main purpose is to change the textfields with conditions based on the circles
        //current translate properties relative to each textbox.
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                checkBounds(travelingCircle);
                checkBounds(circle2);
                if(ackCircle != null) {
                    checkBounds(ackCircle);
                }
            }
        };
        sT.play();
        timer.start();
    }

    //Moves the blue circle
    @FXML public void moveCircle2() {
        //Circle's path from input box to the sender's router.
        sT2 = new SequentialTransition();
        //initial path from Application layer to r0.
        Path path = new Path();
        path.getElements().add(new MoveTo(inputBox.getTranslateX() - circle2.getRadius(), inputBox.getTranslateY() - 10));
        path.getElements().add(new VLineTo(PhL1.getTranslateY()));
        path.getElements().add(new HLineTo(l1Line.getTranslateX() - circle2.getRadius()));
        path.getElements().add(new VLineTo(C2R2.getTranslateY() - circle2.getRadius()));
        path.getElements().add(new HLineTo(l0.getTranslateX() - circle2.getRadius()));
        sT2.getChildren().add(new PathTransition(Duration.seconds(4), path, circle2));

        //Add to graph for dijkstra's algorithm
        List<Character> pathing = makeGraph(randomNums2).getShortestPath('0', '5');
        Collections.reverse(pathing);

        sT2.getChildren().addAll(dijkstraPath(pathing, circle2, randomNums2, false));

        //Moving circle from destination router to output box.
        Path fPath = new Path();
        fPath.getElements().add(new MoveTo(r5.getTranslateX() + 25, r5.getTranslateY() + 25));
        fPath.getElements().add(new HLineTo(l2Line.getTranslateX() - circle2.getRadius()));
        fPath.getElements().add(new VLineTo(PhL2.getHeight() + PhL2.getTranslateY() - circle2.getRadius()));
        fPath.getElements().add(new HLineTo(PhL2.getTranslateX() + PhL2.getWidth() - circle2.getRadius()));
        fPath.getElements().add(new VLineTo(outputBox.getTranslateY()));
        PathTransition pT1 = new PathTransition(Duration.seconds(4), fPath);
        sT2.getChildren().add(pT1);
        sT2.setNode(circle2);

        //This timer's main purpose is to change the textfields with conditions based on the circles
        //current translate properties relative to each textbox.
        sT2.play();
    }

    //Shows text and extra windows based on where the circle is clicked.
    @FXML public void circleClicked() {
        if(travelingCircle.getBoundsInParent().intersects(AL1.getBoundsInParent())){
            txtArea.setText(binaryInputText);
        }
        if(travelingCircle.getBoundsInParent().intersects(PL1.getBoundsInParent())){
            txtArea.setText(binaryInputText);
        }
        if(travelingCircle.getBoundsInParent().intersects(SL1.getBoundsInParent())){
            txtArea.setText(binaryInputText);
        }
        if(travelingCircle.getBoundsInParent().intersects(TL1.getBoundsInParent())){
            txtArea.setText(BITTLayer);
        }
        if(travelingCircle.getBoundsInParent().intersects(NL1.getBoundsInParent())){
            txtArea.setText(BITNLayer);
        }
        if(travelingCircle.getBoundsInParent().intersects(DL1.getBoundsInParent())){
            txtArea.setText(BITDLLayer);
        }
        if(travelingCircle.getBoundsInParent().intersects(PhL1.getBoundsInParent())){
            txtArea.setText(BITPhLayer);
        }
        if(travelingCircle.getBoundsInParent().intersects(AL2.getBoundsInParent())){
            txtArea.setText(binaryInputText);
        }
        if(travelingCircle.getBoundsInParent().intersects(PL2.getBoundsInParent())){
            txtArea.setText(binaryInputText);
        }
        if(travelingCircle.getBoundsInParent().intersects(SL2.getBoundsInParent())){
            txtArea.setText(binaryInputText);
        }
        if(travelingCircle.getBoundsInParent().intersects(TL2.getBoundsInParent())){
            txtArea.setText(BITTLayer);
        }
        if(travelingCircle.getBoundsInParent().intersects(NL2.getBoundsInParent())){
            txtArea.setText(BITNLayer);
        }
        if(travelingCircle.getBoundsInParent().intersects(DL2.getBoundsInParent())){
            txtArea.setText(BITDLLayer);
        }
        if(travelingCircle.getBoundsInParent().intersects(PhL2.getBoundsInParent())){
            txtArea.setText(BITPhLayer);
        }
    }

    //Pausing and playing the animation
    @FXML public void pauseButtonClicked() {
        if (sT != null) {
            if (sT.getStatus() == Animation.Status.PAUSED) {
                sT.play();
                timer.start();
            } else {
                sT.pause();
                timer.stop();
            }
        }
        if (sT2 != null) {
            if (sT2.getStatus() == Animation.Status.PAUSED) {
                sT2.play();
                timer.start();
            } else {
                sT2.pause();
                timer.stop();
            }
        }
        if(arqST != null) {
            if(arqST.getStatus() == Animation.Status.PAUSED) {
                arqST.play();
                timer.start();
            } else {
                arqST.pause();
                timer.stop();
            }
        }
    }

    //HBox for the analog window, comes with the lines in them.
    private HBox drawAnalogLines() {
        double translateXCounter = 0;
        CubicCurve curveOne;
        CubicCurve curveTwo;
        CubicCurve curveThree;
        CubicCurve curveFour;
        ArrayList<CubicCurve> curveList = new ArrayList<>();
        HBox returnBox = new HBox();
        returnBox.setPrefSize(1000, 100);
        returnBox.setTranslateX(50);
        for (char i : bitsArray) {

            if (i == '0') {
                curveOne = new CubicCurve(-10, 0, 0, -75, 0, -75, 10, 0);
                curveOne.setFill(Color.WHITE);
                curveOne.setStroke(Color.BLACK);
                curveOne.setTranslateX(-translateXCounter);
                curveOne.setTranslateY((returnBox.getPrefHeight() / 2) - 28.5);
                curveList.add(curveOne);

                translateXCounter += 3.25;

                curveTwo = new CubicCurve(-10, 0, 0, 75, 0, 75, 10, 0);
                curveTwo.setFill(Color.WHITE);
                curveTwo.setStroke(Color.BLACK);
                curveTwo.setTranslateX(-translateXCounter);
                curveTwo.setTranslateY((returnBox.getPrefHeight() / 2) + 28.5);
                curveList.add(curveTwo);

                translateXCounter += 3.25;
            } else {
                curveOne = new CubicCurve(-5, 0, 0, -75, 0, -75, 5, 0);
                curveOne.setFill(Color.WHITE);
                curveOne.setStroke(Color.BLACK);
                curveOne.setTranslateX(-translateXCounter);
                curveOne.setTranslateY((returnBox.getPrefHeight() / 2) - 28.5);
                curveList.add(curveOne);

                translateXCounter += 3.25;

                curveTwo = new CubicCurve(-5, 0, 0, 75, 0, 75, 5, 0);
                curveTwo.setFill(Color.WHITE);
                curveTwo.setStroke(Color.BLACK);
                curveTwo.setTranslateX(-translateXCounter);
                curveTwo.setTranslateY((returnBox.getPrefHeight() / 2) + 28.5);
                curveList.add(curveTwo);

                translateXCounter += 3.25;

                curveThree = new CubicCurve(-5, 0, 0, -75, 0, -75, 5, 0);
                curveThree.setFill(Color.WHITE);
                curveThree.setStroke(Color.BLACK);
                curveThree.setTranslateX(-translateXCounter);
                curveThree.setTranslateY((returnBox.getPrefHeight() / 2) - 28.5);
                curveList.add(curveThree);

                translateXCounter += 3.25;

                curveFour = new CubicCurve(-5, 0, 0, 75, 0, 75, 5, 0);
                curveFour.setFill(Color.WHITE);
                curveFour.setStroke(Color.BLACK);
                curveFour.setTranslateX(-translateXCounter);
                curveFour.setTranslateY((returnBox.getPrefHeight() / 2) + 28.5);
                curveList.add(curveFour);

                translateXCounter += 3.25;
            }
        }
        returnBox.getChildren().addAll(curveList);
        return returnBox;
    }

    //Stage for the analog signals in physical layer.
    public void analogLines() {
        Label label1 = new Label("+1");
        Label label2 = new Label("0");
        Label label3 = new Label("-1");

        label1.setTranslateY(10);
        label2.setTranslateY(70);
        label3.setTranslateY(130);

        ancP.getChildren().clear();
        digLinesPres = false;
        ancP.getChildren().addAll(label1, label2, label3, drawAnalogLines());

        paneForLines.setContent(ancP);
        anLinesPres = true;
    }

    //HBox for the digital window, comes with the lines in them.
    public HBox drawLines() {
        HBox oneTime = new HBox();
        oneTime.setPrefSize(1000, 100);
        oneTime.setTranslateX(50);
        if (oneTime.getChildren().isEmpty() == false) {
            oneTime.getChildren().clear();
        }
        bitsArray = new char[inputText.length()];
        bitsArray = BITPhLayer.toCharArray();
        ArrayList<Line> lineList = new ArrayList<>();
        Line currLine;
        Line vertLine;
        double transYif0 = 144;
        double transYif1 = 25;

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

    //Stage for the digital signals in physical layer.
    public void digitalLines() {
        Label label1 = new Label("+1");
        Label label2 = new Label("0");
        Label label3 = new Label("-1");

        label1.setTranslateY(10);
        label2.setTranslateY(70);
        label3.setTranslateY(130);

        ancP.getChildren().clear();
        anLinesPres = false;
        ancP.getChildren().addAll(label1, label2, label3, drawLines());

        paneForLines.setContent(ancP);
        digLinesPres = true;
    }

    //Populates text near lines to represent weights of those lines.
    public void placeText(int[] rand, int[] rand2) {
        t0.setText("" + rand[0] + ", ");
        t1.setText("" + rand[2] + ", ");
        t2.setText("" + rand[4] + ", ");
        t3.setText("" + rand[1] + ", ");
        t4.setText("" + rand[3] + ", ");
        t5.setText("" + rand[5] + ", ");
        t6.setText("" + rand[6] + ", ");
        t7.setText("" + rand[8] + ", ");
        t8.setText("" + rand[7] + ", ");
        t9.setText("" + rand[9] + ", ");
        t10.setText("" + rand[10] + ", ");

        t0.setFill(Color.RED);
        t1.setFill(Color.RED);
        t2.setFill(Color.RED);
        t3.setFill(Color.RED);
        t4.setFill(Color.RED);
        t5.setFill(Color.RED);
        t6.setFill(Color.RED);
        t7.setFill(Color.RED);
        t8.setFill(Color.RED);
        t9.setFill(Color.RED);
        t10.setFill(Color.RED);

        t01.setText("" + rand2[0]);
        t11.setText("" + rand2[2]);
        t21.setText("" + rand2[4]);
        t31.setText("" + rand2[1]);
        t41.setText("" + rand2[3]);
        t51.setText("" + rand2[5]);
        t61.setText("" + rand2[6]);
        t71.setText("" + rand2[8]);
        t81.setText("" + rand2[7]);
        t91.setText("" + rand2[9]);
        t101.setText("" + rand2[10]);

        t01.setFill(Color.BLUE);
        t11.setFill(Color.BLUE);
        t21.setFill(Color.BLUE);
        t31.setFill(Color.BLUE);
        t41.setFill(Color.BLUE);
        t51.setFill(Color.BLUE);
        t61.setFill(Color.BLUE);
        t71.setFill(Color.BLUE);
        t81.setFill(Color.BLUE);
        t91.setFill(Color.BLUE);
        t101.setFill(Color.BLUE);
    }

    //Builds the datagram for each layer.
    public void binaryBuilding() {
        //input only to binary
        inputText = inputBox.getText();
        bytes = inputText.getBytes();
        binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        binaryInputText = binary.toString();

        //Transport layer
        BITTLayer = String.format("%16s", Integer.toBinaryString(sourceAndDestPort)).replace(' ', '0') + String.format("%16s", Integer.toBinaryString(sourceAndDestPort)).replace(' ', '0') + binaryInputText;

         //Network layer
            //VER
            BITNLayer = String.format("%4s", Integer.toBinaryString(0x4)).replace(' ', '0');
            //HLEN
            int hlen = 20;
            BITNLayer += String.format("%4s", Integer.toBinaryString(hlen / 4)).replace(' ', '0');
            //Service Type, routine and no priorities, set to all 0's
            BITNLayer += "00000000";
            //Total length
            int tlen = BITTLayer.length() + hlen;
            BITNLayer += String.format("%16s", Integer.toBinaryString(tlen / 4)).replace(' ', '0');
            if(tlen > 65535) {
                fragNeeded = true;
                System.out.println("This packet needs fragmentation.");
                double fragsNeeded = Math.ceil(tlen / 65535);
                BITNLayer += String.format("%16s", fragsNeeded).replace(' ', '0');
            } else {
                //ID
                BITNLayer += "0000000000000000";
                //Flags & Frag off
                BITNLayer += "0000000000000000";
            }
            //Initial TTL is 64 hops
            BITNLayer += "01000000";
            //TCP is the protocol
            BITNLayer += String.format("%8s", Integer.toBinaryString(0x06)).replace(' ', '0');
            //CRC
            BITNLayer += CRC16(BITNLayer);
            //Source and dest IP
            BITNLayer += String.format("%32s", Integer.toBinaryString(r5IP)).replace(' ', '0') + String.format("%32s", Integer.toBinaryString(r0IP)).replace(' ', '0') + BITTLayer;
        //Datalink layer, 6 bytes for Source and 6 bytes for dest.
            BITDLLayer = String.format("%32s", Integer.toBinaryString(r5MAC)).replace(' ', '0') + String.format("%32s", Integer.toBinaryString(r0MAC)).replace(" ", "0") + BITNLayer;
            BITDLLayer += CRC32(BITDLLayer);
        //Physical layer
            for(int i = 0; i < 7; ++i) {
                BITPhLayer += "10101010";
            }
        BITPhLayer += "10101011" + BITDLLayer;
    }

    //Error detection algorithm for the ethernet frame checked by the reciever.
    public void ARQ32(String data) {
        boolean dataCorrect = true;
        String crc = data.substring(data.length() - 16, data.length());
        String message = data.substring(0, data.length() - 17);
        int sum = 0;
        ArrayList<String> addingBits = new ArrayList<>();

        //split up message into length 16 substrings
        int index = 0;
        while(index < message.length()) {
            addingBits.add(message.substring(index, Math.min(index + 16, message.length())));
            index += 16;
        }

        //Add words with one's compliment addition
        for(int i =0; i < addingBits.size() - 1; ++i) {
            int value = Integer.parseInt(addingBits.get(i), 2);
            int value2 = Integer.parseInt(addingBits.get(i+1), 2);

            sum += (value) + (~value2);
        }

        //If compliment of sum = 0, the message is accepted. otherwise it's rejected.
        if(~sum == 0) {
            dataCorrect = true;
        }

        if(dataCorrect) {
            sendACK();
        }
    }

    //Error detection algorithm for the ethernet frame made by the sender.
    public String CRC32(String addCRC) {
        int[] CRCTable = {
            0x00000000, 0x77073096, 0xee0e612c, 0x990951ba, 0x076dc419, 0x706af48f, 0xe963a535, 0x9e6495a3,
            0x0edb8832, 0x79dcb8a4, 0xe0d5e91e, 0x97d2d988, 0x09b64c2b, 0x7eb17cbd, 0xe7b82d07, 0x90bf1d91,
            0x1db71064, 0x6ab020f2, 0xf3b97148, 0x84be41de, 0x1adad47d, 0x6ddde4eb, 0xf4d4b551, 0x83d385c7,
            0x136c9856, 0x646ba8c0, 0xfd62f97a, 0x8a65c9ec, 0x14015c4f, 0x63066cd9, 0xfa0f3d63, 0x8d080df5,
            0x3b6e20c8, 0x4c69105e, 0xd56041e4, 0xa2677172, 0x3c03e4d1, 0x4b04d447, 0xd20d85fd, 0xa50ab56b,
            0x35b5a8fa, 0x42b2986c, 0xdbbbc9d6, 0xacbcf940, 0x32d86ce3, 0x45df5c75, 0xdcd60dcf, 0xabd13d59,
            0x26d930ac, 0x51de003a, 0xc8d75180, 0xbfd06116, 0x21b4f4b5, 0x56b3c423, 0xcfba9599, 0xb8bda50f,
            0x2802b89e, 0x5f058808, 0xc60cd9b2, 0xb10be924, 0x2f6f7c87, 0x58684c11, 0xc1611dab, 0xb6662d3d,
            0x76dc4190, 0x01db7106, 0x98d220bc, 0xefd5102a, 0x71b18589, 0x06b6b51f, 0x9fbfe4a5, 0xe8b8d433,
            0x7807c9a2, 0x0f00f934, 0x9609a88e, 0xe10e9818, 0x7f6a0dbb, 0x086d3d2d, 0x91646c97, 0xe6635c01,
            0x6b6b51f4, 0x1c6c6162, 0x856530d8, 0xf262004e, 0x6c0695ed, 0x1b01a57b, 0x8208f4c1, 0xf50fc457,
            0x65b0d9c6, 0x12b7e950, 0x8bbeb8ea, 0xfcb9887c, 0x62dd1ddf, 0x15da2d49, 0x8cd37cf3, 0xfbd44c65,
            0x4db26158, 0x3ab551ce, 0xa3bc0074, 0xd4bb30e2, 0x4adfa541, 0x3dd895d7, 0xa4d1c46d, 0xd3d6f4fb,
            0x4369e96a, 0x346ed9fc, 0xad678846, 0xda60b8d0, 0x44042d73, 0x33031de5, 0xaa0a4c5f, 0xdd0d7cc9,
            0x5005713c, 0x270241aa, 0xbe0b1010, 0xc90c2086, 0x5768b525, 0x206f85b3, 0xb966d409, 0xce61e49f,
            0x5edef90e, 0x29d9c998, 0xb0d09822, 0xc7d7a8b4, 0x59b33d17, 0x2eb40d81, 0xb7bd5c3b, 0xc0ba6cad,
            0xedb88320, 0x9abfb3b6, 0x03b6e20c, 0x74b1d29a, 0xead54739, 0x9dd277af, 0x04db2615, 0x73dc1683,
            0xe3630b12, 0x94643b84, 0x0d6d6a3e, 0x7a6a5aa8, 0xe40ecf0b, 0x9309ff9d, 0x0a00ae27, 0x7d079eb1,
            0xf00f9344, 0x8708a3d2, 0x1e01f268, 0x6906c2fe, 0xf762575d, 0x806567cb, 0x196c3671, 0x6e6b06e7,
            0xfed41b76, 0x89d32be0, 0x10da7a5a, 0x67dd4acc, 0xf9b9df6f, 0x8ebeeff9, 0x17b7be43, 0x60b08ed5,
            0xd6d6a3e8, 0xa1d1937e, 0x38d8c2c4, 0x4fdff252, 0xd1bb67f1, 0xa6bc5767, 0x3fb506dd, 0x48b2364b,
            0xd80d2bda, 0xaf0a1b4c, 0x36034af6, 0x41047a60, 0xdf60efc3, 0xa867df55, 0x316e8eef, 0x4669be79,
            0xcb61b38c, 0xbc66831a, 0x256fd2a0, 0x5268e236, 0xcc0c7795, 0xbb0b4703, 0x220216b9, 0x5505262f,
            0xc5ba3bbe, 0xb2bd0b28, 0x2bb45a92, 0x5cb36a04, 0xc2d7ffa7, 0xb5d0cf31, 0x2cd99e8b, 0x5bdeae1d,
            0x9b64c2b0, 0xec63f226, 0x756aa39c, 0x026d930a, 0x9c0906a9, 0xeb0e363f, 0x72076785, 0x05005713,
            0x95bf4a82, 0xe2b87a14, 0x7bb12bae, 0x0cb61b38, 0x92d28e9b, 0xe5d5be0d, 0x7cdcefb7, 0x0bdbdf21,
            0x86d3d2d4, 0xf1d4e242, 0x68ddb3f8, 0x1fda836e, 0x81be16cd, 0xf6b9265b, 0x6fb077e1, 0x18b74777,
            0x88085ae6, 0xff0f6a70, 0x66063bca, 0x11010b5c, 0x8f659eff, 0xf862ae69, 0x616bffd3, 0x166ccf45,
            0xa00ae278, 0xd70dd2ee, 0x4e048354, 0x3903b3c2, 0xa7672661, 0xd06016f7, 0x4969474d, 0x3e6e77db,
            0xaed16a4a, 0xd9d65adc, 0x40df0b66, 0x37d83bf0, 0xa9bcae53, 0xdebb9ec5, 0x47b2cf7f, 0x30b5ffe9,
            0xbdbdf21c, 0xcabac28a, 0x53b39330, 0x24b4a3a6, 0xbad03605, 0xcdd70693, 0x54de5729, 0x23d967bf,
            0xb3667a2e, 0xc4614ab8, 0x5d681b02, 0x2a6f2b94, 0xb40bbe37, 0xc30c8ea1, 0x5a05df1b, 0x2d02ef8d,
        };

        int crc = 0xffffffff;
        for (byte b : addCRC.getBytes()) {
            crc = (crc >>> 8) ^ CRCTable[(crc ^ b) & 0xff];
        }
        crc = crc ^ 0xffffffff;
        return String.format("%32s", Integer.toBinaryString(crc)).replace(' ', '0');
    }

    //Error detection algorithm for the network layer.
    public String CRC16(String addCRC) {
        int[] table = {
            0x0000, 0xC0C1, 0xC181, 0x0140, 0xC301, 0x03C0, 0x0280, 0xC241,
            0xC601, 0x06C0, 0x0780, 0xC741, 0x0500, 0xC5C1, 0xC481, 0x0440,
            0xCC01, 0x0CC0, 0x0D80, 0xCD41, 0x0F00, 0xCFC1, 0xCE81, 0x0E40,
            0x0A00, 0xCAC1, 0xCB81, 0x0B40, 0xC901, 0x09C0, 0x0880, 0xC841,
            0xD801, 0x18C0, 0x1980, 0xD941, 0x1B00, 0xDBC1, 0xDA81, 0x1A40,
            0x1E00, 0xDEC1, 0xDF81, 0x1F40, 0xDD01, 0x1DC0, 0x1C80, 0xDC41,
            0x1400, 0xD4C1, 0xD581, 0x1540, 0xD701, 0x17C0, 0x1680, 0xD641,
            0xD201, 0x12C0, 0x1380, 0xD341, 0x1100, 0xD1C1, 0xD081, 0x1040,
            0xF001, 0x30C0, 0x3180, 0xF141, 0x3300, 0xF3C1, 0xF281, 0x3240,
            0x3600, 0xF6C1, 0xF781, 0x3740, 0xF501, 0x35C0, 0x3480, 0xF441,
            0x3C00, 0xFCC1, 0xFD81, 0x3D40, 0xFF01, 0x3FC0, 0x3E80, 0xFE41,
            0xFA01, 0x3AC0, 0x3B80, 0xFB41, 0x3900, 0xF9C1, 0xF881, 0x3840,
            0x2800, 0xE8C1, 0xE981, 0x2940, 0xEB01, 0x2BC0, 0x2A80, 0xEA41,
            0xEE01, 0x2EC0, 0x2F80, 0xEF41, 0x2D00, 0xEDC1, 0xEC81, 0x2C40,
            0xE401, 0x24C0, 0x2580, 0xE541, 0x2700, 0xE7C1, 0xE681, 0x2640,
            0x2200, 0xE2C1, 0xE381, 0x2340, 0xE101, 0x21C0, 0x2080, 0xE041,
            0xA001, 0x60C0, 0x6180, 0xA141, 0x6300, 0xA3C1, 0xA281, 0x6240,
            0x6600, 0xA6C1, 0xA781, 0x6740, 0xA501, 0x65C0, 0x6480, 0xA441,
            0x6C00, 0xACC1, 0xAD81, 0x6D40, 0xAF01, 0x6FC0, 0x6E80, 0xAE41,
            0xAA01, 0x6AC0, 0x6B80, 0xAB41, 0x6900, 0xA9C1, 0xA881, 0x6840,
            0x7800, 0xB8C1, 0xB981, 0x7940, 0xBB01, 0x7BC0, 0x7A80, 0xBA41,
            0xBE01, 0x7EC0, 0x7F80, 0xBF41, 0x7D00, 0xBDC1, 0xBC81, 0x7C40,
            0xB401, 0x74C0, 0x7580, 0xB541, 0x7700, 0xB7C1, 0xB681, 0x7640,
            0x7200, 0xB2C1, 0xB381, 0x7340, 0xB101, 0x71C0, 0x7080, 0xB041,
            0x5000, 0x90C1, 0x9181, 0x5140, 0x9301, 0x53C0, 0x5280, 0x9241,
            0x9601, 0x56C0, 0x5780, 0x9741, 0x5500, 0x95C1, 0x9481, 0x5440,
            0x9C01, 0x5CC0, 0x5D80, 0x9D41, 0x5F00, 0x9FC1, 0x9E81, 0x5E40,
            0x5A00, 0x9AC1, 0x9B81, 0x5B40, 0x9901, 0x59C0, 0x5880, 0x9841,
            0x8801, 0x48C0, 0x4980, 0x8941, 0x4B00, 0x8BC1, 0x8A81, 0x4A40,
            0x4E00, 0x8EC1, 0x8F81, 0x4F40, 0x8D01, 0x4DC0, 0x4C80, 0x8C41,
            0x4400, 0x84C1, 0x8581, 0x4540, 0x8701, 0x47C0, 0x4680, 0x8641,
            0x8201, 0x42C0, 0x4380, 0x8341, 0x4100, 0x81C1, 0x8081, 0x4040,
        };

        int crc = 0x0000;
        for (byte b : addCRC.getBytes()) {
            crc = (crc >>> 8) ^ table[(crc ^ b) & 0xff];
        }
        return String.format("%16s", Integer.toBinaryString(crc)).replace(' ', '0');
    }

    //The timer calls this method to constantly check for collisions between the circle and the layers.
    private void checkBounds(Shape block) {
        if(block == ackCircle) {
            if(!anLinesPres && (block.getBoundsInParent().intersects(l8.getBoundsInParent()) || block.getBoundsInParent().intersects(l9.getBoundsInParent()))) {
                analogLines();
            }
        }
        if(!digLinesPres && (block.getBoundsInParent().intersects(l1Line.getBoundsInParent()) || (block.getBoundsInParent().intersects(C2R2.getBoundsInParent())))){
            digitalLines();
        }
        if(!anLinesPres && block.getBoundsInParent().intersects(l0.getBoundsInParent()) && !anLinesPres) {
            analogLines();
        }
        if(!digLinesPres && block.getBoundsInParent().intersects(r2C2.getBoundsInParent()) || (block.getBoundsInParent().intersects(l2Line.getBoundsInParent()))) {
            digitalLines();
        }
        if(block.getBoundsInParent().intersects(inputBox.getBoundsInParent())){
            inputBox.setStyle("-fx-background-color: green; -fx-border-color: black");
        }

        if(block.getBoundsInParent().intersects(outputBox.getBoundsInParent())) {
            outputBox.setStyle("-fx-background-color: green; -fx-border-color: black");
            if(block == travelingCircle) {
                circle1Finished = true;
            } else if (block == circle2) {
                circle2Finished = true;
            }
        }
        //wait for both circles to be at destination, then send ARQ
        if(circle1Finished && circle2Finished && !arqChecked){
            outputBox.setStyle("-fx-background-color: green; -fx-border-color: black");
            outputBox.setText(inputText);
            arqChecked = true;
            outputBox.setText(inputText);
            ARQ32(BITDLLayer);
        }

        if(block == ackCircle) {
            if(block.getBoundsInParent().intersects(inputBox.getBoundsInParent())) {
                inputBox.setText("Message recieved!");
            }
        }
        boolean collisionDetected;

        for (Shape static_bloc : layers) {
            collisionDetected = block.getBoundsInParent().intersects(static_bloc.getBoundsInParent());
            if (collisionDetected) {
                static_bloc.setFill(Color.GREEN);
            }
        }
    }

    //Sends the Acknowledgement packet.
    public void sendACK() {

        //turns the red text into purple
        t0.setFill(Color.PURPLE);
        t1.setFill(Color.PURPLE);
        t2.setFill(Color.PURPLE);
        t3.setFill(Color.PURPLE);
        t4.setFill(Color.PURPLE);
        t5.setFill(Color.PURPLE);
        t6.setFill(Color.PURPLE);
        t7.setFill(Color.PURPLE);
        t8.setFill(Color.PURPLE);
        t9.setFill(Color.PURPLE);
        t10.setFill(Color.PURPLE);

            arqST = new SequentialTransition();

            if(ackCircle != null) {
                ackCircle.setOpacity(1);
            }
            ackCircle = new Circle();
            GridPane.setColumnIndex(ackCircle, 0);
            GridPane.setRowIndex(ackCircle, 1);
            GridPane.setColumnSpan(ackCircle, 1);
            GridPane.setRowSpan(ackCircle, 1);
            GridPane.setHalignment(ackCircle, HPos.LEFT);
            GridPane.setValignment(ackCircle, VPos.TOP);
            ackCircle.setRadius(travelingCircle.getRadius());
            ackCircle.setTranslateX(travelingCircle.getTranslateX());
            ackCircle.setTranslateY(travelingCircle.getTranslateY());
            ackCircle.setFill(Color.PURPLE);
            ackCircle.setStroke(Color.BLACK);
            mainGrid.getChildren().add(ackCircle);

            Path ackToR5 = new Path();
            ackToR5.getElements().add(new MoveTo(outputBox.getTranslateX() + outputBox.getWidth() - ackCircle.getRadius(), outputBox.getTranslateY() + outputBox.getHeight() - ackCircle.getRadius()));
            ackToR5.getElements().add(new VLineTo(PhL2.getHeight() + PhL2.getTranslateY() - ackCircle.getRadius()));
            ackToR5.getElements().add(new HLineTo(l2Line.getTranslateX() - ackCircle.getRadius()));
            ackToR5.getElements().add(new VLineTo(r2C2.getTranslateY() - ackCircle.getRadius()));
            ackToR5.getElements().add(new HLineTo(r2C2.getTranslateX() - ackCircle.getRadius()));
            arqST.getChildren().add(new PathTransition(Duration.seconds(4), ackToR5, ackCircle));

            List<Character> pathing = makeGraph(randomNums).getShortestPath('5', '0');
            Collections.reverse(pathing);
            arqST.getChildren().addAll(dijkstraPath(pathing, ackCircle, randomNums, true));

            Path r0ToInBox = new Path();
            r0ToInBox.getElements().add(new MoveTo(r0.getTranslateX() + 25, r0.getTranslateY() + 25));
            r0ToInBox.getElements().add(new HLineTo(l1Line.getTranslateX() - ackCircle.getRadius()));
            r0ToInBox.getElements().add(new VLineTo(PhL1.getTranslateY() + PhL1.getHeight() - ackCircle.getRadius()));
            r0ToInBox.getElements().add(new HLineTo(PhL1.getTranslateX() - ackCircle.getRadius()));
            r0ToInBox.getElements().add(new VLineTo(inputBox.getTranslateY()));

            arqST.getChildren().add(new PathTransition(Duration.seconds(4), r0ToInBox, ackCircle));
            arqST.setNode(ackCircle);
            arqST.play();

    }

    //Returns a list of paths for a node to move. This is the most effecient path from r0 to r5, or r5 to r0.
    //The int 25 added to the translates is for the center of the circle to be directly on the line.
    public ArrayList<PathTransition> dijkstraPath(List<Character> pathing, Shape block, int[] rand, boolean returning) {
        if(returning) {
            lastNode = '5';
        } else {
            lastNode = '0';
        }
        int duration = 0;
        double[] lastNodePos = new double[2];
        ArrayList<PathTransition> returnList = new ArrayList<>();
        //Building middle transitions
        for (Character c : pathing) {
            switch (c) {
                case '0':
                    switch (lastNode) {
                        case '1':
                            duration = rand[0];
                            lastNodePos[0] = r1.getTranslateX() + 25;
                            lastNodePos[1] = r1.getTranslateY() + 25;
                            break;
                        case '3':
                            duration = rand[1];
                            lastNodePos[0] = r3.getTranslateX() + 25;
                            lastNodePos[1] = r3.getTranslateY() + 25;
                            break;
                    }
                    returnList.add(new PathTransition(Duration.seconds(duration), new Path(new MoveTo(lastNodePos[0], lastNodePos[1]), new LineTo(r0.getTranslateX() + 25, r0.getTranslateY() + 25)), block));
                    lastNode = '0';
                    break;
                //going to 1 from... (only 3 possible sources)
                case '1':
                    switch (lastNode) {
                        case '0':
                            duration = rand[0];
                            lastNodePos[0] = r0.getTranslateX() + 25;
                            lastNodePos[1] = r0.getTranslateY() + 25;
                            break;
                        case '2':
                            duration = rand[2];
                            lastNodePos[0] = r2.getTranslateX() + 25;
                            lastNodePos[1] = r2.getTranslateY() + 25;
                            break;
                        case '4':
                            duration = rand[3];
                            lastNodePos[0] = r4.getTranslateX() + 25;
                            lastNodePos[1] = r4.getTranslateY() + 25;
                            break;
                    }
                    //Add to list: a new pathtransition of time duration, containing a new path to the next node in pathing found by dijkstras.
                    returnList.add(new PathTransition(Duration.seconds(duration), new Path(new MoveTo(lastNodePos[0], lastNodePos[1]), new LineTo(r1.getTranslateX() + 25, r1.getTranslateY() + 25)), block));
                    lastNode = '1';
                    break;

                //going to 2 from...
                case '2':
                    switch (lastNode) {
                        case '1':
                            duration = rand[2];
                            lastNodePos[0] = r1.getTranslateX() + 25;
                            lastNodePos[1] = r1.getTranslateY() + 25;
                            break;
                        case '3':
                            duration = rand[4];
                            lastNodePos[0] = r3.getTranslateX() + 25;
                            lastNodePos[1] = r3.getTranslateY() + 25;
                            break;
                        case '7':
                            duration = rand[5];
                            lastNodePos[0] = r7.getTranslateX() + 25;
                            lastNodePos[1] = r7.getTranslateY() + 25;
                            break;
                    }
                    returnList.add(new PathTransition(Duration.seconds(duration), new Path(new MoveTo(lastNodePos[0], lastNodePos[1]), new LineTo(r2.getTranslateX() + 25, r2.getTranslateY() + 25)), block));
                    lastNode = '2';
                    break;
                //going to 3 from...
                case '3':
                    switch (lastNode) {
                        case '0':
                            duration = rand[1];
                            lastNodePos[0] = r0.getTranslateX() + 25;
                            lastNodePos[1] = r0.getTranslateY() + 25;
                            break;
                        case '2':
                            duration = rand[4];
                            lastNodePos[0] = r2.getTranslateX() + 25;
                            lastNodePos[1] = r2.getTranslateY() + 25;
                            break;
                        case '6':
                            duration = rand[6];
                            lastNodePos[0] = r6.getTranslateX() + 25;
                            lastNodePos[1] = r6.getTranslateY() + 25;
                            break;
                    }
                    returnList.add(new PathTransition(Duration.seconds(duration), new Path(new MoveTo(lastNodePos[0], lastNodePos[1]), new LineTo(r3.getTranslateX() + 25, r3.getTranslateY() + 25)), block));
                    lastNode = '3';
                    break;

                case '4':
                    switch (lastNode) {
                        case '1':
                            duration = rand[3];
                            lastNodePos[0] = r1.getTranslateX() + 25;
                            lastNodePos[1] = r1.getTranslateY() + 25;
                            break;
                        case '5':
                            duration = rand[7];
                            lastNodePos[0] = r5.getTranslateX() + 25;
                            lastNodePos[1] = r5.getTranslateY() + 25;
                            break;
                        case '7':
                            duration = rand[8];
                            lastNodePos[0] = r7.getTranslateX() + 25;
                            lastNodePos[1] = r7.getTranslateY() + 25;
                            break;
                    }
                    returnList.add(new PathTransition(Duration.seconds(duration), new Path(new MoveTo(lastNodePos[0], lastNodePos[1]), new LineTo(r4.getTranslateX() + 25, r4.getTranslateY() + 25)), block));
                    lastNode = '4';
                    break;

                case '5':
                    switch (lastNode) {
                        case '4':
                            duration = rand[7];
                            lastNodePos[0] = r4.getTranslateX() + 25;
                            lastNodePos[1] = r4.getTranslateY() + 25;
                            break;
                        case '6':
                            duration = rand[9];
                            lastNodePos[0] = r6.getTranslateX() + 25;
                            lastNodePos[1] = r6.getTranslateY() + 25;
                            break;
                    }
                    returnList.add(new PathTransition(Duration.seconds(duration), new Path(new MoveTo(lastNodePos[0], lastNodePos[1]), new LineTo(r5.getTranslateX() + 25, r5.getTranslateY() + 25)), block));
                    lastNode = '5';
                    break;

                case '6':
                    switch (lastNode) {
                        case '3':
                            duration = rand[6];
                            lastNodePos[0] = r3.getTranslateX() + 25;
                            lastNodePos[1] = r3.getTranslateY() + 25;
                            break;
                        case '5':
                            duration = rand[9];
                            lastNodePos[0] = r5.getTranslateX() + 25;
                            lastNodePos[1] = r5.getTranslateY() + 25;
                            break;
                        case '7':
                            duration = rand[10];
                            lastNodePos[0] = r7.getTranslateX() + 25;
                            lastNodePos[1] = r7.getTranslateY() + 25;
                            break;
                    }
                    returnList.add(new PathTransition(Duration.seconds(duration), new Path(new MoveTo(lastNodePos[0], lastNodePos[1]), new LineTo(r6.getTranslateX() + 25, r6.getTranslateY() + 25)), block));
                    lastNode = '6';
                    break;

                case '7':
                    switch (lastNode) {
                        case '2':
                            duration = rand[5];
                            lastNodePos[0] = r2.getTranslateX() + 25;
                            lastNodePos[1] = r2.getTranslateY() + 25;
                            break;
                        case '4':
                            duration = rand[8];
                            lastNodePos[0] = r4.getTranslateX() + 25;
                            lastNodePos[1] = r4.getTranslateY() + 25;
                            break;
                        case '6':
                            duration = rand[3];
                            lastNodePos[0] = r6.getTranslateX() + 25;
                            lastNodePos[1] = r6.getTranslateY() + 25;
                            break;
                    }
                    returnList.add(new PathTransition(Duration.seconds(duration), new Path(new MoveTo(lastNodePos[0], lastNodePos[1]), new LineTo(r7.getTranslateX() + 25, r7.getTranslateY() + 25)), block));
                    lastNode = '7';
                    break;
            }
        }
        return returnList;
    }

    //Used for dijkstra's algorithm.
    private Graph makeGraph(int[] rand) {
        Graph retGraph = new Graph();
        //read as: node '0' (which is r0) goes to: r1 with weight randomnums[0], and r3 with weight randomnums[1].
        retGraph.addVertex('0', Arrays.asList(new Vertex('1', rand[0]), new Vertex('3', rand[1])));
        retGraph.addVertex('1', Arrays.asList(new Vertex('0', rand[0]), new Vertex('2', rand[2]), new Vertex('4', rand[3])));
        retGraph.addVertex('2', Arrays.asList(new Vertex('1', rand[2]), new Vertex('3', rand[4]), new Vertex('7', rand[5])));
        retGraph.addVertex('3', Arrays.asList(new Vertex('0', rand[1]), new Vertex('2', rand[4]), new Vertex('6', rand[6])));
        retGraph.addVertex('4', Arrays.asList(new Vertex('5', rand[7]), new Vertex('7', rand[8]), new Vertex('1', rand[3])));
        retGraph.addVertex('5', Arrays.asList(new Vertex('4', rand[7]), new Vertex('6', rand[9])));
        retGraph.addVertex('6', Arrays.asList(new Vertex('3', rand[6]), new Vertex('5', rand[9]), new Vertex('7', rand[10])));
        retGraph.addVertex('7', Arrays.asList(new Vertex('6', rand[10]), new Vertex('4', rand[8]), new Vertex('2', rand[5])));

        return retGraph;
    }

    //Refreshes the scene
    public void refresh() {
        ancP.getChildren().clear();
        txtArea.setText("");
        if (sT != null) {
            if (sT.getStatus() == Animation.Status.RUNNING) {
                sT.stop();
                sT = null;
            }
        }
        if (sT2 != null) {
            if (sT2.getStatus() == Animation.Status.RUNNING) {
                sT2.stop();
                sT2 = null;
            }
        }
        travelingCircle.setTranslateX(85);
        travelingCircle.setTranslateY(50);
        circle2.setTranslateX(85);
        circle2.setTranslateY(40);

        inputBox.setStyle(" -fx-background-color: CC1212; -fx-border-color: black");
        outputBox.setStyle(" -fx-background-color: CC1212; -fx-border-color: black");
        inputBox.setText("");

        Random rand = new Random(System.nanoTime());
        for (int i = 0; i < 11; ++i) {
            randomNums[i] = rand.nextInt(7) + 1;
            randomNums2[i] = rand.nextInt(7) + 1;
        }

        if (ackCircle != null) {
            ackCircle.setOpacity(0);
            ackCircle.setTranslateX(outputBox.getTranslateX() + outputBox.getWidth() - ackCircle.getRadius());
            ackCircle.setTranslateY(outputBox.getTranslateY() + outputBox.getHeight() - ackCircle.getRadius());
        }
        if(timer != null) {
            timer.stop();
        }

        this.initialize(url, rb);
    }

    private void setRouterInfo() {
        String r0IPFormat = "";
        String r5IPFormat = "";

        for (int i = 0; i < Integer.toHexString(r0IP).length(); i += 2) {
            r0IPFormat += Integer.valueOf(Integer.toHexString(r0IP).substring(i, i + 2), 16) + ".";
        }

        for (int i = 0; i < Integer.toHexString(r0IP).length(); i += 2) {
            r5IPFormat += Integer.valueOf(Integer.toHexString(r5IP).substring(i, i + 2), 16) + ".";
        }
        routerInfo.setText("Sender IP: " + r0IPFormat + "\nReciever IP: " + r5IPFormat +"\nSender MAC: " + Integer.toHexString(r0MAC).toUpperCase() + "\nReciever MAC: " + Integer.toHexString(r5MAC).toUpperCase());
    }

}
