package Prestabanco.Backend.services;

import Prestabanco.Backend.entities.FileUploadEntity;
import Prestabanco.Backend.repositories.FileUploadRepository;
import Prestabanco.Backend.services.FileUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FileUploadServiceTest {

    @Mock
    private FileUploadRepository fileUploadRepository;

    @InjectMocks
    private FileUploadService fileUploadService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Tests for saveFileUpload
    @Test
    public void testSaveFileUploadSuccess() {
        FileUploadEntity file = new FileUploadEntity();
        when(fileUploadRepository.save(file)).thenReturn(file);

        FileUploadEntity result = fileUploadService.saveFileUpload(file);

        assertNotNull(result);
        verify(fileUploadRepository, times(1)).save(file);
    }

    @Test
    public void testSaveFileUploadThrowsNullPointerExceptionOnNull() {
        assertThrows(NullPointerException.class, () -> fileUploadService.saveFileUpload(null));
    }

    @Test
    public void testSaveFileUploadValidFile() {
        FileUploadEntity file = new FileUploadEntity();
        file.setFileName("test.pdf");
        when(fileUploadRepository.save(file)).thenReturn(file);

        FileUploadEntity result = fileUploadService.saveFileUpload(file);

        assertEquals("test.pdf", result.getFileName());
    }

    @Test
    public void testSaveFileUploadNonExistentFileId() {
        FileUploadEntity file = new FileUploadEntity();
        file.setId(100L);
        when(fileUploadRepository.save(file)).thenReturn(file);

        FileUploadEntity result = fileUploadService.saveFileUpload(file);

        assertEquals(100L, result.getId());
    }

    @Test
    public void testSaveFileUploadEmptyFileName() {
        FileUploadEntity file = new FileUploadEntity();
        file.setFileName("");
        when(fileUploadRepository.save(file)).thenReturn(file);

        FileUploadEntity result = fileUploadService.saveFileUpload(file);

        assertEquals("", result.getFileName());
    }

    @Test
    public void testSaveFileUploadHandlesException() {
        FileUploadEntity file = new FileUploadEntity();
        when(fileUploadRepository.save(file)).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> fileUploadService.saveFileUpload(file));

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void testSaveFileUploadVerifyFileNamePersistence() {
        FileUploadEntity file = new FileUploadEntity();
        file.setFileName("persisted_file.pdf");
        when(fileUploadRepository.save(file)).thenReturn(file);

        FileUploadEntity result = fileUploadService.saveFileUpload(file);

        assertEquals("persisted_file.pdf", result.getFileName());
    }

    @Test
    public void testSaveFileUploadVerifyIdPersistence() {
        FileUploadEntity file = new FileUploadEntity();
        file.setId(123L);
        when(fileUploadRepository.save(file)).thenReturn(file);

        FileUploadEntity result = fileUploadService.saveFileUpload(file);

        assertEquals(123L, result.getId());
    }

    @Test
    public void testSaveFileUploadVerifyRepositoryCall() {
        FileUploadEntity file = new FileUploadEntity();
        file.setFileName("repository_test.pdf");
        when(fileUploadRepository.save(file)).thenReturn(file);

        fileUploadService.saveFileUpload(file);

        verify(fileUploadRepository, times(1)).save(file);
    }

    @Test
    public void testSaveFileUploadWithNullFileName() {
        FileUploadEntity file = new FileUploadEntity();
        file.setFileName(null);
        when(fileUploadRepository.save(file)).thenReturn(file);

        FileUploadEntity result = fileUploadService.saveFileUpload(file);

        assertNull(result.getFileName());
    }

    // Tests for updateUser
    @Test
    public void testUpdateUserSuccess() {
        FileUploadEntity file = new FileUploadEntity();
        when(fileUploadRepository.save(file)).thenReturn(file);

        FileUploadEntity result = fileUploadService.updateUser(file);

        assertNotNull(result);
    }

    @Test
    public void testUpdateUserThrowsNullPointerExceptionOnNull() {
        assertThrows(NullPointerException.class, () -> fileUploadService.updateUser(null));
    }

    @Test
    public void testUpdateUserExistingId() {
        FileUploadEntity file = new FileUploadEntity();
        file.setId(1L);
        when(fileUploadRepository.save(file)).thenReturn(file);

        FileUploadEntity result = fileUploadService.updateUser(file);

        assertEquals(1L, result.getId());
    }

    @Test
    public void testUpdateUserFileNameUpdate() {
        FileUploadEntity file = new FileUploadEntity();
        file.setFileName("updated_file.pdf");
        when(fileUploadRepository.save(file)).thenReturn(file);

        FileUploadEntity result = fileUploadService.updateUser(file);

        assertEquals("updated_file.pdf", result.getFileName());
    }

    @Test
    public void testUpdateUserRepositorySaveCalledOnce() {
        FileUploadEntity file = new FileUploadEntity();
        file.setFileName("save_once.pdf");
        when(fileUploadRepository.save(file)).thenReturn(file);

        fileUploadService.updateUser(file);

        verify(fileUploadRepository, times(1)).save(file);
    }

    @Test
    public void testUpdateUserHandlesException() {
        FileUploadEntity file = new FileUploadEntity();
        when(fileUploadRepository.save(file)).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> fileUploadService.updateUser(file));

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void testUpdateUserWithEmptyFileName() {
        FileUploadEntity file = new FileUploadEntity();
        file.setFileName("");
        when(fileUploadRepository.save(file)).thenReturn(file);

        FileUploadEntity result = fileUploadService.updateUser(file);

        assertEquals("", result.getFileName());
    }

    @Test
    public void testUpdateUserVerifyIdPersistence() {
        FileUploadEntity file = new FileUploadEntity();
        file.setId(2L);
        when(fileUploadRepository.save(file)).thenReturn(file);

        FileUploadEntity result = fileUploadService.updateUser(file);

        assertEquals(2L, result.getId());
    }

    @Test
    public void testUpdateUserNullFileName() {
        FileUploadEntity file = new FileUploadEntity();
        file.setFileName(null);
        when(fileUploadRepository.save(file)).thenReturn(file);

        FileUploadEntity result = fileUploadService.updateUser(file);

        assertNull(result.getFileName());
    }

    // Tests para deleteFileUpload
    @Test
    public void testDeleteFileUploadSuccess() throws Exception {
        doNothing().when(fileUploadRepository).deleteById(1L);

        boolean result = fileUploadService.deleteFileUpload(1L);

        assertTrue(result);
    }

    @Test
    public void testDeleteFileUploadThrowsExceptionOnInvalidId() {
        doThrow(new RuntimeException("Database error")).when(fileUploadRepository).deleteById(100L);

        Exception exception = assertThrows(Exception.class, () -> fileUploadService.deleteFileUpload(100L));

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void testDeleteFileUploadVerifyRepositoryDeleteCalled() throws Exception {
        doNothing().when(fileUploadRepository).deleteById(1L);

        fileUploadService.deleteFileUpload(1L);

        verify(fileUploadRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteFileUploadForNonExistentId() {
        doThrow(new RuntimeException("File not found")).when(fileUploadRepository).deleteById(100L);

        Exception exception = assertThrows(Exception.class, () -> fileUploadService.deleteFileUpload(100L));

        assertEquals("File not found", exception.getMessage());
    }

    @Test
    public void testDeleteFileUploadWithZeroId() {
        doThrow(new IllegalArgumentException("Invalid ID")).when(fileUploadRepository).deleteById(0L);

        Exception exception = assertThrows(Exception.class, () -> fileUploadService.deleteFileUpload(0L));

        assertEquals("Invalid ID", exception.getMessage());
    }

    @Test
    public void testDeleteFileUploadWithNegativeId() {
        doThrow(new IllegalArgumentException("Invalid ID")).when(fileUploadRepository).deleteById(-1L);

        Exception exception = assertThrows(Exception.class, () -> fileUploadService.deleteFileUpload(-1L));

        assertEquals("Invalid ID", exception.getMessage());
    }

    @Test
    public void testDeleteFileUploadWithLargeId() throws Exception {
        doNothing().when(fileUploadRepository).deleteById(999999L);

        boolean result = fileUploadService.deleteFileUpload(999999L);

        assertTrue(result);
    }

    @Test
    public void testDeleteFileUploadRepositoryDeleteNotCalled() throws Exception {
        verify(fileUploadRepository, times(0)).deleteById(1L);
    }

    @Test
    public void testDeleteFileUploadMultipleDeletes() throws Exception {
        doNothing().when(fileUploadRepository).deleteById(1L);
        doNothing().when(fileUploadRepository).deleteById(2L);

        fileUploadService.deleteFileUpload(1L);
        fileUploadService.deleteFileUpload(2L);

        verify(fileUploadRepository, times(1)).deleteById(1L);
        verify(fileUploadRepository, times(1)).deleteById(2L);
    }
}
