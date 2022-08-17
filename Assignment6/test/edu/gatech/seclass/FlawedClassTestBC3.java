package edu.gatech.seclass;

import org.junit.Test;

public class FlawedClassTestBC3 {
    @Test
    public void Test1() {
        FlawedClass.flawedMethod3(2000);
    }

    @Test
    public void Test2() {
        FlawedClass.flawedMethod3(3000);
    }

    @Test
    public void Test3() {
        FlawedClass.flawedMethod3(4000);
    }
}
