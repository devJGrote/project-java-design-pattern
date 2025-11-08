package de.inosofttech.example.viergewinnt;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Web-Server für das "4 gewinnt" Spiel.
 */
public class ConnectFourWebServer {
    private static final int PORT = 8081;
    private static ConnectFourGame game;
    private static final Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        // Initialisiere Spiel mit 6 Zeilen und 7 Spalten (Standard "4 gewinnt")
        game = new ConnectFourGame(6, 7);

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // API-Endpunkte
        server.createContext("/api/state", new StateHandler());
        server.createContext("/api/move", new MoveHandler());
        server.createContext("/api/reset", new ResetHandler());
        server.createContext("/api/config", new ConfigHandler());
        server.createContext("/api/aimove", new AIMoveHandler());
        
        // Statische Dateien
        server.createContext("/", new StaticFileHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("4 Gewinnt Server läuft auf http://localhost:" + PORT);
        System.out.println("Öffne http://localhost:" + PORT + "/connectfour.html im Browser");
    }

    /**
     * Handler für den Spielzustand.
     */
    static class StateHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                GameState state = new GameState(game);
                String response = gson.toJson(state);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes(StandardCharsets.UTF_8));
                os.close();
            }
        }
    }

    /**
     * Handler für Spielzüge.
     */
    static class MoveHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Lese Request Body
                InputStream is = exchange.getRequestBody();
                String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                
                Map<String, Object> request = gson.fromJson(body, Map.class);
                int column = ((Double) request.get("column")).intValue();

                // Führe Zug aus
                boolean success = game.makeMove(column);

                Map<String, Object> response = new HashMap<>();
                response.put("success", success);
                response.put("gameState", new GameState(game));

                String jsonResponse = gson.toJson(response);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, jsonResponse.getBytes(StandardCharsets.UTF_8).length);

                OutputStream os = exchange.getResponseBody();
                os.write(jsonResponse.getBytes(StandardCharsets.UTF_8));
                os.close();
            }
        }
    }

    /**
     * Handler für Spiel-Reset.
     */
    static class ResetHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                game.reset();

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("gameState", new GameState(game));

                String jsonResponse = gson.toJson(response);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, jsonResponse.getBytes(StandardCharsets.UTF_8).length);

                OutputStream os = exchange.getResponseBody();
                os.write(jsonResponse.getBytes(StandardCharsets.UTF_8));
                os.close();
            }
        }
    }

    /**
     * Handler für Spielkonfiguration (neues Spiel mit Einstellungen).
     */
    static class ConfigHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Lese Request Body
                InputStream is = exchange.getRequestBody();
                String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                
                @SuppressWarnings("unchecked")
                Map<String, Object> request = gson.fromJson(body, Map.class);
                
                // Extrahiere Konfiguration
                String mode = (String) request.get("mode"); // "pvp" oder "ai"
                String difficulty = (String) request.get("difficulty"); // "easy", "medium", "hard"
                String player1Color = (String) request.get("player1Color");
                String player2Color = (String) request.get("player2Color");
                
                // Symbol-Mapping basierend auf Farbe
                char player1Symbol = getSymbolForColor(player1Color);
                char player2Symbol = getSymbolForColor(player2Color);
                
                // Spielernamen
                String player1Name = "Spieler 1";
                String player2Name = "ai".equals(mode) ? "KI" : "Spieler 2";
                
                // Erstelle neues Spiel
                game = new ConnectFourGame(6, 7, 
                    player1Name, player1Color.toUpperCase(), player1Symbol,
                    player2Name, player2Color.toUpperCase(), player2Symbol);
                
                // Aktiviere KI wenn gewünscht
                if ("ai".equals(mode) && difficulty != null) {
                    ConnectFourAI.Difficulty diff;
                    switch (difficulty.toLowerCase()) {
                        case "easy":
                            diff = ConnectFourAI.Difficulty.EASY;
                            break;
                        case "hard":
                            diff = ConnectFourAI.Difficulty.HARD;
                            break;
                        default:
                            diff = ConnectFourAI.Difficulty.MEDIUM;
                    }
                    game.enableAI(diff);
                }
                
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("gameState", new GameState(game));

                String jsonResponse = gson.toJson(response);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, jsonResponse.getBytes(StandardCharsets.UTF_8).length);

                OutputStream os = exchange.getResponseBody();
                os.write(jsonResponse.getBytes(StandardCharsets.UTF_8));
                os.close();
            }
        }
    }

    /**
     * Handler für KI-Züge.
     */
    static class AIMoveHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                int aiColumn = game.getAIMove();
                
                Map<String, Object> response = new HashMap<>();
                
                if (aiColumn != -1) {
                    response.put("success", true);
                    response.put("column", aiColumn);
                } else {
                    response.put("success", false);
                }

                String jsonResponse = gson.toJson(response);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, jsonResponse.getBytes(StandardCharsets.UTF_8).length);

                OutputStream os = exchange.getResponseBody();
                os.write(jsonResponse.getBytes(StandardCharsets.UTF_8));
                os.close();
            }
        }
    }

    /**
     * Hilfsmethode: Gibt das Symbol für eine Farbe zurück.
     */
    private static char getSymbolForColor(String color) {
        if (color == null) return 'Y';
        switch (color.toLowerCase()) {
            case "gelb":
            case "yellow":
                return 'Y';
            case "rot":
            case "red":
                return 'R';
            case "blau":
            case "blue":
                return 'B';
            case "grün":
            case "green":
                return 'G';
            default:
                return 'Y';
        }
    }

    /**
     * Handler für statische Dateien.
     */
    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            
            if (path.equals("/")) {
                path = "/connectfour.html";
            }

            try {
                InputStream is = getClass().getResourceAsStream("/public" + path);
                if (is == null) {
                    String response = "404 - Datei nicht gefunden";
                    exchange.sendResponseHeaders(404, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                    return;
                }

                byte[] bytes = is.readAllBytes();
                
                // Setze Content-Type
                if (path.endsWith(".html")) {
                    exchange.getResponseHeaders().set("Content-Type", "text/html");
                } else if (path.endsWith(".js")) {
                    exchange.getResponseHeaders().set("Content-Type", "application/javascript");
                } else if (path.endsWith(".css")) {
                    exchange.getResponseHeaders().set("Content-Type", "text/css");
                }

                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                String response = "500 - Interner Serverfehler";
                exchange.sendResponseHeaders(500, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }
}
