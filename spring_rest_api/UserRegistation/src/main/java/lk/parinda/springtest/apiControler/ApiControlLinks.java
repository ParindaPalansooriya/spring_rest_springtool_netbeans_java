package lk.parinda.springtest.apiControler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lk.parinda.springtest.userData.Image;
import lk.parinda.springtest.userData.User;
import lk.parinda.springtest.userData.UserMethords;

@RestController
public class ApiControlLinks {
	
	@Autowired
	UserMethords userMethods;
	
	// GET Methods
	@RequestMapping(method = RequestMethod.GET, value = "/user/all")
	public List<User> getAllUsers(){
		return userMethods.getAllUsers();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/user/one/name/{name}")
	public User getOneUsersByName(@PathVariable String name){
		return userMethods.getOneUsersByName(name);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/user/one/email/{email}")
	public User getOneUsersByEmail(@PathVariable String email){
		return userMethods.getOneUsersByEmail(email);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/user/one/id/{id}")
	public User getOneUsersById(@PathVariable String id){
		return userMethods.getOneUsersById(id);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/user/files/{id}")
	public List<Image> getOneUsersImagesById(@PathVariable String id){
		return userMethods.getImagesById(id);
	}
	
	//POST Methods
	
	@RequestMapping(method = RequestMethod.POST, value = "/user")
	public User addUser(@RequestBody User user) {
		return userMethods.addUser(user);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/user/files/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public User addFile(@RequestParam("file") MultipartFile file, @PathVariable String id) {
		return userMethods.addFile(file, id);
	}
	
	//PUT Methods
	
	@RequestMapping(method = RequestMethod.PUT, value = "/user/{id}")
	public User updateUser(@RequestBody User user, @PathVariable String id) {
		return userMethods.updateUser(user, id);
	}
	
	//DELETE Methods
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/user/{id}")
	public User deleteUser(@PathVariable String id) {
		return userMethods.deleteUser(id);
	}
	
	/*
	@RequestMapping(method = RequestMethod.DELETE, value = "/user/files/{imageid}")
	public User deleteFile(@PathVariable String imageid) {
		return userMethods.deleteFile(imageid);
	}
	*/

}
