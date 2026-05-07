package com.automation.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import java.util.concurrent.ConcurrentHashMap;

public class RetryAnalyzer implements IRetryAnalyzer {
    private static final int maxRetryCount = 2;
    private static final ConcurrentHashMap<String, Integer> retryCountMap = new ConcurrentHashMap<>();

    @Override
    public boolean retry(ITestResult result) {
        String testKey = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
        int currentRetry = retryCountMap.getOrDefault(testKey, 0);
        
        if (currentRetry < maxRetryCount) {
            retryCountMap.put(testKey, currentRetry + 1);
            
            // Log retry attempt to console for visibility
            System.out.println("\n TEST RETRY ATTEMPT");
            System.out.println("Test: " + testKey);
            System.out.println("Current Retry: " + (currentRetry + 1) + "/" + maxRetryCount);
            System.out.println("Failure: " + result.getThrowable().getMessage());
            System.out.println("================================\n");
            
            return true;  // Retry the test
        }
        
        // Clean up after final failure
        retryCountMap.remove(testKey);
        return false;  // Don't retry anymore
    }   
}
