package cn.daycode.fatalism.transaction;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TransactionAppTest {

    @Test
    void main() {
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            String str = in.next();
            String[] splits = str.split(" ");
            System.out.println(splits[splits.length - 1]);
        }
    }
}