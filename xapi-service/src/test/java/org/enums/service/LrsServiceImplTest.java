package org.enums.service.implementation;

import org.enums.dtos.response.SaveStatementResponse;
import org.enums.model.XapiStatement;
import org.enums.repository.StatementRepository;
import org.enums.validation.XapiValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LrsServiceImplTest {

    @Mock
    private StatementRepository repository;

    @Mock
    private XapiValidator validator;

    @InjectMocks
    private LrsServiceImpl lrsService;

    @Test
    void saveStatement_shouldGenerateId_whenIdIsNull() {
        // Arrange
        XapiStatement statement = new XapiStatement();
        statement.setId(null); // Explicitly null

        // Act
        SaveStatementResponse response = lrsService.saveStatement(statement);

        // Assert
        // 1. Verify validator was called
        verify(validator, times(1)).validateOrThrow(statement);

        // 2. Verify repository saved a statement with a NEW UUID
        verify(repository, times(1)).save(argThat(s ->
                s.getId() != null && s.getId().toString().length() > 0
        ));

        // 3. Verify response message
        assertNotNull(response);
        assertEquals("Statement saved successfully", response.getMessage());
    }

    @Test
    void saveStatement_shouldKeepExistingId_whenIdIsProvided() {
        // Arrange
        UUID existingId = UUID.randomUUID();
        XapiStatement statement = new XapiStatement();
        statement.setId(existingId);

        // Act
        lrsService.saveStatement(statement);

        // Assert
        // Verify repository saved the statement with the ORIGINAL ID
        verify(repository, times(1)).save(argThat(s ->
                s.getId().equals(existingId)
        ));
    }

    @Test
    void saveStatement_shouldThrowException_whenValidationFails() {
        // Arrange
        XapiStatement statement = new XapiStatement();

        // Mock the validator to throw an exception
        doThrow(new IllegalArgumentException("Validation failed"))
                .when(validator).validateOrThrow(statement);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lrsService.saveStatement(statement);
        });

        assertEquals("Validation failed", exception.getMessage());

        // Verify repository was NEVER called (transaction aborted)
        verify(repository, never()).save(any());
    }

    @Test
    void getStatements_shouldReturnListFromRepository() {
        // Arrange
        XapiStatement s1 = new XapiStatement();
        XapiStatement s2 = new XapiStatement();
        List<XapiStatement> expectedList = Arrays.asList(s1, s2);

        when(repository.findAll()).thenReturn(expectedList);

        // Act
        List<XapiStatement> result = lrsService.getStatements();

        // Assert
        assertEquals(2, result.size());
        assertEquals(expectedList, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    void saveStatement_shouldThrowException_whenInputIsNull() {
        // Arrange
        XapiStatement nullStatement = null;

        // Mock Validator to throw exception when receiving null (if your validator handles it)
        // OR rely on the service throwing NullPointerException if it calls methods on null
        doThrow(new IllegalArgumentException("Statement cannot be null"))
                .when(validator).validateOrThrow(null);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lrsService.saveStatement(nullStatement);
        });

        assertEquals("Statement cannot be null", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void saveStatement_shouldPropagateException_whenRepositoryFails() {
        // Arrange
        XapiStatement statement = new XapiStatement();
        // Validation passes
        doNothing().when(validator).validateOrThrow(statement);

        // Simulate DB crash (e.g., ConnectionRefused or DataIntegrityViolation)
        when(repository.save(any(XapiStatement.class)))
                .thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            lrsService.saveStatement(statement);
        });

        assertEquals("Database connection failed", exception.getMessage());
    }

    @Test
    void getStatements_shouldHandleNullReturnFromRepository() {
        // Arrange
        // Simulate a bad repository implementation returning null
        when(repository.findAll()).thenReturn(null);

        // Act
        List<XapiStatement> result = lrsService.getStatements();

        // Assert
        // Depending on your implementation, this might return null or crash.
        // If your service just returns repository.findAll(), this confirms it returns null.
        assertNull(result);

        // Note: Ideally, your service should wrap this to return Collections.emptyList()
        // to avoid NullPointerExceptions in the Controller.
    }

    @Test
    void saveStatement_shouldValidateBeforeGeneratingId() {
        // Arrange
        XapiStatement statement = new XapiStatement();
        statement.setId(null);

        // Act
        lrsService.saveStatement(statement);

        // Assert
        // Use InOrder to verify the sequence of execution
        org.mockito.InOrder inOrder = inOrder(validator, repository);

        // 1. Validator must be called FIRST
        inOrder.verify(validator).validateOrThrow(statement);

        // 2. Repository save must be called SECOND
        inOrder.verify(repository).save(any(XapiStatement.class));
    }
}