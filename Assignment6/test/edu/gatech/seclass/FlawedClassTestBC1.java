package edu.gatech.seclass;

import org.junit.Test;

public class FlawedClassTestBC1 {

    @Test
    public void Test1() { FlawedClass.flawedMethod1(20); }

    
    @Test
    public void Test2() { FlawedClass.flawedMethod1(-50); }
}
