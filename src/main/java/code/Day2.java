package code;

import code.utils.DayUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Day2 {

  public static void main(String[] args) {
    DayUtils utils = new DayUtils(2, 1, DayUtils.FetchOption.FETCH_IF_EMPTY);
    List<IdRange> idRanges = parseInput(utils.getStringInput());
    utils.startTimer();
    BigInteger sum = solve(idRanges);
    utils.endTimer();
    utils.printAnswer(sum);

    utils.startTimer();
    BigInteger sum2 = solve2(idRanges);
    utils.endTimer();
    utils.printAnswer(sum2);
  }

  private static BigInteger solve2(List<IdRange> ranges) {
    BigInteger sum = new BigInteger("0");
    for (IdRange range : ranges) {
      BigInteger c = range.firstId;
      System.out.printf("Processing %s\n", c.toString());
      while (c.compareTo(range.lastId) < 1) {
        String id = c.toString();
        int len = id.length();
        char[] arr = id.toCharArray();
        for (int i = 1; i < len / 2 + 1; i++) {
          int flag = 1;
          if (len % i != 0)
            continue;
          for (int j = 0; j < len - i; j++) {
            if (arr[j] != arr[j + i]) {
              flag = 0;
              break;
            }
          }
          if (flag == 1)
          {
            System.out.println(c);
            sum = sum.add(c);
            break;
          }
        }
        c = c.add(new BigInteger("1"));
      }
    }
    return (sum);
  }

  private static BigInteger solve(List<IdRange> ranges) {
    BigInteger sum = new BigInteger("0");
    for (IdRange range : ranges) {
      BigInteger c = range.firstId;
      System.out.printf("Processing %s\n", c.toString());
      while (c.compareTo(range.lastId) < 1) {
        int len = c.toString().length();
        if (len % 2 == 0) {
          BigInteger divider = BigInteger.valueOf(10).pow(len / 2);
          BigInteger first = c.divide(divider);
          BigInteger second = c.subtract(first.multiply(divider));
          if (first.compareTo(second) == 0) {
            sum = sum.add(c);
          }
        }
        c = c.add(new BigInteger("1"));
      }
    }
    return (sum);
  }

  private static List<IdRange> parseInput(String input) {
    return Arrays.stream(input.split(",")).map(e -> {
      String[] split = e.split("-");
      return new IdRange(new BigInteger(split[0]), new BigInteger(split[1]));
    }).toList();
  }

  record IdRange(BigInteger firstId, BigInteger lastId) {}
}