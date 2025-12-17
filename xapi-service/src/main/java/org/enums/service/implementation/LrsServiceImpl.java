package org.enums.service.implementation;


import org.enums.dtos.response.SaveStatementResponse;
import org.enums.model.XapiStatement;
import org.enums.repository.StatementRepository;
import org.enums.service.LrsService;
import org.enums.validation.XapiValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LrsServiceImpl implements LrsService {

    private final StatementRepository repository;
    private final XapiValidator validator;

    public LrsServiceImpl(StatementRepository repository, XapiValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }


    @Override
    public SaveStatementResponse saveStatement(XapiStatement statement) {
        validator.validateOrThrow(statement);

        if (statement.getId() == null) {
            statement.setId(UUID.randomUUID());
        }

        repository.save(statement);

        return new SaveStatementResponse(
                "Statement saved successfully"
        );
    }

    @Override
    public List<XapiStatement> getStatements() {
        return repository.findAll();
    }


}