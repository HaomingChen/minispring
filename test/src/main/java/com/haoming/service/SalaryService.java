package com.haoming.service;

import com.haoming.beans.Bean;

/**
 * @author 58212
 * @date 2020-01-31 0:00
 */
@Bean
public class SalaryService {
    public Integer calSalary(Integer experience){
        return experience * 5000;
    }
}
