package br.ufrpe.n8queens

import br.ufrpe.n8queens.controller.BoardController
import br.ufrpe.n8queens.controller.BoardController.Companion.queenImage
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage

class N8QueensUI : Application() {
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
        BoardController.stage = primaryStage!!
    }
}

fun main() {
    Application.launch(N8QueensUI::class.java)
}