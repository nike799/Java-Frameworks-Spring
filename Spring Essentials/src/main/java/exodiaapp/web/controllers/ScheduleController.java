package exodiaapp.web.controllers;

import exodiaapp.domain.models.binding.DocumentRegisterBindingModel;
import exodiaapp.domain.models.service.DocumentServiceModel;
import exodiaapp.service.document.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class ScheduleController extends BaseController {
    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    public ScheduleController(DocumentService documentService, ModelMapper modelMapper) {
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/schedule")
    public ModelAndView schedule(HttpSession session) {
        if (session.getAttribute("username") == null) {
          return this.redirect("/");
        }
        return this.view("schedule");
    }

    @PostMapping("/schedule")
    public ModelAndView schedulePost(@ModelAttribute DocumentRegisterBindingModel documentRegisterBindingModel) {
        DocumentServiceModel documentServiceModel = this.documentService.documentRegister(this.modelMapper.map(documentRegisterBindingModel, DocumentServiceModel.class));
        return this.redirect("/details/" + documentServiceModel.getId());
    }
}
