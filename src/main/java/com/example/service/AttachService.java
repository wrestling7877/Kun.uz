package com.example.service;

import com.example.dto.entityDto.AttachDto;
import com.example.entity.AttachEntity;
import com.example.exp.ItemNotFoundException;
import com.example.repository.ArticleTypeRepository;
import com.example.repository.AttachRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class AttachService {
    @Autowired
    private AttachRepository attachRepository;

    @Value("${attach.upload.folder}")
    public String attachUploadFolder;

    @Value("${attach.download.url}")
    public String attachDownloadUrl;
    @Autowired
    private ArticleTypeRepository articleTypeRepository;


    public String saveToSystem(MultipartFile file) {
        try {
            String pathFolder = getYearMonthDay(); // 2001/09/15
            File folder = new File(attachUploadFolder + pathFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String fileName = UUID.randomUUID().toString();
            String extension = getExtension(file.getOriginalFilename());

            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachUploadFolder + pathFolder + "/" + fileName + "." + extension);
            Files.write(path, bytes);

            AttachEntity attachEntity = new AttachEntity();
            attachEntity.setId(fileName);
            attachEntity.setSize(file.getSize());
            attachEntity.setPath(pathFolder);
            attachEntity.setExtension(extension);
            attachEntity.setOriginalName(file.getOriginalFilename());
            attachEntity.setCreatedDate(LocalDateTime.now());
            attachRepository.save(attachEntity);

            AttachDto dto = new AttachDto();
            dto.setSize(file.getSize());
            dto.setPath(pathFolder);
            dto.setId(fileName);
            dto.setExtension(extension);
            dto.setOriginalName(file.getOriginalFilename());
            dto.setCreatedData(LocalDateTime.now());
            dto.setUrl(attachDownloadUrl + fileName + extension);

            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean fileDelete(String id) {

        Optional<AttachEntity> entity = attachRepository.findById(id);
        File file = new File(attachUploadFolder + entity.get().getPath() + "/" + id + "." + entity.get().getExtension());

           boolean isDeleted = file.delete();
             if (isDeleted){
                 attachRepository.deleteById(id);
             }
             return isDeleted;
    }

    public Page<AttachDto> loadImageAllPage(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<AttachEntity> pageObj = attachRepository.findAll(pageable);
        List<AttachEntity> entityList = pageObj.getContent();
        long totalElement = pageObj.getTotalElements();

        List<AttachDto> content = new LinkedList<>();

        for (AttachEntity entity : entityList) {

            AttachDto dto = new AttachDto();
            dto.setSize(entity.getSize());
            dto.setPath(entity.getPath());
            dto.setId(entity.getId());
            dto.setExtension(entity.getExtension());
            dto.setOriginalName(entity.getOriginalName());
            dto.setCreatedData(entity.getCreatedDate());
            dto.setUrl(attachDownloadUrl + entity.getId());
            content.add(dto);

        }

        return new PageImpl<>(content, pageable, totalElement);
    }

    // second page
//    public Page<byte[]> loadImageAllPage(Integer page, Integer size) {
//        BufferedImage originalImage;
//        Pageable pageable = PageRequest.of(page, size);
//        Page<AttachEntity> pageObj = attachRepository.findAll(pageable);
//        List<AttachEntity> entityList = pageObj.getContent();
//        long totalElement = pageObj.getTotalElements();
//
//        List<byte[]> content = new LinkedList<>();
//        try {
//
//
//                for (AttachEntity entity : entityList) {
//
//                    originalImage = ImageIO.read(new File(attachUploadFolder + entity.getPath() + "/" + entity.getId() + "." + entity.getExtension()));
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    ImageIO.write(originalImage, "png", outputStream);
//                    outputStream.flush();
//                    byte[] imageInByte = outputStream.toByteArray();
//                    outputStream.close();
//                    content.add(imageInByte);
//
//                }
//
//
//
//        } catch (Exception e) {
//            return null;
//        }
//
//        return new PageImpl<>(content,pageable,totalElement);
//    }

    public byte[] loadImage(String fileNameId) {
        String id = fileNameId.split("\\.")[0];
        BufferedImage originalImage;

        try {


            Optional<AttachEntity> entity = attachRepository.findById(id);
            if (entity.isEmpty()) {
                return new byte[0];
            }

            originalImage = ImageIO.read(new File(attachUploadFolder + entity.get().getPath() + "/" + id + "." + entity.get().getExtension()));
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", byteStream);

            byteStream.flush();

            byte[] imageInByte = byteStream.toByteArray();
            byteStream.close();
            return imageInByte;

        } catch (Exception e) {
            return new byte[0];
        }
    }
//    public byte[] loadImage(String fileName) {
//        BufferedImage originalImage;
//
//        try {
//            String id = fileName.split("\\.")[0];
//
//            Optional<AttachEntity> entity = attachRepository.findById(id);
//            if (entity.isEmpty()) {
//                return new byte[0];
//            }
//
//            originalImage = ImageIO.read(new File(attachUploadFolder + entity.get().getPath() + fileName));
//            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
//            ImageIO.write(originalImage, "png", byteStream);
//
//            byteStream.flush();
//
//            byte[] imageInByte = byteStream.toByteArray();
//            byteStream.close();
//            return imageInByte;
//
//        } catch (Exception e) {
//            return new byte[0];
//        }
//    } ////

    public byte[] open_general(String fileNameId) {
        String id = fileNameId.split("\\.")[0];
        Optional<AttachEntity> optional = attachRepository.findById(id);
        AttachEntity entity = optional.get();
        try {
            Path path = Paths.get(attachUploadFolder + entity.getPath() + "/" + id + "." + entity.getExtension());
            byte[] data = Files.readAllBytes(path);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

//    public byte[] open_general(String fileName) {
//
//        try {
//            Path path = Paths.get(attachUploadFolder + fileName);
//            byte[] data = Files.readAllBytes(path);
//            return data;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new byte[0];
//    }

    public Resource download(String id) {
       BufferedImage originalImage;
       Optional<AttachEntity> optional = attachRepository.findById(id);
       AttachEntity entity = optional.get();
        try {

            originalImage = ImageIO.read(new File(attachUploadFolder + entity.getPath() + "/" + id + "." + entity.getExtension()));

            Path file = Paths.get(attachUploadFolder +entity.getPath()+"/"+ id+"."+entity.getExtension());

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file !");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error:" + e.getMessage());
        }
    }


    public String getYearMonthDay() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; //2001/09/15
    }


    public String getExtension(String fileName) {//jpg,png,mp4,mp3...

        int lastIndex = fileName.lastIndexOf(".");

        return fileName.substring(lastIndex + 1);

    }


    public AttachDto getById(String id) {
        Optional<AttachEntity> optional = attachRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Attach Not Found");
        }

        AttachDto dto = new AttachDto();
        dto.setId(id);
        dto.setUrl(attachDownloadUrl  + id + "." + optional.get().getExtension());
        return dto;
    }



    public String saveToSystemOld(MultipartFile file) {


        try {

            File folder = new File(attachUploadFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get("attaches/" + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
