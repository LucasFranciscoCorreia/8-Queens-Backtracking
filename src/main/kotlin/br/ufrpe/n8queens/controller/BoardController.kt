package br.ufrpe.n8queens.controller

import br.ufrpe.n8queens.model.Row
import io.github.palexdev.materialfx.beans.NumberRange
import io.github.palexdev.materialfx.controls.MFXButton
import io.github.palexdev.materialfx.controls.MFXSlider
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.stage.Stage
import java.net.URL
import java.util.*
import kotlin.properties.Delegates

class BoardController : Initializable {
    /**
     * Companion object for the BoardController class.
     * 
     * This object contains the following properties:
     * 
     * - `stage`: A lateinit property of type `Stage` used to manage the primary stage of the application.
     * - `queenImage`: A lateinit property of type `Image` representing the image of a queen piece.
     * - `queens`: A mutable list of `ImageView` objects initialized with 8 `ImageView` instances, representing the queen pieces on the board.
     * - `root`: An instance of `Row` used as the root layout for the board.
     * - `slider`: A lateinit property of type `MFXSlider` used for controlling some aspect of the board (e.g., speed of animation).
     * - `frame`: A property of type `Double` managed by the `Delegates.notNull` delegate, representing the current frame or state.
     * - `isSearching`: A boolean property indicating whether the search algorithm is currently running.
     */
    companion object {
        lateinit var stage: Stage;

        lateinit var queenImage: Image;

        private val queens: MutableList<ImageView> = MutableList(8){ ImageView()}
        private val root: Row = Row()
        private lateinit var slider: MFXSlider;

        private var frame by Delegates.notNull<Double>();

        private var isSearching = false;

    }

    /**
     * FXML annotation to inject the custom slider component from the FXML file.
     */
    @FXML
    private lateinit var customSlider: MFXSlider

    /**
     * FXML annotation to inject the search button component from the FXML file.
     */
    @FXML
    private lateinit var searchButton: MFXButton

    /**
     * FXML annotation to inject the pane component from the FXML file.
     */
    @FXML
    private lateinit var pane: Pane


    /**
     * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     *
     * This method performs the following actions:
     * - Loads the image of the queen from the assets folder.
     * - Sets the queen image to the `queenImage` property.
     * - Adds a number range to the custom slider's ranges.
     * - Initializes the `slider` property with the custom slider.
     * - Sets the initial frame value from the custom slider's value.
     */
    @FXML
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val stream = javaClass.classLoader.getResourceAsStream("br/ufrpe/n8queens/assets/Queen.png")
        queenImage = Image(stream)
        customSlider.ranges1.add(NumberRange.of(customSlider.min, customSlider.max))
        slider = customSlider;
        frame = customSlider.value;
    }

    /**
     * Updates the graphical representation of the board up to the given depth.
     * Clears the current pane and queens list, then iterates through the nodes up to the specified depth,
     * placing a queen image at the appropriate position for each node.
     *
     * @param depth The depth up to which the board should be updated.
     */
    fun updateFrame(depth: Int){
        Platform.runLater {
            pane.children.clear()
            queens.clear()
            var node: Row? = root
            for (i in 0 until depth) {
                val queen = ImageView(queenImage).apply {
                    fitHeight = 80.0;
                    fitWidth = 80.0;
                    layoutX = (node!!.j - 1) * 80.0;
                    layoutY = i * 80.0
                }
                queens.add(queen)
                node = node?.next
            }
            pane.children.addAll(queens);
        }
    }

    /**
     * A thread used to perform the search operation for solving the 8-queens problem.
     * This thread is nullable and can be started, stopped, or checked for its status.
     */
    private var searchThread: Thread? = null

    @FXML
    /**
     * Initiates or stops the search for the N-Queens solution.
     * 
     * If the search is not currently running, it starts a new search in a separate thread.
     *
     * @warning Because of the nature of the javafx application, the UI freezes when you enter a loop,
     * making it impossible to show the queens as a solution is being searched. To solve this 
     * problem, we use a separate thread to perform the search and update the frame.
     * 
     * The search depth is incremented until it reaches 8 or the search is stopped.
     * The search thread updates the UI with the current depth and sleeps for a duration specified by `customSlider`.
     * If the search is interrupted, it stops and updates the UI accordingly.
     * 
     * If the search is currently running, it stops the search and updates the UI.
     * 
     * The UI elements `searchButton` and `customSlider` are updated to reflect the current state of the search.
     */
    fun SearchSolution() {
        if (!isSearching) {
            isSearching = true
            searchThread = Thread {
                var depth = 1
                while (depth < 8 && isSearching) {
                    depth = N8Queens.search(root)
                    updateFrame(depth)
                    try {
                        Thread.sleep(customSlider.value.toLong())
                    }catch (e: InterruptedException){
                        return@Thread;
                    }
                }
                updateFrame(depth)
                Platform.runLater {
                    searchButton.text = "Search"
                    searchButton.style = "-fx-background-color: #0d6efd; -fx-text-fill: white;"
                    searchThread = null;
                    isSearching = false
                }
            }
            searchThread?.isDaemon = true
            searchThread?.start()
            searchButton.text = "Stop"
            searchButton.style = "-fx-background-color: #dc3545; -fx-text-fill: white;"
        } else {
            isSearching = false
            searchThread?.interrupt()
            searchThread = null;
            searchButton.text = "Continue"
            searchButton.style = "-fx-background-color: #ffc107;"
        }
    }
}
