package com.perscholas.services;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class Utils {
	static Logger log = Logger.getLogger(Utils.class);

	public String writeFile(MultipartFile file, String ISBN13) {
		File serverFile=null;
		if (file != null && !file.isEmpty()) {

			try {
				byte[] bytes = file.getBytes();

				ClassLoader cl = this.getClass().getClassLoader();

				InputStream is = cl.getResourceAsStream("properties/application.properties");

				if (is != null) {
					log.info("be read file");
				} else {
					log.error("cannot read file");
				}

				Properties prop = new Properties();

				prop.load(is);
				// Creating the directory to store file
				String imagesPath = prop.getProperty("images");

				log.info("Read from application.properties imagesPath=" + imagesPath);

				File dir = new File(imagesPath);
				if (!dir.exists())
					dir.mkdirs();

				log.info("dir: " + dir.getAbsolutePath());

				// Create the file on server
				serverFile = new File(
						dir.getAbsolutePath() + File.separator + ISBN13 +"."+ FilenameUtils.getExtension(file.getOriginalFilename()));

				

				log.info("write file started...");

				FileCopyUtils.copy(bytes, serverFile);


				log.info("Server File Location=" + serverFile.getAbsolutePath());

			} catch (Exception e) {
				log.error("You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
				return "Fail";
			}
		} else {

			log.error("You failed to upload because the file was empty.");
			return "Fail";
		}
		return serverFile.getName();

	}

	public File readFile( String fileName) {

		if (fileName != null && !fileName.isEmpty()) {
			try {

				ClassLoader cl = this.getClass().getClassLoader();

				InputStream is = cl.getResourceAsStream("properties/application.properties");

				if (is != null) {
					log.info("be read file");
				} else {
					log.error("cannot read file");
				}

				Properties prop = new Properties();

				prop.load(is);
				// Creating the directory to store file
				String imagesPath = prop.getProperty("images");

				log.info("Read from application.properties imagesPath=" + imagesPath);

				File dir = new File(imagesPath);
				if (!dir.exists())
					return null;

				log.info("dir: " + dir.getAbsolutePath());

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);

				log.info("serverFile: " + serverFile.getAbsolutePath());

				return serverFile;
			} catch (Exception e) {

				return null;

			}
		} else {

			log.info("You failed to upload " + fileName + " because the file was empty.");
			return null;
		}
	}

	/**
	 * This method use to store username and custid in session after login
	 * successful. It call from
	 * MySimpleUrlAuthenticationSuccessHandler.onAuthenticationSuccess method
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param authentication
	 *            Authentication
	 * 
	 */
	public void setUserNameInSession(HttpServletRequest request, String userName) {

		// store username
		request.getSession().setAttribute("userName", userName);
	}

	public void setCustIdInSession(HttpServletRequest request, int custId) {

		// store username
		request.getSession().setAttribute("custId", custId);

	}

	/**
	 * This method use to get username in session
	 * 
	 * @param request
	 * @return username
	 */
	public static String getUserNameFromSession(HttpServletRequest request) {
		return (String) request.getSession().getAttribute("userName");
	}

	/**
	 * 
	 * This method use to get customerid stored in session
	 * 
	 * @param request
	 * @return customer id
	 */

	public static Object getCustIdFromSession(HttpServletRequest request) {

		return request.getSession().getAttribute("custId");
	}
}
