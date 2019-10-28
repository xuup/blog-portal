package com.blog.controller.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.blog.dto.PictureDto;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;


@Controller
@RequestMapping("checkPic")
public class CheckPicController {
	
	static Logger logger = LoggerFactory.getLogger(CheckPicController.class);
	
	static double ee = 0.00669342162296594323;
	
	static double a = 6378245.0;
	
	static double pi = 3.1415926535897932384626;
	
	static String api_key = "3e80546942165c93a07c3bda8d63f85f";
	
	static String api_getposition_url = "https://restapi.amap.com/v3/geocode/regeo?";
	
	static String base_dir = "/tmp/images";
	
	@RequestMapping("index")
	public String index(HttpServletRequest request){
		return "tool/fileUpload";
	}
	
	@ResponseBody
	@RequestMapping("upload")
	public PictureDto uploadFile(MultipartFile picFile) throws IllegalStateException, IOException{
		String name = picFile.getName();
		String originalFileName = picFile.getOriginalFilename();
		String extName = originalFileName.substring(originalFileName.lastIndexOf("."));
		if(!(extName.equals(".jpg") || extName.equals(".png")) ){
			return null;
		}
		String newFileName = UUID.randomUUID().toString() + extName;
		// 指定存储文件的根目录
		File dirFile = new File(base_dir);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
		
		String newFilePath = base_dir +"/"+ newFileName;
		picFile.transferTo(new File(newFilePath));
		PictureDto pictureDto = getPicInfo(newFilePath);
		pictureDto.setNewFileName(newFileName);
		return pictureDto;
	}
	
	public PictureDto getPicInfo(String filePath){
		
		File file = new File(filePath);
		String artist = "";
		String make = "";
		String model = "";
		String time = "";
		String location = "";
		String latitudeStr = "";
		String longitudeStr = "";
		PictureDto pictureDto = new PictureDto();
        try {
			Metadata metadata = ImageMetadataReader.readMetadata(file);
			for(Directory dictionary : metadata.getDirectories()) {
				for(Tag tag:dictionary.getTags()) {
					System.out.println(tag);
				}
			}
			
			/**
			 * 获取拍摄时间 
			 */
			ExifIFD0Directory exifIFD0Directory = 
					metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			if(exifIFD0Directory != null){
				artist = exifIFD0Directory.getDescription(ExifIFD0Directory.TAG_ARTIST);
				make = exifIFD0Directory.getDescription(ExifIFD0Directory.TAG_MAKE);
				model = exifIFD0Directory.getDescription(ExifIFD0Directory.TAG_MODEL);
			}
			
			
			ExifSubIFDDirectory exifSubIFDDirectory = 
					metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
			if(exifSubIFDDirectory != null){
				time = exifSubIFDDirectory.getDescription(ExifIFD0Directory.TAG_DATETIME_ORIGINAL);
			}
			
			System.out.println("图像信息： " + artist + "  " + make + " " + model + " " + time);
			
			//get gps
			System.out.println("转换经纬度信息......");
			// obtain the Exif directory
			GpsDirectory directory
			    = metadata.getFirstDirectoryOfType(GpsDirectory.class);
			
			if(directory != null){
				// query the tag's value
				String latitude = directory.getDescription(GpsDirectory.TAG_LATITUDE);
				String longitude = directory.getDescription(GpsDirectory.TAG_LONGITUDE);
				String newlatitude = translateLocation(latitude);
				String newlongtitude = translateLocation(longitude);
				double[] hxlocation = wgs84togcj02(newlatitude, newlongtitude);
				
				System.out.println("获取到经度纬度： " + hxlocation[0] + " " + hxlocation[1]);
				/**
				 *  获取位置信息
				 */
				latitudeStr = hxlocation[1] + "";
				longitudeStr = hxlocation[0] + "";
				location = getAddress(hxlocation[1], hxlocation[0]); 
			}
			
			pictureDto.setLatitude(latitudeStr);
			pictureDto.setLongitude(longitudeStr);
			pictureDto.setFilePath(filePath);
			pictureDto.setMake(make);
			pictureDto.setModel(model);
			pictureDto.setCreateTime(time);
			pictureDto.setLocation(location);
			
		} catch (ImageProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pictureDto;
	}
	
	/**
     * 转换经纬度坐标值
     * @param location
     * @return
     */
    public String translateLocation(String location) {
    	String[] locations = location.split(" ");
    	String hour = locations[0].substring(0, locations[0].length()-1);
    	String minutes= locations[1].substring(0,locations[1].length()-1);
    	String seconds = locations[2].substring(0, locations[2].length()-1);
    	
    	double locationVal = Double.valueOf(hour) + Double.valueOf(minutes)/60 + Double.valueOf(seconds)/3600;
    	String s=String.format("%.6f",locationVal);
    	logger.info("转换后的经纬度{}", s);
    	return s;
    }
    
    
    /**
     * wgs坐标转cj02坐标
     * @param latitude
     * @param longtitude
     * @return
     */
    public double[] wgs84togcj02(String latitude, String longtitude) {
    	double lat = Double.valueOf(latitude);
    	double lgt = Double.valueOf(longtitude);
    	double[] locations = new double[2];
    	if(lat < 0.8293 || lat > 55.8271 || lgt < 72.004 || lgt > 137.8374) {
    		locations[0] = lat;
    		locations[1] = lgt;
    		return locations;
    	}
    	
    	double dlat = transFormLat(lat - 35.0, lgt - 105.0);
    	double dlgt = transFormLgt(lat - 35.0, lgt - 105.0);
    	double radlat = lat / 180.0 * pi;
    	double magic = Math.sin(radlat);
    	magic = 1 - ee * magic * magic;
    	double sqrtmagic = Math.sqrt(magic);
    	double ddlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * pi);
    	double ddlgt = (dlgt * 180.0) / (a / sqrtmagic * Math.cos(radlat) * pi);

    	double mglat = lat + ddlat;
    	double mglgt = lgt + ddlgt;
    	
    	locations[0] = mglat;
    	locations[1] = mglgt;
    	
    	return locations;
    }
    
    
    
    public double transFormLat(double lat, double lgt) {
    	double ret = (double) (-100.0 + 2.0 * lgt + 3.0 * lat + 0.2 * lat * lat + 0.1 * lgt * lat + 0.2 * Math.sqrt(Math.abs(lgt)));
    	ret =  (double) (ret + (20.0 * Math.sin(6.0 * lgt * pi) + 20.0 * Math.sin(2.0 * lgt * pi)) * 2.0 / 3.0);
    	ret += (20.0 * Math.sin(lat * pi) + 40.0 * Math.sin(lat / 3.0 * pi)) * 2.0 / 3.0;
	    ret += (160.0 * Math.sin(lat / 12.0 * pi) + 320 * Math.sin(lat * pi / 30.0)) * 2.0 / 3.0;
    	return ret;
    }
    
    public double transFormLgt(double lat, double lgt) {
    	double ret = (double) (300.0 + lgt + 2.0 * lat + 0.1 * lgt * lgt + 0.1 * lgt * lat + 0.1 * Math.sqrt(Math.abs(lgt)));
	    ret += (20.0 * Math.sin(6.0 * lgt * pi) + 20.0 * Math.sin(2.0 * lgt * pi)) * 2.0 / 3.0;
	    ret += (20.0 * Math.sin(lgt * pi) + 40.0 * Math.sin(lgt / 3.0 * pi)) * 2.0 / 3.0;
	    ret += (150.0 * Math.sin(lgt / 12.0 * pi) + 300.0 * Math.sin(lgt / 30.0 * pi)) * 2.0 / 3.0;
	    return ret;
    }
    
    
    /**
     * 根据坐标获取详细地址
     * @param latitude
     * @param longtitude
     * @return
     */
    
    public String getAddress(double latitude, double longtitude) {
    	
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	HttpGet httpGet = new HttpGet(api_getposition_url + "key=" + api_key + "&location=" + latitude + "," + longtitude);
    	HttpResponse response;
    	String result = "";
    	BufferedReader bufferedReader = null;
    	StringBuffer entityBuffer = new StringBuffer();
    	try {
    		response = httpclient.execute(httpGet);
    		HttpEntity entity = response.getEntity();
    		bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
    		String line = null;
    		while((line = bufferedReader.readLine()) != null) {
    			entityBuffer.append(line + "\n");
    		}
    		result = entityBuffer.toString();
		} catch (ClientProtocolException e) {
			logger.error("请求地图信息异常");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("请求地图信息I/O异常");
			e.printStackTrace();
		}
    	logger.info(result);
    	JSONObject jsonObject = new JSONObject(result);
    	String address = jsonObject.getJSONObject("regeocode").getString("formatted_address");
    	logger.info("地点坐标为:{}", address);
    	return address;
    }

}
