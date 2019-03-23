package exodiaapp.web.controllers;

import exodiaapp.domain.models.service.DocumentServiceModel;
import exodiaapp.domain.models.view.DocumentViewModel;
import exodiaapp.service.document.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class DocumentPrintController extends BaseController {
    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    public DocumentPrintController(DocumentService documentService, ModelMapper modelMapper) {
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/print/{id}")
    public ModelAndView print(@PathVariable String id, ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
           return this.redirect("/");
        }
        DocumentServiceModel documentServiceModel = this.documentService.findDocumentById(id);
        modelAndView.addObject("documentPrintDetails", this.modelMapper.map(documentServiceModel, DocumentViewModel.class));
        return this.view("document-print", modelAndView);
    }

    @PostMapping("/print/{documentId}")
    public ModelAndView printPost(@PathVariable String documentId, HttpServletResponse response) throws IOException {
        DocumentServiceModel documentServiceModel = this.documentService.findDocumentById(documentId);
        this.documentService.printDocument(documentServiceModel);
//        byte[] data = documentServiceModel.getContent().getBytes();
//        response.setContentType("application/pdf");
//        response.setHeader("Content-disposition", "attachment; filename=" + "my.pdf");
//        response.setContentLength(data.length);
//
//        response.getOutputStream().write(data);
//        response.getOutputStream().flush();
        return this.redirect("/home");
    }
}
