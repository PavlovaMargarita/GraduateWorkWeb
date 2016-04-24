package by.bsu.controller;

import by.bsu.Utils;
import by.bsu.algorithm.search.Hash;
import by.bsu.algorithm.search.PHash;
import by.bsu.model.ImageInformation;
import by.bsu.service.IImageSearchService;
import com.cloudinary.Cloudinary;
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
import com.cloudinary.utils.ObjectUtils;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

@Controller
@RequestMapping("/request")
public class ImageController {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    IImageSearchService iImageSearchService;

    @RequestMapping(value="/findSimilar", method = RequestMethod.POST)
    public @ResponseBody
    String findSimilarImages(@RequestParam(value = "file", required = true) MultipartFile file) throws Exception {
        List<ImageInformation> result = new ArrayList(iImageSearchService.findSimilarImage(file));
        String json = new Gson().toJson(result);
        return json;

//        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//                "cloud_name", "graduatework",
//                "api_key", "593791151728485",
//                "api_secret", "yU_xhmPOozFfRQa3izDzT3sIFmE"));
//        BufferedImage bi = ImageIO.read(file.getInputStream());
//        File outputfile = new File("tempfile.jpg");
//        ImageIO.write(bi, "jpg", outputfile);
//        Map uploadResult = cloudinary.uploader().upload(outputfile, null);
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        ImageInformation imageInformation = new ImageInformation();
//        imageInformation.setHash(Utils.arrayToString(Hash.getHash(bi)));
//        imageInformation.setpHash(Utils.arrayToString(PHash.getHash(bi)));
//        imageInformation.setUrl(uploadResult.get("url").toString());
//        session.save(imageInformation);
//        session.getTransaction().commit();

//        return "test";
    }

    @RequestMapping(value="/retrieveFace", method = RequestMethod.POST)
    public @ResponseBody
    String retrieveFace(@RequestParam(value = "file", required = true) MultipartFile file) throws Exception {
        return iImageSearchService.retrieveFace(file);
    }
}
