package ProjectFiles.Input;


import javax.swing.*;

// I do not know how or even if this class works as I generated it with chat gpt.
// I probably wont fix it because I have a better version in a different game engine project

public class gameTimer {
    private Timer timer;
    private long lastFrameTime;
    private final int targetFPS = 60;
    private final long optimalFrameTime = 1000000000 / targetFPS;  // nanoseconds per frame

    public gameTimer() {
        lastFrameTime = System.nanoTime();
    }

    public void start(java.util.function.Consumer<Double> updateAndRepaint) {
        
        timer = new Timer(0, e -> {
            
            // Current frame's start time
            long currentFrameTime = System.nanoTime();

            // Calculate delta time in seconds
            double deltaTime = (currentFrameTime - lastFrameTime) / 1_000_000_000.0;

            // Call the update and repaint logic
            updateAndRepaint.accept(deltaTime);

            // Calculate how long the frame took
            long frameDuration = System.nanoTime() - currentFrameTime;

            // If the frame was too fast, sleep for the remaining time to maintain target FPS
            if (frameDuration < optimalFrameTime) {
                try {
                    Thread.sleep((optimalFrameTime - frameDuration) / 1_000_000);  // Convert to milliseconds
                } catch (InterruptedException ex) {}
            }

            // Set the last frame time to the current one
            lastFrameTime = currentFrameTime;
        });

        // Start the timer
        timer.start();
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }
}

