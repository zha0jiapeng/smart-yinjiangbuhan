package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

public interface QualityImportService {

    void importTestingDoc(List<List<String>> listInfo);

    void importTestingDocx(Map<String, List<String>> mapInfo);

    void importTestingReplyDoc(List<List<String>> listInfo);

    void importTestingReplyDocx(Map<String, List<String>> mapInfo);


}
