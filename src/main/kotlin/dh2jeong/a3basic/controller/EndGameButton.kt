package dh2jeong.a3basic.controller

import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.control.Button

class EndGameButton(private val startX: Double, private val startY: Double) : Button("Exit Game") {
    init {
        prefWidth = 150.0
        layoutX = startX
        layoutY = startY
        onAction = EventHandler {
            Platform.exit()
        }
        isFocusTraversable = false
    }
}