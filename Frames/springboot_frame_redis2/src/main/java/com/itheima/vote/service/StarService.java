package com.itheima.vote.service;

import com.itheima.vote.domain.Star;

import java.util.List;
import java.util.Set;

public interface StarService {
    Set<String> getCharts(String ticket);

    Set<String> getAll();

}
