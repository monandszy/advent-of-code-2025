package code;

import code.utils.DayUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static code.Day1.Direction.LEFT;
import static code.Day1.Direction.RIGHT;

class Day1 {

  public static void main(String[] args) {
    DayUtils utils = new DayUtils(1, 1, DayUtils.FetchOption.FETCH_IF_EMPTY);
    utils.startTimer();
    Dial dial = new Dial(50, 0);
    List<Turn> turns = processInput(utils.getListInput());
//    solve(turns, dial);
    PreciseDial preciseDial = new PreciseDial(50, 0);
    System.out.println(preciseDial);
    solve(turns, preciseDial);
    utils.endTimer();
  }

  private static List<Turn> processInput(List<String> input)
  {
    Pattern pattern = Pattern.compile("(?<direction>[LR])(?<rotations>\\d+)");
    return input.stream().map(e -> {
      Matcher matcher = pattern.matcher(e);
      if (matcher.matches()) {
        Direction direction = matcher.group("direction").equals("L") ? LEFT : RIGHT;
        Integer rotations = Integer.parseInt(matcher.group("rotations"));
        return new Turn(direction, rotations);
      }
      throw new RuntimeException("Invalid input processing");
    }).toList();
  }

  private static void solve(List<Turn> turns, Dial dial) {

    for (Turn e : turns) {
      System.out.println(dial);
      if (e.direction().equals(RIGHT))
        dial.turnRight(e);
      else
        dial.turnLeft(e);
//      if (dial.position.equals(0))
//        dial.increment();
      System.out.println(e);
      System.out.println(dial);
      System.out.println();
    }
    System.out.printf("Counter: %s%n", dial.getCounter());
  }


  static class PreciseDial extends Dial{

    public PreciseDial(Integer position, Integer counter) {
      this.counter = counter;
      this.position = position;
    }

    void turnRight(Turn turn) {
      position = position + turn.rotations();
      if (position > 99) {
        counter += position / 100;
        position = position % 100;
      } else if (position == 0)
        increment();
    }

    void turnLeft(Turn turn) {
      int tmp = position;
      position = position - (turn.rotations());
      if (position < 0) {
        if(tmp != 0)
          increment();
        position = -position;
        counter += position / 100;
        position = 100 - (position % 100);
        if (position == 100)
          position = 0;
      } else if (position == 0)
        increment();
    }
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  static class Dial {
    Integer position;
    Integer counter;

    void increment()
    {
      counter++;
    }

    void turnRight(Turn turn) {
      position = position + turn.rotations();
      if (position > 99)
        position = position % 100;
    }

    void turnLeft(Turn turn) {
      position = position - (turn.rotations() % 100);
      if (position < 0)
        position = 100 + (position);
    }
  }

  record Turn(
      Direction direction, Integer rotations
  ) {}

  enum Direction {
    RIGHT,
    LEFT
  }
}