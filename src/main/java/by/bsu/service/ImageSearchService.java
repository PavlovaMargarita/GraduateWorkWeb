package by.bsu.service;

import by.bsu.Utils;
import by.bsu.algorithm.search.Hash;
import by.bsu.algorithm.search.PHash;
import by.bsu.model.ImageInformation;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class ImageSearchService implements IImageSearchService {

    @Autowired
    SessionFactory sessionFactory;

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

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

    @Override
    public List<String> retrieveFace(MultipartFile file) throws IOException {

        BufferedImage bi = ImageIO.read(file.getInputStream());
        File outputfile = new File("tempfile.jpg");
        ImageIO.write(bi, "jpg", outputfile);
        Mat image = Highgui.imread("tempfile.jpg");
        Mat image2 = Highgui.imread("tempfile.jpg");

        // Detect faces in the image.
        // MatOfRect is a special container class for Rect.
        MatOfRect faceDetections = new MatOfRect();
        CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("/haarcascade_frontalface_alt.xml").getPath().substring(1));
        faceDetector.detectMultiScale(image, faceDetections);

        // Draw a bounding box around each face.
        for (Rect rect : faceDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }

        for(int i = 0; i < faceDetections.toArray().length; i += 2){
            Rect rect = faceDetections.toArray()[i];
            Core.rectangle(image2, new Point(rect.x-5, rect.y-7), new Point(rect.x + rect.width + 8, rect.y + rect.height+ 3), new Scalar(0, 255, 0));
        }

        // Save the visualized detection.
        Highgui.imwrite("faceDetection.jpg", image);
        Highgui.imwrite("faceDetection2.jpg", image2);

        //load file to cloudinary
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "graduatework",
                "api_key", "593791151728485",
                "api_secret", "yU_xhmPOozFfRQa3izDzT3sIFmE"));
        Map uploadResult = cloudinary.uploader().upload(new File("faceDetection.jpg"), null);
        Map uploadResult2 = cloudinary.uploader().upload(new File("faceDetection2.jpg"), null);

        List<String> result = new ArrayList<>(2);
        result.add(uploadResult.get("url").toString());
        result.add(uploadResult2.get("url").toString());
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
