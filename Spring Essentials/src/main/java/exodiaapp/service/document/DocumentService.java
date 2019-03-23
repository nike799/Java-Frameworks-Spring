package exodiaapp.service.document;

import exodiaapp.domain.models.service.DocumentServiceModel;

import java.util.List;

public interface DocumentService {
    DocumentServiceModel documentRegister(DocumentServiceModel documentServiceModel);

    List<DocumentServiceModel> getAllDocuments();

    DocumentServiceModel findDocumentById(String id);

    void printDocument(DocumentServiceModel documentServiceModel);
}
