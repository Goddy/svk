package be.spring.app.controller;

import be.spring.app.form.UploadForm;
import be.spring.app.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController extends AbstractController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	ImageService imageService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String testPost(@RequestParam("name") String name,
						   @RequestParam("file") MultipartFile file, ModelMap modelMap) {
		try {
			String test = imageService.upload(file);
			modelMap.put("image", imageService.getImage(test, "png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "test";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String testGet(ModelMap map) {
		UploadForm form = new UploadForm();
		map.put("form", form);
		return "test";
	}
}
