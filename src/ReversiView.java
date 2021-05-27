import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;

/**
 * Class for the game view
 */
public class ReversiView extends Application implements Observer {
    /**
     * The ReversiController reversiController
     */
    private ReversiController reversiController;
    /**
     * the GraphicsContext graphicsContext
     */
    private GraphicsContext graphicsContext;
    /**
     * Label
     */
    private Label label;

    /**
     * Constructor of ReversiView
     */
    public ReversiView() {
        reversiController = new ReversiController();
        reversiController.reversiModel.addObserver(this);
    }

    /**
     * @param stage the input stage
     */
    @Override
    public void start(Stage stage) {

        stage.setTitle("Reversi");
        BorderPane borderPane = new BorderPane();

        MenuBar bar = new MenuBar();
        Menu menu = new Menu("File");
        Label label = new Label("New Game");
        MenuItem item = new CustomMenuItem(label);
        menu.getItems().add(item);
        bar.getMenus().add(menu);
        this.label = new Label(labelForScore());
        Canvas canvas = new Canvas(ReversiConstants.SIZE_GUI, ReversiConstants.SIZE_GUI);
        graphicsContext = canvas.getGraphicsContext2D();
        newGame(canvas, stage, label);
        gameRunner(canvas, stage, label);
        borderPane.setTop(bar);
        borderPane.setCenter(canvas);
        borderPane.setBottom(this.label);

        Group group = new Group();
        group.getChildren().add(borderPane);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * New game
     *
     * @param board the input board
     * @param stage the input stage
     * @param label the input label
     */
    private void newGame(Canvas board, Stage stage, Label label) {

        graphicsContext.setFill(Color.GREEN);
        graphicsContext.fillRect(0, 0, ReversiConstants.SIZE_GUI, ReversiConstants.SIZE_GUI);
        graphicsContext.setFill(Color.BLACK);
        int y = 9;
        while (y < ReversiConstants.SIZE_GUI) {
            graphicsContext.setLineWidth(2);
            graphicsContext.strokeLine(9, y, 377, y);
            y += 46;
        }
        int x = 9;
        while (x < ReversiConstants.SIZE_GUI) {
            graphicsContext.strokeLine(x, 9, x, 377);
            x += 46;
        }
        graphicsContext.setFill(Color.TRANSPARENT);
        {
            int i = 0;
            while (i < ReversiConstants.BOARD_SIZE) {
                for (int j = 0; j < ReversiConstants.BOARD_SIZE; j++) {
                    graphicsContext.fillOval(getPixels(i), getPixels(j), 40, 40);
                }
                i++;
            }
        }
        ReversiBoard reversiBoard = reversiController.getReversiModel().getReversiBoard();

        int i = 0;
        while (i < ReversiConstants.BOARD_SIZE) {
            for (int j = 0; j < ReversiConstants.BOARD_SIZE; j++) {
                if (reversiBoard.getBoard()[i][j] == ReversiConstants.EMPTY_VALUE)
                    graphicsContext.setFill(Color.TRANSPARENT);
                else if (reversiBoard.getBoard()[i][j] == ReversiConstants.W_VALUE)
                    graphicsContext.setFill(Color.WHITE);
                else if (reversiBoard.getBoard()[i][j] == ReversiConstants.B_VALUE)
                    graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillOval(this.getPixels(i), this.getPixels(j), 40, 40);
            }
            i++;
        }
        this.label.setText(labelForScore());
    }

    /**
     * getter pixel
     *
     * @param location the location
     * @return the location
     */
    private int getPixels(int location) {
        return 12 + 46 * location;
    }

    /**
     * Getter for the location
     *
     * @param pixel the input pixel
     * @return the location
     */
    private int getLocation(double pixel) {
        return (int) (pixel - 9) / 46;
    }

    /**
     * Runner game
     *
     * @param board board
     * @param stage stage
     * @param label label
     */
    private void gameRunner(Canvas board, Stage stage, Label label) {

        board.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouse) {
                if (reversiController.checkWinner()) {
                    gameOver(board, stage, label);
                    return;
                }
                String role = ReversiConstants.HUMAN_FLAG;
                boolean checkMove = false;
                int rowLocation = 0;
                int colLocation = 0;

                if (reversiController.checkMove(ReversiConstants.HUMAN_FLAG)) {
                    rowLocation = getLocation(mouse.getX());
                    colLocation = getLocation(mouse.getY());
                    if (rowLocation >= 0 && rowLocation < 8 && colLocation >= 0 && colLocation < 8) {

                        if (reversiController.checkMove(rowLocation, colLocation, role, false)) {
                            reversiController.checkMove(rowLocation, colLocation, role, true);
                            reversiController.moveAction(rowLocation, colLocation, role);
                            role = ReversiConstants.COMPUTER_FLAG;
                        }
                    }
                }
                ReversiView.this.label.setText(labelForScore());
                if (!reversiController.checkMove(ReversiConstants.HUMAN_FLAG) && !reversiController.checkMove(ReversiConstants.COMPUTER_FLAG)) {
                    gameOver(board, stage, label);
                    return;
                }

                if (role.equals(ReversiConstants.COMPUTER_FLAG) && reversiController.checkMove(ReversiConstants.COMPUTER_FLAG)) {
                    while (!checkMove) {
                        rowLocation = (int) (Math.random() * ReversiConstants.BOARD_SIZE);
                        colLocation = (int) (Math.random() * ReversiConstants.BOARD_SIZE);
                        checkMove = reversiController.checkMove(rowLocation, colLocation, role, false);
                    }
                    reversiController.checkMove(rowLocation, colLocation, role, true);
                    reversiController.moveAction(rowLocation, colLocation, role);
                }
                ReversiView.this.label.setText(labelForScore());
                if (reversiController.checkWinner()) {
                    gameOver(board, stage, label);
                }
            }
        });


        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent wc) {
                try {
                    ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(ReversiConstants.FILE_NAME));
                    outputStream.writeObject(reversiController.getReversiModel().getReversiBoard());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    File file = new File(ReversiConstants.FILE_NAME);
                    file.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                newGame();
                newGame(board, stage, label);
                update(reversiController.reversiModel, reversiController.reversiModel.getReversiBoard());
            }
        });
    }

    /**
     * reset game
     */
    private void newGame() {
        reversiController.reversiModel = new ReversiModel();
        reversiController.reversiModel.addObserver(this);
        reversiController.clear();
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param model   the observable object.
     * @param oBoard an argument passed to the <code>notifyObservers</code>
     *            method.
     */
    public void update(Observable model, Object oBoard) {
        ReversiBoard rb = (ReversiBoard) oBoard;
        for (int i = 0; i < ReversiConstants.BOARD_SIZE; i++) {
            for (int j = 0; j < ReversiConstants.BOARD_SIZE; j++) {
                if (rb.getBoard()[i][j] == ReversiConstants.EMPTY_VALUE)
                    graphicsContext.setFill(Color.TRANSPARENT);
                else if (rb.getBoard()[i][j] == ReversiConstants.W_VALUE)
                    graphicsContext.setFill(Color.WHITE);
                else if (rb.getBoard()[i][j] == ReversiConstants.B_VALUE)
                    graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillOval(this.getPixels(i), this.getPixels(j), 40, 40);
            }
        }
        label.setText(labelForScore());
    }

    /**
     * Label score
     *
     * @return score
     */
    private String labelForScore() {
        return "White: " + reversiController.getHumanScore() + " - Black: " + reversiController.getComputerScore();
    }

    /**
     * Check game over
     *
     * @param board board
     * @param stage stage
     * @param label label
     */
    private void gameOver(Canvas board, Stage stage, Label label) {
        if (reversiController.getComputerScore() > reversiController.getHumanScore())
            new Alert(Alert.AlertType.INFORMATION, "You lose!").showAndWait();
        else if (reversiController.getHumanScore() > reversiController.getComputerScore())
            new Alert(Alert.AlertType.INFORMATION, "You win!").showAndWait();
        else
            new Alert(Alert.AlertType.INFORMATION, "It's a tie!").showAndWait();
        try {
            File file = new File(ReversiConstants.FILE_NAME);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        newGame();
        newGame(board, stage, label);
        update(reversiController.reversiModel, reversiController.reversiModel.getReversiBoard());
    }
}