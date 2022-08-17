package edu.gatech.seclass;

import org.junit.Test;

public class FlawedClassTestMCDC4 {

    @Test
    public void Test1() {
        FlawedClass.flawedMethod4(0, 0, 0, 0);
    }

    @Test
    public void Test2() {
        FlawedClass.flawedMethod4(1, -1, 1, -1);
    }

    @Test
    public void Test3() {
        FlawedClass.flawedMethod4(1, -1, 1, 1);
    }


    @Test
    public void Test4() {
        FlawedClass.flawedMethod4(1, -1, -1, -1);
    }

    @Test
    public void Test5() {
        FlawedClass.flawedMethod4(1, -1, -1, 1);
    }

    
    @Test
    public void Test6() {
        FlawedClass.flawedMethod4(1, 1, 0, 0);
    }

}
