package org.enums.service;

import org.enums.dtos.response.SaveStatementResponse;
import org.enums.model.XapiStatement;

import java.util.List;

public interface LrsService {

    SaveStatementResponse saveStatement(XapiStatement statement);
    List<XapiStatement> getStatements();
}
