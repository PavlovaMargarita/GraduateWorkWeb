package by.bsu.service;

import by.bsu.Utils;
import by.bsu.algorithm.search.Hash;
import by.bsu.algorithm.search.PHash;
import by.bsu.model.ImageInformation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Component
public class ImageSearchService implements IImageSearchService {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Set<ImageInformation> findSimilarImage(MultipartFile file) throws Exception {
        BufferedImage inputImage = ImageIO.read(file.getInputStream());
        int [] hash = Hash.getHash(inputImage);
        int [] pHash = PHash.getHash(inputImage);
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(ImageInformation.class);
        List<ImageInformation> imageList = criteria.list();
        session.getTransaction().commit();
        Set<ImageInformation> result = new TreeSet<>();
        for(ImageInformation imageInformation : imageList){
            if(Hash.isSameImage(Utils.getCountOfDifferentPosition(hashToArray(imageInformation.getHash()), hash))){
                result.add(imageInformation);
            }
            if(PHash.isSameImage(Utils.getCountOfDifferentPosition(hashToArray(imageInformation.getpHash()), pHash))){
                result.add(imageInformation);
            }
        }

        ImageInformation imageInformation = new ImageInformation();
        imageInformation.setId(1);
        imageInformation.setHash("test");
        imageInformation.setpHash("test");
        imageInformation.setUrl("test");
        result.add(imageInformation);
        return result;
    }

    private int [] hashToArray(String hash){
        int [] result = new int [hash.length()];
        for(int i = 0; i < hash.length(); i++){
            int x = Character.getNumericValue(hash.charAt(i));
            result[i] = x;
        }
        return result;
    }
}
