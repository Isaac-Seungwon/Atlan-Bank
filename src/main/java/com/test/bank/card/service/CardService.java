package com.test.bank.card.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.bank.card.domain.BenefitsDTO;
import com.test.bank.card.domain.CardAnnualFeeDTO;
import com.test.bank.card.domain.CardDTO;
import com.test.bank.card.repository.CardDAO;

@Service
public class CardService {

	@Autowired
	private CardDAO dao;

	public List<CardDTO> getCategoryCreditCardList(String category) {
		
		switch (category) {
			case "saving-discount" : category = "적립/할인"; break;
			case "travle" : category = "여행"; break;
			case "shopping" : category = "쇼핑"; break;
			case "charging" : category = "주유/충전"; break;
			case "communication" : category = "통신"; break;
			case "education" : category = "교육"; break;
			case "medical" : category = "의료"; break;
			case "food" : category = "음식"; break;
			case "rental" : category = "렌탈"; break;
			case "choice" : category = "선택형"; break;
			case "public" : category = "공공/정부지원"; break;
		}
		
		List<CardDTO> list = dao.getCategoryCreditCardList(category);
		
		//Add annualFeeList to CardDTOList
		for (CardDTO card : list) {
			List<CardAnnualFeeDTO> feeList = dao.getAnnualFeeList(card.getCardSeq());
			
			for (CardAnnualFeeDTO f : feeList) {
				int fee = Integer.parseInt(f.getAnnualFee());
				String newFee = String.format("%,d", fee);
				f.setAnnualFee(newFee);
			}
			
			card.setFeeList(feeList);
			//System.out.println(card.toString());
		}
		
		return list;
	}

	public List<CardDTO> getNewCreditCardList() {
		return dao.getNewCreditCardList();
	}

	public CardDTO getCreditCard(String seq) {
		
		CardDTO dto = dao.getCard(seq);
		
		//Add annualFeeList to CardDTO
		List<CardAnnualFeeDTO> feeList = dao.getAnnualFeeList(seq);
		
		for (CardAnnualFeeDTO f : feeList) {
			int fee = Integer.parseInt(f.getAnnualFee());
			String newFee = String.format("%,d", fee);
			f.setAnnualFee(newFee);
		}
		
		dto.setFeeList(feeList);
		
		//Add benefitList to CardDTO
		List<BenefitsDTO> benefitList = dao.getBenefitList(seq);
		dto.setBenefitList(benefitList);
		
		//System.out.println(benefitList.toString());
		
		return dto; 
	}

	public List<CardDTO> getCategoryDebitCardList(String category) {

		switch (category) {
			case "discount" : category = "할인"; break;
			case "saving" : category = "적립"; break;
			case "food" : category = "음식"; break;
			case "shopping" : category = "쇼핑"; break;
			case "travle" : category = "여행"; break;
			case "communication" : category = "통신"; break;
			case "education" : category = "교육"; break;
			case "culture" : category = "문화"; break;
			case "id" : category = "학생증"; break;
			case "public" : category = "공공/정부지원"; break;
		}
		
		return dao.getCategoryDebitCardList(category);
	}

	public CardDTO getDebitCard(String seq) {
		return dao.getCard(seq);
	}

	
}
