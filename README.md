# Settlers of Catan
The classic board game Settlers of Catan made in Java

## Classes 
**SettlersofCatan**: Main class, runs games logic and player turns

**Scenes**: Collection of smaller scenes that will be used throughout the game e.g. Title Screen

**OfflineGameScene**: Scene that displays the board and player tiles

**Bank**: The Bank class holds all the Resource Cards in the game

**City**: Upgraded version of the settlement class

**DevelopmentCard**: All the development card types

**Edge**: An edge on a tile, should be 6 of them

**EdgeLink**: Used whenever two tiles share an edge

**Line**: Used to split up the game board into 5 horizontal lines (3, 4, 5, 4, 3)

**Player**: Holds all player data including resources

**ResourceCard**: The deck of all resource cards each player holds

**Road**: Holds the owner of each road and location on screen

**Robber**: Allows player to move robber and steal resources from other players

**Settlement**: Holds the owner of each settlement and location on screen

**Tile**: Tiles on the board, has a type(resource), a number(for the rollDice method)

**Vertex**: A vertex on a tile, contains a vertexID, and a tileID 

**VertexLink**: An arraylist of Vertexes, used whenever 2 tiles share a Vertex

