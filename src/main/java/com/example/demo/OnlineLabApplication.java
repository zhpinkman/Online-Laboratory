package com.example.demo;

import com.example.demo.controllers.MzLabController;
import com.example.demo.domain.handlers.MzLab;
import com.example.demo.domain.lab.PhlebotomistInfo;
import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.user.Prescription;
import com.example.demo.domain.utility.Address;
import com.example.demo.domain.utility.FullTestInfo;
import com.example.demo.domain.utility.Receipt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class OnlineLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineLabApplication.class, args);
//		runTestCommands();
	}

	private static void runTestCommands(){
		try {
			MzLab mzLab = MzLab.getInstance();
			List<TestDesc> tests = mzLab.getAllTests();
			List<Prescription> presc = mzLab.getReviewedPrescriptions();

			List<String> selectedTests = new ArrayList<>();
			selectedTests.add(tests.get(0).getTestName());  ////

			mzLab.setPatientTests(selectedTests);
			List<Address> addresses = mzLab.getPatientAddresses();
			mzLab.setPatientTestAddress(addresses.get(0));
			mzLab.attachPrescriptionToTest(presc.get(0).getId());
			List<FullTestInfo> fullTestInfoList = mzLab.verifyPatientTestRequest();
			mzLab.setSelectedLabForTests(fullTestInfoList.get(0).getLabName());
			List<Date> dates = mzLab.confirmTestInfo();
			PhlebotomistInfo phlebotomistInfo= mzLab.selectTimeForTest(dates.get(0));
			Receipt receipt = mzLab.getReceipt();
			mzLab.confirmPaymentReceipt();

		}catch (Exception e){
			e.printStackTrace();
		}


	}

}
