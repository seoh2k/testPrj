package app.com.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired 
	HomeService homeService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		List<Users> usersList = homeService.getUsersList();
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("usersList", usersList);
		
		return "home";
	}
	
	@RequestMapping(value = "/getUserOne")
	public String getUserOne(Model model,
								@RequestParam(value="id", required = true) int id) {
		
		Users user = homeService.getUserOne(id);
		
		model.addAttribute("user", user);
		
		return "getUserOne";
	}
	
	@RequestMapping(value = "/removeUser", method = RequestMethod.POST)
	public String removeUser(@RequestParam(value="id", required = true) int id) {
		
		homeService.removeUser(id);
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
	public String modifyUser(Users user) {
		
		homeService.modifyUser(user);
		
		return "redirect:/getUserOne?id="+user.getId();
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public String addUser() {
		
		return "addUser";
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(Users user) {
		
		homeService.addUser(user);
		
		return "redirect:/";
	}
}
