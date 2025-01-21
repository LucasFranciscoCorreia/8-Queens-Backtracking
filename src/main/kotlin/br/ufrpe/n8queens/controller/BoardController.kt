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
    companion object {
        lateinit var stage: Stage;

        lateinit var queenImage: Image;

        private val queens: MutableList<ImageView> = MutableList(8){ ImageView()}
        private val root: Row = Row()
        private lateinit var slider: MFXSlider;

        private var frame by Delegates.notNull<Double>();

        private var isSearching = false;

    }

    @FXML
    private lateinit var customSlider: MFXSlider;

    @FXML
    private lateinit var searchButton: MFXButton

    @FXML
    private lateinit var pane: Pane


    @FXML
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val stream = javaClass.classLoader.getResourceAsStream("br/ufrpe/n8queens/assets/Queen.png")
        queenImage = Image(stream)
        customSlider.ranges1.add(NumberRange.of(customSlider.min, customSlider.max))
        slider = customSlider;
        frame = customSlider.value;
    }

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
            //customSlider.value = frame
        }
    }

    private var searchThread: Thread? = null

    @FXML
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
