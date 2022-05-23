package jp.co.seattle.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.seattle.library.service.RentalsService;

/**
 * 貸出履歴用コントローラー
 */
@Controller
public class LendingHistroyController {
	
	@Autowired
	RentalsService rentalsService;

		 @RequestMapping(value = "/lendingHistroy", method = RequestMethod.GET) //value＝actionで指定したパラメータ
		    //RequestParamでname属性を取得
		    public String lendingHistroy(Model model) {
			 
			 	model.addAttribute("rentList", rentalsService.getRentList());
			 	
		        return "history";
		 }
}
