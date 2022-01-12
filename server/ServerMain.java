public class ServerMain {
    /*
    Operation: Elfen::login(username: String, password: String)
    Scope: User;
    New: newUser: User
    Messages: User::{availableGames, invalidLogin_e}
    Post: If the login is successful, sends the user all available games. Otherwise, sends the user a “invalidLogin_e” message to inform them that the login has failed.
     */

    /*
    Operation: Elfen::createNewGame(numberOfPlayers: int, numGameRounds: int, mode: Mode, witchEnabled: boolean, destinationTownEnabled: boolean)
    Scope: Game;
    New: newGame: Game;
    Messages: User:: {gameCreationFailed_e; newGameState}
    Post: Sends a new game state to the user upon success. in case the game is not successfully created, the operation outputs an “gameCreationFailed_e” message to the user.

     */

    /*
    Operation: Elfen::availableGames(availableGames: Set{Game})
    Scope: User; Game;
    Messages: User::{availableGames, invalidLogin_e}
    Post: If the login is successful, sends the user all available games. Otherwise, sends the user a “invalidLogin_e” message to inform them that the login has failed.
     */

    /*
    Operation: Elfen::gameCreationConfirmed(game: Game)
    Scope: Game; User;
    New: newGame: Game;
    Messages: User:: {gameCreationFailed_e; gameCreationConfirmed}
    Post: Sends a game creation confirmed message to the user upon success. In case the game is not successfully created, the operation outputs an “gameCreationFailed_e” message to the user.
     */

}
