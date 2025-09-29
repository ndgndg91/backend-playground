# Redis-Performance

```
% brew install jmeter

% open /usr/local/bin/jmeter
```

## V1 - not applied redis
```
% /usr/local/bin/jmeter -n -t ./jmeter/v1/v1.jmx -l ./jmeter/v1/v1.jtl

WARNING: package sun.awt.X11 not in java.desktop
Creating summariser <summary>
Created the tree successfully using Redis-Performance.jmx
Starting standalone test @ 2022 Oct 9 16:16:16 KST (1665299776726)
Waiting for possible Shutdown/StopTestNow/HeapDump/ThreadDump message on port 4445
summary +  18350 in 00:00:13 = 1388.3/s Avg:    31 Min:     2 Max:   134 Err:     0 (0.00%) Active: 87 Started: 87 Finished: 0
summary +  56931 in 00:00:30 = 1897.7/s Avg:    88 Min:    35 Max:  1223 Err:     0 (0.00%) Active: 200 Started: 200 Finished: 0
summary =  75281 in 00:00:43 = 1741.9/s Avg:    74 Min:     2 Max:  1223 Err:     0 (0.00%)
summary +  32990 in 00:00:17 = 1948.3/s Avg:   102 Min:    84 Max:   249 Err:     0 (0.00%) Active: 0 Started: 200 Finished: 200
summary = 108271 in 00:01:00 = 1800.0/s Avg:    82 Min:     2 Max:  1223 Err:     0 (0.00%)
Tidying up ...    @ 2022 Oct 9 16:17:16 KST (1665299836933)
... end of run
```
<img width="1843" alt="스크린샷 2022-10-09 오후 4 26 35" src="https://user-images.githubusercontent.com/19872667/194743681-ff150a0a-aec8-43cb-9119-1643b732fd79.png">


## V2 - applied redis - cache map
```
 % /usr/local/bin/jmeter -n -t ./jmeter/v2/v2.jmx -l ./jmeter/v2/v2.jtl
WARNING: package sun.awt.X11 not in java.desktop
Creating summariser <summary>
Created the tree successfully using ./jmeter/v2/v2.jmx
Starting standalone test @ 2022 Oct 9 17:47:15 KST (1665305235878)
Waiting for possible Shutdown/StopTestNow/HeapDump/ThreadDump message on port 4445
summary +  73320 in 00:00:14 = 5213.7/s Avg:     8 Min:     0 Max:  1063 Err:     0 (0.00%) Active: 93 Started: 93 Finished: 0
summary + 194217 in 00:00:30 = 6474.1/s Avg:    26 Min:     1 Max:   279 Err:     0 (0.00%) Active: 200 Started: 200 Finished: 0
summary = 267537 in 00:00:44 = 6071.8/s Avg:    21 Min:     0 Max:  1063 Err:     0 (0.00%)
summary +  96543 in 00:00:16 = 6028.7/s Avg:    33 Min:     2 Max:   298 Err:     0 (0.00%) Active: 0 Started: 200 Finished: 200
summary = 364080 in 00:01:00 = 6060.3/s Avg:    24 Min:     0 Max:  1063 Err:     0 (0.00%)
Tidying up ...    @ 2022 Oct 9 17:48:16 KST (1665305296014)
... end of run

```

<img width="1849" alt="스크린샷 2022-10-09 오후 5 51 46" src="https://user-images.githubusercontent.com/19872667/194747373-9436c2cf-a006-4b07-8d1f-f9eff4b4a425.png">

## V3 - applied redis - local cache map
```
% /usr/local/bin/jmeter -n -t ./jmeter/v3/v3.jmx -l ./jmeter/v3/v3.jtl
WARNING: package sun.awt.X11 not in java.desktop
Creating summariser <summary>
Created the tree successfully using ./jmeter/v3/v3.jmx
Starting standalone test @ 2022 Oct 10 11:52:23 KST (1665370343723)
Waiting for possible Shutdown/StopTestNow/HeapDump/ThreadDump message on port 4445
summary +  28870 in 00:00:06 = 4646.0/s Avg:     4 Min:     0 Max:   245 Err:     0 (0.00%) Active: 41 Started: 41 Finished: 0
summary + 218830 in 00:00:30 = 7294.3/s Avg:    18 Min:     0 Max:   252 Err:     0 (0.00%) Active: 200 Started: 200 Finished: 0
summary = 247700 in 00:00:36 = 6839.9/s Avg:    16 Min:     0 Max:   252 Err:     0 (0.00%)
summary + 158730 in 00:00:24 = 6649.5/s Avg:    29 Min:     0 Max:  1341 Err:     0 (0.00%) Active: 0 Started: 200 Finished: 200
summary = 406430 in 00:01:00 = 6764.3/s Avg:    22 Min:     0 Max:  1341 Err:     0 (0.00%)
Tidying up ...    @ 2022 Oct 10 11:53:23 KST (1665370403872)
... end of run
```

<img width="1845" alt="스크린샷 2022-10-10 오전 11 55 09" src="https://user-images.githubusercontent.com/19872667/194793378-0b577be7-5e3a-44e7-add6-7e58f9939e12.png">
