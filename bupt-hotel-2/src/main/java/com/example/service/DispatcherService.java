package com.example.service;

import java.util.List;

public interface DispatcherService {
    int queryFreeResources();

    List<String> queryTopKWaitingACNumbers(Integer k);

    void establishConnection(List<String> list);

    void releaseResources(List<String> acNumberList);

    List<String> checkUnworkableACs();
}
