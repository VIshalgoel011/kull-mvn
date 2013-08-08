package com.kull.webxml;

import java.text.MessageFormat;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;

import com.kull.MachineUtil;



public class IpAddressSearchWebService extends BaseWebXmlService {

	
	public IpAddressSearchWebService(){
		this.serviceUrl=SERVICES_BASE_URL+"/"+this.getClass().getSimpleName()+SERVICES_SUBFIX;
	}
	
	public enum Endpoint{
		getCountryCityByIp
	}
	
	public IpGeo thisIpGeo() throws Exception{
		return getCountryCityByIp(MachineUtil.DNSOnWindows());
	}
	
	public IpGeo getCountryCityByIp(String theIpAddress) throws Exception{
		String param=MessageFormat.format("theIpAddress={0}", theIpAddress);
		Document doc=this.doGetEndPoint(Endpoint.getCountryCityByIp.name(), param);
		List<Node> nodes=doc.getRootElement().elements();
		IpGeo ipGeo=this.new IpGeo();
		ipGeo.ip=nodes.get(0).getText();
		ipGeo.geo=nodes.get(1).getText();
		return ipGeo;
	}
	
	public class IpGeo{
		protected String ip,geo;

		public String getIp() {
			return ip;
		}

		public String getGeo() {
			return geo;
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         IpAddressSearchWebService service=new IpAddressSearchWebService();
         try {
			IpGeo ipGeo=service.getCountryCityByIp("10.42.170.92");
			ipGeo=service.thisIpGeo();
			System.out.println(ipGeo.ip+":"+ipGeo.geo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	

}
