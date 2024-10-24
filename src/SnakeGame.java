import java.util.*;

public class SnakeGame {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 10;
    private static final char SNAKE_BODY = 'O';
    private static final char FOOD = 'X';
    private static final char EMPTY = '.';

    private List<int[]> snake;
    private int[] food;
    private int direction; // 0: up, 1: right, 2: down, 3: left
    private boolean gameOver;

    public SnakeGame() {
        snake = new ArrayList<>();
        snake.add(new int[]{HEIGHT / 2, WIDTH / 2});
        generateFood();
        direction = 1;
        gameOver = false;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (!gameOver) {
            printBoard();
            System.out.print("Enter direction (W/A/S/D): ");
            char input = scanner.next().toUpperCase().charAt(0);
            updateDirection(input);
            move();
            if (snake.get(0)[0] == food[0] && snake.get(0)[1] == food[1]) {
                snake.add(0, new int[]{food[0], food[1]});
                generateFood();
            }
            checkCollision();
        }
        System.out.println("Game Over! Your score: " + (snake.size() - 1));
    }

    private void printBoard() {
        char[][] board = new char[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            Arrays.fill(board[i], EMPTY);
        }
        board[food[0]][food[1]] = FOOD;
        for (int[] part : snake) {
            board[part[0]][part[1]] = SNAKE_BODY;
        }
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    private void generateFood() {
        Random rand = new Random();
        int row, col;
        boolean validPosition;
        do {
            row = rand.nextInt(HEIGHT);
            col = rand.nextInt(WIDTH);
            validPosition = true;
            for (int[] part : snake) {
                if (part[0] == row && part[1] == col) {
                    validPosition = false;
                    break;
                }
            }
        } while (!validPosition);
        food = new int[]{row, col};
    }

    private void updateDirection(char input) {
        switch (input) {
            case 'W': if (direction != 2) direction = 0; break;
            case 'D': if (direction != 3) direction = 1; break;
            case 'S': if (direction != 0) direction = 2; break;
            case 'A': if (direction != 1) direction = 3; break;
        }
    }

    private void move() {
        int[] head = snake.get(0);
        int[] newHead = new int[]{head[0], head[1]};
        switch (direction) {
            case 0: newHead[0]--; break;
            case 1: newHead[1]++; break;
            case 2: newHead[0]++; break;
            case 3: newHead[1]--; break;
        }
        snake.add(0, newHead);
        snake.remove(snake.size() - 1);
    }

    private void checkCollision() {
        int[] head = snake.get(0);
        if (head[0] < 0 || head[0] >= HEIGHT || head[1] < 0 || head[1] >= WIDTH) {
            gameOver = true;
        }
        for (int i = 1; i < snake.size(); i++) {
            if (Arrays.equals(head, snake.get(i))) {
                gameOver = true;
                break;
            }
        }
    }

    public static void main(String[] args) {
        new SnakeGame().play();
    }
}