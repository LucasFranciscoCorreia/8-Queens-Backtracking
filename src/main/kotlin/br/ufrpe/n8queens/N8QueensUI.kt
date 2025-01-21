package br.ufrpe.n8queens

import br.ufrpe.n8queens.controller.BoardController
import br.ufrpe.n8queens.controller.BoardController.Companion.queenImage
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage

class N8QueensUI : Application() {
    /**
     * Starts the JavaFX application by setting up the primary stage.
     *
     * @param primaryStage The primary stage for this application, onto which
     * the application scene can be set.
     *
     * The method performs the following steps:
     * 1. Loads the FXML layout from the specified resource path.
     * 2. Creates a new scene with the loaded layout and sets its dimensions.
     * 3. Sets the title of the primary stage to "8 Queens".
     * 4. Assigns the created scene to the primary stage.
     * 5. Disables the ability to resize the primary stage.
     * 6. Centers the primary stage on the screen.
     * 7. Displays the primary stage.
     * 8. Adds an icon to the primary stage.
     * 9. Sets the static stage reference in the BoardController to the primary stage.
     */
    override fun start(primaryStage: Stage) {
        val url = javaClass.classLoader.getResource("br/ufrpe/n8queens/fxml/board.fxml")
        val pane = FXMLLoader.load<AnchorPane>(url)
        val scene = Scene(pane, 640.0, 712.0)
        primaryStage.title = "8 Queens"
        primaryStage.scene = scene
        primaryStage.isResizable = false
        primaryStage.centerOnScreen()
        primaryStage.show()
        primaryStage.icons.add(queenImage)
        BoardController.stage = primaryStage
    }
}

fun main() {
    Application.launch(N8QueensUI::class.java)
}