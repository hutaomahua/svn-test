package com.lyht.util;
 
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
/**
 * 阿里云短信发送工具类
 * @author Justin
 * @date 2017年12月12日
 *
 */
public class SendSMSUtil {
	private static Logger log = LoggerFactory.getLogger(SendSMSUtil.class);
	//产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
 
    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId ="LTAI7P3dYeyUNhpf";// "LTAIcMduyXiF4pVv";
    static final String accessKeySecret ="Mb1UiPKEjo94Tw9eU8XHaBLjhCVSEk"; //"kZgz1d1MTlc9Oiq7AxjAEHH9QkWC3S";
    
   /**
    * 
    * @param phones	发送的手机号（多个手机号用“,”号隔开，如String phones = "13877901111,13877902222";）
    * @param params	短信模板的参数，格式如String params = "{\"name\":\"李四\",\"code\":\"1234\"}";
    * @return
    * @throws ClientException
    */
    public static SendSmsResponse sendSms(String phones,String jsonObjectStringParams) throws ClientException {
 
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
 
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-beijing", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-beijing", "cn-beijing", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
 
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        /*request.setPhoneNumbers("13877906666,13877907777,13877908888");*/
        request.setPhoneNumbers(phones);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("容题网");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_159782310");//SMS_151579531");//
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为String param = "{\"name\":\"张三\",\"code\":\"1234\"}";
        request.setTemplateParam(jsonObjectStringParams);
 
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
 
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
 
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
 
        return sendSmsResponse;
    }
 
    public static QuerySendDetailsResponse querySendDetails(String bizId,String phones) throws ClientException {
 
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
 
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
 
        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phones);
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);
 
        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
 
        return querySendDetailsResponse;
    }
    
    /**
     * 调用发送短信方法并返回相关数据
     * @param phones
     * @param params
     * @throws ClientException
     * @throws InterruptedException
     */
    public static void send(String phones,String jsonObjectStringParams) throws ClientException, InterruptedException{
    	//调用发短信的方法
        SendSmsResponse response = sendSms(phones,jsonObjectStringParams);
        log.info("短信接口返回的数据----------------");
        log.info("Code=" + response.getCode());
        log.info("Message=" + response.getMessage());
        log.info("RequestId=" + response.getRequestId());
        log.info("BizId=" + response.getBizId());
 
        Thread.sleep(3000L);
 
        //查明细
        if(response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId(),phones);
            log.info("短信明细查询接口返回数据----------------");
            log.info("Code=" + querySendDetailsResponse.getCode());
            log.info("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                log.info("SmsSendDetailDTO["+i+"]:");
                log.info("Content=" + smsSendDetailDTO.getContent());
                log.info("ErrCode=" + smsSendDetailDTO.getErrCode());
                log.info("OutId=" + smsSendDetailDTO.getOutId());
                log.info("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                log.info("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                log.info("SendDate=" + smsSendDetailDTO.getSendDate());
                log.info("SendStatus=" + smsSendDetailDTO.getSendStatus());
                log.info("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            log.info("TotalCount=" + querySendDetailsResponse.getTotalCount());
            log.info("RequestId=" + querySendDetailsResponse.getRequestId());
        }
    }
    
    public static void main(String[] args) throws ClientException, InterruptedException {
    	//发送短信的手机号（多个号码用英文","号隔开）
//    	String phones = "13426406656";
////    	String name = "张三";
//    	String  code = "861224";  
//    	Map<String,String> map = new HashMap<String,String>();
////		map.put("name",name);
//		map.put("code", code); 
//		String jsonObjectStringParams = JSONObject.fromObject(map).toString();	//短信模板参数（json格式的字符串）
//		
//		
//		//传参数调用方法发送短信 SMS_159782310
//		send(phones,jsonObjectStringParams);
    	
    	
    	
    	  QuerySendDetailsResponse querySendDetailsResponse = querySendDetails("911319852106898434^0","13426406656");
          log.info("短信明细查询接口返回数据----------------");
          log.info("Code=" + querySendDetailsResponse.getCode());
          log.info("Message=" + querySendDetailsResponse.getMessage());
          int i = 0;
          for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
          {
              log.info("SmsSendDetailDTO["+i+"]:");
              log.info("Content=" + smsSendDetailDTO.getContent());
              log.info("ErrCode=" + smsSendDetailDTO.getErrCode());
              log.info("OutId=" + smsSendDetailDTO.getOutId());
              log.info("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
              log.info("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
              log.info("SendDate=" + smsSendDetailDTO.getSendDate());
              log.info("SendStatus=" + smsSendDetailDTO.getSendStatus());
              log.info("Template=" + smsSendDetailDTO.getTemplateCode());
          }
	}
} 