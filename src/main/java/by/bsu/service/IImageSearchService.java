package by.bsu.service;

import by.bsu.model.ImageInformation;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public interface IImageSearchService {
    public Set<ImageInformation> findSimilarImage(MultipartFile file) throws Exception;
    public List<String> retrieveFace(MultipartFile file) throws IOException;
}
