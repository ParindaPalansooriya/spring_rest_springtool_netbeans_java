package lk.parinda.springtest.userData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserMethords {

	@Autowired
	UserRepository user_repository;
	
	@Autowired
	ImageRepository image_repository;

	public List<User> getAllUsers() {
		return user_repository.findAll();
	}

	public User getOneUsersByName(String name) {
		return user_repository.getOneByName(name).get(0);
	}

	public User getOneUsersByEmail(String email) {
		return user_repository.getOneByEmail(email).get(0);
	}

	public User getOneUsersById(String id) {
		return user_repository.findById(Integer.valueOf(id)).get();
	}

	public List<Image> getImagesById(String id) {
		// TODO Auto-generated method stub
		return image_repository.getImagesById(id);
	}

	public User addUser(User user) {
		return user_repository.save(user);
	}

	public User addFile(MultipartFile file, String id) {
		Image tempImage = new Image();
		User returnUser = new User();
		String saveFileName = file.getOriginalFilename();
		String fileDirection = "C:\\wamp\\www\\restSpringUploadFiles\\"+id;
		
		File fileDirect = new File(fileDirection);
		if(!fileDirect.exists()) {
			fileDirect.mkdirs();
		}
		File converter;
		while(true){
			converter = new File(fileDirection+"\\"+saveFileName);
            if(!converter.exists()) {
                break;
            }else{
            	saveFileName = "copy_"+saveFileName;
            }
        }
		
		try {
			converter.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(converter);
			fileOut.write(file.getBytes());
			fileOut.close();
			
			tempImage.setId(id);
			tempImage.setFile_name(saveFileName);
			image_repository.save(tempImage);
			
			returnUser.setId(200);
			returnUser.setName(saveFileName);
			return returnUser;
		} catch (IOException e) {
			returnUser.setId(400);
			returnUser.setName("fille upload not success. Error massage : "+e.getMessage());
			return returnUser;
		}
		
	}

	public User updateUser(User user, String id) { 
		User returnUser = new User();
		User tempUser = getOneUsersById(id);
		if(user.getEmail() == null) {
			user.setEmail(tempUser.getEmail());
		}if(user.getName() == null) {
			user.setName(tempUser.getName());
		}if(user.getId() == null) {
			user.setId(tempUser.getId());
		}if(user.getPassword() == null) {
			user.setPassword(tempUser.getPassword());
		}
		user_repository.save(user);
		returnUser.setId(200);
		returnUser.setName("Request Success!");
		return returnUser;
	}

	public User deleteUser(String id) {
		User returnUser = new User();
		user_repository.deleteById(Integer.valueOf(id));
		returnUser.setId(200);
		returnUser.setName("Request Success!");
		return returnUser;
	}
	
}
