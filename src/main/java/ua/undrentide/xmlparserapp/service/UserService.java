package ua.undrentide.xmlparserapp.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.undrentide.xmlparserapp.dal.UserRepository;
import ua.undrentide.xmlparserapp.entity.User;
import ua.undrentide.xmlparserapp.exception.IncorrectOperationException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void importUser(MultipartFile multipartFile) {
        File uploadedFile = new File(System.getProperty("user.dir")
                + "/" + multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(uploadedFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream("User.xml");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        XMLDecoder decoder = new XMLDecoder(fileInputStream);
        User user = (User) decoder.readObject();
        if (user == null || user.getName() == null || user.getEmail() == null || user.getPhone() == null) {
            throw new IncorrectOperationException("Invalid user data.");
        }
        userRepository.save(user);
        decoder.close();
        try {
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportUser(Long id, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=User.xml";
        response.setHeader(headerKey, headerValue);
        User user = userRepository.findById(id).orElseThrow();
        ServletOutputStream servletOutputStream;
        try {
            servletOutputStream = response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        XMLEncoder xmlEncoder = new XMLEncoder(servletOutputStream);
        xmlEncoder.writeObject(user);
        xmlEncoder.close();
        try {
            servletOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void fetchUserListById(List<Long> idList, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=UserList.xls";
        response.setHeader(headerKey, headerValue);
        List<User> userList = new ArrayList<>();
        for (Long innerId : idList) {
            userList.add(userRepository.findById(innerId).orElseThrow());
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Users");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("name");
        row.createCell(3).setCellValue("email");
        row.createCell(4).setCellValue("phone");
        int dataRowIndex = 1;
        for (User user : userList) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(user.getId());
            dataRow.createCell(1).setCellValue(user.getName());
            dataRow.createCell(3).setCellValue(user.getEmail());
            dataRow.createCell(4).setCellValue(user.getPhone());
            dataRowIndex++;
        }
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        ServletOutputStream servletOutputStream;
        try {
            servletOutputStream = response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            workbook.write(servletOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            servletOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}