package com.conwaysGameOfLifeAlgo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class OscillatorPatternSimulation {
  int width;
  int height;
  int[][] board;

  public OscillatorPatternSimulation(int width, int height) {
    this.width = width;
    this.height = height;
    this.board = new int[width][height];
  }

  public void printBoard(int boardCount) {
    System.out.println("BoardCount: " + boardCount);
    IntStream.range(0, height)
        .forEach(
            y -> {
              final String[] line = {"|"};
              IntStream.range(0, height)
                  .forEach(
                      x -> {
                        if (this.board[x][y] == 0) {
                          line[0] += "-";
                        } else {
                          line[0] += "$";
                        }
                      });
              line[0] += "|";
              System.out.println(line[0]);
            });
    System.out.println("\n");
  }

  public OscillatorPatternSimulation setLiveState(int x, int y) {
    this.board[x][y] = 1;
    return this;
  }

  public int countNeighbours(int x, int y) {
    AtomicInteger sum = new AtomicInteger();
    IntStream.range(-1, 2)
        .forEach(i -> IntStream.range(-1, 2)
            .forEach(j -> sum.addAndGet(getState(x + i, y + j))));
    sum.addAndGet(-getState(x, y));
    return sum.get();
  }

  public int getState(int x, int y) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      return 0;
    }
    return this.board[x][y];
  }

  public void iterateOscillatorBoard() {
    int[][] newBoard = new int[width][height];

    IntStream.range(0, height)
        .forEach(
            y ->
                IntStream.range(0, width)
                    .forEach(
                        x -> {
                          int neighbours = countNeighbours(x, y);
                          final int state = getState(x, y);
                          if (state == 0 && neighbours == 3) {
                            newBoard[x][y] = 1;
                          } else if (state == 1 && (neighbours < 2 || neighbours > 3)) {
                            newBoard[x][y] = 0;
                          } else {
                            newBoard[x][y] = state;
                          }
                        }));

    this.board = newBoard;
  }

  public static void main(String[] args) {
    OscillatorPatternSimulation simulation =
        new OscillatorPatternSimulation(5, 5)
            .setLiveState(1, 2)
            .setLiveState(2, 2)
            .setLiveState(3, 2);

    IntStream.range(1, 11)
        .forEach(
            i -> {
              simulation.printBoard(i);
              simulation.iterateOscillatorBoard();
            });
  }
}
