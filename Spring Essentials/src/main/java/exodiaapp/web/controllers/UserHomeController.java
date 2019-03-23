package exodiaapp.web.controllers;

import exodiaapp.domain.models.view.DocumentViewModel;
import exodiaapp.service.document.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserHomeController extends BaseController {
    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    public UserHomeController(DocumentService documentService, ModelMapper modelMapper) {
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/home")
    public ModelAndView userHome(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            return this.redirect("/");
        }
        List<DocumentViewModel> documentViewModels =
                Arrays.stream(this.modelMapper.map(this.documentService.getAllDocuments().toArray(), DocumentViewModel[].class))
                        .peek(documentViewModel -> {
                            if (documentViewModel.getTitle().length() > 12) {
                                documentViewModel.setTitle(documentViewModel.getTitle().substring(0, 11).concat("..."));
                            }
                        }).collect(Collectors.toList());
        modelAndView.addObject("documentViewModels", documentViewModels);
        return this.view("home", modelAndView);
    }
}
