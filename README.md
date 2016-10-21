[![Build Status](https://travis-ci.org/oppes/space.svg?branch=master)](https://travis-ci.org/oppes/space)

# space
Welcome to space. A one-dimensional space-adventure game.

## Game Features
- **Back to the roots.** One-dimensional text-based game world
- **Single key.** Full game control through only one key, the space bar
- **Extendable.** Open API for individual game world extensions
- **Online & Multiplayer.** Show off your skills in the space-online gameworld and challenge your friends

## Game Description
All game features and the overall game description are subject to change.
- The Hero is the main character in this game and is controlled by the player.
- The Hero needs to complete the game world and defeat all enemies he encounters.
- The Hero can collect and use weapons, armor and other special items, provided by the game world.
- The more enemies the Hero defeats and the further he gets in the game world, the more points he will earn.
- The Hero can die. He then loses points and respawns at an earlier position in the game world.
- Points will be used to calculate ranking with other players.
- Points (or some kind of collectable money) can be used to buy items in the store (weapons, armor, money-creator-machines, etc.)

## Game Controls
In the game, the Hero moves without user interaction and runs along the path in the game world.
As described in Game Features, the whole interaction with the game world is done through the space bar:

| Space Pattern  | Interaction |
| ------------- | ------------- |
| Single-Space  | Perform the main action. Depends on available weapons or add-ons and on the current game world position. The very basic action will be a single punch.  |
| Double-Space  | Turns around, Hero runs in the opposite direction. |

## Game World Example
An example game world might look something like this:
`H...................W.W........M.........M............R..M...................T.....................M.`

| Symbol  | Description |
| ------------- | ------------- |
| H  | The Hero. The main character in this game. |
| .  | An empty path, ready for the Hero to run along. |
| [letter]  | An object in the game world to interact with. Might be enemies, weapons or other collectable items, rocks, traps, doors, etc. |

## Technical Features
- Space is an online-game.
- The space-online game server hosts the gameworld.
- To start the game, the user needs a space-client to connect to the game server and join the gameworld.
- The space-online gameworld is shared among all players (multiplayer).
- Players must create a space-online account (profile) to play the game.
- The profile consists of the player's login data (username, password), the ingame character(s), gameworld coordinates, points, items, etc. (game state) and is stored on the server.

## Development Guidelines
Development guidelines are permanent subject to change.
- Development is split into three areas: _space-server_ (com.space.server), _space-client_ (com.space.client) and a common codebase for communication and protocols, _space-common_ (com.space.common).
- These code areas should be kept separately.
- space-server and space-common are developed using Java SE 8.
- At least one space-client will be developed in the same programming language. (Other clients may user whatever they desire)
- The codebase should be kept clean, the code should follow common coding conventions and the test coverage should be kept at a high level.
- The codebase will be built automatically at least on a daily basis plus directly after every code change.
- Milestone releases will have a codename and release notes.

## Super short gradle intro
Building
- gradlew clean build

Running
- gradlew space-server:run
- gradlew space-client:run

Create Eclipse files
- gradlew eclipse
