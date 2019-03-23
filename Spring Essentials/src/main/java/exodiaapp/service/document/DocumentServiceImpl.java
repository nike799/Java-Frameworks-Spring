package exodiaapp.service.document;

import exodiaapp.domain.entities.Document;
import exodiaapp.domain.models.service.DocumentServiceModel;
import exodiaapp.repository.DocumentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final ModelMapper modelMapper;

    public DocumentServiceImpl(DocumentRepository documentRepository, ModelMapper modelMapper) {
        this.documentRepository = documentRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public DocumentServiceModel documentRegister(DocumentServiceModel documentServiceModel) {
        Document document = this.documentRepository.save(this.modelMapper.map(documentServiceModel, Document.class));
        return document != null ? this.modelMapper.map(document, DocumentServiceModel.class) : null;
    }

    @Override
    public List<DocumentServiceModel> getAllDocuments() {
        List<Document> documents = this.documentRepository.findAll();
        return List.of(this.modelMapper.map(this.documentRepository.findAll().toArray(), DocumentServiceModel[].class));
    }

    @Override
    public DocumentServiceModel findDocumentById(String documentId) {
        Document document = this.documentRepository.findByIdIsLike(documentId).orElse(null);
        return document != null ? this.modelMapper.map(document, DocumentServiceModel.class) : null;
    }

    @Override
    public void printDocument(DocumentServiceModel documentServiceModel) {
        this.documentRepository.findByIdIsLike(documentServiceModel.getId()).ifPresent(this.documentRepository::delete);
    }
}
