package com.vpp.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class PerformanceAdvice
{
    public static final int NANOSECONDS_IN_A_MILLISECOND = 1000000;

    public Object recordTiming(ProceedingJoinPoint jp) throws Throwable
    {
        double timeNow = System.nanoTime();

        try
        {
            Object returnValue = jp.proceed();
            return returnValue;
        }
        finally {
            double timeAfter = System.nanoTime();

            double timeTaken = timeAfter - timeNow;
            double timeInMilis = timeTaken / NANOSECONDS_IN_A_MILLISECOND;
            System.out.println("Time taken for method " + jp.getSignature().getName() + " is " + timeInMilis);
        }
    }
}
