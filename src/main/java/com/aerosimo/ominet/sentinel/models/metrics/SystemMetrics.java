/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      SystemMetrics.java                                              *
 * Created:   17/09/2025, 13:46                                               *
 * Modified:  17/09/2025, 13:46                                               *
 *                                                                            *
 * Copyright (c)  2025.  Aerosimo Ltd                                         *
 *                                                                            *
 * Permission is hereby granted, free of charge, to any person obtaining a    *
 * copy of this software and associated documentation files (the "Software"), *
 * to deal in the Software without restriction, including without limitation  *
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,   *
 * and/or sell copies of the Software, and to permit persons to whom the      *
 * Software is furnished to do so, subject to the following conditions:       *
 *                                                                            *
 * The above copyright notice and this permission notice shall be included    *
 * in all copies or substantial portions of the Software.                     *
 *                                                                            *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,            *
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES            *
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                   *
 * NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                 *
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,               *
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING               *
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                 *
 * OR OTHER DEALINGS IN THE SOFTWARE.                                         *
 *                                                                            *
 ******************************************************************************/

package com.aerosimo.ominet.sentinel.models.metrics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;

public class SystemMetrics {

    private static final Logger log;

    static {
        log = LogManager.getLogger(SystemMetrics.class.getName());
    }

    // Initialize arrays with required size
    static String[] diskusage = new String[3];
    static String[] memoryusage = new String[4];
    static ArrayList<String> cpuusage = new ArrayList<>();

    public static String[] getDisk() {
        File root = new File("/");
        diskusage[0] = String.format("%.2fGB", (double) root.getTotalSpace() / 1073741824);
        diskusage[1] = String.format("%.2fGB", (double) root.getFreeSpace() / 1073741824);
        diskusage[2] = String.format("%.2fGB", (double) root.getUsableSpace() / 1073741824);
        return diskusage;
    }

    public static String[] getMemory() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        memoryusage[0] = String.format("%.2fGB", (double) memoryMXBean.getHeapMemoryUsage().getInit() / 1073741824);
        memoryusage[1] = String.format("%.2fGB", (double) memoryMXBean.getHeapMemoryUsage().getUsed() / 1073741824);
        memoryusage[2] = String.format("%.2fGB", (double) memoryMXBean.getHeapMemoryUsage().getMax() / 1073741824);
        memoryusage[3] = String.format("%.2fGB", (double) memoryMXBean.getHeapMemoryUsage().getCommitted() / 1073741824);
        return memoryusage;
    }

    public static ArrayList<String> getCpu() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        cpuusage.clear(); // reset list to avoid duplicates
        for (Long threadID : threadMXBean.getAllThreadIds()) {
            ThreadInfo info = threadMXBean.getThreadInfo(threadID);
            if (info != null) { // avoid null ThreadInfo
                cpuusage.add(info.getThreadName());
                cpuusage.add(String.valueOf(info.getThreadState()));
                cpuusage.add(String.format("%s", threadMXBean.getThreadCpuTime(threadID)));
            }
        }
        return cpuusage;
    }
}