package edu.gatech.seclass;

import org.junit.Test;



public class FlawedClassTestSC3 {

    @Test
    public void Test1() { FlawedClass.flawedMethod3(1); }

    @Test
    public void Test2() { FlawedClass.flawedMethod3(10); }

    @Test
    public void Test3() { FlawedClass.flawedMethod3(50); }

    @Test
    public void Test4() { FlawedClass.flawedMethod3(100); }
}
