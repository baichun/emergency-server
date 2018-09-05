package com.comtom.bc.exchange.service.impl;

import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.signature.Signature;
import com.comtom.bc.exchange.util.XmlUtil;
import com.comtom.sign.SignatureHelper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @author nobody 签名文件的解析验证
 */
@Service
public class SignValidateService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public boolean analysis(File file, File ebdFile, EBD eBD) {

		if (logger.isInfoEnabled()) {
			logger.info("SignValidateService.analysis start.");
		}

		boolean result = false;

		// 将文件转换xml
		String xmlString;

		try {
			xmlString = FileUtils.readFileToString(file, "utf-8");

			// 签名对象
			Signature sigNature = XmlUtil.fromXml(xmlString);
			String relatedEBDID = sigNature.getRelatedEBD().getEBDID();
			if (!eBD.getEBDID().equals(relatedEBDID)) {
				return false;
			}
			 String certSN = sigNature.getSignatureCert().getCertSN();
			 String signCounter = sigNature.getSignatureCert().getIssuerID();
			 String digestAlgorithm = sigNature.getDigestAlgorithm();
			 String signatureAlgorithm = sigNature.getSignatureAlgorithm();
			 String signatureValue = sigNature.getSignatureValue();

			// 验证签名
			 result = SignatureHelper.verifySign(ebdFile, signatureValue);
//			// TODO 验签不了
//			result = true;
		} catch (IOException e) {
			logger.error("Sign validate exception.", e.getMessage());
		}

		return result;
	}
}
