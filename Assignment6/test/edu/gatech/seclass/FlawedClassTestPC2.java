package edu.gatech.seclass;

import org.junit.Test;

public class FlawedClassTestPC2 {
    @Test
    public void Test1() {
        FlawedClass.flawedMethod2(10, 5);
    }

    @Test
    public void Test2() {
        FlawedClass.flawedMethod2(20, 1);
    }

    @Test
    public void Test3() {
        FlawedClass.flawedMethod2(20, 30);
    }

    @Test
    public void Test4() {
        FlawedClass.flawedMethod2(10, 50);
    }
}
