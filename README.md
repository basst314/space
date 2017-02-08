# space [![Build Status](https://travis-ci.org/basst314/space.svg?branch=master)](https://travis-ci.org/basst314/space) [![volkswagen status](https://auchenberg.github.io/volkswagen/volkswargen_ci.svg?v=1)](https://github.com/auchenberg/volkswagen)
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
- Items are stored in the Inventory
- Items can be activated through item-slots 1-n (see 'additional controls')
- The more enemies the Hero defeats and the further he gets in the game world, the more points he will earn.
- The Hero can die. He then loses points and respawns at an earlier position in the game world.
- Points will be used to calculate ranking with other players.
- Points (or some kind of collectable money) can be used to buy items in the store (weapons, armor, money-creator-machines, etc.)

## Game Controls
In the game, the Hero moves without user interaction and runs along the path in the game world.
As described in Game Features, the whole interaction with the game world is done through the space bar:

| Space Pattern  | Interaction |
| ------------- | ------------- |
| Single-Space  | Perform the main action. Depends on available/activated weapons or add-ons and on the current game world position. The very basic action will be a single punch.  |
| Double-Space  | Turns around, Hero runs in the opposite direction. |
| Tripple-Space  | Hero uses next item in his inventory. |

### Additional Controls:
| Key  | Function |
| ------------- | ------------- |
| I  | Show inventory  |
| M  | Show Map  |


## Game World Example
An example game world might look something like this:
`H/..................W.W........M.........M............R..M...................T.....................M.`

| Symbol  | Description |
| ------------- | ------------- |
| H  | The Hero. The main character in this game. |
| /  | The Hero has a weapon equiped and is ready for combat|
| .  | An empty path, ready for the Hero to run along. |
| [any letter]  | An object in the game world to interact with. Might be enemies, weapons or other collectable items, rocks, traps, doors, etc. |

## Technical Features
- Space is an online-game.
- The space-online game server hosts the gameworld.
- To start the game, the user needs a space-client to connect to the game server and join a gameworld.
- A space-online gameworld may be shared among multiple players (multiplayer).
- Players must create a space-online account (profile) to play the game.
- The profile consists of the player's login data (username, password), the ingame character(s), active gameworlds, gameworld coordinates, points, items, etc. (game state) and is stored on the server.

## Heroku Integration
| Branch  | Link |
| ----------- | ----------- |
| *Client-UI* |      [Client](http://the-space-game-test.herokuapp.com/) / [Server-Api](http://the-space-game-test.herokuapp.com/api/world) |
| *Master* |   [Client](http://the-space-game-staging.herokuapp.com/) / [Server-Api](http://the-space-game-staging.herokuapp.com/api/world) |
| *Stable* |    [Client](http://the-space-game.herokuapp.com/) / [Server-Api](http://the-space-game.herokuapp.com/api/world) |

## Milestones

### #1 Siam Cat
- Find following Milestone Name
- A game-world can be generated and displayed (Server-Client-Roundtrip over Network)
- World is persistent for one game session
- Server and Client are communicating via WebSockets
- Basic Events can be sent between client and server
- Hero can move, attack a monster and pick up an item

### #2 Angora Cat
- Find following milestone name
- Monster can passively attack hero (hero can die)
- Hero and monsters have health displayed in game world
- World segments can be connected by doors
- World updates automatically without sending step events
- A world can contain many heros (controlled by different players)
- Events from the websocket client can be send by space bar
