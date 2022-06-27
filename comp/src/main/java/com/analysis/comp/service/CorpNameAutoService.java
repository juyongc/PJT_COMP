package com.analysis.comp.service;

import com.analysis.comp.model.repository.CorpCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CorpNameAutoService {

    @Autowired
    private CorpCodeRepository corpCodeRepository;

    public List<String> corpNameAuto(String name) {
        List<String> corpNameList = corpCodeRepository.checkNameAuto(name);
        return corpNameList;
    }

}
