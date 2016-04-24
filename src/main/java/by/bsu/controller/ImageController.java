package by.bsu.controller;

import by.bsu.model.ImageInformation;
import by.bsu.service.IImageSearchService;
import com.google.gson.Gson;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/request")
public class ImageController {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    IImageSearchService iImageSearchService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public void addImageInformation(){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ImageInformation imageInformation = new ImageInformation();
        imageInformation.setHash("101");
        imageInformation.setUrl("test");
        session.save(imageInformation);
        session.getTransaction().commit();
    }

    @RequestMapping(value="/findSimilar", method = RequestMethod.POST)
    public @ResponseBody
    String UploadFile(@RequestParam(value="file", required=true) MultipartFile file) throws Exception {
        List<ImageInformation> result = new ArrayList(iImageSearchService.findSimilarImage(file));
        String json = new Gson().toJson(result);
        return json;
    }

}
