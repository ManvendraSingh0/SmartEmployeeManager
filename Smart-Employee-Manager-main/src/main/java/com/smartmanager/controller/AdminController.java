package com.smartmanager.controller;
import com.smartmanager.dao.Contact_repo;
import com.smartmanager.dao.Repository;
import com.smartmanager.dao.UserLogRepository;
import com.smartmanager.entities.Contact;
import com.smartmanager.entities.Message;
import com.smartmanager.entities.User;
import com.smartmanager.entities.UserLog;
import com.smartmanager.service.ContactService;
import com.smartmanager.service.LogService;
import com.smartmanager.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    private LogService logService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;
    public static String s="";
    @Autowired
    private  UserLogRepository userLogRepository;
    @Autowired
    private Repository repository;
    @Autowired
    private Contact_repo contact_repo;
    @GetMapping("/currently-log-in")
    public String user_currentLogIn(Model model)
    {
        List<UserLog> userlog = userLogRepository.findAllByLogoutTimeIsNull();
        model.addAttribute("userLog", userlog);
        return "/currentlyLogIn.html";
    }
    @GetMapping("/dashboard")
    public String admin_dashboard(Model model)
    {
        return "adminDashboard1.html";
    }
    @GetMapping("/tracks/{page}")
    public String user_tracks(Model model, @PathVariable("page") Integer page, @RequestParam(value = "keyword", required = false) String keyword)
    {
        Pageable pageable= PageRequest.of(page, 10);
        Page<UserLog> userlog;
//        List<UserLog>  = userLogRepository.findAllByLogoutTimeIsNotNull();
        if(keyword!=null && !keyword.isEmpty() )
        {
            userlog= logService.searchEmployees(page,10,keyword);
        }
        else {
            userlog=userLogRepository.findAllByLogoutTimeIsNotNull(pageable);
        }
        model.addAttribute("userLog", userlog);
        model.addAttribute("user_c", userlog.get());
        model.addAttribute("current_page", page);
        model.addAttribute("total_pages", userlog.getTotalPages());
        return "/user_tracks.html";
    }

    @GetMapping("/all_contacts/{page}")
    public String view_contact(Model model, @PathVariable("page") Integer page, @RequestParam(value = "keyword", required = false) String keyword)
    {
        System.out.println(keyword);
        Pageable pageable= PageRequest.of(page, 5);
        Page<User> contact;
        if(keyword!=null && !keyword.isEmpty() )
        {
            contact= userService.searchEmployees(page,5,keyword);
        }
        else {
            contact=repository.findAll(pageable);
        }

        if(page>contact.getTotalPages()-1)
        {
            model.addAttribute("message", " page not found");
            return "normal/error.html";
        }
        System.out.println(contact.get());
        model.addAttribute("user_c", contact.get());
        model.addAttribute("current_page", page);
        model.addAttribute("total_pages", contact.getTotalPages());
        return "teamLeader";
    }
    @GetMapping("/all_members/{page}/{username}")
    public String view_contact(Model model,@PathVariable("page") Integer page,@PathVariable("username") String username,@RequestParam(value = "keyword", required = false) String keyword)
    {
        s=username;
        System.out.println(keyword);
//        session.removeAttribute("message");
//        String username=principal.getName();
        User user=repository.findByEmail(username);
        Pageable pageable=PageRequest.of(page, 5);
        Page<Contact> contact;
        if(keyword!=null && !keyword.isEmpty() )
        {
            contact= contactService.searchEmployees(page,5,keyword);
        }
        else {
            contact=contact_repo.findByUser_id(user.getId(),pageable);
        }
        if(page>contact.getTotalPages()-1)
        {
            model.addAttribute("message", " page not found");
            return "normal/error.html";
        }
        System.out.println(contact.get());
        model.addAttribute("user_c", contact.get());
        model.addAttribute("current_page", page);
        model.addAttribute("total_pages", contact.getTotalPages());
        model.addAttribute("username",username);

        return "teamMember.html";
    }
    @PostMapping("/deleteTracks")
    public String deleteTracks()
    {
//        System.out.println("heyyyyy");
        userLogRepository.deleteAll();
        return "redirect:/Admin/tracks/0";
    }
    @PostMapping("/delete")
    public String delete_form(@RequestParam("hidden") Integer id,HttpSession session)
    {
        session.removeAttribute("message");
         repository.deleteById(id);
//        Optional<Contact> contact=contact_repo.findById(id);
//        contact_repo.delete(contact.get());
//        File delfile;
//        try {
//            delfile = new ClassPathResource("/static/img").getFile();
//            File f1= new File(delfile,contact.get().getImage());
//            f1.delete();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//
//            e.printStackTrace();
//        }

        System.out.println(id);
        return "redirect:/Admin/all_contacts/0";
    }
    @PostMapping("/deleteMember")
    public String delete_form1(@RequestParam("hidden") Integer id,HttpSession session)
    {
        session.removeAttribute("message");
        Optional<Contact> contact=contact_repo.findById(id);

        contact_repo.delete(contact.get());
        File delfile;
        try {
            delfile = new ClassPathResource("/static/img").getFile();
            File f1= new File(delfile,contact.get().getImage());
            f1.delete();
        } catch (IOException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
        System.out.println(id);
        return "redirect:/Admin/all_members/0/"+s;
    }
    @PostMapping("/update")
    public String update_form(@RequestParam("hidden") Integer id,Model model,HttpSession session)
    {
        session.removeAttribute("message");
        Optional<Contact> contact=contact_repo.findById(id);
        model.addAttribute("contact", contact.get());
        System.out.println(contact.get());
        return "update_form_member";
    }

    @PostMapping("/update-submit")
    public String update_submit(@ModelAttribute("contact") Contact contact, @RequestParam("photo") MultipartFile file, @RequestParam("image") String image, @RequestParam("id") Integer id, Principal principal, HttpSession session)
    {
        session.removeAttribute("message");
        if(file.isEmpty())
            contact.setImage(image);
        else
        {
            try {
                File delfile = new ClassPathResource("/static/img").getFile();
                File f1= new File(delfile, image);
                f1.delete();
                File savefile = new ClassPathResource("/static/img").getFile();
                Path path= Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                contact.setImage(file.getOriginalFilename());
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
//        String username=principal.getName();
        User user=repository.findByEmail(s);
        contact.setUser(user);
        contact.setCid(id);
        System.out.println(contact);
        contact_repo.save(contact);
        return "redirect:/Admin/all_members/0/"+s;
    }
    @GetMapping("/showFormForUpdate/{id}")
    public  String showFormForUpdate(@PathVariable(value ="id") int id,Model model)
    {
//        Employee  employee = employeeService.getEmployeeById(id);
        Optional<User> user= repository.findById(id);
        model.addAttribute("user",user.get());
        return  "update_form_leader";
    }
    @PostMapping("/updateLeader")
    public String update_formL(@ModelAttribute("user") User user, Model model)
    {
        repository.save(user);
        return "redirect:/Admin/all_contacts/0";
    }
    @GetMapping("/signup")
    public String signup(Model model)
    {
        model.addAttribute("tittle", "Registration page");
        model.addAttribute("user", new User());
        return "signup.html";
    }
}