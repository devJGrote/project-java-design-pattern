package de.inosofttech.example.gameoflife.web;

import de.inosofttech.example.gameoflife.GameOfLife;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.util.logging.Logger;

/**
 * Web server for Game of Life with REST API and Web UI.
 */
public class GameOfLifeWebServer {
    private static final Logger LOGGER = Logger.getLogger(GameOfLifeWebServer.class.getName());
    private static final int DEFAULT_PORT = 7070;
    
    private GameOfLife game;
    private final Javalin app;

    public GameOfLifeWebServer(int port) {
        app = Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);
        });

        setupRoutes();
        app.start(port);
        
        LOGGER.info("Game of Life Web Server started on http://localhost:" + port);
    }

    private void setupRoutes() {
        // Initialize new game
        app.post("/api/init", ctx -> {
            int width = ctx.queryParamAsClass("width", Integer.class).getOrDefault(100);
            int height = ctx.queryParamAsClass("height", Integer.class).getOrDefault(100);
            double probability = ctx.queryParamAsClass("probability", Double.class).getOrDefault(0.3);
            
            game = new GameOfLife(width, height, probability);
            ctx.json(getGameState());
            
            LOGGER.info(String.format("New game initialized: %dx%d", width, height));
        });

        // Get current state
        app.get("/api/state", ctx -> {
            if (game == null) {
                game = new GameOfLife(100, 100, 0.3);
            }
            ctx.json(getGameState());
        });

        // Next generation
        app.post("/api/next", ctx -> {
            if (game == null) {
                game = new GameOfLife(100, 100, 0.3);
            }
            game.nextGeneration();
            ctx.json(getGameState());
        });

        // Run multiple generations
        app.post("/api/run", ctx -> {
            if (game == null) {
                game = new GameOfLife(100, 100, 0.3);
            }
            int count = ctx.queryParamAsClass("count", Integer.class).getOrDefault(1);
            game.runGenerations(count);
            ctx.json(getGameState());
        });

        // Reset game
        app.post("/api/reset", ctx -> {
            if (game != null) {
                int width = game.getCurrentGrid().getWidth();
                int height = game.getCurrentGrid().getHeight();
                game = new GameOfLife(width, height, 0.3);
            } else {
                game = new GameOfLife(100, 100, 0.3);
            }
            ctx.json(getGameState());
            LOGGER.info("Game reset");
        });
    }

    private GameOfLifeState getGameState() {
        if (game == null) {
            return new GameOfLifeState(0, 0, 0, 0, new boolean[0][0]);
        }
        
        return new GameOfLifeState(
            game.getGeneration(),
            game.getAliveCount(),
            game.getCurrentGrid().getWidth(),
            game.getCurrentGrid().getHeight(),
            game.getCurrentGrid().getCellsAsBoolean()
        );
    }

    public void stop() {
        app.stop();
        LOGGER.info("Game of Life Web Server stopped");
    }

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid port number, using default: " + DEFAULT_PORT);
            }
        }
        
        new GameOfLifeWebServer(port);
        LOGGER.info("Open http://localhost:" + port + " in your browser");
    }
}
