package exodiaapp.web.controllers;

import exodiaapp.domain.models.view.DocumentViewModel;
import exodiaapp.service.document.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class DocumentDetailsController extends BaseController {
    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    public DocumentDetailsController(DocumentService documentService, ModelMapper modelMapper) {
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/details/{documentId}")
    public ModelAndView documentDetails(@PathVariable String documentId, ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
           return this.redirect("/");
        }
        DocumentViewModel documentViewModel = this.modelMapper.map(this.documentService.findDocumentById(documentId), DocumentViewModel.class);
        modelAndView.addObject("documentDetails", documentViewModel);
        return this.view("document-details",modelAndView);
    }
}
