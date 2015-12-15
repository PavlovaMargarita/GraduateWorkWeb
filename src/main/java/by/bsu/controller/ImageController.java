package by.bsu.controller;

import by.bsu.model.ImageInformation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;

@Controller
public class ImageController {

    @Autowired
    SessionFactory sessionFactory;

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
}
