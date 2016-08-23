# space
Welcome to space. A one-dimensional space-adventure game.

## Game Features
- **Back to the roots.** One-dimensional text-based game world
- **Single key.** Full game control through only one key, the space bar
- **Extendable.** Open API for individual game world extensions

## Game Description
All game features and the overall game description are due to change.
- The Hero is the main character in this game and is controlled by the player.
- The Hero needs to complete the game world and defeat all enemies he encounters.
- The Hero can collect and use weapons, armor and other special items, provided by the game world.
- The more enemies the Hero defeats and the further he gets in the game world, the more points he will earn.
- The Hero can die. He then loses points and respawns at an earlier position in the game world.
- Points will be used to calculate ranking with other players.
- Points (or some kind of collectable money) could be used to buy items in the store (weapons, armor, money-creator-machines, etc.)

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
