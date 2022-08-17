package edu.gatech.seclass;

import org.junit.Test;

public class FlawedClassTestSC2 {

    @Test
    public void Test1() { FlawedClass.flawedMethod2(200, 100); }

    
    @Test
    public void Test2() { FlawedClass.flawedMethod2(400, 200); }

    @Test
    public void Test3() { FlawedClass.flawedMethod2(800, 200); }
}
