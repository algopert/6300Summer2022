package edu.gatech.seclass;

import org.junit.Test;

public class FlawedClassTestSC4 {

    @Test
    public void Test1() {
        FlawedClass.flawedMethod4(1, -1, 1, 1);
    }

    @Test
    public void Test2() {
        FlawedClass.flawedMethod4(1, 1, 1, -1);
    }

    @Test
    public void Test3() {
        FlawedClass.flawedMethod4(0, 1, 1, 1);
    }

}
